package cn.sfturing.web;

import cn.sfturing.dao.CommentDao;
import cn.sfturing.dao.FeedBackDao;
import cn.sfturing.entity.Comment;
import cn.sfturing.entity.FeedBack;
import cn.sfturing.entity.HelpQA;
import cn.sfturing.service.HelpQAService;
import cn.sfturing.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author sfturing
 * @date 2017年6月1日
 */
@Controller
public class HelpController {
    @Autowired
    private HelpQAService helpQAService;
    @Autowired
    private FeedBackDao feedBackDao;

    @Autowired
    private CommentDao commentDao;

    /**
     * 用户帮助首页
     *
     * @return
     */
    @RequestMapping(value = "/help", method = RequestMethod.GET)
    public String helpIndex(Model model) {
        List<HelpQA> commonQA = helpQAService.findQAByType("常见问题");
        List<HelpQA> signQA = helpQAService.findQAByType("如何注册");
        List<HelpQA> accountQA = helpQAService.findQAByType("帐号管理");
        model.addAttribute("commonQA", commonQA);
        model.addAttribute("signQA", signQA);
        model.addAttribute("accountQA", accountQA);
        return "help/helpIndex";
    }

    /**
     * 意见反馈首页
     *
     * @return
     */
    @RequestMapping(value = "/feedBack", method = RequestMethod.GET)
    public String feedBack() {
        return "feedBack/feedBack";

    }

    //意见反馈
    @RequestMapping(value = "/feedBackInfo", method = RequestMethod.POST)
    public String feedBack(FeedBack feedBack) {
        feedBackDao.inserFeedBack(feedBack);
        return "feedBack/thanksFeedBack";

    }

    //意见反馈
    @RequestMapping(value = "/commentInfo", method = RequestMethod.POST)
    public String comment(Comment comment) {
        comment.setCtime(DateUtil.getCurrentTime(DateUtil.DateFormat.YYYY_MM_DD_HH_mm_ss));
        commentDao.insertComment(comment);
        return "doctor/thanksComment";

    }
}
