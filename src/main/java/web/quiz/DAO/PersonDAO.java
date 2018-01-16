package web.quiz.DAO;

import web.quiz.model.Person;
import java.util.List;

public interface PersonDAO {
    public int save(Person person);
    public int delete(Person person);
    public List<Person> getAll();
}
