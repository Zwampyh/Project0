package com.revature.driver;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.revature.controller.UserController;
import com.revature.repository.UserDao;
import com.revature.repository.UserDaoInterface;
import com.revature.services.User;

public class MainMenu {
	
	private Scanner sc;
	UserDaoInterface uDao = new UserDao();
	UserController userController = new UserController(new Scanner(System.in), uDao);
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
	

	public User login() throws SQLException {
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
	
	public void getUserMenu(User user) throws SQLException {
		if (user.getType().equals("customer")){
			customerMenu(user);
		} else if (user.getType().equals("employee")) {
			employeeMenu();
		} else if (user.getType().equals("admin")) {
			adminMenu();
		}
	}

	
	private void customerMenu(User user) throws SQLException {
		while(true) {
			System.out.println("Menu: 1 - View account info, 2 - Withdraw, 3 - Deposit, 4 - Transfer, 5 - apply for new acccount.");
			
			try {
				int choice = getUserInt();
				switch(choice) {
				case 1: userController.getAccountInfo(user.getId());
					System.out.println();
					break;
				case 2: 
					System.out.println("Which account would you like to withdraw from? Type 0 to exit.");
					try {
						int choiceAccNum = getUserInt();
						if (choiceAccNum == 0) {
							break;
						}
						userController.withdraw(choiceAccNum);
					} catch (InputMismatchException e) {
						System.out.println("Invalid input!");
					}
				case 3:
					break;
				case 4:
					break;
				case 5:
					break;
				default: System.out.println("Invalid input!");
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid input!");
				sc.nextLine();
			}

					
					
					
			
		}
	}


	private void employeeMenu() {
		System.out.println("employee");
		
	}


	private void adminMenu() {
		// TODO Auto-generated method stub
		
	}
	
}	
	
