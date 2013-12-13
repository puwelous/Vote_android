package com.android.lp.vote.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Question implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long q_id = Long.valueOf(-1);
	private Long q_survey = Long.valueOf(-1);
	private QUESTION_TYPE q_type = QUESTION_TYPE.RADIO;
	private String q_question = new String();
	private List<Answer> answers = new ArrayList<Answer>() ;

	public Question(Long q_survey, QUESTION_TYPE q_type, String q_question) {
		super();
		this.q_survey = q_survey;
		this.q_type = q_type;
		this.q_question = q_question;
		this.answers = new ArrayList<Answer>();
	}

	public Question(Long q_id, Long q_survey, QUESTION_TYPE q_type,
			String q_question) {
		super();
		this.q_id = q_id;
		this.q_survey = q_survey;
		this.q_type = q_type;
		this.q_question = q_question;
		this.answers = new ArrayList<Answer>();
	}
	
	public Question(Long q_survey, QUESTION_TYPE q_type, String q_question, List<Answer> answers) {
		super();
		this.q_survey = q_survey;
		this.q_type = q_type;
		this.q_question = q_question;
		this.answers = answers;
		this.answers = new ArrayList<Answer>();
	}

	public Question(Long q_id, Long q_survey, QUESTION_TYPE q_type,
			String q_question, List<Answer> answers) {
		super();
		this.q_id = q_id;
		this.q_survey = q_survey;
		this.q_type = q_type;
		this.q_question = q_question;
		this.answers = answers;
		this.answers = new ArrayList<Answer>();
	}
	
	private Question(){
		super();
		this.answers = new ArrayList<Answer>();
	}

	public Long getQ_id() {
		return q_id;
	}

	public void setQ_id(Long q_id) {
		this.q_id = q_id;
	}

	public Long getQ_survey() {
		return q_survey;
	}

	public void setQ_survey(Long q_survey) {
		this.q_survey = q_survey;
	}

	public QUESTION_TYPE getQ_type() {
		return q_type;
	}

	public void setQ_type(QUESTION_TYPE q_type) {
		this.q_type = q_type;
	}

	public String getQ_question() {
		return q_question;
	}

	public void setQ_question(String q_question) {
		this.q_question = q_question;
	}
	
	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}	

	public String toFullString() {
		return "Question [q_id=" + q_id + ", q_survey=" + q_survey
				+ ", q_type=" + q_type + ", q_question=" + q_question
				+ ", answers=" + answers + "]";
	}	
	
	@Override
	public String toString() {
		return q_question;
	}	

	public enum QUESTION_TYPE {
		RADIO, CHECKBOX, RAW
	}
}
