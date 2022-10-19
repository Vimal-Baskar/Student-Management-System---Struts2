package com.exceptions;

import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;


public class GenericException extends Exception 
{
	private static final long serialVersionUID = 1L;
	private JSONObject response = new JSONObject(); 
	boolean isError = true;
	
	public GenericException(String code,String message, JSONObject fields,String status) 
	{
		JSONObject details = new JSONObject();
		try
		{	
			Iterator<?> keys = fields.keys();
			while(keys.hasNext())
			{
				String keyName = (String)keys.next();
				details.put(keyName,fields.opt(keyName));
			}
			response.put("code", code);
			response.put("message", message);
			response.put("details", details);
			response.put("status", status);
		}
		catch (JSONException e) 
		{
			e.printStackTrace();
		}
	}
	
	public String getMessage() {
		return response.toString();
	}
	
	public void setError(boolean isError) {
		this.isError = isError;
	}	
}