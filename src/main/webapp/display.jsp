<%--
  Created by IntelliJ IDEA.
  User: TommyYang
  Date: 2017/11/9
  Time: 20:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>照片墙</title>
    <script src="js/jquery-3.2.1.min.js"></script>
    <style type="text/css">
        .img{
            width: 300px;
            height: 400px;
        }
    </style>
</head>
<body>
<h1 align="center"> 欢迎来到你的照片墙</h1>
<p id = "info" align="center"></p>
<div id="displayDiv">

</div>

</body>
</html>
<script>
    $(document).ready(function(){
        $.getJSON("fileapi/display.do", function(resData){
            var list = resData.data;
            if(list.length == 0){
                $('#info').html("还没有照片呢！赶紧使用<<静拍>>拍照吧！")
            }
            var html = "<table>";
            for (var i = 0; i < list.length;i++){
                if(i % 4 == 0){
                    if(i == 0){
                        html += "<tr><td><img src='"+ list[i] +"' class='img'></td>"
                    }
                    else{
                        html += "</tr><tr><td><img src='"+ list[i] +"' class='img'></td>"
                    }
                }else {
                    html += "<td><img src='"+ list[i] +"' class='img'></td>"
                }
            }
            html += "</tr></table>"
            $('#displayDiv').html(html);
        });
    });
</script>
