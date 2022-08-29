package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnecter {

	public static Connection getConnection() throws SQLException{
		return DriverManager.getConnection(
				System.getenv("db_url"),
				System.getenv("db_name"),
				System.getenv("db_pass"));
	}
	
}
