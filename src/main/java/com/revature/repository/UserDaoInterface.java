package com.revature.repository;

import java.sql.SQLException;

import com.revature.services.User;

public interface UserDaoInterface {

	
	//CRUD
	
	//Read
	
	public User getUser(String username, String password) throws SQLException;
	
	
}
