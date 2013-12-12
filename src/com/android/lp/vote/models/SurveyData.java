package com.android.lp.vote.models;

public class SurveyData {

	private Long sd_id;
	private Long sd_survey;
	private Long sd_respondent;
	
	public SurveyData(Long sd_survey, Long sd_respondent) {
		super();
		this.sd_survey = sd_survey;
		this.sd_respondent = sd_respondent;
	}

	public SurveyData(Long sd_id, Long sd_survey, Long sd_respondent) {
		super();
		this.sd_id = sd_id;
		this.sd_survey = sd_survey;
		this.sd_respondent = sd_respondent;
	}

	public Long getSd_id() {
		return sd_id;
	}

	public void setSd_id(Long sd_id) {
		this.sd_id = sd_id;
	}

	public Long getSd_survey() {
		return sd_survey;
	}

	public void setSd_survey(Long sd_survey) {
		this.sd_survey = sd_survey;
	}

	public Long getSd_respondent() {
		return sd_respondent;
	}

	public void setSd_respondent(Long sd_respondent) {
		this.sd_respondent = sd_respondent;
	}

	@Override
	public String toString() {
		return "SurveyData [sd_id=" + sd_id + ", sd_survey=" + sd_survey
				+ ", sd_respondent=" + sd_respondent + "]";
	}
	
	
}
