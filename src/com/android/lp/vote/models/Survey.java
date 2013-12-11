package com.android.lp.vote.models;

public class Survey {

	private Long s_id;
	private Long s_creator;
	private String s_title;
	private SURVEY_TYPE s_type;
	private String s_start_time;
	private String s_end_time;
	private String s_hash_or_url;
	
	
	public Survey( Long s_creator, String s_title, SURVEY_TYPE s_type,
			String s_start_time, String s_end_time, String s_hash_or_url) {
		super();
		this.s_creator = s_creator;
		this.s_title = s_title;
		this.s_type = s_type;
		this.s_start_time = s_start_time;
		this.s_end_time = s_end_time;
		this.s_hash_or_url = s_hash_or_url;
	}	
	
	public Survey(Long s_id, Long s_creator, String s_title, SURVEY_TYPE s_type,
			String s_start_time, String s_end_time, String s_hash_or_url) {
		super();
		this.setS_id(s_id);
		this.s_creator = s_creator;
		this.s_title = s_title;
		this.s_type = s_type;
		this.s_start_time = s_start_time;
		this.s_end_time = s_end_time;
		this.s_hash_or_url = s_hash_or_url;
	}
	
	@Override
	public String toString() {
		return "Survey [s_id=" + s_id + ", s_creator=" + s_creator
				+ ", s_title=" + s_title + ", s_type=" + s_type
				+ ", s_start_time=" + s_start_time + ", s_end_time="
				+ s_end_time + ", s_hash_or_url=" + s_hash_or_url + "]";
	}
	
	public Long getS_creator() {
		return s_creator;
	}
	public void setS_creator(Long s_creator) {
		this.s_creator = s_creator;
	}
	public String getS_title() {
		return s_title;
	}
	public void setS_title(String s_title) {
		this.s_title = s_title;
	}
	public SURVEY_TYPE getS_type() {
		return s_type;
	}
	public void setS_type(SURVEY_TYPE s_type) {
		this.s_type = s_type;
	}
	public String getS_start_time() {
		return s_start_time;
	}
	public void setS_start_time(String s_start_time) {
		this.s_start_time = s_start_time;
	}
	public String getS_end_time() {
		return s_end_time;
	}
	public void setS_end_time(String s_end_time) {
		this.s_end_time = s_end_time;
	}
	public String getS_hash_or_url() {
		return s_hash_or_url;
	}
	public void setS_hash_or_url(String s_hash_or_url) {
		this.s_hash_or_url = s_hash_or_url;
	}
	public Long getS_id() {
		return s_id;
	}
	public void setS_id(Long s_id) {
		this.s_id = s_id;
	}
	
	public enum SURVEY_TYPE {
	    GLOBAL, LOCAL
	}
}
