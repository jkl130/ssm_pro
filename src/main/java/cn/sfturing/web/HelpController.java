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
import org.springframework.web.bind.annotation.ResponseBody;

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
    @ResponseBody
    @RequestMapping(value = "/commentInfo", method = RequestMethod.POST)
    public String comment(Model model,Comment comment) {
        comment.setCtime(DateUtil.getCurrentTime(DateUtil.DateFormat.YYYY_MM_DD_HH_mm_ss));
        commentDao.insertComment(comment);
        List<Comment> comments = commentDao.findByDoctorId(comment.getDoctorId());
        model.addAttribute("comments", comments);
        StringBuilder html = new StringBuilder();
        for (int i = 0; i < comments.size(); i++) {
            html.append("<div class=\"panel panel-default\"><div class=\"panel-heading\"><h4 class=\"panel-title\"><a data-toggle=\"collapse\" data-parent=\"#accordion\"href=\"#collapse").append(i).append("\">").append(comments.get(i).getTitle()).append("</a></h4></div><div id=\"collapse${status.index}\" class=\"panel-collapse collapse in\"><div class=\"panel-body\"><div><span class=\"glyphicon glyphicon-user\"></span><span>").append(comments.get(i).getUserName()).append("</span><span class=\"summary-text small\">").append(comments.get(i).getCtime()).append("</span></div><div>").append(comments.get(i).getContent()).append("</div>");
            if(comments.get(i).getAnswer()!=null){
                html.append("<div><span class=\"glyphicon glyphicon-user\"></span><span>${doctor.doctorName}</span><span class=\"summary-text small\">").append(comments.get(i).getAtime()).append("</span></div><div>").append(comments.get(i).getAnswer()).append("</div>");
            }
            html.append("</div></div></div>");
        }
        return html.toString();

    }
}
