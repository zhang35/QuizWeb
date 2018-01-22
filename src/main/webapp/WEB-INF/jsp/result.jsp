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
            var results;
            var quesitons;
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
                quesitons = data.questions;
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
<div class="span3">
	<div class="dk_container span3 dk_shown dk_theme_default" id="dk_container_herolist" tabindex="1"><a class="dk_toggle"><span class="dk_label">X-Men</span><span class="select-icon"></span></a><div class="dk_options"><ul class="dk_options_inner"><li class=""><a data-dk-dropdown-value="0">Choose hero</a></li><li class=""><a data-dk-dropdown-value="1">Spider Man</a></li><li class=""><a data-dk-dropdown-value="2">Wolverine</a></li><li class=""><a data-dk-dropdown-value="3">Captain America</a></li><li class="dk_option_current"><a data-dk-dropdown-value="X-Men">X-Men</a></li><li class=""><a data-dk-dropdown-value="Crocodile">Crocodile</a></li></ul></div></div><select value="X-Men" class="span3" tabindex="1" name="herolist" style="display: none;">
	<option value="0">Choose hero</option>
	<option value="1">Spider Man</option>
	<option value="2">Wolverine</option>
	<option value="3">Captain America</option>
	<option value="X-Men" selected="selected">X-Men</option>
	<option value="Crocodile">Crocodile</option>
</select>
</div>
<div id="content">
	<table class="table table-striped"> <thead> <tr> <th>Student-ID</th> <th>First Name</th> </tr> </thead>
		<tbody>
		<tr>
			<td>001</td>
			<td></td>
			<td>Reddy</td>
			<td>A+</td>
		</tr>
		<tr>
			<td>002</td>
			<td>Smita</td>
			<td>Pallod</td>
			<td>A</td>
		</tr>
		<tr>
			<td>003</td>
			<td>Rabindranath</td>
			<td>Sen</td>
			<td>A+</td>
		</tr>
		</tbody>
	</table>
</div>



</body>
</html>
