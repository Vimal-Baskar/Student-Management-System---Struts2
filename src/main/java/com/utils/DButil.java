package com.utils;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DButil 
{
	
	public static boolean isString(Object obj) 
	{
		String value = obj.getClass().getSimpleName();
		return value.equals(String.class.getSimpleName());
	}
	
//	public static JSONObject generateSuccessResponse(String code,String message, ArrayList<Object> fields,String status)
//	{
//		JSONObject details = new JSONObject(fields);
//		JSONObject response = new JSONObject();
//		try 
//		{	
//			JSONArray field = new JSONArray(fields.toArray());
//			details.put("fields",field);
//			response.put("code", code);
//			response.put("message", message);
//			response.put("details", details);
//			response.put("status", status);
//		}
//		catch (JSONException e) 
//		{
//			e.printStackTrace();
//		}
//		return response;
//	}
	
//	public static StringBuilder convertWhereParamsToString(Map<String,List<Object>> map) {
//		StringBuilder response = new StringBuilder();
//		int length = map.size(),count=0;
//		for (Entry<String, List<Object>> item : map.entrySet()) 
//		{
//			response.append(item.getKey());
//			response.append("in [");
//			List<Object> singleItem = item.getValue();
//			for(Object value : singleItem) 
//			{
//				if(isString(value)) 
//				{
//					response.append("'");
//					response.append(value);
//					response.append("'");
//				}
//				else
//				{
//					response.append(value);
//				}
//			}
//			count++;
//			response.append("] ");
//			if(count!=length-1) {
//				response.append("AND ");
//			}
//		}
//		return response;
//	}
	
}