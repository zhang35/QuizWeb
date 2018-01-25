package DAO;

import model.Person;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonDAOImpl implements PersonDAO {
    @Autowired
    private SessionFactory sessionFactory;

    public void delete(Person person) {
        sessionFactory.getCurrentSession().delete(person);
    }

    public int save(Person person) {
        return (Integer)sessionFactory.getCurrentSession().save(person);
    }

    public void update(Person person) {
        sessionFactory.getCurrentSession().update(person);
    }

    public Person get(String id) {
        return (Person) sessionFactory.getCurrentSession().get(Person.class, id);
    }

    public List<Person> getAll() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Person.class);
        return criteria.list();
    }
}
