package cn.sfturing.web;

import cn.sfturing.dao.CommentDao;
import cn.sfturing.entity.*;
import cn.sfturing.service.DoctorService;
import cn.sfturing.service.HospitalService;
import cn.sfturing.service.OrderRecordsService;
import cn.sfturing.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sfturing
 * @date 2017年5月31日
 */
@Controller
public class DoctorController {
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private HospitalService hospitalService;
    @Autowired
    private PageUtils pageUtils;
    @Autowired
    private OrderRecordsService OrderService;

    @Autowired
    private CommentDao commentDao;

    /**
     * 医生主界面(推荐医生)
     *
     * @return
     */
    @RequestMapping(value = "/doctorIndex/{page}")
    public String officeIdex(Model model, @PathVariable("page") int page) {
        // 查询推荐的医院
        List<Hospital> hospitalRe = hospitalService.findHosByRe();
        // 设置页面
        pageUtils.setCurrentPage(page);
        pageUtils.setTotalRecord(doctorService.findDoctorByReNum(hospitalRe));
        int start;
        if (pageUtils.getCurrentPage() == 0) {
            start = 0;
        } else {
            start = pageUtils.getPageRecord() * (pageUtils.getCurrentPage() - 1);
        }
        Map<String, Object> doctorMap = new HashMap<String, Object>();
        doctorMap.put("list", hospitalRe);
        doctorMap.put("start", start);
        doctorMap.put("size", pageUtils.getPageRecord());
        List<Doctor> doctorRe = doctorService.findDoctorByRe(doctorMap);
        model.addAttribute("pages", pageUtils);
        model.addAttribute("doctorRe", doctorRe);
        return "doctor/doctorIndex";
    }

    /**
     * 医生详情
     *
     * @return
     */
    @RequestMapping(value = "/doctorInfoShow/{id}", method = RequestMethod.GET)
    public String hosInfoShow(Model model, @PathVariable(value = "id") int id) {
        Doctor doctor = doctorService.findDoctorById(id);
        Hospital hospital = hospitalService.findHosById(doctor.getHosId());
        List<Comment> comments = commentDao.findByDoctorId(id);
        model.addAttribute("hos", hospital);
        model.addAttribute("doctor", doctor);
        model.addAttribute("comments", comments);

        return "doctor/doctorInfoShow";
    }

    @ResponseBody
    @RequestMapping(value = "/doctorOrderShow/{id}", method = RequestMethod.GET)
    public List<OrderDateAndTime> hosOrderInfoShow(@PathVariable(value = "id") int id) {
        // 1 根据医生id查询订单记录
        List<OrderRecords> orderList = OrderService.findOrderRecordsByDoctorID(id);
        // 2 创建一个订单记录大小的list用来存放当天每个时间段的预约情况
        List<OrderDateAndTime> date = new ArrayList<>(orderList.size());

        // 3 遍历所有订单信息
        for (OrderRecords orderRecords : orderList) {
            // 3.1 创建预约时间信息对象
            OrderDateAndTime orderDateAndTime = new OrderDateAndTime(orderRecords.getTransactDate());
            String transactTime = orderRecords.getTransactTime();
            if (transactTime.equals("8:00-11:00")) {
                // 3.2 如果订单的预约时间段为该时间段, 就将该时间段设置为不可预约
                orderDateAndTime.setS(false);
            }
            if (transactTime.equals("13:00-15:00")) {
                orderDateAndTime.setZ(false);
            }
            if (transactTime.equals("15:00-18:00")) {
                orderDateAndTime.setX(false);
            }
            // 3.3 将对象放入list中
            date.add(orderDateAndTime);
        }
        return date;
    }

    /**
     * 全部医生
     *
     * @return
     */
    @RequestMapping(value = "/allDoctor/{page}")
    public String orderOffcie(Model model, @PathVariable("page") int page, Doctor doctor) {
        // 将输入条件传回前台
        CommonCondition commonCondition = new CommonCondition();
        commonCondition.setHospitalName(doctor.getHospitalName());
        commonCondition.setOfficesName(doctor.getOfficesName());
        commonCondition.setDoctorName(doctor.getDoctorName());
        commonCondition.setDoctorTitle(doctor.getDoctorTitle());
        commonCondition.setDoctorDegree(doctor.getDoctorDegree());
        commonCondition.setDoctorAdministrative(doctor.getDoctorAdministrative());
        pageUtils.setCurrentPage(page);
        pageUtils.setTotalRecord(doctorService.findDoctorNum(doctor));
        int start;
        if (pageUtils.getCurrentPage() == 0) {
            start = 0;
        } else {
            start = pageUtils.getPageRecord() * (pageUtils.getCurrentPage() - 1);
        }
        List<Doctor> doctorRe = doctorService.findDoctorByCondition(doctor, start, pageUtils.getPageRecord());
        // 查询医生的职位
        List<String> doctorTitle = doctorService.findDoctorTitle();
        List<String> doctorAdministrative = doctorService.findDoctorAdministrative();
        List<String> doctorDegree = doctorService.findDoctorDegree();
        model.addAttribute("pages", pageUtils);
        model.addAttribute("doctorRe", doctorRe);
        // 查询条件
        model.addAttribute("commonCondition", commonCondition);
        // 将查询的医生职称传到前台
        model.addAttribute("doctorTitle", doctorTitle);
        model.addAttribute("doctorAdministrative", doctorAdministrative);
        model.addAttribute("doctorDegree", doctorDegree);
        return "doctor/doctor";
    }

}
