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
}

