package com.example.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.example.model.Bar;
import com.example.model.Foo;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class HttpConnector {

	private static final Object BASE_URL = "http://127.0.0.1:8000/webservice/";

	private Gson gson;

	public HttpConnector() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder
				.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
		gson = gsonBuilder.create();
	}

	public Foo[] getFoos() {
		Response response = request("foos", null);
		if (response.getCode() >= 400) {
			throw new RuntimeException(response.getBody());
		}
		return gson.fromJson(response.getBody(), Foo[].class);
	}

	public Response addOrUpdateFoo(Foo foo) {
		Response response = request("foos", gson.toJson(foo));
		return response;
	}

	public Bar[] getBars() {
		Response response = request("bars", null);
		if (response.getCode() >= 400) {
			throw new RuntimeException(response.getBody());
		}
		return gson.fromJson(response.getBody(), Bar[].class);
	}

	private Response request(String path, String data) {
		try {
			URL url = new URL(String.format("%s%s/", BASE_URL, path));
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty("Accept", "application/json");

			if (data != null) {
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setDoOutput(true);
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
						conn.getOutputStream()));
				bw.write(data);
				System.out.println(data);
				bw.flush();
			}

			int code = conn.getResponseCode();
			InputStream in;
			if (code >= 400) {
				in = conn.getErrorStream();
			} else {
				in = conn.getInputStream();
			}

			return new Response(code, readStream(in));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private String readStream(InputStream in) {
		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		StringBuilder sb = new StringBuilder();
		String line;
		try {
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public class Response {
		private int code;
		private String body;

		public Response(int code, String body) {
			super();
			this.code = code;
			this.body = body;
		}

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getBody() {
			return body;
		}

		public void setBody(String body) {
			this.body = body;
		}

		@Override
		public String toString() {
			return "Response [code=" + code + ", body=" + body + "]";
		}

	}

}
