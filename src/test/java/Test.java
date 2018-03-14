import jdk.nashorn.internal.runtime.Debug;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashMap;
import java.util.Map;

public class Test {
    @org.junit.Test
    //json: [ [{option1,num1},{option2,num2},...], [{option1,num1},{option2,num2},...],...]
      public void parseScoreStr() {
      String scoreStr = "0121#2210#0010";
      String [] strArray = scoreStr.split("#")    ;
        int questionNum = strArray[0].length();
        int maxOptionNum = 3; //最多的选项数
        int [][] count = new int[questionNum][maxOptionNum];
        for (int i=0; i<questionNum; i++) {
            for (int j = 0; j < maxOptionNum; j++) {
                count[i][j] = 0;
            }
        }
//               问题1 问题2 问题3
//        str1 = 1      1      2
//        str2 = 2      2      0
//        str3 = 0      0      1
//      统计结果放入count数组中：
//   统计： 选项1 选项2 选项3
//    第一题 3    3    0
//    第二题 4    2    0
//    第三题 2    2    2

        for (int i=0; i<questionNum; i++){
            for (int j=0; j<strArray.length; j++){
                count[i][Character.getNumericValue(strArray[j].charAt(i))]++;
            }
        }

        for (int i=0; i<questionNum; i++) {
            System.out.println("ques" + i);
            for (int j = 0; j < maxOptionNum; j++) {
                System.out.print(count[i][j]);
                System.out.println("");
            }
        }

    }


    public enum Mark {VALID, INVALID};
    @org.junit.Test
    public int isValidVote(String voteResults){
        //传入结果形式            "02……120#11……000#……#" 注：#分割各受测评对象的成绩。
        //测评结果是否合法标识
/**
*
*/
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
            //规则2
            if(num_Exellent/((Len-1)*1.0) < 0.9) { return Mark.INVALID.ordinal(); }
            toltal_num_Exellent++;
        }
        //规则2
        if((num_Inept > 0) && (strs[i].charAt(Len-1) != '2')) { return Mark.INVALID.ordinal(); }
    }
    //规则3
		if(toltal_num_Exellent/(strs[0].length()*1.0) > 0.8) { return Mark.INVALID.ordinal(); }

    //合法票
		return Mark.VALID.ordinal();
    }
}