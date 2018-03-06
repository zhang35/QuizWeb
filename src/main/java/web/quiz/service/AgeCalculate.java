package web.quiz.service;

import java.util.Calendar;
import java.util.Date;

public class AgeCalculate {
	/*
	 * ���ܣ����ݳ������ڼ�������
	 * �������ַ������ͳ������� yyyy-MM-dd
	 * ����ֵ������
	 */
	public int getAgeByBirth(String birthday){
		int age = -1;
		if(birthday == null || birthday == ""){
			System.out.println("���󣺳�������Ϊ�գ�");
			return -2;
		}
			
		//��ȡ�����ַ����е��ꡢ�¡���
		String strs[] = birthday.trim().split("-");
		int year_birth = Integer.parseInt(strs[0]);
		int month_birth = Integer.parseInt(strs[1]);
		int day_birth = Integer.parseInt(strs[2]);
		
		//��ȡ��ǰʱ����ꡢ�¡���
		Calendar cal = Calendar.getInstance();
		int year_now = cal.get(Calendar.YEAR);
		int month_now = cal.get(Calendar.MONTH) + 1;
		int day_now = cal.get(Calendar.DATE);		
				
		//�������䣨���꣩����0~1�갴0���
		if(year_now >= year_birth){
			if(year_now == year_birth && month_now >= month_birth){
				age = 0;
				if(month_now == month_birth && day_now < day_birth){
					age = -1;   //�������ڲ��Ϸ�
				}				
			}else if(year_now > year_birth){
					  age = year_now - year_birth;
					  if((month_now < month_birth) || 
						 (month_now == month_birth && day_now < day_birth))
						  age--;					
			}					
		}
		
		if(age < 0){			
			System.out.println("���󣺳������ڲ��Ϸ������ڵ�ǰ���ڣ�");		
		}
		
		return age;
	}
	
	//���ط���getAgeByBirth  �������� Date
	public int getAgeByBirth(Date birthday){
		int age = -1;
		
		
		return age;		
	}
	
}
