package com.revature.project.bankmanage;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.project.bank.AccountManagement;
import com.revature.project.bank.ManageAccount;

/**
 * Servlet implementation class Withdraw
 */
public class Withdraw extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	ManageAccount ma = new ManageAccount();
	AccountManagement am = new AccountManagement();
	private Connection dbcon;
	private static final Logger LOGGER = LogManager.getLogger(Logger.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Withdraw() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    /**
	 * @see Servlet#init(ServletConfig)
	 */
    public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
    	
    	try {
    		Class.forName("org.postgresql.Driver");
    		dbcon = DriverManager.getConnection("jdbc:postgresql://localhost:5432/banksystem",
    				"postgres", "Rose112");
    		System.out.println("Connected to PostgreSQL database!");
    	} catch (ClassNotFoundException e) {
    		e.printStackTrace();
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		HttpSession session=request.getSession();
		String uname=(String) session.getAttribute("userName");
		
		try {
			String sql = "select user_id, first_name, last_name from bankmanage.useraccount where user_name=?";
			PreparedStatement pstmt = dbcon.prepareStatement(sql);
			pstmt.setString(1, uname);
	        ResultSet rs = pstmt.executeQuery();
	        int id = 0;
	       
			while(rs.next()) {
				id = rs.getInt(1);
			}
			
			if(request.getParameter("Confirm") != null) {
				String amount = request.getParameter("amount");
		        BigDecimal amt = new BigDecimal(amount);
		        am.withdraw(id, amt);
				RequestDispatcher rd = request.getRequestDispatcher("account");
				rd.forward(request,response);
			}
			else if(request.getParameter("Cancel") != null)
				response.sendRedirect("http://localhost:8080/BankManageSystem/account.html");
		
			}  catch (SQLException e) {
		        e.printStackTrace();
	    }

	}
}