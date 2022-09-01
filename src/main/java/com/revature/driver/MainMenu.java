package com.revature.driver;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.revature.controller.UserController;
import com.revature.repository.AccountDao;
import com.revature.repository.AccountDaoInterface;
import com.revature.repository.UserDao;
import com.revature.repository.UserDaoInterface;
import com.revature.repository.exception.AccNotFoundException;
import com.revature.repository.exception.UserNotFoundException;
import com.revature.repository.AUBridgeDao;
import com.revature.repository.AUBridgeDaoInterface;
import com.revature.services.User;

public class MainMenu {
	
	private Scanner sc;
	UserDaoInterface uDao = new UserDao();
	AccountDaoInterface aDao = new AccountDao();
	AUBridgeDaoInterface auBDao = new AUBridgeDao();
	UserController userController = new UserController(new Scanner(System.in), uDao, aDao, auBDao);
	boolean loggedIn = false;
	
	public MainMenu(Scanner sc) {
		this.sc = sc;
		
	}
	
	
	public  String getUserString() {
		return sc.nextLine();
	 }
	public int getUserInt() {
		return sc.nextInt();
	}
	public  double getUserDub() {
		return sc.nextDouble();
	}
	

	public User login() throws SQLException, UserNotFoundException {
		User user = userController.login();
	if (user != null) {
		System.out.println("Welcome, " + user.getUsername() + "!");
		
		} else {
			System.out.println("User or password incorrect!");
			login();
			
		}
	return user;
	}

	public double loginMenu() throws SQLException {
		
		System.out.println("Menu - Type 1 to login to existing User, 2 to create new User, 3 to exit.");
		double choice = getUserDub();
		return choice;	
	}
	
