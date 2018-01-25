package DAO;

import model.Question;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuestionDAOImpl implements QuestionDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public int save(Question question) {
        return (Integer) sessionFactory.getCurrentSession().save(question);
    }

    public void delete(Question question) {
        sessionFactory.getCurrentSession().delete(question);
    }

    public List<Question> getAll() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Question.class);
        return criteria.list();
    }
}
