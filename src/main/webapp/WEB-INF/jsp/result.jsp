<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--使EL表达式${}生效，小心坑啊--%>
<%@page isELIgnored="false" %>
<%--使JSTL <c: forEach>这样的标签生效--%>
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
	<title>查看结果</title>
</head>
<body>
<h3 class="headline">参评人员:</h3>
<div id="content">
	<table class="table table-striped">
		<thead>
		<tr>
			<th>编号</th> <th>姓名</th> <th>单位</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${persons}" var="person">
			<tr>
				<td>${person.id}</td>
				<td><a href="${person.id}/detail">${person.name}</a></td>
				<td>${person.department}</td>
			</tr>
		</c:forEach>

		</tbody>
	</table>
	<p>点击姓名，查看详细结果</p>
    <hr />
	<p>已投票总人数：${totalUserNum}</p>
	<p>本轮投票人数：${currentUserNum}</p>
	<a href="<c:url value="resetIPs"  />" onclick="javascript:alert('开放成功！');" class="link">重新开放投票</a>
	<hr />
	<a href="<c:url value="printResult"  />" class="link">保存结果为Word</a>
</div>

</body>
</html>
