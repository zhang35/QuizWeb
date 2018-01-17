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
    <link href="resources/css/index.css" rel="stylesheet">
    <link href="resources/css/flat-ui.min.css" rel="stylesheet">
    <link href="resources/css/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <%--properties文件放到resources文件夹--%>
    <%ResourceBundle resourceBundle = ResourceBundle.getBundle("./META-INF/properties/myres"); %>
    <script type="text/javascript">
    var pw = <%=resourceBundle.getString("pass")%>
        alert(pw);
    </script>
</head>
<body>
<div id="content">
    <h1 class="headline">输入密码</h1>
    <input type="text" value="" class="span3">
    <button type="submit" id="btnSubmit" class="btn btn-hg btn-primary btn-wide">提交</button>
</div>
</body>
</html>
