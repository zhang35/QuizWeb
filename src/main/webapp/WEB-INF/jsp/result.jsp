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
	<%
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ path + "/";
	%>
	<base href="<%=basePath%>" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="resources/css/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="resources/css/flat-ui.min.css" rel="stylesheet">
	<link href="resources/css/result.css" rel="stylesheet">
	<title>查看结果</title>

	<script src="resources/js/jquery-3.1.1.min.js" type="text/javascript"></script>
	<script src="resources/js/flat-ui.min.js" type="text/javascript"></script>
	<script src="resources/js/application.js" type="text/javascript"></script>
	<script type="text/javascript">
        function StandardPost (url,args)
        {
            var form = $("<form method='post'></form>");
            form.attr({"action":url});
            for (arg in args)
            {
                var input = $("<input type='hidden'>");
                input.attr({"name":arg}); //将name属性设置为arg
                input.val(args[arg]); //将input的内容设置为对应的值
                form.append(input);
            }

            var validate = $("#custom-switch-02").prop("checked");
            var input = $("<input type='hidden'>");
            input.attr({"name": "validate"});
            input.val(validate);
            form.append(input);

            //HTML标准规定如果form表单没有被添加到document里，那么form表单提交将会被终止
            $(document.body).append(form);
            form.submit();
        }
        function resetIPs() {
            if ($('#currentVoterNum').text()=='0'){
                alert("开放成功！！！");
			}
			else {
                $.ajax({
                    type: "POST",
                    url: "resetIPs",
                    async: false,
                    dataType: 'text',
                    success: function(data){
                        alert("开放成功！");
                        $('#currentVoterNum').text('0');
                    },
                    error: function(){
                        alert("发送请求失败");
                    }
                });
			}
        };

        //每隔2000ms运行一次函数
        setInterval("getVoterNum()",2000);
        function getVoterNum() {
            $.ajax({
                type: "POST",
                url: "getVoterNum",
                async: false,
                dataType: 'json',
                success: function (data) {
                    $('#totalVoterNum').text(data.totalVoterNum);
                    $('#currentVoterNum').text(data.currentVoterNum);
                }
            });
        }
	</script>
</head>
<body>
<div id="content">
<h3>参评人员:</h3>
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
				<td><a href="javascript:;" onclick="StandardPost('detail', {'id': '${person.id}', 'name':'${person.name}'})">${person.name}</a></td>
				<td>${person.department}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<p>过滤废票：<input type="checkbox" name="validate" data-toggle="switch" id="custom-switch-02" /></p>
	<p>点击姓名，查看详细结果</p>
    <hr />
	<p>已投票总人数：<span id="totalVoterNum"></span></p>
	<p>本轮投票人数：<span id="currentVoterNum"></span></p>
	<a href="javascript:;" onclick="resetIPs();" class="btn-primary btn-lg">开放投票</a>
	<hr />
    <p>导出结果为Word文档：</p>
    <%--<a href="javascript:;" onclick="fileDownload();" class="btn-primary btn-lg">下载文件</a>--%>
	<%--<a href="fileDownload" class="btn-primary btn-lg">下载文件</a>--%>
   <a href="javascript:;" onclick="StandardPost('fileDownload', null);" class="btn-primary btn-lg">下载文件</a>
</div>

</body>
</html>
