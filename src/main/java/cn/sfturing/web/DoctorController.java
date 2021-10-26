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
        List<OrderRecords> orderLists = OrderService.findOrderRecordsByDoctorID(id);
        List<OrderDateAndTime> date = OrderService.findOrderRecordsDateByDoctorID(id);
        for (int m = 0; m < date.size(); m++) {
            for (int n = 0; n < orderLists.size(); n++) {
                if (orderLists.get(n).getTransactDate().equals(date.get(m).getTransactDate())) {
                    if (orderLists.get(n).getTransactTime().equals("8:00-11:00")) {
                        date.get(m).setS(false);
                    }
                    if (orderLists.get(n).getTransactTime().equals("13:00-15:00")) {
                        date.get(m).setZ(false);
                    }
                    if (orderLists.get(n).getTransactTime().equals("15:00-18:00")) {
                        date.get(m).setX(false);
                    }
                }
            }
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
