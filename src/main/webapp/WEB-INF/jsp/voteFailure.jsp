<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>进入测评失败</title>
    <link href="resources/css/index.css" rel="stylesheet">
    <link href="resources/css/flat-ui.min.css" rel="stylesheet">
    <link href="resources/css/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script src="resources/js/jquery-3.1.1.min.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            window.alert("请联系管理员开放测评权限");
            window.history.back(-1);
        });
    </script>
</head>

<body>
    <div id="content">
    <h1 class="headline">请勿重复测评！</h1>
    </div>
</body>
</html>
