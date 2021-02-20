package com.revature.project.bank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class BankManagementSystem {
	Connection connection = ConnectionBank.connectionResult();
	private static final Logger LOGGER = LogManager.getLogger(BankManagementSystem.class);
	 
	public static void main(String[] args) {
		LOGGER.debug("Debug Message Logged !!!");
        LOGGER.info("Info Message Logged !!!");
		try  {
	        //System.out.println("Connected to PostgreSQL database!");
	        
	        UserAccounts user = null;
	        UserAccountsManagement userAcct = new UserAccountsManagement();
	        ManageAccount ma = new ManageAccount();
	        AccountManagement am = new AccountManagement();
	        
	        BigDecimal amount;  //equivalent for Numeric type in JDBC
	        ResultSet rs;
	        
	        Scanner scan = new Scanner(System.in);
	        System.out.println("\nThank you for choosing MilleniumBank.");
	        
	        rolesDisplay();
	        System.out.println("Enter your choice: ");
	        String option = scan.next().toLowerCase();
	        char roleUser = option.charAt(0);
	        boolean manage = false;
	        
	        if(roleUser == 'c') {
	        	user = new Customer();
	        	manage = false;
	        }
	        else if(roleUser == 'e') {
	        	user = new Employee();
	        	manage = true;
	        }
	        else
	        	System.out.println("Goodbye!");
	        
	        
	        int choice;
	        
	        do {
	        	userMenu();
	        	System.out.println("Enter your choice: ");
		        choice = scan.nextInt();
		        
	        	switch(choice) {
			        case 1:
			        	System.out.println("First Name: ");
			        	user.setFirst_name(scan.next());
			        	System.out.println("Last Name: ");
			        	user.setLast_name(scan.next());
			        	System.out.println("Email: ");
			        	user.setEmail(scan.next());
			        	System.out.println("User Name: ");
			        	user.setUser_name(scan.next());
			        	System.out.println("Password: ");
			        	user.setPassword(scan.next());
			        	userAcct.addUser(user);
			        	LOGGER.info("User account created.");
		
			        	rs = ma.loginSuccess(user.getUser_name());
			        	int userId=0;
		        		while(rs.next()) {
		        			userId = rs.getInt(1);
		        			String name = rs.getString(2) + " "+rs.getString(3);
		        		}
			        	System.out.println("Enter the initial amount to be deposited: ");
			        	BigDecimal amtBd = scan.nextBigDecimal();
			        	double amt = amtBd.doubleValue();
			        	while(ma.checkAmount(amt) == -1) {
			        		System.out.println("Amount cannot be negative! Enter a positive value: ");
			        		amtBd = scan.nextBigDecimal();
				        	amt = amtBd.doubleValue();
			        	}
			        	boolean approve = ma.approveAccount(amt);
			        	if(approve) {
			        		ma.setRoleID(manage, userId);
			        		ma.createAcct(userId);
			        		ma.setBalance(userId, amtBd);
			        		LOGGER.info("Your bank account has been created. You may login to acess the services.");
			        		userMenu();
			        		System.out.println("Enter login status: ");
					        choice = scan.nextInt();
			        	}
			        	else {
			        		System.out.println("You may exit the system and talk to a representative about your options.");
			        		userMenu();
					        System.out.println("Enter login status: ");
					        choice = scan.nextInt();
				        	break;
			        	}
			        	break;
			        case 2:
			        	System.out.println("User Name: ");
			        	user.setUser_name(scan.next());
			        	System.out.println("Password: ");
			        	user.setPassword(scan.next());
			        	//ma.loginCheck(user.getUser_name(), user.getPassword());
			        	int id = 0;
			        	if(ma.loginCheck(user.getUser_name(), user.getPassword())) {
			        		rs = ma.loginSuccess(user.getUser_name());
			        		while(rs.next()) {
			        			id = rs.getInt(1);
			        			System.out.print("\nWelcome ");
			        			System.out.print(rs.getString(2) + " ");
			        			System.out.print(rs.getString(3) + "!\n\n");
			        		}
			        		System.out.print("\nYour current balance is: $" + ma.currentBalance(id) 
			        				+ " for account " + ma.acctNumber(id) + ".\n");
			        		
			        		char service;
			        		
				        	do {
				        		accountOptions();
				        		System.out.println("Enter your choice: ");
					        	option = scan.next().toUpperCase();
					        	service = option.charAt(0);
					        	String confirm = new String("N");
					        	
				        		switch(service) {
					        		case 'D':
					        			System.out.println("Deposit? Y/N");
					        			if(scan.next().equalsIgnoreCase("N"))
					        				break;
					        			while(confirm.equalsIgnoreCase("N")) {
						        			System.out.println("Enter the amount to deposit into your account: ");
						        			amount = scan.nextBigDecimal();
						        			System.out.println("You entered: " +amount);
						        			System.out.println("Confirm deposit? (Y/N)");
						        			confirm = scan.next();
						        			if(confirm.equalsIgnoreCase("Y")) {
							        			am.deposit(id, amount);
							        			System.out.println("Your new balance for account " + ma.acctNumber(id) 
							        					+ " is: $" + ma.currentBalance(id) + ".\n");
							        			break;
						        			}
					        			}
					        			break;
					        			
					        		case 'W':
					        			System.out.println("Withdraw? Y/N");
					        			if(scan.next().equalsIgnoreCase("N"))
					        				break;
					        			while(confirm.equalsIgnoreCase("N")) {
						        			System.out.println("Enter the amount to withdraw from your account: ");
						        			amount = scan.nextBigDecimal();
						        			System.out.println("You entered: " +amount);
						        			System.out.println("Confirm withdraw? (Y/N)");
						        			confirm = scan.next();
						        			if(confirm.equalsIgnoreCase("Y")) {
						        				LOGGER.info("Withdrawing $" +amount+ "from your account!");
						        				am.withdraw(id, amount);
							        			LOGGER.info("Your new balance for account " + ma.acctNumber(id) 
					        							+ " is: $" + ma.currentBalance(id) + ".\n");
							        			break;
						        			}
					        			}
					        			break;
					        			
					        		case 'T':
					        			System.out.println("Transfer? Y/N");
					        			if(scan.next().equalsIgnoreCase("N"))
					        				break;
					        			rs = userAcct.selectUser();
					        			System.out.println("\tID\t\tNmae\t");
					        			while(rs.next()) {
						        			System.out.println("\t" + rs.getInt(1) + "\t\t" 
						        					+ rs.getString(2) + " " + rs.getString(3) + "\n");
						        		}
					        			while(confirm.equalsIgnoreCase("N")) {
						        			System.out.println("Enter the amount to transfer: ");
						        			amount = scan.nextBigDecimal();
						        			int sendId = id;
						        			System.out.println("Enter the ID of the receiver: ");
						        			int receiverId = scan.nextInt();
						        			String receiverName = userAcct.selectUserName(receiverId);
						        			System.out.print("Transfer $" +amount+ " to " + receiverName+ ". ");
						        			System.out.println("Confirm transfer? (Y/N)");
						        			confirm = scan.next();
						        			if(confirm.equalsIgnoreCase("Y")) {
						        				LOGGER.info("Transferring $" +amount+ "from your account!");
							        			am.transfer(sendId, receiverId, amount);
							        			System.out.println("Your new balance for account " + ma.acctNumber(id) 
					        							+ " is: $" + ma.currentBalance(id) + ".\n");
						        			}
					        			}
						        		break;
							        			
					        		case 'E':
								        LOGGER.info("Thank you for using our services.");
								        LOGGER.info("Closing session!");
					        			break;
			        			}
				        		
				        	} while(service != 'E');	
			        	}
			        	else {
			        		choice = 3;
			        		break;
			        	}
			        	break;
			        	
			        case 3:
			        	userMenu();
				        System.out.println("Enter choice: ");
				        choice = scan.nextInt();
			        	break;
			        	
			        case 0:
			        	LOGGER.info("Signing Out!");
			        	rolesDisplay();
			        	System.out.println("Enter choice: ");
			        	option = scan.next().toLowerCase();
				        roleUser = option.charAt(0);
			        	if(roleUser == 'c')
				        	user = new Customer();
				        else if(roleUser == 'e')
				        	user = new Employee();
				        else
				        	LOGGER.info("Goodbye!");
				        break;
		        }
	        } while(choice != 0);
	        
	        
	        scan.close();
	        
		} catch (SQLException e) {
	        LOGGER.info("!!!Connection failure!!!");
	        e.printStackTrace();
		}
	}
	
	public static void userMenu() {
		System.out.print("\nChoose one of the following ---\n"
				+ "\n1: Register (Create user account.)"
				+ "\n2: Login"
				+ "\n3: Main Menu"
				+ "\n0: Logging out\n\n");
	}
	
	public static void rolesDisplay() {
		System.out.print("\n     Choose from the options below:     \n"
				+ "\nC: customer"
				+ "\nE: employee"
				+ "\nQ: quit\n\n");
	}	
	
	public static void accountOptions() {
		System.out.print("\n     Choose from the following services:     \n"
				+ "\n(D)eposit"
				+ "\n(W)ithdraw"
				+ "\n(T)ransfer"
				+ "\n(E)xit\n\n");
	}	
	
	public static void employeeMenu() {
		System.out.print("\n     Choose from the following options:     \n"
				+ "\n(C)reate"
				+ "\n(R)ead"
				+ "\n(U)pdate"
				+ "\n(D)elete"
				+ "\n(E)xit\n\n");
	}
}
