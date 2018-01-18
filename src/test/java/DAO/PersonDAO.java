package DAO;

import model.Person;

import java.util.List;

public interface PersonDAO {
    public int save(Person person);
    public void update(Person person);
    public Person get(String id);
    public void delete(Person person);
    public List<Person> getAll();
}
