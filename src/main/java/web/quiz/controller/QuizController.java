package web.quiz.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.springframework.web.servlet.ModelAndView;
import web.quiz.service.DBService;
import web.quiz.model.*;
@Controller
public class QuizController {
    @Resource
    private DBService dbService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String defaultPage() {
        return "login";
    }

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/vote", method = RequestMethod.GET)
    public String vote() {
        return "vote";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public ModelAndView check(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String pw = request.getParameter("pass");
        System.out.println(pw);
        //不能是==，字符串对比用equals
        if (pw.equals("123")) {
            ModelAndView mav = new ModelAndView("result");
            System.out.println("yeah");
            List<Result> results = dbService.loadResults();
//            request.setAttribute("results", results);
//            request.setAttribute("message", "HelloJSP");

//            mav.addObject("results", results);
//            mav.addObject("message", "helloJSP");
            model.addAttribute("results", results);
            model.addAttribute("message", "helloJSP");
            return mav;
        } else {
            return new ModelAndView("login");
        }
    }

    //在方法的参数列表中添加形参 ModelMap map,spring 会自动创建ModelMap对象。
    //然后调用map的put(key,value)或者addAttribute(key,value)将数据放入map中，spring会自动将数据存入request。
    @RequestMapping(value = "/showResult", method = RequestMethod.GET)
    public ModelAndView result(HttpServletRequest request, ModelMap model) {
//        List<Result> results = dbService.loadResults();
//        System.out.println(results);
//        model.addAttribute("results", results);
        System.out.println("yeah");
        model.addAttribute("message", "helloJSP");
        return new ModelAndView("result");
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public String submit(HttpServletRequest request, HttpServletResponse response) {
        //获得表单中所有值
        Enumeration<String> enu = request.getParameterNames();

        //第一个是发送过来的隐藏的questionNum，确定个数。
        int questionNum = Integer.parseInt(request.getParameter(enu.nextElement()));

        while (enu.hasMoreElements()) {
            //对于每趟循环，第一个是发送过来的隐藏的name,中文名字
            String paraName = (String) enu.nextElement();
            String name = request.getParameter(paraName);
            //对于每趟循环，第二个是发送过来的隐藏的id,编号
            paraName = (String) enu.nextElement();
            String id = request.getParameter(paraName);

            String scoreStr = "";
            //剩下的读取questionNum个答案
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
        List<Person> persons = dbService.loadPersons();
        List<String> names = new ArrayList<String>();
        List<String> ids = new ArrayList<String>();
        for (Person p : persons) {
            names.add(p.getName());
            ids.add(p.getId());
        }

        List<Question> questions = dbService.loadQuestions();

        Quiz quiz = new Quiz();
        quiz.setNames(names);
        quiz.setQuestions(questions);
        quiz.setIds(ids);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(quiz);
        System.out.println(jsonString);

        //解决传到前端后中文乱码问题
        byte[] b = jsonString.getBytes("UTF-8");
        return b;
    }

    //将数据返回到JSP页面
    @RequestMapping(value = "/loadResult", method = RequestMethod.GET)
    public byte[] loadResult() throws IOException {
        List<Result> results = dbService.loadResults();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(results);
        System.out.println(jsonString);
        //解决传到前端后中文乱码问题
        byte[] b = jsonString.getBytes("UTF-8");
        return b;
    }

}