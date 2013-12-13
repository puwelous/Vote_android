package com.android.lp.communication.messages;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.android.lp.vote.models.Answer;
import com.android.lp.vote.models.Question;

public class QuestionsAnswersMessageCreate {

	private List<Question> surveyQuestions = new ArrayList<Question>();

	public QuestionsAnswersMessageCreate(List<Question> surveyQuestions) {
		super();
		this.surveyQuestions = surveyQuestions;
	}

	public JSONObject toJson() throws JSONException {

		JSONObject singleEntry = new JSONObject();

		JSONArray dataWrapper = new JSONArray();

		for (Iterator<Question> iteratorQuestions = surveyQuestions.iterator(); iteratorQuestions
				.hasNext();) {

			Question singleQuestion = (Question) iteratorQuestions.next();

			singleEntry.put("single_question",
					createJsonObjectQuestion(singleQuestion));

			JSONArray answersPerQuestion = new JSONArray();

			for (Iterator<Answer> iteratorAnswers = singleQuestion.getAnswers()
					.iterator(); iteratorAnswers.hasNext();) {

				Answer singleAnswer = (Answer) iteratorAnswers.next();

				answersPerQuestion.put( createJsonObjectAnswer(singleAnswer) );
			}

			singleEntry.put("answers_per_question", answersPerQuestion);
			
			dataWrapper.put(singleEntry);
		}

		return new JSONObject().put("client_data", dataWrapper);
	}

	private JSONObject createJsonObjectQuestion(Question singleQuestion)
			throws JSONException {
		
		JSONObject questionAsJson = new JSONObject();

		questionAsJson.put("q_survey", singleQuestion.getQ_survey());
		questionAsJson.put("q_type", singleQuestion.getQ_type().name()
				.toUpperCase());
		questionAsJson.put("q_question", singleQuestion.getQ_question());

		return questionAsJson;
	}

	private JSONObject createJsonObjectAnswer(Answer singleAnswer)
			throws JSONException {
		JSONObject answerAsJson = new JSONObject();

		answerAsJson.put("a_question", singleAnswer.getA_question());
		answerAsJson.put("a_answer", singleAnswer.getA_answer());

		return answerAsJson;
	}
}
