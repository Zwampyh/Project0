package com.revature.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.services.Admin;
import com.revature.services.Customer;
import com.revature.services.Employee;
import com.revature.services.User;
import com.revature.util.DBConnecter;

public class UserDao implements UserDaoInterface{
	
	Logger consoleLogger;
	Logger fileLogger;
	
	public UserDao() {
		consoleLogger = LoggerFactory.getLogger("consoleLogger");
		fileLogger = LoggerFactory.getLogger("fileLogger");
	}

	@Override
	public User getUser(String username, String password) throws SQLException{
		consoleLogger.debug("Getting user with username: " + username);
		fileLogger.debug("Get User from Database");
		
		User user = null;
		
		final String sql = "SELECT * FROM user_info WHERE user_name = '"+username+"';";
		
		try (Connection connection = DBConnecter.getConnection();
			Statement statement = connection.createStatement();)
		{
			ResultSet set = statement.executeQuery(sql);
			
			if(set.next()) {
				if (set.getString(4).equals("customer")) {
				user = new Customer(
						set.getInt(1),
						set.getString(2),
						set.getString(3),
						set.getString(4),
						set.getString(5),
						set.getString(6)); 
				} else if(set.getString(4).equals("admin")) {
				user = (User) new Admin(
						set.getInt(1),
						set.getString(2),
						set.getString(3),
						set.getString(4),
						set.getString(5));
				} else if(set.getString(4).equals("employee")) {
				user = new Employee(
						set.getInt(1),
						set.getString(2),
						set.getString(3),
						set.getString(4),
						set.getString(5));		
				}

			}
		} catch (SQLException e) {
			consoleLogger.error(e.getMessage());
			fileLogger.error(e.toString());
		}
		
		return user;
	}

}
