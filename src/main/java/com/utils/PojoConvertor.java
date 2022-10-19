package com.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.pojo.Pojo;

public class PojoConvertor 
{
	public static Map<String,Object> convertPojoToMap(String pojoFieldPrefix, Pojo obj) throws IllegalArgumentException, IllegalAccessException
	{
		Map<String,Object> mapping = new HashMap<>();
        Field[] allFields = obj.getClass().getDeclaredFields();
        for(Field eachField : allFields)
        {
        	eachField.setAccessible(true);
        	mapping.put(pojoFieldPrefix+"_"+eachField.getName(),eachField.get(obj));
        }
		return mapping;
	}
	
	public static JSONObject convertPojoToJSON(Pojo obj) throws IllegalArgumentException, IllegalAccessException, JSONException
	{
		JSONObject json = new JSONObject();
        Field[] allFields = obj.getClass().getDeclaredFields();
        for(Field eachField : allFields)
        {
        	eachField.setAccessible(true);
        	if(eachField.get(obj)!=null) {
        		json.put(eachField.getName(),eachField.get(obj));
        	}
        }
		return json;
	}
	
}