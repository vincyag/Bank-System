package com.revature.project.bankmanage;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.project.bank.*;

/**
 * Servlet implementation class Register
 */
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserAccounts user = null;
    UserAccountsManagement userAcct = new UserAccountsManagement();
    ManageAccount ma = new ManageAccount();
	private Connection dbcon;
	private static final Logger LOGGER = LogManager.getLogger(Logger.class.getName());
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
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
    		LOGGER.info("Connected to PostgreSQL database!");
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
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		register(request,response);
	}
	
	protected void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();

        try {
            String sql;
            Statement stmt;
	        ResultSet rs;
	        
	        boolean manage = false;
	        
	        String roleUser = request.getParameter("role");
	        if("employee".equals(roleUser)) {
	        	user = new Employee();
	        	manage = true;
	        	System.out.println(manage);
	        }
	        else if("customer".equals(roleUser)) {
	        	user = new Customer();
	        	manage = false;
	        }
	        
	        boolean login = false;
			String result = null;
	        
	        String firstName = request.getParameter("fName");
	        String lastName = request.getParameter("lName");
	        String email = request.getParameter("email");
	        String uname = request.getParameter("userName");
			String upass = request.getParameter("userPass");
			
			if ((firstName == null || "".equals(firstName))
					|| (lastName == null || "".equals(lastName))
					|| (email == null || "".equals(email))
					|| (uname == null || "".equals(uname))
					|| (upass == null || "".equals(upass))) {
				/*String error = "Mandatory Parameters Missing";
				request.getSession().setAttribute("errorReg", error);
				response.sendRedirect("login.html#register");*/
				out.print("var error = 'Mandatory Parameters Missing';");
			}
			
			sql = "select user_name from bankmanage.useraccount";
			stmt = dbcon.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				result = rs.getString(1);
				if(uname.equals(result)) {
					login = true;
					break;
				}	
			}
			
			if(login) {
				out.println("<h4>Username already exists</h4>");
				out.println("<a href=\"http://localhost:8080/BankManageSystem/\">Home</a>");
			}
			else {
				user.setFirst_name(firstName);
	        	user.setLast_name(lastName);
	        	user.setEmail(email);
	        	user.setUser_name(uname);
	        	user.setPassword(upass);
	        	userAcct.addUser(user);
	        	LOGGER.info("User account created.");
	        	out.println("<h2>Registration completed successfully!</h2><br/>");
	        	rs = ma.loginSuccess(user.getUser_name());
	        	int userId=0;
        		while(rs.next()) {
        			userId = rs.getInt(1);
        			String name = rs.getString(2) + " "+rs.getString(3);
        		}
        		ma.setRoleID(manage, userId);
	        	response.sendRedirect("login.html");
			}
			
			rs.close();
			stmt.close();
            dbcon.close();

        } catch (SQLException ex) {
            out.println("<h1>SQLexception</h1>");
        } finally {
            out.close();
        }
	}

}
