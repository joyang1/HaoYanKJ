<%--
  Created by IntelliJ IDEA.
  User: tommy
  Date: 2017/12/23
  Time: 13:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<%
    String webRoot = "/HaoYanKJ";
%>
<head>
    <title>Chart</title>
    <meta charset="utf-8" />
    <script src="http://cdn.jsdelivr.net/sockjs/0.3.4/sockjs.min.js"></script>
    <script src="<%=webRoot%>/js/jquery-3.2.1.min.js"></script>
    <style type="text/css">
        html,body{
            position: relative;
            width: 100%;
            height: 100%;
            padding: 0px;
            margin: 0px;
        }
        canvas{
            width: 30%;
            height: 30%;
            position: absolute;
            left: 200px;
            top: 0px;
            z-index: 100;
        }
    </style>
</head>
<body>
<table>
    <tr><td colspan="2"><button style="width:100%;" id="connect">连接</button></td></tr>
    <tr><td colspan="2"><button style="width:100%;" id="close">断开</button></td></tr>
    <tr>
        <td>昵称</td>
        <td><input type="text" id="nickName" value="tommy"/></td>
    </tr>
    <tr>
        <td>状态</td>
        <td><input type="text" readonly="readonly" value="未连接" id="status"/></td>
    </tr>
    <tr>
        <td colspan="2"><canvas id="canvas"></canvas></td>
    </tr>
</table>
<script type="text/javascript">
    $(document).ready(function() {
        var self = this;
        //var sockjsAddr = "ws://tommyyang.cn:8090/HaoYanKJ/hello/nickname";
        var sockjsAddr = "/HaoYanKJ/hello/nickname";
        //var sockjsAddr = "/hello/nickname";
        //var sockjsAddr = "ws://localhost:8080/hello/nickname";
        var sockjsClient = null;
        var canvas = document.getElementById("canvas");
        var context = canvas.getContext("2d");

        var onopen = function() {
            console.log('open');
            $("#status").val("已连接");
        };

        var onmessage = function(e) {
//            $("#img").attr("src",e.data);
            var image = new Image();
            image.onload = function() {
                context.drawImage(image, 0, 0, 775, 580);
            };
            image.src = e.data;
//            window.setInterval(function(){
//                context.drawImage(e.data, 0, 0, 375, 180);
//            },15);
        };
        var onclose = function() {
            console.log('close');
            $("#status").val("未连接");
        };
        var nickName = $.trim($("#nickName").val());
        sockjsClient = new SockJS(sockjsAddr + "/" + nickName);
        //sockjsClient = new WebSocket(sockjsAddr + "/" + nickName+".do");
        sockjsClient.onopen = onopen;
        sockjsClient.onmessage = onmessage;
        sockjsClient.onclose = onclose;

        $("#close").click(function() {
            if (sockjsClient != null) {
                sockjsClient.close();
                sockjsClient = null;
                $("#status").val("未连接");
            }
        });
    });
</script>
</body>
</html>
