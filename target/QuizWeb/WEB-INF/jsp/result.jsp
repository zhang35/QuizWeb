<%--
  Created by IntelliJ IDEA.
  User: jiaqi
  Date: 2018/1/17
  Time: 上午11:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script src="resources/js/jquery-3.1.1.min.js" type="text/javascript"></script>
	<link href="resources/css/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="resources/css/flat-ui.min.css" rel="stylesheet">
	<script type="text/javascript">
        window.onload = function(){
            loadPage();
        };

        function loadPage(){
            //获取问卷数据quiz,放入data中
            $.getJSON("loadResult",function(data){
                var results = data;
                var result = results[0];
                var testDiv = '<div class="test">' + result.scoreStr + "</div>";
                $("#content").html(testDiv);
            });
        }

	</script>

	<title>测评页</title>
</head>
<body>
<h3 class="headline">结果统计:</h3>
<div id="content">

</div>
</body>
</html>
