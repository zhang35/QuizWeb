package web.quiz.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Question {
    @Id
    @GeneratedValue
	int id;
	private String title;
	private String options; //存储选项，选项间用'#'隔开
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getOptions() {
		return options;
	}
	public void setOptions(String options) {
		this.options = options;
	}
}
