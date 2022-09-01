package com.revature.controller;

import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import com.revature.repository.AccountDao;
import com.revature.repository.AccountDaoInterface;
import com.revature.repository.UserDao;
import com.revature.repository.UserDaoInterface;
import com.revature.repository.exception.AccNotFoundException;
import com.revature.repository.exception.UserNotFoundException;
import com.revature.repository.AUBridgeDaoInterface;
import com.revature.services.Account;
import com.revature.services.Customer;
import com.revature.services.User;

public class UserController {
	
	private Scanner sc;
	private UserDaoInterface userDao;
	private AccountDaoInterface accountDao;
	private AUBridgeDaoInterface auBridge;
	
	public UserController(Scanner sc, UserDaoInterface userDao, AccountDaoInterface accountDao, AUBridgeDaoInterface auBridge) {
		super();
		this.sc = sc;
		this.userDao = userDao;
		this.accountDao = accountDao;
		this.auBridge = auBridge;
	}
	
	
	//UserInput
	
	public String getUserInput() {
		return sc.nextLine();
	 }
	
	public double getUserDouble() {
		return sc.nextDouble();
	}
	
	
	//login methods
	public User validateLogin(String username, String password) throws SQLException, UserNotFoundException {
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
	
	public User login() throws SQLException, UserNotFoundException {
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
	
	
	//Dao Interactions
	public List<Account> getAccounts(int userId) throws SQLException {
		AccountDao accDao = new AccountDao();
		List<Account> accounts = accDao.getAccountsByUserId(userId);
		return accounts;
	}
	
	public void getAllAccountInfo(int userId) throws SQLException {
		List<Account> accounts = getAccounts(userId);
		for(Account i: accounts) {
			System.out.println("Account number " + i.getAccNum() + " has a balance of $" + i.getBalance());
		}
	}
	
	public void getAccountNums(int userId) throws SQLException{
		List<Account> accounts = getAccounts(userId);
		for(Account i: accounts) {
			System.out.println(i);
		}
	}

	public void withdraw(int accountID) throws SQLException, AccNotFoundException {
		AccountDao accDao = new AccountDao();
		Account account;
		try {
			account = accDao.getAccountbyAccNum(accountID);
		System.out.print("How much would you like to withdraw?");
		double amount = getUserDouble();
		double total = account.getBalance() - amount;
		if (total < 0) {
			System.out.println("Not enough funds in account!");
		}
		else { 
		accDao.updateAccountAmount(total, accountID);
		}
		} catch (AccNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}


	public void deposit(int accountID) throws SQLException, AccNotFoundException{
		try {
		Account account = accountDao.getAccountbyAccNum(accountID);
		System.out.println("How much would you like to deposit?");
		double amount = getUserDouble();
		double total = account.getBalance() + amount;
		accountDao.updateAccountAmount(total, accountID);
		} catch (AccNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void transfer(int acc1, int acc2) throws SQLException{
		AccountDao accDao = new AccountDao();
		Account account1;
		try {
			account1 = accDao.getAccountbyAccNum(acc1);

		Account account2 = accDao.getAccountbyAccNum(acc2);
		System.out.println("How much would you like to transfer?");
		double amount = getUserDouble();
		double total1 = account1.getBalance() - amount;
		double total2 = account2.getBalance() + amount;
		if (total1 < 0 || total2 < 0) {
			System.out.println("Not enough funds!!");
		} else {
		accDao.updateAccountAmount(total1, acc1);
		accDao.updateAccountAmount(total2, acc2);
		System.out.println("Success!");
		}

		} catch (AccNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	
	public void applyForNewAccount(User user) {
		userDao.apply(user.getId());
	}

	public int getUniqueId() {
		List<Integer> userIDs = userDao.getUserIDs();
		List<Integer> randomList = new LinkedList<Integer>();
		for (int i = 1; i<100 ; i++) {
			randomList.add(i);
		}
		for (int i:userIDs) {
			randomList.remove(Integer.valueOf(i));
		}
		
		Collections.shuffle(randomList);
		int UniqueId = randomList.get(1);
		
		return UniqueId;
		
	}
	
	public int getUniqueAcc() {
		List<Integer> accIDs = accountDao.getAccountIDs();
		List<Integer> randomList = new LinkedList<Integer>();
		for (int i = 1; i<1000 ; i++) {
			randomList.add(i);
		}
		for (int i:accIDs) {
			randomList.remove(Integer.valueOf(i));
		}
		
		Collections.shuffle(randomList);
		int UniqueId = randomList.get(1);
		
		return UniqueId;
		
		
	}
	
	public String validateEmail(String email) {
		List<String> userEmails = userDao.getUserEmails();
			for  (String i:userEmails) {
				if (i.equals(email)) {
					return "bad";
				}
			}
			return "good";		
	}
	
	public String validateUsername(String username) {
		List<String> usernames= userDao.getUsernames();
			for  (String i:usernames) {
				if (i.equals(username)) {
					return "bad";
				}
			}
			return "good";		
	}
	
	public User newUser() {
		User user = null;
		while(true) {
			int userID = getUniqueId();
			System.out.println("Create username:");
			String username = getUserInput();
			String checkU = validateUsername(username);
			if (checkU.equals("bad")) {
				System.out.println("invalid username!");
				break;
			}
			System.out.println("Create password:");
			String password = getUserInput();
			String type = "customer";
			System.out.println("What's your email?");
			String email = getUserInput();
			String checkE = validateEmail(email);
			if (checkE.equals("bad")) {
				System.out.println("invalid email!");
				break;
				}
			String pending = "notPending";
			user = new Customer(userID, username, password, type, email, pending);
			break;
		
		}
		return user;
		
	}

	public void getAllUserAndAccounts() throws SQLException {
		UserDao uDao = new UserDao();
		AccountDao aDao = new AccountDao();
		List<Integer> userList = uDao.getUserID2();
		for(int i: userList) {
			List<Account> accounts = aDao.getAccountsByUserId(i);
			System.out.println("User "+i+" has accounts:");
			for(Account a: accounts) {
				System.out.println(a.getAccNum());
			}
		}
	}

	public void getUserwithID(int userId) throws SQLException, UserNotFoundException{
		try {
			User user = userDao.getUserWithID(userId);
			if (user.getType().equals("customer")) {
				System.out.println("UserID = "+user.getId()+", Username = "+user.getUsername()+", email = "+user.getEmail()+", New Account pending = "+user.getPending());
				
			} else {
				System.out.println("invalid ID!");
				}
			
		} catch (UserNotFoundException e){
			System.out.println("invalid ID!");
		}
	}
	public void createUser() throws SQLException {
		User user = newUser();
		if (user == null) {
			System.out.println("invalid creation!");
		} else {
			userDao.createUser(user);
			System.out.println("User created!");
		}
	
	}

	public void approveOrDeny(int userid, int aOrD) throws SQLException, UserNotFoundException {
		User user = userDao.getUserWithID(userid);
		if (user.getPending().equals("Pending")) {
			int accID;
			userDao.resetpending(userid);
			if (aOrD == 1) {
				accID = getUniqueAcc();
				accountDao.createAccount(accID);
				auBridge.createBridge(accID, userid);
				System.out.println("Created!");
			} else {
				System.out.println("Denied!");
			}
		} else {
			System.out.println("User is not requesting new account!");
		}
	}
	
	public boolean validateAccount(User user, int accID) throws SQLException {
		List<Integer> userIDs= new LinkedList<>();
		userIDs = auBridge.getUsersbyAccount(accID);
		boolean allowed = false;
		for(Integer i:userIDs) {
			if (i.equals(user.getId())) {
				allowed = true;
			}
		}
		return allowed;
	}

	
	public void deleteAccountBridge(int accNum) {
		auBridge.deleteBridge(accNum);
	}
}
