package com.kseb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	final String driver="com.mysql.cj.jdbc.Driver";
	final String url="jdbc:mysql://localhost:3306/kseb";
	final String user="root";
	final String password="password@123";
	Connection con=null;
	
	public Connection getConnection() {
		try {
			Class.forName(driver);
			con=DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
}
