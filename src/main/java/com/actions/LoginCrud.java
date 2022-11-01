package com.actions;

import org.json.JSONException;
import org.json.JSONObject;

import com.exceptions.GenericException;
import com.pojo.StudentPojo;
import com.utils.JSONConvertor;
import com.validations.LoginValidation;

public class LoginCrud implements CrudHandler
{

	@Override
	public JSONObject get(JSONObject json) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject post(JSONObject json) throws JSONException {
		StudentPojo student = new StudentPojo();
		try {
			student = (StudentPojo) JSONConvertor.convertJSONToPojo(json, student);
			String userId = LoginValidation.checkLogin(student);
			JSONObject fields = new JSONObject();
			fields.put("token", userId+"abcd");
			GenericException response = new GenericException("AUTHENTICATION SUCCESS","student token generated successfully",fields,"SUCCESS",200);
			response.setError(false);
			return new JSONObject(response.getMessage());
			
		} catch (NoSuchFieldException e) {
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
		catch(GenericException e)
		{
			return new JSONObject(e.getMessage());
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public JSONObject put(JSONObject json) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject delete(JSONObject json) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject patch(JSONObject json) {
		// TODO Auto-generated method stub
		return null;
	}
	
}