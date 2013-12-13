package com.android.lp.communication;

public interface IReactor {
	/**
	 * Represents handler action of caller entity after execution.
	 * @param result Result of Asynchronous execution.
	 */
	public void onPostExecute(String result);
	
	/**
	 * Represents preparation of caller entity before execution.
	 */	
	public void onPreExecute();
}