	public void getUserMenu(User user) throws SQLException, UserNotFoundException, AccNotFoundException {
		if (user.getType().equals("customer")){
			customerMenu(user);
		} else if (user.getType().equals("employee")) {
			employeeMenu();
		} else if (user.getType().equals("admin")) {
			adminMenu();
		}
	}

	
	private void customerMenu(User user) throws SQLException, UserNotFoundException, AccNotFoundException {
		boolean looping = true;
		while(looping) {
			System.out.println("Menu: 1 - View account info, 2 - Withdraw, 3 - Deposit, 4 - Transfer, 5 - apply for new acccount, 6 - logout");
			
			try {
				int choice = getUserInt();
				switch(choice) {
				case 1: userController.getAllAccountInfo(user.getId());
					System.out.println();
					break;
				case 2: System.out.println("Which account would you like to withdraw from?");
					try {
						int choiceAccNum = getUserInt();
						boolean allowed = userController.validateAccount(user, choiceAccNum);
						if (allowed) {
						userController.withdraw(choiceAccNum);
						System.out.println("Success!");
						} else {
							System.out.println("invalid account number!");
						}
					} catch (InputMismatchException e) {
						System.out.println("Invalid input!");
					}
					break;
				case 3: System.out.println("Which account would you like to deposit to?");
					try {
						int choiceAccNum = getUserInt();
						boolean allowed = userController.validateAccount(user, choiceAccNum);
						if (allowed) {
						userController.deposit(choiceAccNum);
						System.out.println("Success!");
						} else { System.out.println("invalid account number!");}
					} catch (InputMismatchException e) {
						System.out.println("Invalid input!");
					}
					break;
				case 4: System.out.println("Which accounts would you like to transfer between?");
					userController.getAllAccountInfo(user.getId());
					try {
						
						System.out.println("Account 1");
						int choiceAccNum1 = getUserInt();
						System.out.println("Account 2");
						int choiceAccNum2 = getUserInt();
						boolean allowed = userController.validateAccount(user, choiceAccNum1);
						boolean allowed2 = userController.validateAccount(user, choiceAccNum2);
						if (allowed && allowed2) {userController.transfer(choiceAccNum1, choiceAccNum2);}
						else { System.out.println("invalid account!");}
					} catch (InputMismatchException e) {
						System.out.println("Invalid input!");
					}
					break;
				case 5: if (user.getPending().equals("notPending")) {
							userController.applyForNewAccount(user);
							user = uDao.getUser(user.getUsername(), user.getPassword());
							System.out.println("Applied!");
						} else {
							System.out.println("Can't apply for a new account yet!");
						}
					break;
				case 6:
					looping = false;
					break;
				default: System.out.println("Invalid input!");
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid input!");
				sc.nextLine();
			}

					
					
					
			
		}
	}


	private void employeeMenu() throws SQLException, UserNotFoundException {
		boolean looping = true;
		while (looping) {
			System.out.println("Menu: 1 - view all users and accounts 2 - view account information, 3 - view user information, 4 - approve or deny account, 5 - logout");
		
		
		try {
			int choice = getUserInt();
			switch (choice) {
			case 1 : 
				userController.getAllUserAndAccounts();
				break;
			case 2 :
				System.out.println("Who's accounts would you like to look at? userID:");
				int userId = getUserInt();
				userController.getAllAccountInfo(userId);
				break;
			case 3 :
				System.out.println("Which userID would you like the user information of?");
				int userId2 = getUserInt();
				userController.getUserwithID(userId2);
				break;
			case 4 :
				System.out.println("Which userID would you like to approve or deny?");
				int userId3 = getUserInt();
				System.out.println("1 - approve, 2 - deny");;
				int aOrD = getUserInt();
				userController.approveOrDeny(userId3, aOrD);
				System.out.println("Done!");
				break;
			case 5 : 
				looping = false;
				break;
			
			}
		}catch (InputMismatchException e) {
			System.out.println("Invalid input!");
			sc.nextLine();
			}
		}
	}


	private void adminMenu() throws SQLException, UserNotFoundException, AccNotFoundException {
		boolean looping = true;
		while (looping) {
			System.out.println("Menu: 1 - view all users and accounts 2 - view account information, 3 - view user information, 4 - approve or deny account, 5 - withdraw from an account, 6 - deposit into an account, 7 - transfer between two accounts, 8 - cancel an account, 9 - logout");
		
		
		try {
			int choice = getUserInt();
			switch (choice) {
			case 1 : 
				userController.getAllUserAndAccounts();
				break;
			case 2 :
				System.out.println("Who's accounts would you like to look at? userID:");
				int userId = getUserInt();
				userController.getAllAccountInfo(userId);
				break;
			case 3 :
				System.out.println("Which userID would you like the user information of?");
				int userId2 = getUserInt();
				userController.getUserwithID(userId2);
				break;
			case 4 :
				System.out.println("Which userID would you like to approve or deny?");
				int userId3 = getUserInt();
				System.out.println("1 - approve, 2 - deny");;
				int aOrD = getUserInt();
				userController.approveOrDeny(userId3, aOrD);
				System.out.println("Done!");
				break;
			case 5 :
				System.out.println("Which account would you like to withdraw from? Type 0 to exit.");
				try {
					int choiceAccNum = getUserInt();
					if (choiceAccNum == 0) {
						break;
					}
					userController.withdraw(choiceAccNum);
					System.out.println("Done!");
					} catch (InputMismatchException e) {
						System.out.println("Invalid input!");
					}
					break;
			case 6 :
				System.out.println("Which account would you like to deposit to? Type 0 to exit.");
				try {
					int choiceAccNum = getUserInt();
					if (choiceAccNum == 0) {
						break;
					}
					userController.deposit(choiceAccNum);
					System.out.println("Done!");
				} catch (InputMismatchException e) {
					System.out.println("Invalid input!");
				}
				break;	
			case 7 :
				System.out.println("Which accounts would you like to transfer between? Type 0 to exit.");
				try {
					System.out.println("Account 1:");
					int choiceAccNum1 = getUserInt();
					if (choiceAccNum1 == 0) {
						break;
					}
					System.out.println("Account 2:");
					int choiceAccNum2 = getUserInt();
					userController.transfer(choiceAccNum1, choiceAccNum2);
					System.out.println("Done!");
				} catch (InputMismatchException e) {
					System.out.println("Invalid input!");
				}
				break;
			case 8 :
				System.out.println("Which account would you like to freeze?");
				try {
					int choiceAccNum = getUserInt();
					userController.deleteAccountBridge(choiceAccNum);
					System.out.println("Done!");
				} catch (InputMismatchException e) {
					System.out.println("Invalid input!");
				}
				break;
			case 9 : 
				looping = false;
				break;
			
			}
		}catch (InputMismatchException e) {
			System.out.println("Invalid input!");
			sc.nextLine();
			}
		}
		
	}


	public void createUser() throws SQLException {
		userController.createUser();
		System.out.println("Done!");
		
	}
	
}	
	
