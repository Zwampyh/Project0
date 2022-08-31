package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

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

	//Create
	
	public void createUser(User user) throws SQLException {
		fileLogger.debug("Created User in Database - " + user);
		final String sql = "INSERT INTO user_info (user_id, user_name, user_pass, user_type, user_email, user_req) values ("+user.getId()+",'"+user.getUsername()+"','"+user.getPassword()+"','"+user.getType()+"','"+user.getEmail()+"','"+user.getPending()+"')";
	
		try(Connection connection = DBConnecter.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);)
		{
			statement.execute();
			
			
		} catch (SQLException e) {
			consoleLogger.error(e.getMessage());
			fileLogger.error(e.toString());
		}
		
	
	}
	
	//Read
	
	@Override
	public User getUser(String username, String password) throws SQLException{
		fileLogger.debug("Get User from Database - " + username);
		
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

	public User getUserWithID(int userID){
		fileLogger.debug("Getting User from Database with ID - " + userID);
		User user = null;
		
		final String sql = "SELECT * FROM user_info WHERE user_id = '"+userID+"';";
		
		try (Connection connection = DBConnecter.getConnection();
			Statement statement = connection.createStatement();)
		{
			ResultSet set = statement.executeQuery(sql);
			
			if(set.next()) {
				user = new Customer(
						set.getInt(1),
						set.getString(2),
						set.getString(3),
						set.getString(4),
						set.getString(5),
						set.getString(6)); 


			}
		} catch (SQLException e) {
			consoleLogger.error(e.getMessage());
			fileLogger.error(e.toString());
		}
		
		return user;
	}

	public List<Integer> getUserIDs(){
		fileLogger.debug("Getting all UserIDs");
		List<Integer> userIDs = new LinkedList<>();
		
		final String sql = "Select user_id from user_info;";
		
		try(Connection connection = DBConnecter.getConnection();
				Statement statement = connection.createStatement();)
		{
			ResultSet set = statement.executeQuery(sql);
			
			while(set.next()) {
				userIDs.add(set.getInt(1));
			}
		}
		catch (SQLException e) {
			consoleLogger.error(e.getMessage());
			fileLogger.error(e.toString());
		}
		
		
		return userIDs;
	}
	
	public List<Integer> getUserID2(){
		fileLogger.debug("Getting only customer IDs");
		List<Integer> userIDs = new LinkedList<>();
		
		final String sql = "Select user_id from user_info where user_type = 'customer';";
		
		try(Connection connection = DBConnecter.getConnection();
				Statement statement = connection.createStatement();)
		{
			ResultSet set = statement.executeQuery(sql);
			
			while(set.next()) {
				userIDs.add(set.getInt(1));
			}
		}
		catch (SQLException e) {
			consoleLogger.error(e.getMessage());
			fileLogger.error(e.toString());
		}
		
		
		return userIDs;
	}
	
	public List<String> getUserEmails(){
		fileLogger.debug("Getting Emails");
		List<String> userEmails = new LinkedList<>();
		
		final String sql = "Select user_email from user_info;";
		
		try(Connection connection = DBConnecter.getConnection();
				Statement statement = connection.createStatement();)
		{
			ResultSet set = statement.executeQuery(sql);
			
			while(set.next()) {
				userEmails.add(set.getString(1));
			}
		}
		catch (SQLException e) {
			consoleLogger.error(e.getMessage());
			fileLogger.error(e.toString());
		}
		
		
		return userEmails;
	}
	
	public List<String> getUsernames(){
		fileLogger.debug("Getting all usernames");
		List<String> userUsernames = new LinkedList<>();
		
		final String sql = "Select user_name from user_info;";
		
		try(Connection connection = DBConnecter.getConnection();
				Statement statement = connection.createStatement();)
		{
			ResultSet set = statement.executeQuery(sql);
			
			while(set.next()) {
				userUsernames.add(set.getString(1));
			}
		}
		catch (SQLException e) {
			consoleLogger.error(e.getMessage());
			fileLogger.error(e.toString());
		}
		
		
		return userUsernames;
	}
	
	//Update

	public void apply(Integer userID) {
		fileLogger.debug("Changing user's application to Pending, ID - " + userID);
		final String sql = "UPDATE user_info SET user_req = 'Pending' WHERE user_id = "+userID+";";
		
			
		try(Connection connection = DBConnecter.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);)
		{	
			statement.execute();
			
			
		} catch (SQLException e) {
			
			consoleLogger.error(e.getMessage());
			fileLogger.error(e.toString());
		}
	}

	
	public void resetpending(int userID) {
		fileLogger.debug("Changing user's application to notPending, ID - " + userID);
		final String sql = "UPDATE user_info SET user_req = 'notPending' WHERE user_id = "+userID+";";
		
			
		try(Connection connection = DBConnecter.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);)
		{	
			statement.execute();
			
			
		} catch (SQLException e) {
			
			consoleLogger.error(e.getMessage());
			fileLogger.error(e.toString());
		}
	}


}

