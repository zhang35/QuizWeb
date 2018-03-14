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
    }
    public int numOfNewIPs() {
        int count = 0;
        List<Result> results = resultDAO.getAll();
        for (Result result : results){
            if (!result.getIp().endsWith("*")){
                count++;
            }
        }
        return count;
    }
    public boolean containsIP(String ip) {
        List<Result> results = resultDAO.getByIP(ip);
        if (results!=null && results.size() > 0){
            return true;
        }

        return false;
    }

    public void resetIP() {
        List<Result> results = resultDAO.getAll();
        for (Result result : results){
            //ip后加*，以脱离containsIP的查询。*的个数代表重置IP的次数。
            result.setIp(result.getIp() + '*');
            resultDAO.saveOrUpdate(result);
        }
    }
}
