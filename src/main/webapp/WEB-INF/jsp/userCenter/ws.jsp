<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="${mybasePath}assets/css/index.css">
</head>
<script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.js"/>
<%
    /********** 保存网站的基本路径 ***********/
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    //将该路径地址缓存到 session中 ,例如：http://localhost:8090/tjnu_ssh_1128/
    session.setAttribute("mybasePath", basePath);
%>

<script>
    var socket;

    function openWebSocket() {
        if (typeof (WebSocket) == "undefined") {
            console.log("对不起，您的浏览器不支持WebSocket");
        } else {
            var webSocketUrl = "ws://localhost/ws/" + $("#userId").val();
            if (socket != null) {
                socket.close();
                socket = null;
            }
            socket = new WebSocket(webSocketUrl);
            //打开
            socket.onopen = function () {
                console.log("Client>>>>WebSocket已打开");
            };
            //获取消息
            socket.onmessage = function (msg) {
                console.log(msg.data);
                $("#msg").val(msg.data)

            };
            //关闭
            socket.onclose = function () {
                console.log("Client>>>>WebSocket已关闭");
            };
            //发生错误
            socket.onerror = function () {
                console.log("Client>>>>WebSocket发生了错误");
            }
        }
    }

    function sendMessage() {
        if (typeof (WebSocket) == "undefined") {
            console.log("对不起，您的浏览器不支持WebSocket");
        } else {
            socket.send('{"toUserId":"' + $("#toUserId").val() + '","msg":"' + $("#msg").val() + '"}');
        }
    }
</script>

<body>
<div id="panel">
    <div class="panel-header">
        <h2>即时通讯IM</h2>
    </div>
    <div class="panel-content">
        <div class="user-pwd">
            <button class="btn-user">发</button>
            <input id="userId" name="userId" type="text" value="张三">
        </div>
        <div class="user-pwd">
            <button class="btn-user">收</button>
            <input id="toUserId" name="toUserId" type="text" value="李四">
        </div>
        <div class="user-msg">
            <input id="msg" name="msg" type="text" value="">
        </div>
        <div class="panel-footer">
            <button class="login-btn" onclick="openWebSocket()">连接WebSocket</button>
            <button class="login-btn" onclick="sendMessage()">发送消息</button>

        </div>
    </div>
</div>
</body>

</html>