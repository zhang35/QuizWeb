<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="web.quiz.model.Result" %>
<%--使EL表达式${}生效，小心坑啊--%>
<%@page isELIgnored="false" %>
<%--使JSTL <c: forEach>这样的标签生效--%>
<%@ page import="java.util.List" %>
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
            // loadPage();
            // window.location.href = "/index";
        };

        function loadPage(){
            //获取问卷数据quiz,放入data中
            var results;
            var questions;
            $.getJSON("loadResult",function(data){
                results = data;
                var result = results[0];
                var testDiv = '<div class="test">' + result.scoreStr + "</div>";

                var tableDiv = '<table class="table table-striped"> <thead> <tr> <th>姓名</th> <th>成绩</th> </tr> </thead>';
                tableDiv.append('');
                tableDiv.append('</table>');
                $("#content").html(tableDiv);
            });

            $.getJSON("loadPaper",function(data) {
                questions = data.questions;
                var names = data.names;
                var ids = data.ids;
                var numNames = names.length;
                var numQuestions = questions.length;
                var sendQuestionNum = '<input type="hidden" name="questionNum" value="' + numQuestions + '">'; //隐藏字段，发送题目数。name=questionNum,value=numQuestions


            });
        }

	</script>

	<title>测评页</title>
</head>
<body>
<h3 class="headline">结果统计:</h3>
<div id="content">
	<%
		List<Result> results = (List<Result>) request.getAttribute("results");
		String message = (String)request.getAttribute("message");
	%>
	<table class="table table-striped"> <thead> <tr> <th>编号</th> <th>姓名</th> <th>评分</th></tr> </thead>
		<tbody>
        <c:forEach items="${results}" var="result">
			<tr>
				<td>${result.id}</td>
				<td>${result.getName()}</td>
				<td>${result.getScoreStr()}</td>
			</tr>
		</c:forEach>

		</tbody>
	</table>
</div>



</body>
</html>
