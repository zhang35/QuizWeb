<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>民主测评</title>
    <link href="resources/css/flat-ui.min.css" rel="stylesheet">
    <link href="resources/css/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="resources/css/index.css" rel="stylesheet">
</head>
<body>
    <div id="content" class="content">
    <h1 class="headline">民主测评</h1>
    <p><font color="#FF0000">开始测评前请认真阅读以下注意事项！</font></p>
    <p>请根据被测评人的平时表现,客观进行测评。</p>
    <p>测评等级共分为：<b>优秀</b>、<b>称职</b>、和<b>不称职</b>三个等次。单项总优秀率高于90%的，总评才能定为优秀；有一个单项为不称职的，总评即为不称职；所有参评人的总评优秀率不超过80%。</p>
    <p>请逐项测评，不要遗漏测评项。填写不完整的测评卷将被视为无效。</p>
    <a href="<c:url value="vote" />" class="link">参加测评</a>
    </div>
</body>
</html>