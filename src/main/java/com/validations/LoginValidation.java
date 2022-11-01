package com.validations;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.actions.StudentCrud;
import com.exceptions.GenericException;
import com.pojo.StudentPojo;
import com.utils.PojoConvertor;

public class LoginValidation 
{
	public static String checkLogin(StudentPojo student) throws IllegalArgumentException, IllegalAccessException, JSONException, GenericException
	{
		JSONObject json = PojoConvertor.convertPojoToJSON(student);
		JSONObject getResponse = new StudentCrud().get(json);
		JSONArray array;
		array = getResponse.getJSONArray("students");
		JSONObject fields = new JSONObject();
		if(array.length()==0)
		{
			throw new GenericException("INVALID DATA","No user exists",fields,"ERROR",400);
		}
		else if(array.length()>1)
		{
			throw new GenericException("INVALID DATA","More than one user exists",fields,"ERROR",400);
		}
		HashMap<String,Object> studentResponse = (HashMap<String,Object>) array.get(0);
		return (String) studentResponse.get("student_id");
	}
}