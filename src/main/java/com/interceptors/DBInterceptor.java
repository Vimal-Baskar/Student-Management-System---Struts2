package com.interceptors;

import java.sql.SQLException;

import com.db.DBConnection;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class DBInterceptor implements Interceptor 
{

private static final long serialVersionUID = 1L;

	//	private Object db = null;
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		try {
			System.out.println("DB starts");
			new DBConnection().setConnection();
			System.out.println("DB initialised");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} 

	@Override
	public String intercept(ActionInvocation arg0) throws Exception {
		return arg0.invoke();
	}

	
}