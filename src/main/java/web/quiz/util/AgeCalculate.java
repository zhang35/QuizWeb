package web.quiz.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AgeCalculate {
	
	/*
	 * 功能：根据出生日期计算年龄
	 * 参数：字符串类型出生日期 yyyy-MM-dd
	 * 返回值：整形
	 */
	public static int getAgeByBirth(String birthday){
		int age = -1;
		if(birthday == null || birthday == ""){
			//System.out.println("错误：出生日期为空！");
			return -2;
		}
			
		//截取生日字符串中的年、月、日
		String[] strs = birthday.trim().split("-");
		int year_birth = Integer.parseInt(strs[0]);
		int month_birth = Integer.parseInt(strs[1]);
		int day_birth = Integer.parseInt(strs[2]);
		
		//获取当前时间的年、月、日
		Calendar cal = Calendar.getInstance();
		int year_now = cal.get(Calendar.YEAR);
		int month_now = cal.get(Calendar.MONTH) + 1;
		int day_now = cal.get(Calendar.DATE);		
				
		//计算年龄（周岁）例：0~1岁按0岁计
		if(year_now >= year_birth){
			if(year_now == year_birth && month_now >= month_birth){
				age = 0;
				if(month_now == month_birth && day_now < day_birth){
					age = -1;   //出生日期不合法
				}				
			}else if(year_now > year_birth){
					  age = year_now - year_birth;
					  if((month_now < month_birth) || 
						 (month_now == month_birth && day_now < day_birth))
						  age--;					
			}					
		}		
		
		//if(age < 0) System.out.println("错误：出生日期不合法，晚于当前日期！");	  
		
		return age;
	}
	
	//重载方法getAgeByBirth  参数类型 Date
	public static int getAgeByBirth(String name, Date birthday){
		int age = -1;	
		if(birthday == null){
			//System.out.println("错误：出生日期为空！");
			return -2;
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		age = getAgeByBirth( formatter.format(birthday));
		
		return age;		
	}	
}
