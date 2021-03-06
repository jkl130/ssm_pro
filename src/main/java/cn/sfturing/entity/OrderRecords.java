package cn.sfturing.entity;

import cn.sfturing.utils.DateUtil;

/**
 * @author sfturing
 * @date 2017年5月22日
 */
public class OrderRecords {
    //预约id
    private int id;
    //用户ID
    private int UserID;
    //预约医院名称
    private String hospitalName;
    //预约科室名称
    private String officesName;
    //医生姓名
    private String doctorName;
	//医生Id
	private int doctorId;
    //预约日期
    private String transactDate;
    //预约时间段
    private String transactTime;
    //疾病信息
    private String diseaseInfo;
    //是否成功
    private int isSuccess;
    //是否发送邮件
    private int isSend;
    //是否取消
    private int isCancel;
    //是否完成订单
    private int isFinish;
    //预约识别码
    private int orderVer;
    //创建预约时间
    private String createTime;

    private String aliTradeNo;
    private int hosId;

    private int officeId;

    public int getOfficeId() {
        return officeId;
    }

    public void setOfficeId(int officeId) {
        this.officeId = officeId;
    }

    public int getHosId() {
        return hosId;
    }

    public void setHosId(int hosId) {
        this.hosId = hosId;
    }

    public String getAliTradeNo() {
        return aliTradeNo;
    }

    public void setAliTradeNo(String aliTradeNo) {
        this.aliTradeNo = aliTradeNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getOfficesName() {
        return officesName;
    }

    public void setOfficesName(String officesName) {
        this.officesName = officesName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getTransactDate() {
        return transactDate;
    }

    public void setTransactDate(String transactDate) {
        this.transactDate = transactDate;
    }

    public String getTransactTime() {
        return transactTime;
    }

    public void setTransactTime(String transactTime) {
        this.transactTime = transactTime;
    }

    public String getDiseaseInfo() {
        return diseaseInfo;
    }

    public void setDiseaseInfo(String diseaseInfo) {
        this.diseaseInfo = diseaseInfo;
    }

    public int getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(int isSuccess) {
        this.isSuccess = isSuccess;
    }

    public int getIsSend() {
        return isSend;
    }

    public void setIsSend(int isSend) {
        this.isSend = isSend;
    }

    public int getIsCancel() {
        return isCancel;
    }

    public void setIsCancel(int isCancel) {
        this.isCancel = isCancel;
    }

    public int getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(int isFinish) {
        this.isFinish = isFinish;
    }

    public int getOrderVer() {
        return orderVer;
    }

    public void setOrderVer(int orderVer) {
        this.orderVer = orderVer;
    }

    public String getCreateTime() {
        return DateUtil.getFormatTime(createTime, DateUtil.DateFormat.YYYY_MM_DD_HH_mm_ss);
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
	public int getDoctorId() { return doctorId;}
	public void setDoctorId(int doctorId) { this.doctorId = doctorId; }
	@Override
	public String toString() {
		return "OrderRecords [id=" + id + ", UserID=" + UserID + ", hospitalName=" + hospitalName + ", officesName="
				+ officesName + ", doctorId =" + doctorId + ",doctorName=" + doctorName + ", transactDate=" + transactDate + ", transactTime="
				+ transactTime + ", diseaseInfo=" + diseaseInfo + ", isSuccess=" + isSuccess + ", isSend=" + isSend
				+ ", isCancel=" + isCancel + ", isFinish=" + isFinish + ", orderVer=" + orderVer + ", createTime="
				+ createTime + "]";
	}


}
