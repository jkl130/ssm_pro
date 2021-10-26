<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>医院列表</title>
<!-- CSS -->
<jsp:include page="../include/headtag.jsp" />
<!-- <link rel="stylesheet"
	href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500"> -->
<link rel="stylesheet"
	href="assets/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="assets/css/form-elements.css">
<link rel="stylesheet" href="assets/css/style.css">
</head>
<body>
	<jsp:include page="../include/head.jsp" />
	<jsp:include page="../include/menu.jsp" />
	<div id="page-wrapper" style="margin-top: 50px;">
		<div id="page-inner">
			<div class="row">
				<div class="col-md-12">
					<p class="text-left">推荐医院列表</p>
					<div class="col-md-12">
						<div class="nav navbar-nav navbar-right">
							<a href="/ssm_pro/orderHos/1">查看更多可预约医院</a>
						</div>
					</div>
				</div>
			</div>
			<hr />
			<br /> <br />
			<c:forEach var="hos" items="${hospital }" varStatus="status">
				<%-- <c:forEach var="i" begin="1" end="9" varStatus="status"> --%>
				<div class="col-md-3">
					<div class="thumbnail" style="width: 300px; height: 400px;">
						<div>
							<img alt="请检查网络，图片加载出现了一点小问题" src="${hos.hospitalImg}"
								height="200" width="200" />
						</div>
						<div class="caption">
							<p>${hos.hospitalName}</p>
							<%-- <p>
								<span class="glyphicon glyphicon-earphone "></span>&nbsp;&nbsp;:&nbsp;${hos.hospitalPhone}
							</p> --%>
							<p>
								<span class="glyphicon glyphicon-home"></span>&nbsp;${hos.hospitalAddress}
							</p>
							<p>

								<c:if test='${hos.isOpen=="1" }'>
								<a class="btn btn-primary" href="<c:url value='/hosInfoShow/${hos.id}' />">现在预约</a>
								</c:if>
								<c:if test='${hos.isOpen=="0" }'>
								<a class="btn btn-default disabled" href="#">未开通</a>
								<a class="btn " href="<c:url value='/hosInfoShow/${hos.id}' />">详细信息</a>
								</c:if>

							</p>
						</div>
					</div>
				</div>

			</c:forEach>
		</div>
		<!-- /. PAGE INNER  -->
	</div>
	<!-- /. PAGE WRAPPER  -->

</body>
<!-- Javascript -->
<script src="assets/bootstrap/js/bootstrap.min.js"></script>
<script src="assets/bootstrap/js/jquery.min.js"></script>
<script src="assets/js/jquery.backstretch.min.js"></script>
</html>