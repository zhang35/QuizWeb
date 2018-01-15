package web.quiz.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
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

import web.quiz.service.DBService;
import web.quiz.model.*;
@Controller
public class QuizController {
    @Resource
    private DBService dbService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String defaultPage() {
        return "index";
    }
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index() {
        return "index";
    }
    @RequestMapping(value = "/vote", method = RequestMethod.GET)
    public String vote() {
        return "vote";
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public String result(HttpServletRequest request, HttpServletResponse response) {
        //获得表单中所有值
        Enumeration<String> enu=request.getParameterNames();

        //第一个是发送过来的隐藏的questionNum，确定个数。
        int questionNum=Integer.parseInt(request.getParameter(enu.nextElement()));

        while(enu.hasMoreElements()){
            //对于每趟循环，第一个是发送过来的隐藏的name,中文名字
            String paraName=(String)enu.nextElement();
            String name = request.getParameter(paraName);
            //对于每趟循环，第二个是发送过来的隐藏的id,编号
            paraName=(String)enu.nextElement();
            String id = request.getParameter(paraName);

            String scoreStr = "";
            //如果已经有以前的成绩，先加上
//            Result res = DBUtil.GetResult(id);
//            if (res != null){
//                scoreStr = res.getScoreStr();
//            }
            //剩下的读取questionNum个答案
            for (int i=0; i<questionNum; i++){
                paraName=(String)enu.nextElement();
                scoreStr += request.getParameter(paraName);
            }

            //每组得分末尾加#以示区分,如，甲同学给打的分#乙同学给打的分
//            res.setId(id);
//            res.setName(name);
//            scoreStr += "#";
//            res.setScoreStr(scoreStr);
//
//            //将成绩写入数据库中
//            DBUtil.SaveToDB(res);

            System.out.println(name + scoreStr);
        }
        //返回result界面,显示结果
        return "result";
    }

    @RequestMapping(value = "/json", method = RequestMethod.GET)
    public String json() {
        return "loadJSON";
    }

    //从后台读试卷题目，写入json，发给前台显示
    @RequestMapping(value = "/loadJSON", method = RequestMethod.GET)
    @ResponseBody
    public byte[] loadJSON() throws IOException{
        System.out.print("oooo");
        List<Person> persons = dbService.loadPersons();
        System.out.print("kkkk");
        List<String> names = new ArrayList<String>();
        List<String> ids = new ArrayList<String>();
        for (Person p : persons){
            names.add(p.getName());
            ids.add(p.getId());
        }

        System.out.print("11111");
        List<Question> questions = dbService.loadQuestions();
        System.out.print("22222");

        Quiz quiz = new Quiz();
        quiz.setNames(names);
        quiz.setQuestions(questions);
        quiz.setIds(ids);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString=objectMapper.writeValueAsString(quiz);
        System.out.println(jsonString);

        //解决传到前端后中文乱码问题
        byte[] b = jsonString.getBytes("UTF-8");
        return b;
    }

}
