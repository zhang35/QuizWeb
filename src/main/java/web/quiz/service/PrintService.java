package web.quiz.service;

import web.quiz.model.Person;

import java.util.List;

public interface PrintService {
    //int [person][question][option] results
    public void printWord(List<Person> persons, int[][][]results);
}
