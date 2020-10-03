package com.estorm.framework.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnection {

	private final String USER_AGENT = "Mozilla/5.0";

	private String response(InputStream inputStream, int responseCode) throws HttpResponseException {
		if(responseCode >= 400 && responseCode <= 511) {
			throw new HttpResponseException(responseCode);
		}

		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		String line;

		try {
			while((line = reader.readLine()) != null) {
				buffer.append(line);
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return buffer.toString();
	}

	// HTTP GET request
	public String sendGet(String url, HttpParameter params) throws Exception {
		URL obj = new URL(url + "?" + params.getParams());
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		return response(con.getInputStream(), con.getResponseCode());
	}
	
	// HTTP POST request
	public String sendPost(String url, HttpParameter params) throws Exception {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		String urlParameters = params.getParams();
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		return response(con.getInputStream(), con.getResponseCode());
	}

}