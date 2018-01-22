package web.quiz.DAO;

import web.quiz.model.Result;

import java.util.List;

public interface ResultDAO {
    public void saveOrUpdate(Result result);
    public Result get(String id);
    public List<Result> getAll();
}
