package web.quiz.controller;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

import web.quiz.service.DBService;
import web.quiz.model.*;
import web.quiz.service.PrintService;

@Controller
public class QuizController {
    @Resource
    private DBService dbService;

    @Resource
    private PrintService printService;

    @Value("${pass}")
    private String pass;
    @Value("${ftlTemplatePath}")
    private String ftlTemplatePath;
    @Value("${wordFolderPath}")
    private String wordFolderPath;

    private List<Question> questions;
    private int questionNum;
    private int maxOptionNum;

    private List<Person> persons;
    private int [][][] finalCounts;

    private Quiz quiz;

    private String voterIPs;
    private int currentVoterNum;
    private int totalVoterNum;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String defaultPage() {
        return "index";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/vote", method = RequestMethod.GET)
    public ModelAndView vote(HttpServletRequest request, ModelMap model) {
        //一个IP只能投一次
        String ip = request.getRemoteAddr();
        if (voterIPs.contains(ip)){
            return new ModelAndView("voteFailure");
        }

        String options[][] = new String[questionNum][maxOptionNum];
        String questionTitles[] = new String[questionNum];
        for (int i=0; i<questionNum; i++) {
            options[i] = questions.get(i).getOptions().split("#");
            questionTitles[i] = questions.get(i).getTitle();
        }

        model.addAttribute("names", this.quiz.getNames());
        model.addAttribute("ids", this.quiz.getIds());
        model.addAttribute("titles", questionTitles);
        model.addAttribute("options", options);

        return new ModelAndView("vote");
    }

    @RequestMapping("/fileDownLoad")
    public ResponseEntity<byte[]> fileDownLoad(HttpServletRequest request) throws Exception{
        //读取答案，统计结果
        System.out.println("分析答案…");
        List<Result> results = dbService.loadResults();
        if (results == null){
            System.out.println("load result error!");
        }
        for (int i=0; i<results.size(); i++){
            this.finalCounts[i] = parseScoreStr(results.get(i).getScoreStr(), maxOptionNum);
        }

        //保存结果为Word

        //得到file:/Users/jiaqi/workspace/QuizWeb/target/QuizWeb/WEB-INF/classes/
        String pathWebroot = this.getClass().getResource("/").toString();
        //去掉前面的file:
        String realFtlTempLatePath = pathWebroot.substring(5) + this.ftlTemplatePath;
        printService.printWord(this.persons, this.finalCounts, this.ftlTemplatePath, this.wordFolderPath);
        System.out.println("saveToWord Success");

        //压缩Word文件夹
        String zipFilePath = this.wordFolderPath + "/results.zip";
        printService.createZip(wordFolderPath, zipFilePath);
        System.out.println("createZip Success");

        //提供下载zip文件
        File file = new File(zipFilePath);
        InputStream is = new FileInputStream(file);
        byte[] body = new byte[is.available()];
        is.read(body);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment;filename=" + file.getName());
        //ContentType用于定义用户的浏览器或相关设备如何显示将要加载的数据，octet-stream代表任意的二进制数据）
        //不加这一句用Safari下载时出现.html后缀
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        HttpStatus statusCode = HttpStatus.OK;
        return new ResponseEntity<byte[]>(body, headers, statusCode);

        //public ResponseEntity（T  body，
        //                       MultiValueMap < String，String > headers，
        //                       HttpStatus  statusCode）
        //HttpEntity使用给定的正文，标题和状态代码创建一个新的。
        //参数：
        //body - 实体机构
        //headers - 实体头
        //statusCode - 状态码
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(ModelMap model) {
        model.addAttribute("loginInfo", "");
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/checkPass", method = RequestMethod.POST)
    public ModelAndView check(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String pw = request.getParameter("pass");
        //不能是==，字符串对比用equals
        if (pw.equals(this.pass)) {
            System.out.println("match");
            model.addAttribute("persons", persons);
            return new ModelAndView("result");
        } else {
            model.addAttribute("loginInfo", "密码错误！");
            return new ModelAndView("login");
        }
    }

    @RequestMapping("/resetIPs")
    @ResponseBody
    public String resetIPs(){
        this.currentVoterNum = 0;
        this.voterIPs = "";
        System.out.println("resetIP success");
        return "resetIP success";

    }
    @RequestMapping("/getVoterNum")
    @ResponseBody
    public Map<String, Object> getVoterNum(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("totalVoterNum", totalVoterNum);
        map.put("currentVoterNum", currentVoterNum);
        return map;
    }

    //在方法的参数列表中添加形参 ModelMap map,spring 会自动创建ModelMap对象。
    //然后调用map的put(key,value)或者addAttribute(key,value)将数据放入map中，spring会自动将数据存入request。
    //单个人的成绩
    @RequestMapping(value = "/{id}/detail", method = RequestMethod.GET)
    public ModelAndView detail(HttpServletRequest request, ModelMap model, @PathVariable String id) {
        System.out.println("分析答案…");
        Result result = dbService.getResultByID(id);
        if (result == null){
           return new ModelAndView("empty");
        }
        int [][] counts = parseScoreStr(result.getScoreStr(), maxOptionNum);
        String [][] options = new String[questionNum][maxOptionNum];
        for (int i=0; i<questionNum; i++) {
            options[i] = questions.get(i).getOptions().split("#");
        }
        String name = result.getName();
        model.addAttribute("name", name);
        model.addAttribute("questions", questions);
        model.addAttribute("options", options);
        model.addAttribute("counts", counts);
        System.out.println("分析完毕");
        return new ModelAndView("detail");
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public String submit(HttpServletRequest request, HttpServletResponse response) {
        //一个IP只能投一次
        String ip = request.getRemoteAddr();
        if (voterIPs.contains(ip)){
            return "voteFailure";
        }

        voterIPs = voterIPs + "#" + request.getRemoteAddr();
        this.currentVoterNum++;
        this.totalVoterNum++;

        //获得表单中所有值
        Enumeration<String> enu = request.getParameterNames();

        while (enu.hasMoreElements()) {
            //对于每趟循环，第一个是发送过来的隐藏的name,中文名字。
            //每趟循环读取一个Person的成绩
            String paraName = (String) enu.nextElement();
            String name = request.getParameter(paraName);
            //对于每趟循环，第二个是发送过来的隐藏的id,编号
            paraName = (String) enu.nextElement();
            String id = request.getParameter(paraName);

            //StringBuffer为字符串变量，拼接字符串时效率较高
            StringBuffer scoreStr = new StringBuffer("");
            //如果已经有以前的成绩，先加上
            Result oldResult = dbService.getResultByID(id);
            if (oldResult != null) {
                String oldResultStr = oldResult.getScoreStr();
                if (oldResultStr != null){
                    scoreStr.append(oldResultStr);
                }
            }
            //读取questionNum个答案
            for (int i = 0; i < questionNum; i++) {
                paraName = (String) enu.nextElement();
                scoreStr.append(request.getParameter(paraName));
            }

            //生成新的Result对象
            Result result = new Result();
            result.setId(id);
            result.setName(name);
            //每组得分末尾加#以示区分,如，甲同学给打的分#乙同学给打的分
            scoreStr.append('#');
            result.setScoreStr(scoreStr.toString());

            //将成绩写入数据库中
            dbService.saveOrUpdateResult(result);

            System.out.println(name + scoreStr);
        }
        //返回result界面,显示结果
        return "voteSuccess";
    }

    private int getMaxOptionNum(List<Question> questions) {

        int  questionNum = questions.size();
        int maxOptionNum = 0;
        //确定最大选项数maxOptionNum
        for (Question question : questions) {
            int optionNum = question.getOptions().split("#").length;
            if (optionNum > maxOptionNum) {
                maxOptionNum = optionNum;
            }
        }
        return maxOptionNum;
    }
    //    method: parseScoreStr
//    scoreStr = "0121#2210#0010";
//          问题1 问题2 问题3  问题4
//     str1 = 0    1    2     1
//     str2 = 2    2    1     0
//     str3 = 0    0    1     0
//      统计结果放入count数组中：
//   统计： 选项1 选项2 选项3
//    第一题 2    0    1
//    第二题 1    1    1
//    第三题 0    2    1
//    第四题 2    1    0
    private int[][] parseScoreStr(String scoreStr, int maxOptionNum) {
        String [] strArray = scoreStr.split("#")    ;
        int questionNum = strArray[0].length();
        int [][] count = new int[questionNum][maxOptionNum];
        for (int i=0; i<questionNum; i++) {
            for (int j = 0; j < maxOptionNum; j++) {
                count[i][j] = 0;
            }
        }
        for (int i=0; i<questionNum; i++){
            for (int j=0; j<strArray.length; j++){
                count[i][Character.getNumericValue(strArray[j].charAt(i))]++;
            }
        }

        return count;
    }

    @PostConstruct
    private void initQuiz(){
        //从数据库读取信息
        System.out.println("读取答卷……");
        this.questions = dbService.loadQuestions();
        this.questionNum = questions.size();
        this.maxOptionNum = getMaxOptionNum(questions);
        System.out.println("读取完毕");

        System.out.println("读取人员……");
        this.persons = dbService.loadPersons();
        System.out.println("读取完毕");

        //包装成Quiz问卷
        List<String> names = new ArrayList<String>();
        List<String> ids = new ArrayList<String>();
        for (Person p : persons) {
            names.add(p.getName());
            ids.add(p.getId());
        }
        this.quiz = new Quiz();
        this.quiz.setNames(names);
        this.quiz.setQuestions(questions);
        this.quiz.setIds(ids);

        System.out.println("人数:" + names.size());
        System.out.println("题目数：" + questionNum);

        //每次重新启动网站时清空IP
        this.voterIPs = "";
        this.currentVoterNum = 0;

        //获取总投票数
        this.totalVoterNum = 0;
        List<Result> results = dbService.loadResults();
        if (results.size() > 0) {
            //获取对第一个person的答案
            String tempScoreStr = results.get(0).getScoreStr();
            if (tempScoreStr != null){
                //每个投票者形成问题数量+1个字符(0000#)
                this.totalVoterNum = tempScoreStr.length() / (this.questionNum+1);
            }
        }

        //初始化测评统计finalCount
        this.finalCounts = new int[names.size()][questionNum][maxOptionNum];
    }
}