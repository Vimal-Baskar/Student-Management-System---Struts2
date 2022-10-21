package com.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.utils.DButil;


public class DB {
	
	private static Connection db = new DBConnection().getConnection();
	
	public static int insertData(String tableName,Map<String,Object> map) throws SQLException 
	{
		StringBuilder query = new StringBuilder();
		StringBuilder values = new StringBuilder();
		query.append("INSERT INTO ");
		query.append(tableName);
		query.append(" (");
		values.append(" (");
		int count=0;
		for (Map.Entry<String, Object> item : map.entrySet()) 
		{
			if(item.getValue()!=null) {
				if(!DButil.isString(item.getValue()) && (int)item.getValue()!=0) {
					if(count==1) 
					{
						query.append(" , ");
						values.append(" , ");
					}
					query.append(item.getKey());
					values.append(item.getValue());
					count=1;
				}
				else if(DButil.isString(item.getValue()))
				{
					if(count==1) {
						query.append(" , ");
						values.append(" , ");
					}
					query.append(item.getKey());
					values.append("'");
					values.append(item.getValue());
					values.append("'");	
					count=1;
				}
			}
		}
		query.append(") VALUES ");
		values.append(");");
		query.append(values);
		System.out.println(query.toString());
		PreparedStatement st;
		st = db.prepareStatement(query.toString(),Statement.RETURN_GENERATED_KEYS);
	    st.executeUpdate();
		ResultSet generatedKeys = st.getGeneratedKeys();
		if (generatedKeys.next()) {
			return generatedKeys.getInt(1);
		}
		else {
			return 0;
		}
	}
	
	public static int updateData(String tableName, Map<String,Object> map, Map<String,Object> whereTo) throws SQLException
	{
		
		StringBuilder query = new StringBuilder();
		query.append("UPDATE ");
		query.append(tableName);
		query.append(" SET ");
		int count=0;
		for (Map.Entry<String, Object> item : map.entrySet()) 
		{
			if(item.getValue()!=null) {
				if(!DButil.isString(item.getValue()) && (int)item.getValue()!=0) {
					if(count==1) 
					{
						query.append(" , ");
					}
					query.append(item.getKey());
					query.append("=");
					query.append(item.getValue());
					count=1;
				}
				else if(DButil.isString(item.getValue()))
				{
					if(count==1) {
						query.append(" , ");
					}
					query.append(item.getKey());
					query.append("=");
					query.append("'");
					query.append(item.getValue());
					query.append("'");	
					count=1;
				}
			}
		}
		int flag=0;
		for (Map.Entry<String, Object> item : whereTo.entrySet()) 
		{
			if(item.getValue()!=null) {
				if(!DButil.isString(item.getValue()) && (int)item.getValue()!=0) {
					if(flag==1) 
					{
						query.append(" and ");
					}
					else
					{
						query.append(" where ");
					}
					query.append(item.getKey());
					query.append("=");
					query.append(item.getValue());
					flag=1;
				}
				else if(DButil.isString(item.getValue()))
				{
					if(flag==1) {
						query.append(" and ");
					}
					else {
						query.append(" where ");
					}
					query.append(item.getKey());
					query.append("=");
					query.append("'");
					query.append(item.getValue());
					query.append("'");	
					flag=1;
				}
			}
		}
		System.out.println(query.toString());
		Statement st;
		st = db.createStatement();
		return st.executeUpdate(query.toString());
	}
	
	public static List<Map<String,Object>> getData(String tableName, Map<String,Object> map) throws SQLException
	{
		List<Map<String,Object>> response = new ArrayList<>();
		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM ");
		query.append(tableName);
		int count=0;
		for (Map.Entry<String, Object> item : map.entrySet()) 
		{
			if(item.getValue()!=null) {
				if(!DButil.isString(item.getValue()) && (int)item.getValue()!=0) {
					if(count==1) 
					{
						query.append(" and ");
					}
					else
					{
						query.append(" where ");
					}
					query.append(item.getKey());
					query.append("=");
					query.append(item.getValue());
					count=1;
				}
				else if(DButil.isString(item.getValue()))
				{
					if(count==1) {
						query.append(" and ");
					}
					else {
						query.append(" where ");
					}
					query.append(item.getKey());
					query.append("=");
					query.append("'");
					query.append(item.getValue());
					query.append("'");	
					count=1;
				}
			}
		}
		Statement st;
		st = db.createStatement();
		System.out.println(query.toString());
		ResultSet rs = st.executeQuery(query.toString());
		while(rs.next()) 
		{
			Map<String,Object> responseMap = new HashMap<>();
			for (Map.Entry<String, Object> item : map.entrySet()) 
			{
				responseMap.put(item.getKey(),rs.getString(item.getKey()));
			}
			response.add(responseMap);
		}
		return response;
	}
	
	public static int deleteData(String tableName, Map<String,Object> map) throws SQLException
	{
		StringBuilder query = new StringBuilder();
		query.append("DELETE FROM ");
		query.append(tableName);
		int count=0;
		for (Map.Entry<String, Object> item : map.entrySet()) 
		{
//			System.out.println(item.getKey()+" "+item.getValue());
			if(item.getValue()!=null) {
				if(!DButil.isString(item.getValue()) && (int)item.getValue()!=0) {
					if(count==1) 
					{
						query.append(" and ");
					}
					else
					{
						query.append(" where ");
					}
					query.append(item.getKey());
					query.append("=");
					query.append(item.getValue());
					count=1;
				}
				else if(DButil.isString(item.getValue()))
				{
					if(count==1) {
						query.append(" and ");
					}
					else {
						query.append(" where ");
					}
					query.append(item.getKey());
					query.append("=");
					query.append("'");
					query.append(item.getValue());
					query.append("'");	
					count=1;
				}
			}
		}
		PreparedStatement st;
		System.out.println(query.toString());
		st = db.prepareStatement(query.toString(),Statement.RETURN_GENERATED_KEYS);
	    return st.executeUpdate();
	}
	
	
	
	
	
}