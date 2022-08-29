package com.revature.repository;

import java.sql.SQLException;
import java.util.List;

import com.revature.services.Account;
import com.revature.services.User;

public interface AccountDaoInterface {

	List<Account> getAccountsByUserId(int userId) throws SQLException;
	
	List<Integer> getUsersAssociatedWithAccount(int accNum) throws SQLException;
	
}
