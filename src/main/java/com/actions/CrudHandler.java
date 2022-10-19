package com.actions;

import org.json.JSONException;
import org.json.JSONObject;


public interface CrudHandler {
	
	public JSONObject get(JSONObject json);
	public JSONObject post(JSONObject json) throws JSONException;
	public JSONObject put(JSONObject json);
	public JSONObject delete(JSONObject json);
	public JSONObject patch(JSONObject json);
	
}