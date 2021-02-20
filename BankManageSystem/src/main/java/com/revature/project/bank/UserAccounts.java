package com.revature.project.bank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class UserAccounts {
	private String first_name;
	private String last_name;
	private String email;
	private String user_name;
	private String password;
	private static final Logger LOGGER = LogManager.getLogger(UserAccounts.class.getName());
	
	public UserAccounts() {
		super();
		LOGGER.info("Inside the Constructor of UserAccounts Class");
	}

	public String getFirst_name() {
		LOGGER.info("First Name :" +first_name);
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		LOGGER.info("Last Name :" +last_name);
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getEmail() {
		LOGGER.info("Email :" +email);
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUser_name() {
		LOGGER.info("User Name :" +user_name);
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getPassword() {
		LOGGER.info("Password :" +password);
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}	
	
	public abstract String toString();
}
