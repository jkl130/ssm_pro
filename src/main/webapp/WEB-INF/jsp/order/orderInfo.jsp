<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>预约挂号</title>
    <!-- CSS -->
    <jsp:include page="../include/headtag.jsp"/>
    <!-- <link rel="stylesheet"
        href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500"> -->
    <link rel="stylesheet"
          href="${mybasePath}assets/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="${mybasePath}assets/css/form-elements.css">
    <link rel="stylesheet" href="${mybasePath}assets/css/style.css">
</head>
<body>
<jsp:include page="../include/head.jsp"/>
<jsp:include page="../include/menu.jsp"/>
<div id="page-wrapper" style="margin-top: 50px;">

    <div id="page-inner">
        <div class="row">
            <div class="col-md-12">
                <h3 class="text-left">预约挂号</h3>
            </div>
        </div>
        <hr/>
        <div class="panel panel-primary text-left">
            <div class="panel-heading">
                <h3 class="panel-title">医生信息</h3>
            </div>
            <div class="panel-body">
                <div class="col-md-5">
                    <div class="col-md-3">
                        <img alt="图片加载出现了一些问题" src="${doctorImg }">
                    </div>
                    <div class="col-md-9">
                        <p>医生姓名：${orderRecords.doctorName }</p>
                        <p>医院名称：${orderRecords.hospitalName }</p>
                        <p>科室名称：${orderRecords.officesName }</p>
                    </div>
                </div>
                <div class="col-md-7">
                    <p>
                        <span style="color: #eb6864; ">门诊类型：普通</span>
                    </p>
                    <p>
                        <span style="color: #eb6864; ">费用：50元</span>
                    </p>
                </div>
            </div>

        </div>
        <div class="panel panel-primary text-left">
            <div class="panel-heading">
                <h3 class="panel-title">时间信息</h3>
            </div>
            <div class="panel-body">
                <div class="col-md-12">
                    <div class="col-md-2">
                        <h3>就诊日期：${orderRecords.transactDate }</h3>
                    </div>
                </div>
                <div class="col-md-12">
                    <div class="col-md-10">
                        <h3>就诊时间：${orderRecords.transactTime }</h3>
                    </div>
                </div>
            </div>
        </div>
        <div class="panel panel-primary text-left">
            <div class="panel-heading">
                <h3 class="panel-title">就诊人信息</h3>
            </div>
            <div class="panel-body">
                <div class="col-md-12">
                    <div class="col-md-1">
                        <h3>姓名：${commonUser.userName }&nbsp;</h3>
                    </div>
                    <div class="col-md-1">
                        <h3>性别：${commonUser.userSex }&nbsp;</h3>
                    </div>
                    <div class="col-md-4">
                        <h3>&nbsp;身份证：${commonUser.userIdenf }</h3>
                    </div>
                    <!-- <div class="col-md-2">
                        <h3>&nbsp;1996-10-14</h3>
                    </div> -->
                    <div class="col-md-2">
                        <h3>&nbsp;电话：${commonUser.userMobile }</h3>
                    </div>
                </div>
                <div class="col-md-12">
                    <div class="col-md-10">
                        <h3>用户邮箱：${commonUser.userEmail }</h3>
                        <div class="alert alert-dismissible alert-warning">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <strong>请注意!</strong> 用户邮箱将作为通知依据 ，就诊人信息本页面不可更改.（请于个人中心处修改个人信息）
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <form class="form-search form-horizontal" id="orderInfo" method="post">
            <div class="panel panel-primary text-left">
                <div class="panel-heading">
                    <h3 class="panel-title">疾病信息</h3>
                </div>
                <div class="panel-body">
                    <input type="hidden" value="${orderRecords.userID }" name="userID"
                           id="userID"> <input type="hidden"
                                               value="${orderRecords.id }" name="id" id="id">

                    <div class="form-group">
                        <label for="diseaseInfo" class="col-lg-1 control-label">疾病信息</label>
                        <div class="col-lg-10">
								<textarea class="form-control" rows="3" id="diseaseInfo" name="diseaseInfo"
                                          style="margin: 0px -6.8375px 0px 0px; width: 424px; height: 80px;"
                                          placeholder="请填写病史，症状，发病时间，接受过的治疗等信息，提前告知医生您的病情，有助于医生对您的诊疗"></textarea>
                        </div>
                    </div>
                </div>
            </div>
            <div class="panel panel-primary text-left">
                <div class="panel-heading">
                    <h3 class="panel-title">支付方式</h3>
                </div>
                <div class="panel-body">
                    <div class="form-group">
                        <div class="col-lg-10">
                            <div class="radio">
                                <label> <input type="radio" name="optionsRadios"
                                               id="optionsRadios2" value="1"
                                               style="width: 20px; height: 20px" checked="">
                                    &nbsp;现场支付(可使用医保，预约后请到医院现场缴费取号。)
                                </label>
                            </div>
                            <div class="radio">
                                <label> <input type="radio" name="optionsRadios"
                                               id="optionsRadios1" value="2"
                                               style="width: 20px; height: 20px">&nbsp;在线支付
                                </label>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
        <div class="text-left">
            <label><input id="check_yuyue_rule" type="checkbox"
                          name="check_yuyue_rule" value="1"
                          style="border: none; margin: 4px 5px 0 0;" class="must_choose fl">
                我已经确认我的个人信息</label>
        </div>
        <div class="text-left">
            <button onclick="submitOrder()" class="btn btn-primary btn-lg"
                    id="submitOrder">提交订单
            </button>
        </div>
        <div class="modal fade" id="myModal"
             tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
             aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"
                                aria-hidden="true">×
                        </button>
                        <h3 id="myModalLabel">支付结果确认</h3>
                    </div>
                    <div class="modal-body">
                        <p class="error-text">
                            <i class="icon-warning-sign modal-icon"></i>
                            是否已完成支付?
                        </p>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-primary" data-dismiss="modal"
                                aria-hidden="true">否
                        </button>
                        <button class="btn btn-primary" id="submit" name="submit"
                                onclick="payDone();">是
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- /. PAGE INNER  -->
</div>
<!-- /. PAGE WRAPPER  -->

</body>
<!-- Javascript -->
<script src="${mybasePath}assets/js/jquery.backstretch.min.js"></script>
<script type="text/javascript">
    function payDone() {
        var $orderInfo = $("#orderInfo");
        $orderInfo.attr("target", "_self");
        $orderInfo.attr("action", "<c:url value='/orderUserCenter'/>");
        $orderInfo.submit();
        return false;
    }

    function submitOrder() {
        var $check_yuyue_rule = $("#check_yuyue_rule");
        var $orderInfo = $("#orderInfo");
        if ($check_yuyue_rule.is(":checked")) {
            if ($("#optionsRadios1").is(":checked")) {
                $orderInfo.attr("target", "_blank");
                $orderInfo.attr("action", "<c:url value='/alipay'/>");
                $orderInfo.submit();
                $('#myModal').modal('show');
                return false;
            }
            $orderInfo.attr("target", "_self");
            $orderInfo.attr("action", "<c:url value='/orderUserCenter'/>");
            $orderInfo.submit();
            return false;
        }
        alert("请确认您的基本信息！")
    }
</script>
</html>