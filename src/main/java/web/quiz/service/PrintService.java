package web.quiz.service;

import org.springframework.http.ResponseEntity;
import web.quiz.model.Person;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.util.List;

public interface PrintService {
    //int [person][question][option] results
    public void printWord(List<Person> persons, int[][][]results, String ftlTemplatePath, String folderPath);

    //参数folderpath：待压缩文件夹路径
    //参数zipFilePath：zip文件保存路径
    public void createZip(String folderPath, String zipFilePath);
}
