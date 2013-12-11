package com.android.lp.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

// Async Task to access the web
public class JSONReceiveTask extends AsyncTask<String, Void, String> {

	protected IReactor resultHandler;
	
	public JSONReceiveTask(IReactor resultHandler) {
		super();
		this.resultHandler = resultHandler;
	}

	@Override
	protected String doInBackground(String... params) {

		HttpClient httpclient = new DefaultHttpClient();

		// Log.i("MyActivity", "params[0]:" + params[0]);
		// HttpPost httppost = new HttpPost(params[0]);

		HttpGet httpget = new HttpGet(params[0]);
		try {

			// HttpResponse response = httpclient.execute(httppost);
			HttpResponse response = httpclient.execute(httpget);
			return inputStreamToString(response.getEntity().getContent())
					.toString();
		}

		catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	private StringBuilder inputStreamToString(InputStream is) throws IOException {
		String rLine = "";
		StringBuilder answer = new StringBuilder();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));

		try {
			while ((rLine = rd.readLine()) != null) {
				answer.append(rLine);
			}
		}

		catch (IOException e) {
			// e.printStackTrace();
			throw e;
		}
		return answer;
	}

	@Override
	protected void onPostExecute(String result) {
		resultHandler.reactOnResult(result);
	}
	
}// end async task
