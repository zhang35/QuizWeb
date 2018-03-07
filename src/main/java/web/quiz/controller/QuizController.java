package web.quiz.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    @RequestMapping(value = "index", method = RequestMethod.GET)
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

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/saveToWord", method = RequestMethod.GET)
    @ResponseBody
    public String saveToWord() {

        System.out.println("分析答案…");
        List<Result> results = dbService.loadResults();
        if (results == null){
            System.out.println("load result error!");
        }
        for (int i=0; i<results.size(); i++){
           this.finalCounts[i] = parseScoreStr(results.get(i).getScoreStr(), maxOptionNum);
        }

        String ftlTemplatePath = "/web/quiz/service/Template.ftl";
//        String ftlTemplatePath = "";
        String folderPath = "/Users/jiaqi/workspace/2018测评";
        printService.printWord(this.persons, this.finalCounts, ftlTemplatePath, folderPath);
        System.out.println("saveToWord Success");
        return "saveToWord Success";
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

    @RequestMapping(value = "/result", method = RequestMethod.POST)
    public ModelAndView check(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String pw = request.getParameter("pass");
        //不能是==，字符串对比用equals
        if (pw.equals("123")) {
            model.addAttribute("persons", persons);
            return new ModelAndView("result");
        } else {
            return new ModelAndView("login");
        }
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
        voterIPs = voterIPs + "#" + request.getRemoteAddr();
        currentVoterNum++;
        totalVoterNum++;

        //获得表单中所有值
        Enumeration<String> enu = request.getParameterNames();

        while (enu.hasMoreElements()) {
            //对于每趟循环，第一个是发送过来的隐藏的name,中文名字
            String paraName = (String) enu.nextElement();
            String name = request.getParameter(paraName);
            //对于每趟循环，第二个是发送过来的隐藏的id,编号
            paraName = (String) enu.nextElement();
            String id = request.getParameter(paraName);

            String scoreStr = "";
            //读取questionNum个答案
            for (int i = 0; i < questionNum; i++) {
                paraName = (String) enu.nextElement();
                scoreStr += request.getParameter(paraName);
            }

//            如果已经有以前的成绩，先加上
            Result oldResult = dbService.getResultByID(id);
            if (oldResult != null) {
                String oldReslutStr = oldResult.getScoreStr();
                if (oldReslutStr != null)
                scoreStr = oldReslutStr + scoreStr;
            }
            //生成新的Result对象
            Result result = new Result();

            result.setId(id);
            result.setName(name);
            //每组得分末尾加#以示区分,如，甲同学给打的分#乙同学给打的分
            scoreStr += "#";
            result.setScoreStr(scoreStr);

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
        for (int i = 0; i < questionNum; i++) {
            int optionNum = questions.get(i).getOptions().split("#").length;
            if (optionNum>maxOptionNum) {
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
        this.totalVoterNum = 0;
        this.currentVoterNum = 0;

        //初始化测评统计finalCount
        this.finalCounts = new int[names.size()][questionNum][maxOptionNum];
    }
}