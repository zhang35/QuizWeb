window.onload = function(){
    loadPage();
};

function loadPage(){
    var person = ["张家齐", "王家腾"];
    var n = person.length;
    for (var i=0; i<n; i++){
        addTest(i, person[i]);
    }
    $(".test").addClass("container-fluid");
    $(".question").each(function(){
        $(this).addClass("col-md-6");
    });
    $("form").append('<button type="submit" id="btnSubmit" class="btn btn-hg btn-primary btn-wide">提交</button>');
}
function addTest(i, name){
    //生成名字。i=0时 name0~14 radio0~44
    var questionCh = ["政治表现", 
        "品德修养", 
        "廉洁自律", 
        "谋划决策", 
        "组织指挥", 
        "教育管理", 
        "团结协作", 
        "学习创新", 
        "精神状态", 
        "工作作风", 
        "履行基本职责", 
        "执行重大任务", 
        "身体素质", 
        "心理素质", 
        "总体评价"];
    var questionEn = ["zhengzhi",
        "pinde",
        "lianjie",
        "mouhua",
        "zuzhi",
        "jiaoyu",
        "tuanjie",
        "xuexi",
        "jingshen",
        "gongzuo",
        "lvxing",
        "zhixing",
        "shenti",
        "xinli",
        "zongti"];
    var radioName = new Array(15);
    var radioID = new Array(45);
    for (var j=0; j<15; j++){
       radioName[j] = questionEn[j] + i; //zhengzhi0
    }
    for (j=0; j<45; j++){
       radioID[j] = "radio" + (i * 45 + j); //radio0~radio44
    }
    //政治表现
    var testDiv = '<div class="test">' + '<p class="name">' + (i+1) + ".&nbsp" + name + '：</p>';
    for (j=0; j<15; j++){
        testDiv = testDiv + '<div class="question"><span class="questionSpan">' + questionCh[j] + '</span>';
        testDiv = testDiv + '<label class="radio myLable" for="' + radioID[j*3] + '"><input type="radio" name="' + radioName[j] + '" value="3" id="' + radioID[j*3] + '" data-toggle="radio" class="custom-radio"><span class="icons"><span class="icon-unchecked"></span><span class="icon-checked"></span></span> 优秀 </label>';
        testDiv = testDiv + '<label class="radio myLable" for="' + radioID[j*3+1] + '"><input type="radio" name="' + radioName[j] + '" value="2" id="' + radioID[j*3+1] + '" data-toggle="radio" class="custom-radio"><span class="icons"><span class="icon-unchecked"></span><span class="icon-checked"></span></span> 合格 </label>';
        testDiv = testDiv + '<label class="radio myLable" for="' + radioID[j*3+2] + '"><input type="radio" name="' + radioName[j] + '" value="1" id="' + radioID[j*3+2] + '" data-toggle="radio" class="custom-radio" required><span class="icons"><span class="icon-unchecked"></span><span class="icon-checked"></span></span> 不合格 </label>';
        testDiv += '</div>';
    }
    $("form").append(testDiv);
}

