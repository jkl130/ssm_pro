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

    /**
     * 定时任务 每两分钟执行一次
     */
    @Scheduled(cron = "0 0/2 * * * ?")
    public void createWorkTime() {
        // 1 查找需要发送通知的订单(未取消、发送且预约状态码为1的订单)
        final List<OrderRecords> orderRecords = orderRecordsDao.findNeedNoticeOrder();
        // 2 循环订单
        for (OrderRecords orderRecord : orderRecords) {
            // 2.1 根据订单中的userId查找用户信息
            final CommonUser commonUser = commonUserDao.findCommonUserByUserId(orderRecord.getUserID());
            final OrderRecords orderRecordInfo = orderRecord;
            // 单独开启线程发送邮件，防止用户等待时间过长，成功日志输出，失败也输出。
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // 2.2 发送邮件并打印是否成功
                    log.info("{} {}", commonUser.getUserEmail(), sendEmailCheck(commonUser, orderRecordInfo) ? "发送成功" : "发送失败");
                }
            }).start();
        }

    }

    public boolean sendEmailCheck(CommonUser commonUser, OrderRecords orderRecords) {
        // 1 根据订单中的doctorId找到医生信息
        Doctor doctor = doctorDao.findDoctorById(orderRecords.getDoctorId());

        // 2 发送邮件
        if (mailUtil.sendMail(commonUser.getUserEmail(),
                "医院预约系统",
                "医院预约挂号【预约通知提醒】",
                "预约挂号成功！请及时在就诊时间前往医院，并在自助机上打印预约凭条，凭预约凭条到诊室报道候诊" +
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
            // 2.1 发送成功更新is_send=1表示已发送
            orderRecordsDao.updateSendSuccess(orderRecords.getId());
            return true;
        } else {
            // 2.2 发送失败更新is_send=2表示已发送但出错
            orderRecordsDao.updateSendFailed(orderRecords.getId());
            return false;
        }
    }
}
