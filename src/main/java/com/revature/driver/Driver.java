package com.revature.driver;


import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.revature.repository.exception.AccNotFoundException;
import com.revature.repository.exception.UserNotFoundException;
import com.revature.services.User;

public class Driver {
	public static void main(String[] args) throws SQLException, UserNotFoundException, AccNotFoundException {
		System.out.println("Welcome to THE BANK");
		boolean exit = false;
		double loggedIn;
		while(exit != true) {
			MainMenu mm = new MainMenu(new Scanner(System.in));
			try {
			loggedIn = mm.loginMenu();
				if (loggedIn == 1) {
					User user = mm.login();
					mm.getUserMenu(user);
				} else if (loggedIn == 2) {
					mm.createUser();
					
				} else if (loggedIn == 3) {
					break;
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid input!");
			} catch (UserNotFoundException e) {
				System.out.println(e.getMessage());
			}
			
			
		}

	}
}
