package com.revature.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.revature.repository.AccountDao;
import com.revature.repository.UserDaoInterface;
import com.revature.services.Account;
import com.revature.services.Customer;
import com.revature.services.User;

public class UserController {
	
	private Scanner sc;
	private UserDaoInterface userDao;
	
	public UserController(Scanner sc, UserDaoInterface userDao) {
		super();
		this.sc = sc;
		this.userDao = userDao;
		
	}
	
	public String getUserInput() {
		return sc.nextLine();
	 }
	
	public double getUserDouble() {
		return sc.nextDouble();
	}
	
	public User validateLogin(String username, String password) throws SQLException {
		if(username == null && password == null) {
			return null;
		} 
		User user = userDao.getUser(username, password);
		User testuser = new Customer(0, username, password, "null", "null", "null");
		if (user == null) {
			return null;
		}
		if (testuser.getPassword().equals(user.getPassword())) {
			return user;
		} else {
			return null;
		}
		
	}
	
	public User login() throws SQLException {
		System.out.println("Username:");
		String username = getUserInput();
		System.out.println("Password");
		String password = getUserInput();
		User user = validateLogin(username, password);
		if(user != null) {
			return user;
		} else {
			return null;
		}
	}
	
	public void getAccountInfo(int userId) throws SQLException {
		AccountDao accDao = new AccountDao();
		List<Account> accountInf = accDao.getAccountsByUserId(userId);
		for(Account i: accountInf) {
			System.out.println("Account number " + i.getAccNum() + " has a balance of " + i.getBalance());
		}
	}

	public void withdraw(int accountID) throws SQLException {
		AccountDao accDao = new AccountDao();
		Account account = accDao.getAccountbyAccNum(accountID);
		System.out.print("How much would you like to withdraw?");
		double amount = getUserDouble();
		double total = account.getBalance() - amount;
		accDao.updateAccountAmount(total, accountID);
		System.out.println(account);
	}
	
	
}
