package cn.sfturing.scheduler;

import cn.sfturing.dao.CommonUserDao;
import cn.sfturing.dao.DoctorDao;
import cn.sfturing.dao.OrderRecordsDao;
import cn.sfturing.entity.CommonUser;
import cn.sfturing.entity.Doctor;
import cn.sfturing.entity.OrderRecords;
import cn.sfturing.utils.MailUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class WorkerScheduler {

    @Autowired
    private OrderRecordsDao orderRecordsDao;
    @Autowired
    private CommonUserDao commonUserDao;
    @Autowired
    private DoctorDao doctorDao;
    @Autowired
    private MailUtil mailUtil;
    private static Logger log = LoggerFactory.getLogger(WorkerScheduler.class);

    @Scheduled(cron = "0 0/2 * * * ?")
    public void createWorkTime() {
        // 查找需要发送通知的订单
        final List<OrderRecords> orderRecords = orderRecordsDao.findNeedNoticeOrder();
        // 循环订单
        for (int i = 0; i < orderRecords.size(); i++) {
            final CommonUser commonUser = commonUserDao.findCommonUserByUserId(orderRecords.get(i).getUserID());
            final OrderRecords orderRecordInfo = orderRecords.get(i);
            // 单独开启线程发送邮件，防止用户等待时间过长，成功日志输出，失败也输出。
            new Thread(new Runnable() {
                public void run() {
                    boolean isSuccess = sendEmailCheck(commonUser, orderRecordInfo);
                    if (isSuccess) {
                        log.info(commonUser.getUserEmail() + "发送成功");

                    } else {
                        log.info(commonUser.getUserEmail() + "发送失败");
                    }
                }
            }).start();
        }

    }

    public boolean sendEmailCheck(CommonUser commonUser, OrderRecords orderRecords) {
        Doctor doctor = doctorDao.findDoctorById(orderRecords.getDoctorId());
        if (mailUtil.sendMail(commonUser.getUserEmail(), "医院预约系统", "医院预约挂号【预约通知提醒】", "预约挂号成功！请及时在就诊时间前往医院，并在自助机上打印预约凭条，凭预约凭条到诊室报道候诊"  +
                "<br>就诊人：\t" +
                commonUser.getUserName() +
                "<br>科室医生：\t" +
                doctor.getOfficesName() +
                " " +
                doctor.getDoctorName() +
                "<br>挂号金额：\t" +
                50 +
                "<br>就诊时间：\t" +
                orderRecords.getTransactDate() +
                " " +
                orderRecords.getTransactTime() +
                "<br>已在线支付：\t" +
                (orderRecords.getIsSuccess() == 1 ? "是" : "否"))) {
            orderRecordsDao.updateSendSuccess(orderRecords.getId());
            return true;
        } else {
            orderRecordsDao.updateSendFailed(orderRecords.getId());
            return false;
        }
    }
}
