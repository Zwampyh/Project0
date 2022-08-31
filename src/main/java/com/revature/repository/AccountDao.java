package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.services.Account;
import com.revature.util.DBConnecter;

public class AccountDao implements AccountDaoInterface{
	
	Logger consoleLogger;
	Logger fileLogger;
	
	public AccountDao() {
		consoleLogger = LoggerFactory.getLogger("consoleLogger");
		fileLogger = LoggerFactory.getLogger("fileLogger");
	}
	
	//Create
	public void createAccount(int accID) {
		fileLogger.debug("Creating account - " + accID);
		final String sql = "INSERT INTO account_info (acc_num, balance) values ("+accID+", 0);";
		
		try(Connection connection = DBConnecter.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);)
		{
			statement.execute();
			
			
		} catch (SQLException e) {
			consoleLogger.error(e.getMessage());
			fileLogger.error(e.toString());
		}
		
	
	}

	//Read
	
	@Override
	public List<Account> getAccountsByUserId(int userId) throws SQLException{
		fileLogger.debug("Get accounts from Database with user_id");
		
		List<Account> accounts = new LinkedList<>();
		List<Integer> accNums = new LinkedList<>();
		
		final String sql = "SELECT * FROM useracc_bridge WHERE user_id = '"+userId+"';";
		
		
		try (Connection connection = DBConnecter.getConnection();
			Statement statement = connection.createStatement();)
		{
			ResultSet set = statement.executeQuery(sql);
			
			while(set.next()) {
				accNums.add(set.getInt(2));
				
			}
			for(Integer i:accNums) {
				final String sql2 = "SELECT * FROM account_info WHERE acc_num = '"+i+"';";
				ResultSet set2 = statement.executeQuery(sql2);	
				while(set2.next()) {
					accounts.add(new Account(set2.getInt(1),set2.getDouble(2)));
					}
			
			}
		} catch (SQLException e) {
			consoleLogger.error(e.getMessage());
			fileLogger.error(e.toString());
		}
		
		return accounts;
	}

	public Account getAccountbyAccNum(int accNum) throws SQLException{
		fileLogger.debug("Get account - " + accNum);
		final String sql = "SELECT * FROM account_info WHERE acc_num = '"+accNum+"';";
		Account account = null;
		
		
		try (Connection connection = DBConnecter.getConnection();
				Statement statement = connection.createStatement();)
			{
				ResultSet set = statement.executeQuery(sql);
				
				if (set.next()) {
					account = new Account(set.getInt(1), set.getDouble(2));
				}		
			} catch (SQLException e) {
				consoleLogger.error(e.getMessage());
				fileLogger.error(e.toString());
			}
		return account;
	}

	@Override
	public List<Integer> getAccountIDs() {
		fileLogger.debug("Getting all AccountIDs");
			List<Integer> userAccounts = new LinkedList<>();
			
			final String sql = "Select acc_num from account_info;";
			
			try(Connection connection = DBConnecter.getConnection();
					Statement statement = connection.createStatement();)
			{
				ResultSet set = statement.executeQuery(sql);
				
				while(set.next()) {
					userAccounts.add(set.getInt(1));
				}
			}
			catch (SQLException e) {
				consoleLogger.error(e.getMessage());
				fileLogger.error(e.toString());
			}
			
			
			return userAccounts;
		}

	//Update

	public void updateAccountAmount(double amount, int accountNum) throws SQLException {
		fileLogger.debug("Updating account "+accountNum+" to have a balance of "+amount);
		final String SQL = "UPDATE account_info SET balance = "+amount+" where acc_num = "+accountNum+";";
		
		try(Connection connection = DBConnecter.getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL);)
		{	
			statement.execute();
			
		} catch (SQLException e) {
			
			consoleLogger.error(e.getMessage());
			fileLogger.error(e.toString());
		}
		
	}



	
	

}
		


