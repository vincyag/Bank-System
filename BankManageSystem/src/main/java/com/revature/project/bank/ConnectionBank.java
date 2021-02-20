package com.revature.project.bank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionBank {
	private static final Logger LOGGER = LogManager.getLogger(ConnectionBank.class.getName());
	
	private static Connection connection;
	
	public static Connection connectionResult() {
		try {
		Class.forName("org.postgresql.Driver");
		connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/banksystem",
				"postgres", "Rose112");
		LOGGER.info("Connected to PostgreSQL database!");
		} catch (ClassNotFoundException e) {
    		e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return connection;	
	}
}
