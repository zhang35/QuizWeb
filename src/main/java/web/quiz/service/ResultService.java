package web.quiz.service;

import org.springframework.http.ResponseEntity;
import web.quiz.model.Person;
import web.quiz.model.Result;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.util.List;

public interface ResultService {
    int getValideVoteNum(List<Result> results, boolean validate);
    int[][][] doStatistics(List<Result> results, int maxOptionNum, boolean validate);

    void printWord(List<Person> persons, List<Result> results, int maxOptionNum, String ftlTemplatePath, String folderPath, boolean validate);

    //参数folderpath：待压缩文件夹路径
    //参数zipFilePath：zip文件保存路径
    void createZip(String folderPath, String zipFilePath);
}
