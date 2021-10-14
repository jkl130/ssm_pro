package cn.sfturing.entity;

import cn.sfturing.utils.DateUtil;

/**
 * @author anjsh
 * @date 2021/10/13 14:25
 */
public class Comment {

    private int id;

    private int doctorId;

    private int userId;

    private String title;

    private String content;

    private String answer;

    private String createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getCreateTime() {
        return DateUtil.getFormatTime(createTime, DateUtil.DateFormat.YYYY_MM_DD_HH_mm_ss);
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
