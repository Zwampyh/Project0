package com.revature.services;

public class Account {
	private int accNum;
	private double balance;
	
	public Account(int accNum, double balance) {
		this.accNum = accNum;
		this.balance = balance;
	}

	public int getAccNum() {
		return accNum;
	}

	public void setAccNum(int accNum) {
		this.accNum = accNum;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Account [accNum=" + accNum + ", balance=" + balance + "]";
	}
	
	
}
