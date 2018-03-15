package web.quiz.service;

import org.springframework.stereotype.Service;
import web.quiz.model.Person;
import web.quiz.model.Result;
import web.quiz.util.*;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ResultServiceImpl implements ResultService {
    public boolean checkValidVote(String voteResults){
        //传入结果形式            "02……120#11……000#……#" 注：#分割各受测评对象的成绩。

        //截取对单个人的测评结果
        String[] strs = voteResults.trim().split("#");

        // Len：总的测评项数；
        // toltal_num_Exellent：全体受测评对象总评为优秀的数量。
        int Len = strs[0].length();
        int toltal_num_Exellent = 0;

        //对规则前两项进行判断
        for(int i=0; i<strs.length; i++){
            //num_Exellent：受测评对象测评项为优秀的数量；num_Inept：受测评对象测评项为不称职的数量；
            int num_Inept = 0;
            int num_Exellent = 0;

            for(int j=0; j<Len-1; j++){
                if(strs[i].charAt(j) == '0') { num_Exellent++; }
                if(strs[i].charAt(j) == '2') { num_Inept++; }
            }

            if(strs[i].charAt(Len-1) == '0') {
                //规则1
                if(num_Exellent/((Len-1)*1.0) < 0.9) {
                    System.out.println("违反规则1，废票");
                    return false;
                }
                toltal_num_Exellent++;
            }
            //规则2
            if((num_Inept > 0) && (strs[i].charAt(Len-1) != '2')) {
                System.out.println("违反规则2，废票");
                return false;
            }
        }
        //规则3
        if(toltal_num_Exellent/(strs.length*1.0) > 0.8) {
            System.out.println("违反规则3，废票");
            return false;
        }
        //合法票
        System.out.println("有效票");
        return true;
    }

	public int getValideVoteNum(List<Result> results, boolean validate){
		int num_of_val = 0;
		for (Result result : results){
			if(result.isValidate()){
				num_of_val++;
			}
		}
		System.out.println("有效票数：" + num_of_val);
		return num_of_val;
	}

	public int[][][] doStatistics(List<Result> results, int maxOptionNum, boolean validate){
		/*存在问题（BUG）：resultStr需要至少记录一次测评结果。*/

		if (results==null || results.isEmpty()){
		    System.out.println("分析失败，结果为空！ from doStatistics");
			return null;
		}

		// T_NUM 参加测评人数 ；P_NUM 测评对象人数；Q_NUM 题目数量；C_NUM 题目选项数。
		String tempScoreStr = results.get(0).getScoreStr();
		//第一个出现#的地方，为题目数量
		int Q_NUM = tempScoreStr.indexOf('#');
		//题目数量÷（问题数+'#'）为测评对象人数
		int P_NUM = tempScoreStr.length()/(Q_NUM + 1);

		int [][][] count = new int[P_NUM][Q_NUM][maxOptionNum];
		for (int i=0; i<P_NUM; i++) {
			for (int j=0; j<Q_NUM; j++)	{
				for (int k=0; k<maxOptionNum; k++){
					count[i][j][k] = 0;
				}
			}
		}

		for (Result result : results){
			//若未开启过滤validate直接统计，若开启了过滤，再看票是否合法
			//java的||运算符从左到右运算
			if(!validate || result.isValidate()){
				//若进行不合法票筛选并且所筛选票不合法，则不对该票进行统计。
				for(int j=0; j<P_NUM; j++){
					for(int k=0; k<Q_NUM; k++){
					    //数字char - '0' 得数字，家腾君骚操作
						int score = result.getScoreStr().charAt(j*(Q_NUM + 1) + k) - '0';
						count[j][k][score]++;
					}
				}
			}
		}
		return count;
	}

    public void createZip(String folderPath, String zipFilePath) {
        CreateZip.toZip(folderPath, zipFilePath, true);
    }

    private static final String[] answers ={"A", "B", "C"};

    public void printWord(List<Person> persons, List<Result> results, int maxOptionNum, String ftlTemplatePath, String folderPath, boolean validate){
        long start = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM");
        //count[i][j][k]表示第i个受测评对象的第j个受测评项被评为k等级（0=优秀、1=合格、2=不合格）的次数。
        int[][][] count = doStatistics(results, maxOptionNum, validate);
        //根据是否进行无效票筛选，统计投票结果。
        int num_of_val = getValideVoteNum(results, validate);

        //循环写出word
        for(int i=0; i<persons.size(); i++)	{
            //根据出生日期计算计算年龄
            Person p = persons.get(i);
            Map<String, Object> date = new HashMap<String, Object>();

            date.put("name", p.getName());
            date.put("sex", p.getSex());
            date.put("department", p.getDepartment());
            date.put("deg_of_edu", p.getDeg_of_edu());

            //格式化日期输出格式   yyyy.MM
            String empty = "";
            date.put("birthday", p.getBirthday() == null? empty : sdf.format(p.getBirthday()));
            date.put("rank_time", p.getRank_time() == null? empty : sdf.format(p.getRank_time()));
            date.put("mil_rank_time", p.getMil_rank_time() == null? empty : sdf.format(p.getMil_rank_time()));
            date.put("mil_time", p.getMil_time() == null? empty : sdf.format(p.getMil_time()));
            date.put("par_time", p.getPar_time() == null? empty : sdf.format(p.getPar_time()));

            //根据生日计算年龄
            int age = AgeCalculate.getAgeByBirth(p.getName(), p.getBirthday());
            //如果birthday值不合法 （-1），则age值为空（-2）。
            if(age<0){
                date.put("age", empty);
                if(age == -1){
                    System.out.println("错误：" + p.getName() + "出生日期不合法，晚于当前日期！");
                }else{
                    System.out.println("错误：" + p.getName() + "出生日期为空！");
                }
            }else{
                date.put("age", age);
            }

            date.put("num_of_ent", results.size());
            date.put("num_of_val", num_of_val);

            //传入各单项优秀、及格、不合格票数及比例
            DecimalFormat df = new DecimalFormat("0.00%");
            for(int k=0; k<count[0].length; k++){
                for(int t=0; t<count[0][0].length; t++){
                    //注：1~N题代表变量尾号为1~N，总评代号变量尾号为0.
                    if(k == count[0].length-1){
                        date.put("num"+answers[t]+0, count[i][k][t]);
                        if(num_of_val == 0){
                            date.put("prop"+answers[t]+0, "0");
                        }else{
                            date.put("prop"+answers[t]+0, df.format(count[i][k][t]/(num_of_val*1.0)));
                        }
                    }else{
                        date.put("num"+answers[t]+(k+1), count[i][k][t]);
                        if(num_of_val == 0){
                            date.put("prop"+answers[t]+0, "0");
                        }else{
                            date.put("prop"+answers[t]+(k+1), df.format(count[i][k][t]/(num_of_val*1.0)));
                        }
                    }
                }
            }

            String finalPath = "";
            //将不同部门的人员分文件夹存储
            /*存在问题（BUG）：同一部门同名人员将无法同时输出*/
            if (!folderPath.endsWith("/")){
                folderPath += "/";
            }
            if(p.getDepartment() == null || p.getDepartment() == ""){
                finalPath = folderPath + "其他/";
            }else{
                finalPath = folderPath + p.getDepartment() + "/";
            }
            String fileName = p.getName() + ".doc";
            CreateWord cw = new CreateWord(ftlTemplatePath, finalPath, fileName, date);
            cw.createDoc();
        }
        long end = System.currentTimeMillis();

        System.out.println("测评表写出完成,耗时" + (end - start) +" ms");
    }
}
