package com.revature.project.bank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountManagement implements Transactions{
	CallableStatement transaction;
	Statement statment;
	double amount;
	Connection connection = ConnectionBank.connectionResult();
	private static final Logger LOGGER = LogManager.getLogger(AccountManagement.class.getName());
	
	public AccountManagement() {
		super();
		LOGGER.info("Inside the Constructor of AccountManagement Class");
	}
	
	public AccountManagement(double amount) {
		super();
		this.amount = amount;
		LOGGER.info("Inside the Constructor of AccountManagement Class");
	}

	
	public void deposit(int id, BigDecimal amount) {
		try  { 
			transaction = connection.prepareCall("CALL bankmanage.depositamt(?,?)");  
			transaction.setInt(1, id); 
			transaction.setBigDecimal(2, amount);
			transaction.execute();
			/*PreparedStatement trans = connection.prepareStatement("CALL bankmanage.depositamt(?,?)");
			trans.setInt(1, id); 
			trans.setBigDecimal(2, amount);   
			trans.executeQuery();*/
			LOGGER.info("Successfully deposited!");
			
        }  catch (SQLException e) {
		        e.printStackTrace();
	    }		
	}

	
	public void withdraw(int id, BigDecimal amount) {
		try  { 
			transaction = connection.prepareCall("CALL bankmanage.withdrawamt(?,?)");  
			transaction.setInt(1, id); 
			transaction.setBigDecimal(2, amount);
			transaction.execute();
			LOGGER.info("Successfully withdraw funds!");
        }  catch (SQLException e) {
		        e.printStackTrace();
	    }		
	}

	
	public void transfer(int sid, int rid, BigDecimal amount) {
		try  { 
			transaction = connection.prepareCall("CALL bankmanage.transferamt(?,?,?)");  
			transaction.setInt(1,sid);   
			transaction.setInt(2,rid);  
			transaction.setBigDecimal(3,amount);  
			transaction.execute();
			LOGGER.info("Transfer successfull!");
        }  catch (SQLException e) {
		        e.printStackTrace();
	    }	
	}


	public ResultSet view() {
		
		return null;
	}
}
