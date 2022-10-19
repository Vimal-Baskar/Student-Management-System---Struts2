package com.utils;

import java.io.BufferedReader;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;

import com.pojo.Pojo;

public class JSONConvertor {
	
	public static JSONObject convertStringToJSON(BufferedReader reader)
	{
		StringBuilder stringRequest = new StringBuilder();
		JSONObject jsonRequest = null;
		String s = null;
		try 
		{
			while((s = reader.readLine()) != null)
			{
//				System.out.println(s);
				stringRequest.append(s);
			}
			jsonRequest = new JSONObject(stringRequest.toString());
			return jsonRequest;

		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}

	public static JSONObject getAllParameters(HttpServletRequest request)
	{
		StringBuilder stringRequest = new StringBuilder();
		JSONObject jsonRequest = null;
		Enumeration<String> params = request.getParameterNames();
		stringRequest.append("{");
		while(params.hasMoreElements()) {
			String parameterName = (String) params.nextElement();
            stringRequest.append(parameterName+" : ");
            if(request.getParameter(parameterName).indexOf(",")!=-1) 
            {
            	stringRequest.append("[");
            	String[] currRequest = request.getParameter(parameterName).split(",");
            	for(int i=0;i<currRequest.length;i++) {
            		stringRequest.append(currRequest[i]);
            		if(i!=currRequest.length-1) {
            			stringRequest.append(",");
            		}
            	}
            	stringRequest.append("]");
            }
            else
            {
            	stringRequest.append(request.getParameter(parameterName));
            }
            stringRequest.append(",");
		}
		stringRequest.append("}");
		try {
			jsonRequest = new JSONObject(stringRequest.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonRequest;
	}

	public static Pojo convertJSONToPojo(JSONObject json,Pojo obj) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
		Iterator<?> index = json.keys();
		Field eachField;
		while(index.hasNext()) {
			String currIndex = index.next().toString();
			eachField = obj.getClass().getDeclaredField(currIndex);
			eachField.setAccessible(true);
			if(json.opt(currIndex).equals(null)) {
				eachField.set(obj,"");
			}
			else {
				eachField.set(obj, json.opt(currIndex));
			}
		}
		return obj;
		
	}

	
}