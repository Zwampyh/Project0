package com.revature.repository;

import java.sql.SQLException;
import java.util.List;

import com.revature.services.User;

public interface UserDaoInterface {

	
	//CRUD
	
	//Create
	
	public void createUser(User user) throws SQLException;
	
	//Read
	
	public User getUser(String username, String password) throws SQLException;


	public List<Integer> getUserIDs();


	public List<String> getUserEmails();


	public List<String> getUsernames();

	public User getUserWithID(int userid) throws SQLException;
	
	//Update
	
	public void apply(Integer id);

	public void resetpending(int userid);


	



}
