package web.quiz.DAO;
import web.quiz.model.Question;

import java.util.List;

public interface QuestionDAO {
    public int save(Question question);
    public void delete(Question question);
    public List<Question> getAll();
}
