package com.android.lp.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

// Async Task to access the web
public class JSONSendTask extends AsyncTask<String, Void, String> {

	protected IReactor resultHandler;
	
	public JSONSendTask(IReactor resultHandler) {
		super();
		this.resultHandler = resultHandler;
	}

	@Override
	protected String doInBackground(String... params) {

		HttpClient httpclient = new DefaultHttpClient();

		HttpPost httpPost = new HttpPost(params[0]);
		
		try {
			StringEntity se = new StringEntity(params[1]);
			httpPost.setEntity(se);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();	
			Log.i("ABCD", e1.getMessage());
		}
		
		try {

			// HttpResponse response = httpclient.execute(httppost);
			HttpResponse response = httpclient.execute(httpPost);
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
		super.onPostExecute(result);
		resultHandler.onPostExecute(result);
	}
	
}// end async task
