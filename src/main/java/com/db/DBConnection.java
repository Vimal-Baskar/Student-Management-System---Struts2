package com.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DBConnection {
	static private Connection connect = null;
	private String user = "root";
	private String password = "root";
	
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
			if(!checkTableExists())
			{
				System.out.println("Creating tables environment ...");
				createTable();
			}
			else
			{
				System.out.println("Sry... Deleting existing table ...");
				connect.createStatement().executeUpdate("DROP TABLE student");
				System.out.println("Creating table environment ...");
				createTable();
			}
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
		
	private boolean checkTableExists() throws SQLException {
		ResultSet rs = connect.createStatement().executeQuery("show tables;");
		while(rs.next())
		{
			if(rs.getString(1).equals("student"))
			{
				return true;
			}
		}
		return false;
	}
	
	private void createTable() throws SQLException
	{
		connect.createStatement().executeUpdate("CREATE TABLE student (student_id INT PRIMARY KEY AUTO_INCREMENT, student_name VARCHAR(50) NOT NULL, student_dob DATE NOT NULL, student_mobile VARCHAR(20) DEFAULT NULL, student_email VARCHAR(50) NOT NULL, CONSTRAINT unique_email UNIQUE (student_email));");
	}
	
}