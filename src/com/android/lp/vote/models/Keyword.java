package com.android.lp.vote.models;

public class Keyword {
	
	private Long k_id;
	private String k_name;
	private Long k_survey;
	
	public Keyword(String k_name, Long k_survey) {
		super();
		this.k_name = k_name;
		this.k_survey = k_survey;
	}
	
	public Keyword(Long k_id, String k_name, Long k_survey) {
		super();
		this.k_id = k_id;
		this.k_name = k_name;
		this.k_survey = k_survey;
	}
	
	public Long getK_id() {
		return k_id;
	}
	public void setK_id(Long k_id) {
		this.k_id = k_id;
	}
	public String getK_name() {
		return k_name;
	}
	public void setK_name(String k_name) {
		this.k_name = k_name;
	}
	public Long getK_survey() {
		return k_survey;
	}
	public void setK_survey(Long k_survey) {
		this.k_survey = k_survey;
	}
	@Override
	public String toString() {
		return "Keyword [k_id=" + k_id + ", k_name=" + k_name + ", k_survey="
				+ k_survey + "]";
	}
}
