package web.quiz.service;

import org.springframework.stereotype.Service;
import web.quiz.model.Person;

import java.util.List;

@Service
public class PrintServiceImpl implements PrintService{
    public void printWord(List<Person> persons, int[][][] results, String ftlTemplatePath, String folderPath) {
        System.out.println("print ok");
    }
}
