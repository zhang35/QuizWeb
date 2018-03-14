package web.quiz.service;
import web.quiz.model.*;

import java.util.List;

public interface DBService{
    List<Question> loadQuestions();

    List<Person> loadPersons();
    Person getPersonByID(String id);

    List<Result> loadResults();
    void saveOrUpdateResult(Result result);
    Result getResultByID(String id);
    boolean containsIP(String ip);
    void resetIP();
    int numOfNewIPs();
}
