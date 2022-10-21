package com.validations;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

import com.exceptions.GenericException;
import com.pojo.Pojo;


public class GenericValidation
{
		
	public static JSONObject validateData() throws ClassNotFoundException, URISyntaxException
	{
		try {
			System.out.println();
			String validationData = new String(Files.readAllBytes(Paths.get(GenericValidation.class.getResource("validations.json").toURI())), StandardCharsets.UTF_8);
			JSONObject json = new JSONObject(validationData);
			return json;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void validate(String className, Pojo obj) throws GenericException, IllegalAccessException, IllegalArgumentException, NoSuchFieldException, JSONException, ClassNotFoundException, URISyntaxException
	{
		JSONObject data = new JSONObject();
		data = validateData().optJSONObject(className);
		Iterator<?> index = data.keys();
		Field eachField;
		while(index.hasNext()) {
			String currIndex = index.next().toString();
			JSONObject metaData = (JSONObject)(data.opt(currIndex));
			eachField = obj.getClass().getDeclaredField(currIndex);
			eachField.setAccessible(true);
			JSONObject fields = new JSONObject();
			if(eachField.get(obj)==null) 
			{
				fields.put(currIndex,"");
			}
			else 
			{
				fields.put(currIndex,eachField.get(obj));
			}
			String dataType = (String) metaData.opt("dataType");
			if(dataType.equals("Text"))
			{
				dataType = "String";
			}
			else if(dataType.equals("Number"))
			{
				dataType = "Integer";
			}
			if(((boolean)metaData.optBoolean("mandatory")) && (eachField.get(obj)==null || eachField.get(obj).equals(""))) 
			{
				throw new GenericException("MANDATORY_NOT_FOUND","Mandatory field not found",fields,"ERROR",400);	
			}
			else if(!(boolean)(metaData.optBoolean("editable"))) 
			{
				throw new GenericException("NOT_EDITABLE","field cannot be edited",fields,"ERROR",400);
			}
			else if(eachField.get(obj)!=null && !dataType.equals(eachField.get(obj).getClass().getSimpleName()))
			{
				throw new GenericException("DATA_TYPE_NOT_SUPPORTED","data type should be of type "+metaData.opt("dataType"),fields,"ERROR",400);
			}
			else if(eachField.get(obj)!=null && (int)metaData.opt("min-length")>((String)eachField.get(obj)).length())
			{
				throw new GenericException("MIN_LENGTH_LIMIT_NOT_REACHED","field should be of minimum length of "+metaData.opt("min-length"),fields,"ERROR",400);
			}
			else if(eachField.get(obj)!=null && (int)metaData.opt("max-length")<((String)eachField.get(obj)).length())
			{
				throw new GenericException("MAX_LENGTH_LIMIT_EXCEEDED","field should be of maximum length of"+metaData.opt("max-length"),fields,"ERROR",400);
			}
		}
	}

}