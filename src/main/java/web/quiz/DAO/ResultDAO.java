package web.quiz.DAO;

import web.quiz.model.Result;

public interface ResultDAO {
    public void saveOrUpdate(Result result);
    public Result get(String id);
}
