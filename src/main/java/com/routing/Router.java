package com.routing;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;
import com.utils.JSONConvertor;

import com.actions.*;

public class Router extends ActionSupport implements ServletRequestAware, ServletResponseAware {
	
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	private JSONObject responseJSON = null;
	
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public void get_students() 
	{
		try 
		{
			JSONObject json = JSONConvertor.getAllParameters(request);
			responseJSON = new StudentCrud().get(json);
			this.response.setContentType("application/json");
			this.response.setStatus(responseJSON.optInt("status_code"));
			responseJSON.remove("status_code");
			System.out.println(responseJSON);
			PrintWriter out = this.response.getWriter();
			out.write(responseJSON.toString());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void post_students() 
	{
		try 
		{
			JSONObject json = JSONConvertor.convertStringToJSON(request.getReader());
			responseJSON = new StudentCrud().post(json);
			this.response.setContentType("application/json");
			this.response.setStatus(responseJSON.optInt("status_code"));
			responseJSON.remove("status_code");
			PrintWriter out = this.response.getWriter();
			out.write(responseJSON.toString());
			Math.round(122.22);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
	}
	
	public void put_students() {
		try 
		{
			JSONObject json = new JSONObject();
			json.put("data", JSONConvertor.convertStringToJSON(request.getReader()));
			json.put("where", JSONConvertor.getAllParameters(request));
			responseJSON = new StudentCrud().put(json);
			this.response.setContentType("application/json");
			this.response.setStatus(responseJSON.optInt("status_code"));
			responseJSON.remove("status_code");
			PrintWriter out = this.response.getWriter();
			out.write(responseJSON.toString());
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void delete_students() {
		try 
		{
			JSONObject json = JSONConvertor.getAllParameters(request);
			responseJSON = new StudentCrud().delete(json);
			this.response.setContentType("application/json");
			this.response.setStatus(responseJSON.optInt("status_code"));
			responseJSON.remove("status_code");
			PrintWriter out = this.response.getWriter();
			out.write(responseJSON.toString());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void get_marks() {
		
	}
	
	public void post_marks() {
		
	}
	
	public void put_marks() {
		
	}
	
	public void delete_marks() {
		
	}
	
	public void students() {
		
	}
	
	public void staff() {
		
	}
	
	public void marks() {
		
	}
	
}