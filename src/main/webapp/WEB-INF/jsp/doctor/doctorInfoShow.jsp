<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>医生简介</title>
    <%
        /********** 保存网站的基本路径 ***********/
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + path + "/";
        //将该路径地址缓存到 session中 ,例如：http://localhost:8090/tjnu_ssh_1128/
        session.setAttribute("mybasePath", basePath);

    %>
    <!-- CSS -->
    <jsp:include page="../include/headtag.jsp"/>
    <!-- <link rel="stylesheet"
        href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500"> -->
    <link rel="stylesheet"
          href="${mybasePath}assets/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="${mybasePath}assets/css/form-elements.css">
    <link rel="stylesheet" href="${mybasePath}assets/css/style.css">
    <link rel="stylesheet" href="${mybasePath}assets/date/css/index.css">
    <script src="${mybasePath}assets/bootstrap/js/jquery.min.js"></script>
    <script src="${mybasePath}assets/date/js/index.js"></script>
</head>
<body>
<jsp:include page="../include/head.jsp"/>
<jsp:include page="../include/menu.jsp"/>
<div id="page-wrapper" style="margin-top: 50px;">
    <div id="page-inner">
        <div class="row">
            <div class="col-md-12">
                <h3 class="text-left">医生详情</h3>
            </div>
        </div>
        <hr/>
        <div class="col-md-12">
            <ul class="nav nav-tabs">
                <c:if test='${hos.isOpen=="1" }'>
                    <li class="active"><a href="#home" data-toggle="tab"
                                          aria-expanded="true">预约挂号</a></li>
                    <li class=""><a href="#orderNotice" data-toggle="tab"
                                    aria-expanded="false">预约须知</a></li>
                </c:if>
                <li class=""><a href="#about" data-toggle="tab"
                                aria-expanded="false">医生简介</a></li>
                <li class=""><a href="#doctorForte" data-toggle="tab"
                                aria-expanded="false">医院特长</a></li>
                <c:if test='${hos.isOpen=="1" }'>
                    <li class=""><a href="#comment" data-toggle="tab"
                                    aria-expanded="false">医院评论</a></li>
                </c:if>
            </ul>
            <div class="col-md-12">
                <div class="col-md-3">
                    <a href="<c:url value='/hosInfoShow/${hos.id}' />"><p class="text-left">${hos.hospitalName }
                    </p></a>
                </div>
                <div class="col-md-5">
                    <p class="text-right">
                        <font color="#eb6864">等级：</font>${hos.hospitalGrade }
                    </p>
                </div>
                <div class="col-md-2">
                    <p class="text-right">
                        <font color="#eb6864">区域：</font>${hos.hospitalArea }
                    </p>
                </div>
                <div class="col-md-2">
                    <p class="text-right">
                        <font color="#eb6864">类型：</font>${hos.hospitalNature }
                    </p>
                </div>
            </div>
            <hr/>
            <div class="col-md-12">
                <div class="col-md-3">
                    <img alt="请检查网络，图片加载出现了一点小问题" src="${doctor.doctorImg }"
                         style="width: 180px; height: 180px;">
                </div>
                <div class="col-md-4">
                    <p class="text-left">
                        <font color="#eb6864">医生姓名：</font>${doctor.doctorName }
                    </p>
                    <p class="text-left">
                        <font color="#eb6864">医生性别：</font>${doctor.doctorSex }
                    </p>
                    <p class="text-left">
                        <font color="#eb6864">医生科室：</font>${doctor.officesName }
                    </p>
                    <p class="text-left">
                        <font color="#eb6864">医院网址：<a href="${hos.hospitalUrl }"
                                                      target="_blank">${hos.hospitalUrl }</a></font>
                    </p>

                </div>
                <div class="col-md-5">
                    <p class="text-left">
                        <font color="#eb6864">行政职位：</font>${doctor.doctorAdministrative }
                    </p>
                    <p class="text-left">
                        <font color="#eb6864">学位：</font>${doctor.doctorDegree }
                    </p>
                    <p class="text-left">
                        <font color="#eb6864">医生职称：</font>${doctor.doctorTitle }
                    </p>
                    <p class="text-left">
                        <font color="#eb6864">教学职称：</font>${doctor.teachTitle }
                    </p>
                </div>
            </div>
            <hr/>
            <div id="myTabContent" class="tab-content">
                <c:if test='${hos.isOpen=="1" }'>
                    <div class="tab-pane fade active in text-left" id="home">
                        <div class="col-md-12">
                            <div style="border-bottom: 1px solid #ccc;"></div>
                            <br/>
                            <br/>
                        </div>
                        <hr/>
                        <form class="form-search form-horizontal" id="orderInfo"
                              action="<c:url value='/order' />" method="post">
                            <input type="hidden" id="orderInfoValue" name="orderInfoValue" value=""/>
                            <input type="hidden" id="hospitalName" name="hospitalName" value="${doctor.hospitalName }"/>
                            <input type="hidden" id="officesName" name="officesName" value="${doctor.officesName }"/>
                            <input type="hidden" id="doctorId" name="doctorId" value="${doctor.id }"/>
                            <input type="hidden" id="officeId" name="officeId" value="${doctor.officeId }"/>
                            <input type="hidden" id="hosId" name="hosId" value="${doctor.hosId }"/>
                            <input type="hidden" id="doctorName" name="doctorName" value="${doctor.doctorName }"/>
                            <input type="hidden" id="userIdenf" name="userIdenf" value="${userInfo.userIdenf }"/>
                            <input type="hidden" id="userEmail" name="userEmail" value="${userInfo.userEmail }"/>
                            <input type="hidden" id="doctorImg" name="doctorImg" value="${doctor.doctorImg }"/>
                        </form>
                        <div class="a" style="width: 800px;">

                        </div>
                    </div>
                    <div class="tab-pane fade text-left" id="orderNotice">
                        <div class="col-md-12 ">
                            <div style="border-bottom: 1px solid #ccc;"></div>
                            <br/>
                            <br/>
                        </div>

                        <p>
                            <br> <strong>电话预约挂号：022-022（24小时）<br>
                            网络预约挂号：http://www.sfturing.cn
                        </strong><br> <br>
                            根据卫生部8月5日通知和卫生局8月20日工作部署，医院名称已完成电话、网络预约挂号的流程建设，现将预约挂号、取号有关事项公布如下，请您认真阅读预约须知：<br>
                            <br> <strong>一、预约时间范围：</strong><br>
                            1、您可预约7天内（试点）日间的副教授、主治医师和住院医师等号源。节假日不安排预约号（含周六、周日）。<br>
                            2、每天早8:30分开始放号;下午14:00停止次日预约挂号。<br> 3、周五14:00停挂至下周一。<br>
                            <br> <strong>二、预约实名制：</strong><br>
                            统一平台电话预约和网上预约挂号均采取实名制注册预约，请您如实提供就诊人员的真实姓名、有效证件号（身份证、军官证、护照）、性别、电话、手机号码、病案号或协和就诊卡条形码上的ID号等有效基本信息。<br>
                            <br> <strong>三、预约取号：</strong><br>
                            1、预约成功后，请患者于就诊当日携带有效证件、预约识别码及协和医院就诊卡到医院挂号窗口验证预约信息（核对与预约登记实名信息一致的本人有效证件和预约识别码）和取号，如验证不符则医院不能提供相应的诊疗服务。如果没有协和医院就诊卡者，请先办好就诊卡后再取号。<br>
                            2、取号时间：上午就诊患者，就诊当日早9：00以前取号。下午就诊患者，就诊当日下午12:00-13:30之间取号。过时未取号者，预约作废。<br>
                            3、取号地点：${hos.hospitalAddress }<br> <br> <strong>四、医生停诊：</strong><br>
                            如遇特殊情况医生停诊，给您造成的不便敬请谅解。医生临时停诊，工作人员将会用电话方式及时通知您，请配合更改就诊日期或更换其他医生，请您保持电话畅通。<br>
                            <br> <strong>五、取消预约：</strong><br>
                            挂出的预约号如办理退号，至少在就诊前一工作日14:00前通过网站或者电话凭预约识别码进行取消。<br> <br>
                            <strong>六、爽约处理：</strong><br> 1、如预约成功后患者未能按时就诊且不办理取消预约号视为爽约。<br>
                            2、一年内（自然年）无故爽约累计达到3次的爽约用户将自动进入系统爽约名单，此后3个月内将取消其预约挂号资格；一年内（自然年）累计爽约6次，取消6个月的预约挂号资格。<br>
                            <br> <strong>七、其它注意事项：</strong><br> 1、注意事项一。<br>
                            2、注意事项二。<br> <br> <strong>交通指南：</strong><br>
                            乘车路线： ${hos.hospitalBusRoute }<br> <br> <br> <br>
                                ${hos.hospitalName }<br>注意：本站一切预约数据均为测试。
                        </p>
                    </div>
                </c:if>
                <div class="tab-pane fade text-left" id="about">
                    <div class="col-md-12 ">

                        <div style="border-bottom: 1px solid #ccc;"></div>
                        <br/>
                        <br/>
                    </div>
                    <hr/>
                    <p>&nbsp;&nbsp;${doctor.doctorAbout }</p>
                </div>
                <div class="tab-pane fade text-left" id="doctorForte">
                    <div class="col-md-12 ">
                        <div style="border-bottom: 1px solid #ccc;"></div>
                    </div>
                    <hr/>
                    <p>&nbsp;&nbsp;${doctor.doctorForte }</p>
                </div>
                <c:if test='${hos.isOpen=="1" }'>
                    <div class="tab-pane fade text-left" id="comment">
                        <div class="col-md-12 ">
                            <div style="border-bottom: 1px solid #ccc;"></div>
                        </div>
                            <form class="form-search form-horizontal" id="feedbackForm"
                                  action="/ssm_pro/commentInfo" method="post">
                                <input type="hidden" value="${userInfo.userId }" name="userId"
                                       id="userId">
                                <input type="hidden" value="${doctor.id }" name="doctorId"
                                       id="doctorId">
                                <input type="hidden" value="${userInfo.userName }" name="userName"
                                       id="userName">
                                <div class="col-lg-12">
                                    <label for="title">摘要</label><br/>
                                    <input type="text" name="title" id="title" style="border: 1px solid #cccccc"><br/>
                                    <label for="content">评论详细</label><br/>
                                    <textarea class="form-control" rows="3" id="content" name="content"
                                              style="margin: 0px -6.8375px 0px 0px; width: 100%; height: 150px; border: 1px solid #cccccc"
                                              placeholder="感谢您的评论" required></textarea>
                                </div>
                            </form>
                            <div class="col-lg-8 text-center" style="margin-top: 7px;">
                                <button onclick="feedback()" class="btn btn-primary btn-lg">提交评论</button>
                            </div>
                        <hr/>
                        <div class="panel-group col-lg-12" id="accordion" style="margin-top: 20px" >
                            <c:if test="${comments.size()==0}">
                                暂无评论
                            </c:if>
                            <c:forEach var="comment" items="${comments}" varStatus="status">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h4 class="panel-title">
                                            <a data-toggle="collapse" data-parent="#accordion"
                                               href="#collapse${status.index}">
                                                    ${comment.title}</a>
                                        </h4>
                                    </div>
                                    <div id="collapse${status.index}" class="panel-collapse collapse in">
                                        <div class="panel-body">
                                            <div>
                                                <span class="glyphicon glyphicon-user"></span>
                                                <span>${comment.userName}</span>
                                                <span class="summary-text small">${comment.ctime}</span>
                                            </div>
                                            <div>${comment.content}</div>
                                            <c:if test="${comment.answer!=null }">
                                                <div>
                                                    <span class="glyphicon glyphicon-user"></span>
                                                    <span>${doctor.doctorName}</span>
                                                    <span class="summary-text small">${comment.atime}</span>
                                                </div>
                                                <div>${comment.answer}</div>
                                            </c:if>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                    <%--                    <div class="tab-pane fade text-left" id="comment">--%>
                    <%--                        <div class="col-md-12 ">--%>
                    <%--                            <div style="border-bottom: 1px solid #ccc;"></div>--%>
                    <%--                        </div>--%>
                    <%--                        <hr/>--%>
                    <%--                        <table class="table table-striped table-hover text-left">--%>
                    <%--                            <thead>--%>
                    <%--                            <tr>--%>
                    <%--                                <th>#</th>--%>
                    <%--                                <th>评论名称</th>--%>
                    <%--                                <th>发布日期</th>--%>
                    <%--                            </tr>--%>
                    <%--                            </thead>--%>
                    <%--                            <tbody>--%>
                    <%--                            <c:forEach var="comment" items="${comments}" varStatus="status">--%>
                    <%--                                <tr>--%>
                    <%--                                    <td>${ status.index + 1}</td>--%>
                    <%--                                    <td><a href="<c:url value='/commentInfo/${comment.id}' />">${comment.title}</a>--%>
                    <%--                                    </td>--%>
                    <%--                                    <td>${comment.createTime }</td>--%>
                    <%--                                </tr>--%>
                    <%--                            </c:forEach>--%>
                    <%--                            </tbody>--%>
                    <%--                        </table>--%>
                    <%--                    </div>--%>
                </c:if>
            </div>
        </div>
    </div>
    <!-- /. PAGE INNER  -->
</div>
<!-- /. PAGE WRAPPER  -->

</body>
<!-- Javascript -->
<script src="${mybasePath}assets/bootstrap/js/bootstrap.min.js"></script>
<script src="${mybasePath}assets/js/jquery.backstretch.min.js"></script>
<script type="text/javascript">
    function feedback() {
        var userId = $("#userId").val();
        if (userId === "") {
            alert("请登录后评论！");
            return false;
        }else{
            if($("#title").val() === ""){
                alert("请填写摘要！");
                return false;
            } else if($("#content").val() === ""){
                alert("评论详细不能为空！");
                return false;
            } else {
                $.ajax({
                    type: "POST",//方法类型
                    dataType: "text",
                    url: "/ssm_pro/commentInfo" ,
                    data: $('#feedbackForm').serialize(),
                    success: function (html) {
                        document.getElementById("accordion").innerHTML=html;
                    },
                    error: function() {
                        alert("发生异常，请稍后再尝试评论！");
                    }
                });
            }
        }
        return false;
    }

    $("#idl dt").click(function () {
        var me = $(this);
        me.nextUntil("dt").toggle();
    });
</script>
</html>