package web.quiz.DAO;

import web.quiz.model.Result;

import java.util.List;

public interface ResultDAO {
    void saveOrUpdate(Result result);
    Result get(String id);
    List<Result> getByIP(String ip);
    List<Result> getAll();

}
