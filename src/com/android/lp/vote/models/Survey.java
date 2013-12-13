package com.android.lp.vote.models;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;
import android.net.ParseException;

public class Survey implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long s_id;
	private Long s_creator;
	private String s_title;
	private SURVEY_TYPE s_type;
	private String s_start_time;
	private String s_end_time;
	private String s_hash_or_url;

	public Survey(Long s_creator, String s_title, SURVEY_TYPE s_type,
			String s_start_time, String s_end_time, String s_hash_or_url) {
		super();
		this.s_creator = s_creator;
		this.s_title = s_title;
		this.s_type = s_type;
		this.s_start_time = s_start_time;
		this.s_end_time = s_end_time;
		this.s_hash_or_url = s_hash_or_url;
	}

	public Survey(Long s_id, Long s_creator, String s_title,
			SURVEY_TYPE s_type, String s_start_time, String s_end_time,
			String s_hash_or_url) {
		super();
		this.setS_id(s_id);
		this.s_creator = s_creator;
		this.s_title = s_title;
		this.s_type = s_type;
		this.s_start_time = s_start_time;
		this.s_end_time = s_end_time;
		this.s_hash_or_url = s_hash_or_url;
	}

	public String toFullString() {
		return "Survey [s_id=" + s_id + ", s_creator=" + s_creator
				+ ", s_title=" + s_title + ", s_type=" + s_type
				+ ", s_start_time=" + s_start_time + ", s_end_time="
				+ s_end_time + ", s_hash_or_url=" + s_hash_or_url + "]";
	}

	@Override
	public String toString() {
		return s_title;
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

	@SuppressLint("SimpleDateFormat")
	public boolean validate() {

		if (this.s_creator == null)
			return false;
		if (this.s_title.length() == 0 || this.s_title.equals("") || this.s_title.length() == 0 || this.s_title.equals(""))
			return false;
		if (s_type == null)
			return false;

		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		formater.setLenient(false);

		java.util.Date startDate;
		java.util.Date endDate;
		
		try {
			startDate = formater.parse(this.s_start_time);
			endDate = formater.parse(this.s_end_time);
			
			//System.out.println("Date1 is after Date2");
        	if(startDate.compareTo(endDate)>0){
        		return false;
        	}
        		
		} catch (ParseException e) {
			try{
				formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				formater.setLenient(false);
				startDate = formater.parse(this.s_start_time);
				endDate = formater.parse(this.s_end_time);
				
	        	if(startDate.compareTo(endDate)>0){
	        		return false;
	        	}				
			}catch (ParseException pe) {
				return false;
			} catch (Exception pe) {
				return false;
			}			
			return false;
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
