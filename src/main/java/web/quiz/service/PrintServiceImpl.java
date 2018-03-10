package web.quiz.service;

import org.springframework.stereotype.Service;
import web.quiz.model.Person;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PrintServiceImpl implements PrintService{

    public void createZip(String folderPath, String zipFilePath) {
        CreateZip.toZip(folderPath, zipFilePath, true);
    }

    public void printWord(List<Person> persons, int[][][] results, String ftlTemplatePath, String folderPath) {
        final String[] answers ={"A", "B", "C", "D"};
        Map<String, Object> date = new HashMap<String, Object>();
        String fileName = null;

        AgeCalculate ac = new AgeCalculate();
        String age = "";

        //循环写出word
        for(int i=0; i<persons.size(); i++)	{
            //根据出生日期计算计算年龄
            Person p = persons.get(i);
            date.put("name", p.getName());
            date.put("sex", p.getSex());
            date.put("birthday", p.getBirthday());
            date.put("department", p.getDepartment());
            date.put("rank_time", p.getRank_time());
            date.put("mil_rank_time", p.getMil_rank_time());
            date.put("deg_of_edu", p.getDeg_of_edu());
            date.put("mil_time", p.getMil_time());
            date.put("par_time", p.getPar_time());

            //根据生日计算年龄
            /*存在问题（BUG）：表中日期格式为  yyyy-MM */
            age = Integer.toString(ac.getAgeByBirth(p.getBirthday()));
            //如果birthday值为空或者不合法 ，则age值为空。
            if(age.equals("-1") || age.equals("-2"))
                age = "";
            date.put("age", age);

            //传入参加人数和有效票数
            /*存在问题（BUG）：逻辑上有效票数小于等于参加人数，需要进行程序分析*/
            int num = 0;
            for(int j=0; j<results[0][0].length; j++){
                num += results[0][0][j];
            }
            date.put("num_of_ent", num);
            date.put("num_of_val", num);

            //传入各单项优秀、及格、不合格票数及比例
            /*存在问题（BUG）：为增强程序鲁棒性应对results值的合法性进行判断。*/
            DecimalFormat df = new DecimalFormat("0.00%");
            for(int k=0; k<results[0].length; k++){
                for(int t=0; t<results[0][0].length; t++){
                    //注：1~N题代表变量尾号为1~N，总评代号变量尾号为0.
                    if(k == results[0].length-1){
                        date.put("num"+answers[t]+0, results[i][k][t]);
                        date.put("prop"+answers[t]+0, df.format(results[i][k][t]/(num*1.0)));
                    }else{
                        date.put("num"+answers[t]+(k+1), results[i][k][t]);
                        date.put("prop"+answers[t]+(k+1), df.format(results[i][k][t]/(num*1.0)));
                    }
                }
            }

            String finalPath = "";
            //将不同部门的人员分文件夹存储
            /*存在问题（BUG）：同一部门同名人员将无法同时输出*/
            if(p.getDepartment() == null || p.getDepartment() == ""){
                finalPath = folderPath + "/其他/";
            }else{
                finalPath = folderPath + "/" + p.getDepartment() + "/";
            }
            fileName = p.getName() + ".doc";
            CreateWord cw = new CreateWord(ftlTemplatePath, finalPath, fileName, date);
            cw.createDoc();
        }


        System.out.println("程序运行结束！");
    }
}
