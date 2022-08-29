package com.revature.driver;


import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.revature.controller.UserController;
import com.revature.repository.AccountDao;
import com.revature.repository.UserDao;
import com.revature.repository.UserDaoInterface;
import com.revature.services.Account;
import com.revature.services.User;

public class Driver {
	public static void main(String[] args) throws SQLException {
		System.out.println("Welcome to THE BANKEROONI");
		boolean exit = false;
		double loggedIn;
		while(exit != true) {
			MainMenu mm = new MainMenu(new Scanner(System.in));
		
			loggedIn = mm.loginMenu();
				if (loggedIn == 1) {
					User user = mm.login();
					mm.getUserMenu(user);
				} else if (loggedIn == 2) {
					//mm.createUser
					continue;
				} else if (loggedIn == 3) {
					break;
				}
		
			
			
		}
		
		
		
		/*
		UserDaoInterface uDao = new UserDao();
		UserController userController = new UserController(new Scanner(System.in), uDao);
		
		User user = userController.login();
		if (user != null) {
			userController.getAccountInfo(user);
		}
		
		AccountDao aDao = new AccountDao();
		try {
			System.out.println(aDao.getUsersAssociatedWithAccount(123));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/

	}
}
