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
<body>
    <div>
    <form method="POST" action="check">
        <div id="loginDiv" class="content loginContent">
            <h3 class="headline">管理员登录</h3>
               <div class="control-group">
                   <span class="questionSpan">密码：</span>
                   <input type="password" class="login-field" value="" placeholder="Password" id="login-pass" name="pass">
                   <button type="submit" id="btnSubmit" class="btn btn-hg btn-primary btn-wide">提交</button>
               </div>
       </div>
    </form>
</div>


</body>
</body>
</html>
