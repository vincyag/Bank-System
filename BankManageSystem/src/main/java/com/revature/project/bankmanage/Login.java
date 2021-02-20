package com.revature.project.bankmanage;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection dbcon;
	private static final Logger LOGGER = LogManager.getLogger(Logger.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
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
		login(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		login(request, response);
	}
	
	protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		//out.println("Connected!");

        try {
            String sql;
            Statement stmt;
	        ResultSet rs;
	        
	        String uname = request.getParameter("userName");
			String upass = request.getParameter("userPass");
			
			boolean login = false;
			String result = null;
			
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
			HttpSession session=request.getSession();
			session.setAttribute("userName", uname);
			
			if(login) {
				sql = "select user_password from bankmanage.useraccount "
						+ "where user_name=?";
				PreparedStatement pstmt = dbcon.prepareStatement(sql);
				pstmt.setString(1, uname);
		        rs = pstmt.executeQuery();
				
		        while(rs.next()) {
		        	result = rs.getString(1);
		        }
				
				if(upass.equals(result)) {
					RequestDispatcher rd = request.getRequestDispatcher("account");
					rd.forward(request,response);
					LOGGER.info("Login Successful!");
				}
				else if(upass.equals(""))
		        {
					out.println("<h5>Password was not entered</h5><br/>");
		            response.sendRedirect("login.html");
		        }
				else {
					out.println("<h5>Incorrect username or password</h5><br/>");
					RequestDispatcher rd = request.getRequestDispatcher("Login");
					rd.include(request, response);
				}
			}
			else {
				out.println("<h5>Username does not exist!</h5><br/>");
				response.sendRedirect("login.html");
			}
			
            rs.close();
            stmt.close();
            dbcon.close();

            out.println("<h1>Servlet postgresServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } catch (SQLException ex) {
            out.println("<h1>SQLexception</h1>");
        } finally {
            out.close();
        }
	}
}
