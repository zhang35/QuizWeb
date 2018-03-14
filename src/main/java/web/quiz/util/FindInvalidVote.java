package web.quiz.util;

/*
 * 功能：识别不合法测评票
 * 规则：1.受测评对象的测评项总优秀率高于90%，总评才能定为优秀；
 *     2.受测评对象的测评项有一项不称职，总评即为不称职；
 *     3.对受测评对象总评为优秀的不超过80%。 
 */
public class FindInvalidVote {	
	
	public static boolean isValidVote(String voteResults){
		//传入结果形式            "02……120#11……000#……#" 注：#分割各受测评对象的成绩。	
		
		//截取对单个人的测评结果
		String[] strs = voteResults.trim().split("#");		
		
		// Len：总的测评项数； num_Exellent：受测评对象测评项为优秀的数量；num_Inept：受测评对象测评项为不称职的数量；
		// toltal_num_Exellent：全体受测评对象总评为优秀的数量。
		int Len = strs[0].length();
		int num_Exellent = 0;
		int num_Inept = 0;
		int toltal_num_Exellent = 0;
		
		//对规则前两项进行判断
		for(int i=0; i<strs.length; i++){
			for(int j=0; j<Len-1; j++){
				if(strs[i].charAt(j) == '0') { num_Exellent++; }							
				if(strs[i].charAt(j) == '2') { num_Inept++; }					
			}
			
			if(strs[i].charAt(Len-1) == '0') { 
				//规则1
				if(num_Exellent/((Len-1)*1.0) < 0.9) { return false; }
				toltal_num_Exellent++; 
			}
			//规则2
			if((num_Inept > 0) && (strs[i].charAt(Len-1) != '2')) { return false; }
		}		
		//规则3
		if(toltal_num_Exellent/(strs.length*1.0) > 0.8) { return false; }
		
		//合法票
		return true;
	}
}
