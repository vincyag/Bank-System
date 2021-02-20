package com.revature.project.bank;

import java.sql.ResultSet;

public interface BankUsers<UserAccounts> {
	public void addUser(UserAccounts user);
	public void deleteUser(int id);
	public void updateUser(int id, String name);
	public String selectUserName(int id);
	public ResultSet selectUser(int id);
	public ResultSet selectUser();
}
