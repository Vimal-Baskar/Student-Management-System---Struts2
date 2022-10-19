package com.validations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.actions.StudentCrud;
import com.exceptions.GenericException;
import com.pojo.StudentPojo;
import com.utils.PojoConvertor;

public class StudentValidation 
{
	
	public static void insertionValidation(StudentPojo student) throws GenericException, JSONException
	{
		String dob = student.getDob();
		String email = student.getEmail();
		String mobile = student.getMobile();
		String[] dobSplit = dob.split("-");
		Pattern pattern = Pattern.compile("[0-9]*");
		JSONObject fields = new JSONObject();
		if(dobSplit.length != 3 || checkValidStudentDate(dobSplit)) {
			fields.put("dob",dob);
			throw new GenericException("INVALID DATA","Enter a valid date for this field",fields,"ERROR");
		}
		else if(!pattern.matcher(mobile).matches())
		{
			fields.put("mobile",mobile);
			throw new GenericException("INVALID DATA","Enter a valid mobile number for this field",fields,"ERROR");
		}
		else if(!email.endsWith("@zoho.com")) {
			fields.put("email",email);
			throw new GenericException("INVALID DATA","Enter a valid email for this field",fields,"ERROR");
		}
		JSONObject json = new JSONObject();
		json.put("email", email);
		ArrayList<Object> field = dataAlreadyExists(json);
		if(field.size()!=0) {
			fields.put("id", new JSONArray(field));
			throw new GenericException("DATA_ALREADY_EXISTS","User mail id already exists",fields,"ERROR");
		}
	}
	
	public static JSONObject updationValidation(StudentPojo student, StudentPojo student1) throws GenericException, JSONException, IllegalArgumentException, IllegalAccessException
	{
		JSONObject fields = new JSONObject();
		ArrayList<Object> field = dataAlreadyExists(PojoConvertor.convertPojoToJSON(student1));
		fields.put("id", new JSONArray(field));
		if(field.size()>1) {
			throw new GenericException("DATA_ALREADY_EXISTS","User mail id already exists",fields,"ERROR");
		}
		insertionValidation(student);
		return fields;
	}

	public static JSONObject getValidation(StudentPojo student) throws GenericException, JSONException, IllegalArgumentException, IllegalAccessException
	{
		ArrayList<Object> field = dataAlreadyExists(PojoConvertor.convertPojoToJSON(student));
		JSONObject fields = new JSONObject();
		if(field.size()==0) 
		{
			throw new GenericException("INVALID DATA","No user exists",fields,"ERROR");
		}
		else {
			fields.put("id", new JSONArray(field));
		}
		return fields;
	}
	
	public static boolean checkValidStudentDate(String[] dobSplit)
	{
		return Integer.parseInt(dobSplit[0])>2000 && Integer.parseInt(dobSplit[0])>2002 && Integer.parseInt(dobSplit[1])<13 && Integer.parseInt(dobSplit[2])<32;
		
	}
	
	public static ArrayList<Object> dataAlreadyExists(JSONObject json) throws JSONException
	{
		JSONObject getResponse = new StudentCrud().get(json);
		JSONArray array;
		ArrayList<Object> fields = new ArrayList<>();
		array = getResponse.getJSONArray("students");
		for(int i=0;i<array.length();i++) 
		{
			HashMap<Object,Object> eachJSON = (HashMap<Object, Object>) array.get(i);
			fields.add(eachJSON.get("student_id"));
		}
		return fields;
	}
	
	
}