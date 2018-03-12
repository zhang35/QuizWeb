<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
                + request.getServerName() + ":" + request.getServerPort()
                + path + "/";
    %>
    <base href="<%=basePath%>" />

    <meta charset="UTF-8">
    <title>进入测评失败</title>
    <script src="resources/js/jquery-3.1.1.min.js" type="text/javascript"></script>
    <link href="resources/css/flat-ui.min.css" rel="stylesheet">
    <script type="text/javascript">
        $(document).ready(function () {
            window.alert("请联系管理员开放投票权限");
            window.history.back(-1);
        });
    </script>
</head>

<body>
<h3>请勿重复投票！</h3>
</body>
</html>
