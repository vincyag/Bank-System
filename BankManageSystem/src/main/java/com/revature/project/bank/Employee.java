package com.revature.project.bank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Employee extends UserAccounts{
	private double salary;
	private static final Logger LOGGER = LogManager.getLogger(Employee.class.getName());

	public Employee() {
		super();
		LOGGER.info("Inside the Constructor of Employee Class");
	}

	public double getSalary() {
		LOGGER.info("Salary :" +salary);
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}	
}
