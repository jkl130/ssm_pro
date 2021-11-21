package cn.sfturing.web;

import cn.sfturing.entity.Hospital;
import cn.sfturing.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author sfturing
 * @date 2017年5月6日
 */
@Controller
public class BaseController {

    @Autowired
    private HospitalService hospitalService;

    /**
     * 用户主页
     *
     * @return
     */
    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String indexOf(Model model) {
        List<Hospital> hosByRe = hospitalService.findHosByRe();
        if (!hosByRe.isEmpty()) {
            model.addAttribute("hospital", hosByRe.get(0));
        }

        return "index/index";
    }

    /**
     * 用户联系我们
     *
     * @return
     */
    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public String contact() {
        return "contact/contactIndex";
    }

    /**
     * 用户联系我们
     *
     * @return
     */
    @RequestMapping(value = "/logOff", method = RequestMethod.GET)
    public String logOff(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }


}
