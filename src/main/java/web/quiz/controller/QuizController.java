package web.quiz.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import sun.applet.AppletListener;
import web.quiz.service.DB2Word;
import web.quiz.service.DBService;
import web.quiz.model.*;

@Controller
public class QuizController {
    @Resource
    private DBService dbService;

    private List<Question> questions;
    private int questionNum;
    private int maxOptionNum;

    private List<Person> persons;

    private Quiz quiz;

    private String userIPs;
    private int currentUserNum;
    private int totalUserNum;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String defaultPage() {
        return "index";
    }

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(HttpServletRequest request) {
        return "index";
    }

    @RequestMapping(value = "/vote", method = RequestMethod.GET)
    public ModelAndView vote(HttpServletRequest request, ModelMap model) {
        //一个IP只能投一次
        String ip = request.getRemoteAddr();
        if (userIPs.contains(ip)){
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

    @RequestMapping(value = "/printResult", method = RequestMethod.GET)
    public String printResult() {
        DB2Word d2w = new DB2Word();
        d2w.db2Word(persons);
        return "saveSuccess";
    }

    @RequestMapping(value = "/resetIPs", method = RequestMethod.GET)
    public ModelAndView resetIPs(ModelMap model) {

        this.currentUserNum = 0;
        this.userIPs = "";

        model.addAttribute("persons", persons);
        model.addAttribute("totalUserNum", totalUserNum);
        model.addAttribute("currentUserNum", currentUserNum);
        return new ModelAndView("result");
    }

    @RequestMapping(value = "/result", method = RequestMethod.POST)
    public ModelAndView check(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String pw = request.getParameter("pass");
        System.out.println(pw);
        //不能是==，字符串对比用equals
        if (pw.equals("123")) {
            model.addAttribute("persons", persons);
            model.addAttribute("totalUserNum", totalUserNum);
            model.addAttribute("currentUserNum", currentUserNum);
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
        userIPs = userIPs + "#" + request.getRemoteAddr();
        currentUserNum++;
        totalUserNum++;

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
                System.out.println(oldReslutStr);
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

    //从后台读试卷题目，写入json，发给前台显示
    @RequestMapping(value = "/loadPaper", method = RequestMethod.GET)
    @ResponseBody
    public byte[] loadPaper() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(this.quiz);
        System.out.println(jsonString);

        //解决传到前端后中文乱码问题
        byte[] b = jsonString.getBytes("UTF-8");
        return b;
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
        this.userIPs = "";
        this.totalUserNum = 0;
        this.currentUserNum = 0;
    }
}