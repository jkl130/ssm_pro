package cn.sfturing.web;

import cn.sfturing.dao.FavouriteDao;
import cn.sfturing.dao.OrderRecordsDao;
import cn.sfturing.entity.CommonUser;
import cn.sfturing.entity.Favourite;
import cn.sfturing.entity.Hospital;
import cn.sfturing.entity.OrderRecords;
import cn.sfturing.service.CommonUserService;
import cn.sfturing.service.HospitalService;
import cn.sfturing.service.OrderRecordsService;
import cn.sfturing.utils.AlipayBean;
import cn.sfturing.utils.AlipayUtil;
import com.alipay.api.AlipayApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author sfturing
 * @date 2017年6月2日
 */
@Controller
public class OrderController {

    @Autowired
    private CommonUserService commnUserService;
    @Autowired
    private OrderRecordsDao orderRecordsDao;
    @Autowired
    private OrderRecordsService orderRecordsService;
    @Autowired
    private FavouriteDao favouriteDao;
    @Autowired
    private HospitalService hospitalService;
    @Autowired
    private AlipayUtil alipayUtil;

    private static Logger log = LoggerFactory.getLogger(OrderController.class);

    /**
     * 用户预约首页
     *
     * @return
     */
    @RequestMapping(value = "/orderIndex", method = RequestMethod.GET)
    public String orderIndex() {

        return "order/orderInfo";
    }

    /**
     * 用户预约
     *
     * @return
     */
    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public String order(String orderInfoValue, String hospitalName, String officesName, String doctorName,
                        String userIdenf, Model model, String doctorImg, Integer doctorId, Integer hosId, Integer officeId) {
        // 分解传入的时间以及时间段
        String orderInfoValueArry[] = orderInfoValue.split(",");
        String transact_date = orderInfoValueArry[0];
        String transact_time = orderInfoValueArry[1];
        CommonUser commonUser = commnUserService.findCommonUserByUserIdenf(userIdenf);
        OrderRecords orderRecords = new OrderRecords();
        // 设置orderRecords
        orderRecords.setUserID(commonUser.getUserId());
        orderRecords.setDoctorId(doctorId);
        orderRecords.setTransactTime(transact_time);
        orderRecords.setTransactDate(transact_date);
        orderRecords.setHospitalName(hospitalName);
        orderRecords.setOfficesName(officesName);
        orderRecords.setDoctorName(doctorName);
        orderRecords.setOfficeId(officeId);
        orderRecords.setHosId(hosId);
        // 设置预约识别码 0代表只选择时间段，未提交订单
        orderRecords.setOrderVer(0);
        // 插入订单
        orderRecordsService.insertOrderRecords(orderRecords);
        model.addAttribute("orderRecords", orderRecords);
        model.addAttribute("commonUser", commonUser);
        model.addAttribute("doctorImg", doctorImg);
        return "order/orderInfo";
    }

    @RequestMapping(value = "/alipay", method = RequestMethod.POST)
    public void alipay(int id, HttpServletResponse response) throws Exception {
        if (orderRecordsDao.findOrderById(id).getIsSuccess() == 1) {
            response.sendRedirect("/ssm_pro/alipayDone?out_trade_no=" + id);
            return;
        }
        AlipayBean alipayBean = new AlipayBean();
        alipayBean.setSubject("挂号费");
        alipayBean.setOut_trade_no(String.valueOf(id));
        alipayUtil.connect(alipayBean, response);
    }

    @RequestMapping(value = "/alipaySuccess", method = RequestMethod.POST)
    public void alipaySuccess(@RequestBody AlipayBean alipayBean) {
        log.info("支付成功");
        orderRecordsDao.updateOrderSuc1(Integer.parseInt(alipayBean.getOut_trade_no()));
    }

    @RequestMapping(value = "/alipayDone", method = RequestMethod.GET)
    public String alipayDone(String out_trade_no) {
        log.info("支付完毕");
        AlipayBean alipayBean = new AlipayBean();
        alipayBean.setOut_trade_no(out_trade_no);
        if (alipayUtil.query(alipayBean)) {
            orderRecordsDao.updateOrderSuc1(Integer.parseInt(out_trade_no));
            return "order/orderPayDone";
        }
        return "order/orderPayFailed";
    }

    @RequestMapping(value = "/payDone/{id}", method = RequestMethod.POST)
    public String payDone(Model model, @PathVariable(value = "id") int id, HttpSession session) throws AlipayApiException {
        if (!alipay(id)) {
            return "order/orderPayFailed";
        }

        return getResult(model, session);
    }

    private String getResult(Model model, HttpSession session) {
        CommonUser commonUser = (CommonUser) session.getAttribute("userInfo");
        if (commonUser != null) {
            // 得到用户的收藏记录
            List<Favourite> favourites = favouriteDao.findFavHos(commonUser.getUserId());
            List<Hospital> hospitals = null;
            if (favourites.size() != 0) {
                hospitals = hospitalService.findFavHos(favourites);
            }
            model.addAttribute("hospitals", hospitals);
            // 得到用户的个人订单
            List<OrderRecords> orderRecords = orderRecordsService.findOrderRecordsByUserID(commonUser.getUserId());
            model.addAttribute("orderRecords", orderRecords);
            model.addAttribute("commonUser", commonUser);
            return "userCenter/userCenter";
        }
        return "userCenter/userCenter";
    }

    /**
     * 提交订单
     *
     * @return
     */
    @RequestMapping(value = "/orderUserCenter", method = RequestMethod.POST)
    public String orderUserCenter(Model model, int userID, int id, String diseaseInfo, String optionsRadios, HttpSession session) {
        if (!alipay(id) && "2".equals(optionsRadios)) {
            // 在线支付且失败, 返回支付错误页面
            return "order/orderPayFailed";
        }

        // 得到用户的收藏记录
        List<Favourite> favourites = favouriteDao.findFavHos(userID);
        List<Hospital> hospitals = null;
        if (favourites.size() != 0) {
            hospitals = hospitalService.findFavHos(favourites);
        }
        model.addAttribute("hospitals", hospitals);
        // 得到用户的信息
        CommonUser commonUser = (CommonUser) session.getAttribute("userInfo");
        model.addAttribute("commonUser", commonUser);
        orderRecordsService.updateOrderSta1(id);
        orderRecordsService.updateOrderdiseaseInfo(diseaseInfo, id);
        // 通过userId查询所有订单，为个人中心提供
        List<OrderRecords> orderRecords = orderRecordsService.findOrderRecordsByUserID(userID);
        log.info("插入订单，已提交订单！");
        model.addAttribute("orderRecords", orderRecords);
        return "userCenter/userCenter";

    }

    private boolean alipay(int id) {
        OrderRecords orderById = orderRecordsDao.findOrderById(id);
        if (orderById.getIsSuccess() != 1) {
            AlipayBean alipayBean = new AlipayBean();
            alipayBean.setOut_trade_no(String.valueOf(id));
            if (!alipayUtil.query(alipayBean)) {
                // 在线支付, 确认订单状况
                return false;

            }
            orderRecordsDao.updateOrderSuc1(id);
        }
        return true;
    }

    /**
     * 取消订单
     */
    @RequestMapping(value = "/cancelOrder/{id}", method = RequestMethod.POST)
    public String cancelOrder(Model model, @PathVariable(value = "id") int id, HttpSession session) {
        // 通过id更改订单状态
        orderRecordsService.cancelOrder(id);
        return getResult(model, session);
    }

}
