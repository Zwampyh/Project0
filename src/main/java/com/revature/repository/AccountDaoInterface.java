package com.revature.repository;

import java.sql.SQLException;
import java.util.List;

import com.revature.services.Account;

public interface AccountDaoInterface {

	List<Account> getAccountsByUserId(int userId) throws SQLException;

	List<Integer> getAccountIDs();
	
	public void createAccount(int accID);
	


}
