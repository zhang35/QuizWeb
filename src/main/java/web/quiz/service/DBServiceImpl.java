package web.quiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.quiz.DAO.PersonDAO;
import web.quiz.DAO.QuestionDAO;
import web.quiz.model.Person;
import web.quiz.model.Question;

import java.util.List;

@Service
@Transactional
public class DBServiceImpl implements DBService{
    @Autowired
    private PersonDAO personDAO;
    @Autowired
    private QuestionDAO questionDAO;

    public List<Person> loadPersons(){
        return personDAO.getAll();
    }

    public Person getPersonByID(String id) {
        return personDAO.get(id);
    }

    public void updatePerson(Person person) {
        personDAO.update(person);
    }

    public List<Question> loadQuestions() {
        return questionDAO.getAll();
    }
}
