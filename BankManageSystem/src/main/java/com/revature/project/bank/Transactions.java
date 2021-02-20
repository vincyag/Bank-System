package com.revature.project.bank;

import java.math.BigDecimal;
import java.sql.ResultSet;

public interface Transactions {
	public void deposit(int id, BigDecimal amount);
	public void withdraw(int id, BigDecimal amount);
	public void transfer(int sid, int rid, BigDecimal amount);
	public ResultSet view();
}
