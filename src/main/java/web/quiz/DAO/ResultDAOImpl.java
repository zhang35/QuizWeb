package web.quiz.DAO;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import web.quiz.model.Result;

@Repository
public class ResultDAOImpl implements ResultDAO{
    @Autowired
    private SessionFactory sessionFactory;

    public void saveOrUpdate(Result result) {
        sessionFactory.getCurrentSession().saveOrUpdate(result);
    }

    public Result get(String id) {
       return (Result)sessionFactory.getCurrentSession().get(Result.class, id);
    }
}
