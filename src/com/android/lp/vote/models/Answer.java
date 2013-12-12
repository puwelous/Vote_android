package com.android.lp.vote.models;

public class Answer {
	
	private Long a_id;
	private Long a_question;
	private String a_answer;
	
	public Answer( Long a_question, String a_answer) {
		super();
		this.a_question = a_question;
		this.a_answer = a_answer;
	}
	
	public Answer(Long a_id, Long a_question, String a_answer) {
		super();
		this.a_id = a_id;
		this.a_question = a_question;
		this.a_answer = a_answer;
	}

	public Long getA_id() {
		return a_id;
	}

	public void setA_id(Long a_id) {
		this.a_id = a_id;
	}

	public Long getA_question() {
		return a_question;
	}

	public void setA_question(Long a_question) {
		this.a_question = a_question;
	}

	public String getA_answer() {
		return a_answer;
	}

	public void setA_answer(String a_answer) {
		this.a_answer = a_answer;
	}

	@Override
	public String toString() {
		return "Answer [a_id=" + a_id + ", a_question=" + a_question
				+ ", a_answer=" + a_answer + "]";
	}
	
	

}
