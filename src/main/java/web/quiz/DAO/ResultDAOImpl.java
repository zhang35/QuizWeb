package web.quiz.DAO;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import web.quiz.model.Result;

import java.util.List;

@Repository
public class ResultDAOImpl implements ResultDAO{
    @Autowired
    private SessionFactory sessionFactory;


    public List<Result> getByIP(String ip) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Result.class);
        criteria.add(Restrictions.eq("ip", ip));
        return criteria.list();
    }

    public void saveOrUpdate(Result result) {
        sessionFactory.getCurrentSession().saveOrUpdate(result);
    }

    public Result get(String id) {
        return (Result)sessionFactory.getCurrentSession().get(Result.class, id);
    }

    public List<Result> getAll() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Result.class);
        return criteria.list();
    }
}
