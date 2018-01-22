package web.quiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.quiz.DAO.PersonDAO;
import web.quiz.DAO.QuestionDAO;
import web.quiz.DAO.ResultDAO;
import web.quiz.model.Person;
import web.quiz.model.Question;
import web.quiz.model.Result;

import java.util.List;

@Service
@Transactional
public class DBServiceImpl implements DBService{
    @Autowired
    private PersonDAO personDAO;
    @Autowired
    private QuestionDAO questionDAO;
    @Autowired
    private ResultDAO resultDAO;

    public List<Person> loadPersons(){
        return personDAO.getAll();
    }

    public List<Result> loadResults() {
        return resultDAO.getAll();
    }

    public Person getPersonByID(String id) {
        return personDAO.get(id);
    }

    public List<Question> loadQuestions() {
        return questionDAO.getAll();
    }

    public Result getResultByID(String id) {
        return resultDAO.get(id);
    }

    public void saveOrUpdateResult(Result result) {
        resultDAO.saveOrUpdate(result);
        System.out.println("结果保存成功!");
    }
}
