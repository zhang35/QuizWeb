<%@ page contentType="text/html; charset=UTF-8"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title>Spring MVC表单处理（多选按钮）</title>


<script src="resources/js/jquery-3.1.1.min.js" type="text/javascript"></script>



<script>
$(document).ready(function(){

	$("button").click(function(){
		$("div").append("<p>hey</p>");

		$.getJSON("loadJSON",function(data,status){
				var names = data.names;
				var questions = data.questions;
				var options = questions[0].options;
			  });
	});
});
</script>
</head>
<body>

<button>获取 JSON 数据</button>
<div></div>
<a href="<c:url value="loadJSON" />" class="link">show json</a>
</body>
</html>