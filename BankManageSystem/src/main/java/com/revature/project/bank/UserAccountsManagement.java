package com.revature.project.bank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAccountsManagement implements BankUsers<UserAccounts> {
	private String sql;
	private PreparedStatement stmt;
	private ResultSet rs;
	Connection connection = ConnectionBank.connectionResult();
	private static final Logger LOGGER = LogManager.getLogger(UserAccountsManagement.class.getName());
	
	public UserAccountsManagement() {
		super();
	}

	public void addUser(UserAccounts user) {
		try { 
			sql = " insert into bankmanage.useraccount (first_name, last_name, email, user_name, user_password) "
	                + " values (?, ?, ?, ?, ?) ";
	        stmt = connection.prepareStatement(sql);
    		stmt.setString(1, user.getFirst_name());
    		stmt.setString(2, user.getLast_name());
    	    stmt.setString(3, user.getEmail());
    	    stmt.setString(4, user.getUser_name());
    	    stmt.setString(5, user.getPassword());
    	    stmt.executeUpdate();
    	    LOGGER.info("Registration successful !!!");
        }  catch (SQLException e) {
		        e.printStackTrace();
	    }		
	}

	
	public void deleteUser(int id) {
		try { 
			sql = " delete from bankmanage.useraccount where user_id = ? ";
	        stmt = connection.prepareStatement(sql);
	        stmt.setInt(1, id);
    	    stmt.executeUpdate();
        }  catch (SQLException e) {
		        e.printStackTrace();
	    }			
	}

	
	public void updateUser(int id, String name) {
		try { 
			sql = " update bankmanage.useraccount set last_name = ? where user_id = ? ";
	        stmt = connection.prepareStatement(sql);
	        stmt.setInt(2, id);
	        stmt.setString(1, name);
    	    stmt.executeUpdate();
        }  catch (SQLException e) {
		        e.printStackTrace();
	    }				
	}

	public ResultSet selectUser(int id) {
		try { 
			sql = " select first_name, last_name, acct_num, balance "
					+ "from bankmanage.useraccount where user_id=?";
	        stmt = connection.prepareStatement(sql);
	        stmt.setInt(1, id);
	        rs = stmt.executeQuery();
	    }  catch (SQLException e) {
		        e.printStackTrace();
	    }	
		return rs;	
	}


	public ResultSet selectUser() {
		try { 
			sql = " select user_id, first_name, last_name "
					+ "from bankmanage.useraccount order by user_id";
	        stmt = connection.prepareStatement(sql);
	        rs = stmt.executeQuery();
	    }  catch (SQLException e) {
		        e.printStackTrace();
	    }	
		return rs;	
	}


	public String selectUserName(int id) {
		String name = new String("");
		try { 
			sql = " select first_name, last_name "
					+ "from bankmanage.useraccount where user_id = ?";
	        stmt = connection.prepareStatement(sql);
	        stmt.setInt(1, id);
	        rs = stmt.executeQuery();
	        while(rs.next()) {
	        	name = rs.getString(1) + " " + rs.getString(2);
	        }
	    }  catch (SQLException e) {
		        e.printStackTrace();
	    }	
		return name;	
	}

}
