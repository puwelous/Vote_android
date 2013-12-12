package com.android.lp.vote.models;

public class User {

	private Long usr_id;
	private String usr_nick;
	private String usr_email;
	private Integer usr_gender;
	private String usr_year_of_birth;
	private String usr_country;
	private String usr_nationality;
	private String usr_last_update;
	
	public User(String usr_nick, String usr_email, Integer usr_gender,
			String usr_year_of_birth, String usr_country, String usr_nationality) {
		super();
		this.usr_nick = usr_nick;
		this.usr_email = usr_email;
		this.usr_gender = usr_gender;
		this.usr_year_of_birth = usr_year_of_birth;
		this.usr_country = usr_country;
		this.usr_nationality = usr_nationality;
	}

	public User(Long usr_id, String usr_nick, String usr_email,
			Integer usr_gender, String usr_year_of_birth, String usr_country,
			String usr_nationality, String usr_last_update) {
		super();
		this.usr_id = usr_id;
		this.usr_nick = usr_nick;
		this.usr_email = usr_email;
		this.usr_gender = usr_gender;
		this.usr_year_of_birth = usr_year_of_birth;
		this.usr_country = usr_country;
		this.usr_nationality = usr_nationality;
		this.usr_last_update = usr_last_update;
	}

	public Long getUsr_id() {
		return usr_id;
	}

	public void setUsr_id(Long usr_id) {
		this.usr_id = usr_id;
	}

	public String getUsr_nick() {
		return usr_nick;
	}

	public void setUsr_nick(String usr_nick) {
		this.usr_nick = usr_nick;
	}

	public String getUsr_email() {
		return usr_email;
	}

	public void setUsr_email(String usr_email) {
		this.usr_email = usr_email;
	}

	public Integer getUsr_gender() {
		return usr_gender;
	}

	public void setUsr_gender(Integer usr_gender) {
		this.usr_gender = usr_gender;
	}

	public String getUsr_year_of_birth() {
		return usr_year_of_birth;
	}

	public void setUsr_year_of_birth(String usr_year_of_birth) {
		this.usr_year_of_birth = usr_year_of_birth;
	}

	public String getUsr_country() {
		return usr_country;
	}

	public void setUsr_country(String usr_country) {
		this.usr_country = usr_country;
	}

	public String getUsr_nationality() {
		return usr_nationality;
	}

	public void setUsr_nationality(String usr_nationality) {
		this.usr_nationality = usr_nationality;
	}

	public String getUsr_last_update() {
		return usr_last_update;
	}

	public void setUsr_last_update(String usr_last_update) {
		this.usr_last_update = usr_last_update;
	}

	@Override
	public String toString() {
		return "User [usr_id=" + usr_id + ", usr_nick=" + usr_nick
				+ ", usr_email=" + usr_email + ", usr_gender=" + usr_gender
				+ ", usr_year_of_birth=" + usr_year_of_birth + ", usr_country="
				+ usr_country + ", usr_nationality=" + usr_nationality
				+ ", usr_last_update=" + usr_last_update + "]";
	}
}
