package web.quiz.controller;

import web.quiz.model.*;

import java.util.List;


public class Quiz {
	private List<Question> questions;
	private List<String> names;
	private List<String> ids;
	public List<String> getNames() {
		return names;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
}

