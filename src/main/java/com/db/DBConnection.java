package com.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DBConnection {
	static private Connection connect = null;
	private String user = "root";
	private String password = "";
	private String[] tableName = {"student"};
	
	public void setConnection() throws SQLException{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306?useUnicode=yes&characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true",
					user,
					password
					);
			System.out.println("Checking for database ...");
			if(!checkDatabaseExists())
			{
				System.out.println("Creating database environment ...");
				connect.createStatement().executeUpdate("create database school");
			}
			connect = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/school?useUnicode=yes&characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true",
							user,
							password
							);
			System.out.println("Checking for table ...");
			for(int i=0;i<tableName.length;i++)
			{
				if(!checkTableExists(tableName[i]))
				{
					System.out.println("Creating tables environment ...");
				}
				else
				{
					connect.createStatement().executeUpdate("DROP TABLE "+tableName[i]);
					System.out.println("Creating table environment ...");
				}
			}
			createTableStudent();
			System.out.println("DB connected");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		return connect;
	}
	
	private boolean checkDatabaseExists() throws SQLException {
		ResultSet rs = connect.createStatement().executeQuery("show databases;");
		while(rs.next())
		{
			if(rs.getString(1).equals("school"))
			{
				return true;
			}
		}
		return false;
	}
		
	private boolean checkTableExists(String tableName) throws SQLException {
		ResultSet rs = connect.createStatement().executeQuery("show tables;");
		while(rs.next())
		{
			if(rs.getString(1).equals(tableName))
			{
				return true;
			}
		}
		return false;
	}
	
	private void createTableStudent() throws SQLException
	{
		connect.createStatement().executeUpdate("CREATE TABLE student (student_id INT PRIMARY KEY AUTO_INCREMENT, student_name VARCHAR(50) NOT NULL, student_dob DATE NOT NULL, student_mobile VARCHAR(20) DEFAULT NULL, student_email VARCHAR(50) NOT NULL, CONSTRAINT unique_email UNIQUE (student_email));");
	}
	
}