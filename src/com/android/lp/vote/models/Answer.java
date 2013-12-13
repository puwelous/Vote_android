package com.android.lp.vote.models;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class Answer implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	private Long a_id = Long.valueOf(-1);
	private Long a_question = Long.valueOf(-1);
	private String a_answer = new String();
	
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

	public String toFullString() {
		return "Answer [a_id=" + a_id + ", a_question=" + a_question
				+ ", a_answer=" + a_answer + "]";
	}
	
	@Override
	public String toString() {
		return a_answer;
	}	
//	
//	@Override
//	public int describeContents() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public void writeToParcel(Parcel dest, int flags) {
//
////		private Long a_id;
////		private Long a_question;
////		private String a_answer;
//		
//		dest.writeLong(this.a_id);
//		dest.writeLong(this.a_question);
//		dest.writeString(this.a_answer);
//	}
//	
//	public Answer(Parcel in) {
//
//		this.a_id = in.readLong();
//		this.a_question = in.readLong();
//		this.a_answer = in.readString();
//	}	
//
//	// More boilerplate
//	// Failure to add this results in the following exception
//	// "android.os.BadParcelableException: Parcelable protocol
//	// requires a Parcelable.Creator object called CREATOR on class"
//	public static final Parcelable.Creator<Answer> CREATOR = new Parcelable.Creator<Answer>() {
//		public Answer createFromParcel(Parcel in) {
//			return new Answer(in);
//		}
//
//		public Answer[] newArray(int size) {
//			return new Answer[size];
//		}
//	};	
}
