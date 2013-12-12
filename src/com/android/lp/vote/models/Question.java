package com.android.lp.vote.models;

public class Question {

	private Long q_id;
	private Long q_survey;
	private QUESTION_TYPE q_type;
	private String q_question;
	
	public Question( Long q_survey, QUESTION_TYPE q_type,
			String q_question) {
		super();
		this.q_survey = q_survey;
		this.q_type = q_type;
		this.q_question = q_question;
	}	

	public Question(Long q_id, Long q_survey, QUESTION_TYPE q_type,
			String q_question) {
		super();
		this.q_id = q_id;
		this.q_survey = q_survey;
		this.q_type = q_type;
		this.q_question = q_question;
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


	@Override
	public String toString() {
		return "Question [q_id=" + q_id + ", q_survey=" + q_survey
				+ ", q_type=" + q_type + ", q_question=" + q_question + "]";
	}


	private enum QUESTION_TYPE{
		RADIO, CHECKBOX, RAW
	}
}
