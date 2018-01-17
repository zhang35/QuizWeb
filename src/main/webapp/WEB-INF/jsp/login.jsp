<%@ page import="java.util.ResourceBundle" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: jiaqi
  Date: 2018/1/17
  Time: 上午10:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>管理员登录</title>
    <script src="resources/js/jquery-3.1.1.min.js" type="text/javascript"></script>
    <link href="resources/css/index.css" rel="stylesheet">
    <link href="resources/css/flat-ui.min.css" rel="stylesheet">
    <link href="resources/css/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <%--properties文件放到resources文件夹--%>
    <%ResourceBundle resourceBundle = ResourceBundle.getBundle("./META-INF/properties/myres"); %>
    <script type="text/javascript">
    var pw = <%=resourceBundle.getString("pass")%>;
    $(document).ready(function () {
        $("#info").hide();
        $("#btnSubmit").click(function(){
            if ($("#password").val()==pw){
                alert("ok");
                $("#password").removeClass("c3");
                $("#info").hide();
            }
            else{
                $("#password").addClass("c3");
                $("#info").show();
            }
        });
    });
    </script>
</head>
<body>
<div id="content">
    <div class="login login-screen">
          <div class="login-form">
            <div class="control-group">
              <input type="text" class="login-field" value="" placeholder="Enter your name" id="login-name">
              <label class="login-field-icon fui-man-16" for="login-name"></label>
            </div>

            <div class="control-group">
              <input type="password" class="login-field" value="" placeholder="Password" id="login-pass">
              <label class="login-field-icon fui-lock-16" for="login-pass"></label>
            </div>

            <a class="btn btn-primary btn-large btn-block" href="#">登录</a>
          </div>
        </div>
</div>
</body>
</html>
