package com.android.lp.vote.models;

public class AnswerData {

	private Long ad_id;
	private Long ad_survey_data;
	private Long ad_answer;
	private String ad_raw_answer;
	
	public AnswerData(Long ad_survey_data, Long ad_answer, String ad_raw_answer) {
		super();
		this.ad_survey_data = ad_survey_data;
		this.ad_answer = ad_answer;
		this.ad_raw_answer = ad_raw_answer;
	}
	
	public AnswerData(Long ad_id, Long ad_survey_data, Long ad_answer,
			String ad_raw_answer) {
		super();
		this.ad_id = ad_id;
		this.ad_survey_data = ad_survey_data;
		this.ad_answer = ad_answer;
		this.ad_raw_answer = ad_raw_answer;
	}

	public Long getAd_id() {
		return ad_id;
	}

	public void setAd_id(Long ad_id) {
		this.ad_id = ad_id;
	}

	public Long getAd_survey_data() {
		return ad_survey_data;
	}

	public void setAd_survey_data(Long ad_survey_data) {
		this.ad_survey_data = ad_survey_data;
	}

	public Long getAd_answer() {
		return ad_answer;
	}

	public void setAd_answer(Long ad_answer) {
		this.ad_answer = ad_answer;
	}

	public String getAd_raw_answer() {
		return ad_raw_answer;
	}

	public void setAd_raw_answer(String ad_raw_answer) {
		this.ad_raw_answer = ad_raw_answer;
	}

	@Override
	public String toString() {
		return "AnswerData [ad_id=" + ad_id + ", ad_survey_data="
				+ ad_survey_data + ", ad_answer=" + ad_answer
				+ ", ad_raw_answer=" + ad_raw_answer + "]";
	}
}
