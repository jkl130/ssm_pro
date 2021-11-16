<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>医院预约挂号</title>
<%
	/********** 保存网站的基本路径 ***********/
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	//将该路径地址缓存到 session中 ,例如：http://localhost:8090/tjnu_ssh_1128/
	session.setAttribute("mybasePath", basePath);
%>
<!-- CSS -->
<jsp:include page="WEB-INF/jsp/include/headtag.jsp" />
<!-- <link rel="stylesheet"
	href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500"> -->
<link rel="stylesheet"
	href="${mybasePath}assets/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="${mybasePath}assets/css/form-elements.css">
<link rel="stylesheet" href="${mybasePath}assets/css/style.css">
</head>
<body>
	<jsp:include page="WEB-INF/jsp/include/head.jsp" />
	<jsp:include page="WEB-INF/jsp/include/menu.jsp" />
	<div id="page-wrapper" style="margin-top: 50px;">
		<div id="page-inner">
			<div class="row">
				<div class="col-md-12 text-left">
					<h3>404</h3>
				</div>
			</div>
			<!-- /. ROW  -->
			<hr />
			<div class="col-md-12 text-left">
				<p>你访问了一个不存在的页面，<a href="/ssm_pro/index">点此</a>返回主页</p>
			</div>

		</div>
		<!-- /. PAGE INNER  -->

	</div>
	<!-- /. PAGE WRAPPER  -->

</body>
<!-- Javascript -->
<script src="${mybasePath}assets/bootstrap/js/bootstrap.min.js"></script>
<script src="${mybasePath}assets/bootstrap/js/jquery.min.js"></script>
<script src="${mybasePath}assets/js/jquery.backstretch.min.js"></script>
<!--不需要背景 <script src="assets/js/scripts.js"></script> -->
<!-- <script type="text/javascript">
	jQuery(document).ready(function() {
	
    $.backstretch("assets/img/backgrounds/1.jpg");
    
    });
    </script> -->
    <script type="text/javascript">
    $("#close").click(function(){
    	 /*  $("#tip").remove();  */
    	  $("#tip").fadeTo("slow", 0.01, function(){//fade
    		    $(this).slideUp("slow", function() {//slide up
    		      $(this).remove();//then remove from the DOM
    		    });
    		  }); 
    });
    </script>
</html>