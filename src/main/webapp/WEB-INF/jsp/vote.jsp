<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script src="resources/js/jquery-3.1.1.min.js" type="text/javascript"></script>
	<link href="resources/css/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="resources/css/flat-ui.min.css" rel="stylesheet">
	<link href="resources/css/votepage.css" rel="stylesheet">
	<script type="text/javascript">
        window.onload = function(){
            loadPage();
        };

        function loadPage(){
            //获取问卷数据quiz,放入data中
            $.getJSON("loadJSON",function(data){
                var names = data.names;
                var ids = data.ids;
                var questions = data.questions;
                var numNames = names.length;
                var numQuestions = questions.length;
                var sendQuestionNum = '<input type="hidden" name="questionNum" value="' + numQuestions + '">'; //隐藏字段，发送题目数。name=questionNum,value=numQuestions
                $("form").append(sendQuestionNum);

                var radioCount = 0;
                //每一个人
                for (var i=0; i<numNames; i++){
                    //生成人名: 1. zhang
                    var testDiv = '<div class="test">' + '<p class="name">' + (i+1) + ".&nbsp" + names[i] + '：</p>';
                    testDiv += '<input type="hidden" name="name' + i + '" value="' + names[i] + '">'; //隐藏字段，发送名字。name=name0,value="张"
                    testDiv += '<input type="hidden" name="id' + i + '" value="' + ids[i] + '">'; //隐藏字段，发送id。name=id0, value=1

                    //每一个题，生成题目和选项
                    for (j=0; j<numQuestions; j++){
                        //题目
                        testDiv = testDiv + '<div class="question"><span class="questionSpan">' + questions[j].title + '</span>';

                        //选项
                        var options = questions[j].options.split("#");

                        //每一个选项，生成每个选项的radio
                        for (k=0; k<options.length; k++){
                            var radioID = "radio" + radioCount; //radio0、radio1……
                            var radioName = questions[j].title_en + i; //zhengzhi0
                            testDiv = testDiv + '<label class="radio myLable" for="' + radioID + '"><input type="radio" name="' + radioName + '" value="' + k + '" id="' + radioID + '" data-toggle="radio" class="custom-radio" required><span class="icons"><span class="icon-unchecked"></span><span class="icon-checked"></span></span>' + options[k] + '</label>';
                            radioCount++;
                        }
                        testDiv += '</div>';
                    }
                    $("form").append(testDiv);
                }
                $(".test").addClass("container-fluid");
                $(".question").each(function(){
                    $(this).addClass("col-md-6");
                });
                $("form").append('<button type="submit" id="btnSubmit" class="btn btn-hg btn-primary btn-wide">提交</button>');
            });

        }

	</script>

	<title>测评页</title>
</head>
<body>
<h3 class="headline">请对以下人员进行测评</h3>
<div class="form-group">
	<form method="POST" action="submit"></form>
</div>
</body>
</html>
