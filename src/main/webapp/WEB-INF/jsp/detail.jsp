<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="web.quiz.model.Result" %>
<%@ page import="web.quiz.model.Question" %>
<%@page isELIgnored="false" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: jiaqi
  Date: 2018/1/24
  Time: 上午9:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<link href="/resources/css/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <title>详细结果</title>
</head>
<body>

<%
    List<Question> questions = (List<Question>) request.getAttribute("questions");
	String options[][] = (String[][])request.getAttribute("options");
    int[][] counts = (int[][]) request.getAttribute("counts");
//    int test = 100;
//    pageContext.setAttribute("test", test);
%>
<%--<p>${test}</p>--%>
<h1>${name} 测评结果:</h1>
        <table class="table table-striped">
		<thead>
		<tr>
			<th>编号</th>
			<th>题目</th>
			<th>评分</th>
		</tr>
		</thead>
		<tbody>
		<%--varStatus status.count是从1计数的，坑！--%>
		<c:forEach items="${questions}" var="question" varStatus="qStatus">
			<tr>
				<td>${question.id}</td>
				<td>${question.title}</td>
				<c:forEach items="${options[qStatus.count-1]}" var="option" varStatus="oStatus">
					<td>${option}:${counts[qStatus.count-1][oStatus.count-1]}票</td>
				</c:forEach>
			</tr>
		</c:forEach>

		</tbody>
	</table>
</body>
</html>
