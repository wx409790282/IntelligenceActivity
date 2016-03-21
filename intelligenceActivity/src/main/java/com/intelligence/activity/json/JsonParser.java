package com.intelligence.activity.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class JsonParser {
	public static JsonParser instance;

	private JsonParser() {
		super();
	}

	public static JsonParser getInstance() {
		if (instance == null) {
			synchronized (JsonParser.class) {
				if (instance == null) {
					instance = new JsonParser();
				}
			}
		}
		return instance;
	}

	public <T> T revertJsonToObj(String json, Class<T> ObjClass) {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();

		T result = gson.fromJson(json, ObjClass);

		return result;
	}
}
