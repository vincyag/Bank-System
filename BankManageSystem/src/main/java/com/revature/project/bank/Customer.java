package com.revature.project.bank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Customer extends UserAccounts {
	private double balance;
	private static final Logger LOGGER = LogManager.getLogger(Customer.class.getName());

	public Customer() {
		super();
		LOGGER.info("Inside the Constructor of Customer Class");
	}

	public double getBalance() {
		LOGGER.info("Balance :" +balance);
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
