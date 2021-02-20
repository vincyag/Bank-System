package com.revature.project.bank;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ManageAccount {
	private String sql;
	CallableStatement transaction;
	PreparedStatement pstmt;
	ResultSet rs;
	Connection connection = ConnectionBank.connectionResult();
	private static final Logger LOGGER = LogManager.getLogger(AccountManagement.class.getName());
	
	public ManageAccount() {
		super();
		LOGGER.info("Inside the Constructor of ManageAccount Class");
	}

	public boolean approveAccount(double amount) {
		boolean approve = false;
		
		if(amount < 500.00) {
			 approve = false;
			 LOGGER.info("Application denied!");
		}
		 else if( amount > 100000.00 ) {
			 approve = false;
			 LOGGER.info("Application denied!");
		}
		 else{
			 approve = true;
			 LOGGER.info("Application approved!");
		}
		
		return approve;
	}
	
	/*public boolean approveAccount(String type, double amount) {
		
	}*/
	
	public boolean loginCheck(String uname, String pwd) {
		boolean login = false;
		String result = null;
		try  { 
			sql = "select user_password from bankmanage.useraccount where user_name=?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, uname);
	        rs = pstmt.executeQuery();
	        
	        while(rs.next()) {
        		//System.out.print("   " +rs.getString(1) +"\t");
	        	result = rs.getString(1);
	        }
		
	        if(result.equals(pwd)) {
	        	LOGGER.info("Login successful");
	        	login = true;
	        }
	        else {
	        	LOGGER.info("Login unsuccessful");
	        	login = false;
	        }
        }  catch (SQLException e) {
		        e.printStackTrace();
	    }	
		return login;
	}
	
	public ResultSet loginSuccess(String uname) {
		try  { 
			sql = "select user_id, first_name, last_name from bankmanage.useraccount where user_name=?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, uname);
	        rs = pstmt.executeQuery();
        }  catch (SQLException e) {
		        e.printStackTrace();
	    }
		 return rs;
	}
	
	public void createAcct(int id)
	{
		int number = id * 50;
		try  { 
			sql = " update bankmanage.useraccount set acct_num = ? where user_id = ? ";
	        pstmt = connection.prepareStatement(sql);
	        pstmt.setInt(2, id);
	        pstmt.setInt(1,number);
    	    pstmt.executeUpdate();
        }  catch (SQLException e) {
		        e.printStackTrace();
	    }		
	}
	
	public void setRoleID(boolean manage, int id)
	{
		try  { 
			sql = " update bankmanage.useraccount set role_id = ? where user_id = ? ";
	        pstmt = connection.prepareStatement(sql);
	        pstmt.setInt(2, id);
	        if(manage)
	        	pstmt.setInt(1,1);
	        else
	        	pstmt.setInt(1,2);
    	    pstmt.executeUpdate();
        }  catch (SQLException e) {
		        e.printStackTrace();
	    }		
	}
	
	public void setBalance(int id, BigDecimal amount)
	{
		try  { 
			sql = " update bankmanage.useraccount set balance = ? where user_id = ? ";
	        pstmt = connection.prepareStatement(sql);
	        pstmt.setInt(2, id);
	        pstmt.setBigDecimal(1,amount);
    	    pstmt.executeUpdate();
        }  catch (SQLException e) {
		        e.printStackTrace();
	    }		
	}
	
	public double currentBalance(int id) {
		double balance = 0;
		try  { 
			sql = "select balance "
					+ "from bankmanage.useraccount where user_id=?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, id);
	        rs = pstmt.executeQuery();
	        if(rs.next())
	        	balance = rs.getDouble(1);
        }  catch (SQLException e) {
		        e.printStackTrace();
	    }
		 return balance;
	}
	
	public int acctNumber(int id) {
		int accNum = 0;
		try  { 
			sql = "select acct_num "
					+ "from bankmanage.useraccount where user_id=?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, id);
	        rs = pstmt.executeQuery();
	        if(rs.next())
	        	accNum = rs.getInt(1);
        }  catch (SQLException e) {
		        e.printStackTrace();
	    }
		 return accNum;
	}
	
	public int checkAmount(double amt) {
		if(amt < 0)
			return -1;
		else
			return 0;
	}
}


