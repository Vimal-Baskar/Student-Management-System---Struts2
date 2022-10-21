package com.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.db.DB;
import com.exceptions.GenericException;
import com.pojo.StudentPojo;
import com.utils.JSONConvertor;
import com.utils.PojoConvertor;
import com.validations.GenericValidation;
import com.validations.StudentValidation;


public class StudentCrud implements CrudHandler{
	
	Map<String,Object> mapping = new HashMap<>();
	StudentPojo student = new StudentPojo();
	
	@Override
	public JSONObject get(JSONObject json) 
	{
		try 
		{
			student = (StudentPojo) JSONConvertor.convertJSONToPojo(json,student);
			mapping = PojoConvertor.convertPojoToMap("student", student);
			List<Map<String,Object>> dbResponse =  DB.getData("student",mapping);
			JSONObject response = new JSONObject();
			if(dbResponse.size()==0)
			{
				response.put("status_code",204);
			}
			else {
				response.put("status_code", 200);
			}
			response.put("students", new JSONArray(dbResponse));
			return response;
		}
		catch(NoSuchFieldException e) {
			e.printStackTrace();
			try
			{
				JSONObject fields = new JSONObject();
				fields.put("fields",e.getMessage());
				throw new GenericException("FIELD_NOT_FOUND_ERROR", "Not able to find the field", fields, "ERROR",400);
			}
			catch(GenericException except) 
			{
				try 
				{
					return new JSONObject(except.getMessage());
				} 
				catch (JSONException e1) 
				{
					e1.printStackTrace();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			try 
			{
				throw new GenericException("INTERNAL_SERVER_ERROR", "Something went wrong", new JSONObject(), "ERROR",500);
			}
			catch(GenericException except) 
			{
				try 
				{
					return new JSONObject(except.getMessage());
				} 
				catch (JSONException e1) 
				{
					e1.printStackTrace();
				}
			}
		}
		return null;
	}
	
	@Override
	public JSONObject delete(JSONObject json) 
	{
		try 
		{			mapping.remove("student_id");

			student = (StudentPojo) JSONConvertor.convertJSONToPojo(json,student);
			JSONObject fields = StudentValidation.getValidation(student);
			mapping = PojoConvertor.convertPojoToMap("student", student);
			int dbResponse = DB.deleteData("student", mapping);
			GenericException ge = new GenericException("DELETION_SUCCESS","student deleted successfully", fields, "SUCCESS",200);
			ge.setError(false);
			return new JSONObject(ge.getMessage());
		} 
		catch (GenericException e) 
		{
			e.printStackTrace();
			try 
			{
				return new JSONObject(e.getMessage());
			} 
			catch (JSONException e1) 
			{
				e1.printStackTrace();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			try 
			{
				throw new GenericException("INTERNAL_SERVER_ERROR", "Something went wrong", new JSONObject(), "ERROR",500);
			}
			catch(GenericException except) 
			{
				try 
				{
					return new JSONObject(except.getMessage());
				} 
				catch (JSONException e1) 
				{
					e1.printStackTrace();
				}
			}
		}
		return null;
	}

	@Override
	public JSONObject post(JSONObject json)
	{
		try 
		{
			student = (StudentPojo) JSONConvertor.convertJSONToPojo(json,student);
			GenericValidation.validate("studentCrud",student);
			StudentValidation.insertionValidation(student);
			JSONObject fields = new JSONObject();
			mapping = PojoConvertor.convertPojoToMap("student", student);
			mapping.remove("student_id");
			int dbResponse = DB.insertData("student",mapping);
			fields.put("id",dbResponse);
			GenericException ge = new GenericException("INSERTION_SUCCESS","student inserted successfully", fields, "SUCCESS",200);
			ge.setError(false);
			return new JSONObject(ge.getMessage());
		} 
		catch (GenericException e) 
		{
			e.printStackTrace();
			try 
			{
				return new JSONObject(e.getMessage());
			} 
			catch (JSONException e1) 
			{
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			try 
			{
				throw new GenericException("INTERNAL_SERVER_ERROR", "Something went wrong", new JSONObject(), "ERROR",500);
			}
			catch(GenericException except) 
			{
				try 
				{
					return new JSONObject(except.getMessage());
				} 
				catch (JSONException e1) 
				{
					e1.printStackTrace();
				}
			}
		} 
		return null;
	}
	@Override
	public JSONObject put(JSONObject json) 
	{
		try {
			student = (StudentPojo) JSONConvertor.convertJSONToPojo(json.optJSONObject("data"),student);
			StudentPojo student1 = new StudentPojo();
			student1 = (StudentPojo) JSONConvertor.convertJSONToPojo(json.optJSONObject("where"),student1);
			JSONObject fields = new JSONObject();
			GenericValidation.validate("studentCrud",student);
			fields = StudentValidation.updationValidation(student, student1);
			mapping = PojoConvertor.convertPojoToMap("student", student);
			mapping.remove("student_id");
			Map<String, Object> whereTo = PojoConvertor.convertPojoToMap("student", student1);
			int dbResponse = DB.updateData("student",mapping,whereTo);
			GenericException ge = new GenericException("UPDATION_SUCCESS","students updated successfully", fields, "SUCCESS",200);
			ge.setError(false);
			return new JSONObject(ge.getMessage());
		}
		catch(GenericException e) {
			e.printStackTrace();
			try {
				return new JSONObject(e.getMessage());
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			try 
			{
				throw new GenericException("INTERNAL_SERVER_ERROR", "Something went wrong", new JSONObject(), "ERROR",500);
			}
			catch(GenericException except) 
			{
				try 
				{
					return new JSONObject(except.getMessage());
				} 
				catch (JSONException e1) 
				{
					e1.printStackTrace();
				}
			}
		}
		return null;
		
	}
	
	@Override
	public JSONObject patch(JSONObject json) 
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}