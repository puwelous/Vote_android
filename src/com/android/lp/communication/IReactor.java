package com.android.lp.communication;

public interface IReactor {
	/**
	 * Represents reaction of caller entity for handling result.
	 * @param result Result of Asynchronous execution.
	 */
	public void reactOnResult(String result);
}
