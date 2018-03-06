package web.quiz.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import web.quiz.model.Person;

public class DB2Word {
	private static Map<String, Object> date;

	public void db2Word(List<Person> persons){
			String fileName = null;
			String filePath = "";
			AgeCalculate ac = new AgeCalculate();
			String age = "";
			date = new HashMap<String, Object>();
				for (Person p : persons ) {
                //数据准备
					date.put("name", p.getName());
					date.put("sex", p.getSex());
					date.put("birthday", p.getBirthday());
					date.put("department", p.getDepartment());
					date.put("rank_time", p.getRank_time());
					date.put("mil_rank_time", p.getMil_rank_time());
					//根据出生日期计算计算年龄
					age = Integer.toString(ac.getAgeByBirth(p.getBirthday()));
					//如果birthday值为空或者不合法 ，则age值为空。
					if(age.equals("-1") || age.equals("-2"))
						age = "";
					date.put("age", age);

					//将不同部门的人员分文件夹存储
					//存在问题：同一部门同名人员将无法同时输出
					filePath = "/Users/jiaqi/workspace/";
					filePath += new SimpleDateFormat("yyyy").format(new Date()) + "测评/";
					if(p.getDepartment() == null || p.getDepartment() == ""){
						filePath = filePath + "其他/";
					}else{
						filePath = filePath + p.getDepartment() + "/";
					}
					System.out.println(filePath);
					fileName = p.getName() + ".doc";
					CreateWord cw = new CreateWord("/web/quiz/service/Template.ftl", filePath, fileName, date);
					cw.createDoc();
				}

                System.out.println("文件保存成功");
			}

	}
