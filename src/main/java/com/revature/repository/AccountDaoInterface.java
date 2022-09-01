package com.revature.repository;

import java.sql.SQLException;
import java.util.List;

import com.revature.repository.exception.AccNotFoundException;
import com.revature.services.Account;

public interface AccountDaoInterface {

	List<Account> getAccountsByUserId(int userId) throws SQLException;

	List<Integer> getAccountIDs();
	
	public void createAccount(int accID);

	Account getAccountbyAccNum(int accountID) throws SQLException, AccNotFoundException;

	void updateAccountAmount(double total, int accountID) throws SQLException;
	


}
