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

/**
 * Servlet implementation class Account
 */
public class Account extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection dbcon;
	private static final Logger LOGGER = LogManager.getLogger(Logger.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Account() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		try {
    		Class.forName("org.postgresql.Driver");
    		dbcon = DriverManager.getConnection("jdbc:postgresql://localhost:5432/banksystem",
    				"postgres", "Rose112");
    		LOGGER.info("Connected to PostgreSQL database!");
    	} catch (ClassNotFoundException e) {
    		e.printStackTrace();
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		welcome(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		welcome(request, response);
	}
	
	protected void welcome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		HttpSession session=request.getSession();
		String uname=(String) session.getAttribute("userName");
		try {
			String sql = "select user_id, first_name, last_name from bankmanage.useraccount where user_name=?";
			PreparedStatement pstmt = dbcon.prepareStatement(sql);
			pstmt.setString(1, uname);
	        ResultSet rs = pstmt.executeQuery();
	        int id = 0;
	        String name = new String("");
			
			while(rs.next()) {
				id = rs.getInt(1);
				//out.print("\nWelcome ");
				name = rs.getString(2) + " " + rs.getString(3);
				//out.print(name + "!\n\n");
			}
			
			//session.setAttribute("userName", uname);
			
			double balance = 0.0;
			sql = "select balance "
					+ "from bankmanage.useraccount where user_id=?";
			pstmt = dbcon.prepareStatement(sql);
			pstmt.setInt(1, id);
	        rs = pstmt.executeQuery();
	        if(rs.next())
	        	balance = rs.getDouble(1);
	        
	        int accNum = 0;
	        sql = "select acct_num "
					+ "from bankmanage.useraccount where user_id=?";
			pstmt = dbcon.prepareStatement(sql);
			pstmt.setInt(1, id);
	        rs = pstmt.executeQuery();
	        if(rs.next())
	        	accNum = rs.getInt(1);
	        
	        out.println("<!DOCTYPE html>");
	        
	        out.println("<html><body>");
	        out.println("<h3>" + name + "</h2>");
	        out.println("<h4>Account Number: " + accNum + "</h4>");
	        out.println("<h4>Current Balance: " + balance + "</h4><br/>");
	        out.println("<h5><ul><li><a href=\"http://localhost:8080/BankManageSystem/account.html\">Services</a></li><br/>");
	        out.println("<li><a href=\"http://localhost:8080/BankManageSystem/\">Sign Out</a></li></ul></h5>");
	        out.println("</body></html>");
			
			}  catch (SQLException e) {
		        e.printStackTrace();
	    }
		
		//out.println("<h2>Welcome "+uname +"</h2><hr/>");
	}
}
