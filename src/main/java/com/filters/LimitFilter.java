package com.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.exceptions.GenericException;

import redis.clients.jedis.Jedis;

public class LimitFilter implements Filter{
	
	private Jedis connect = null;
	private Jedis permConnection = null;
	private Object lock = new Object();
	private int limit = 5;
	int flag=0;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		connect.close();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		String token = req.getHeader("token");
		if(token!=null && token.endsWith("abcd")) {
			try 
			{
	            boolean ok;
	            synchronized (lock) 
	            {
					connect = new Jedis();
	            	String var = connect.get(token);
	            	if(var==null)
	        		{
	            		connect.set(token,"0");
	        		}
	            	int x = Integer.parseInt(connect.get(token));
	            	x+=1;
	                ok = x <= limit;
	                if (!ok) 
			        {
	                	throw new GenericException("LIMIT REACHED", "Concurrent request limit reached",new JSONObject() , "ERROR", 400);
			        }
	            	connect.set(token, Integer.toString(x));
		            connect.close();
	            }
	            	permConnection = new Jedis();
	            	if (ok) 
	            	{
	            		chain.doFilter(request, response);
	            	}
	            	permConnection.close();
	        } 
			catch (GenericException e) 
			{
				((HttpServletResponse) response).setHeader("Content-Type", "application/json");
	            ((HttpServletResponse) response).setStatus(400);
				response.getOutputStream().write(e.getMessage().getBytes());
				return;
			}
	        synchronized (lock) 
	        {
	        	connect = new Jedis();
	            int x = Integer.parseInt(connect.get(token));
	            x-=1;
	            connect.set(token,Integer.toString(x));
		        connect.close();
	        }
		}
		else
		{
			chain.doFilter(request, response);
		}
		
	}

	@Override
	public void init(FilterConfig arg){
		System.out.println("Redis initialised ...");
		connect = new Jedis();
		permConnection = new Jedis();
		connect.flushAll();
		connect.close();
	}
	
}
