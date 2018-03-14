package web.quiz.DAO;

import org.junit.Test;
import web.quiz.model.Question;

import static org.junit.Assert.*;

public class QuestionDAOImplTest {

    QuestionDAOImpl questionDAO = new QuestionDAOImpl();
    @Test
    public void save() {
        Question q = new Question();
        q.setTitle("行不行?");
        q.setOptions("1#2#3");

        questionDAO.save(q);
    }

    @Test
    public void delete() {
    }

    @Test
    public void getAll() {
       System.out.println("hello");
    }
}