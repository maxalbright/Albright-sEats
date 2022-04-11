import javax.servlet.ServletException;
import java.sql.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Util.Constant;
/**
 * Servlet implementation class RegisterDispatcher
 */
@WebServlet("/RegisterDispatcher")
public class RegisterDispatcher extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
   

    /**
     * Default constructor.
     */
    public RegisterDispatcher() {
    }


    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //TODO
    	response.setContentType("text/html");
    	
    	String error = "";
    	
    	String email = request.getParameter("Email");
    	if (email == null) email = "";
    	
    	String name = request.getParameter("Name");
    	if (name == null) name = "";
    	
    	String password = request.getParameter("password");
    	if (password == null) password = "";
    	
    	String confirmpassword = request.getParameter("confirmpassword");
    	if (confirmpassword == null) confirmpassword = "";
    	
    	boolean termsandconditions;
    	if (request.getParameter("checkbox") == null) {
    		termsandconditions = false;
    	} else { termsandconditions = true;}
    	
    	
    	if (!termsandconditions) {
    		error += "You Must Agree to the Terms and Conditions to Register. ";
    	}
    	
    	else if (email.contentEquals("") || name.contentEquals("") 
    			|| password.contentEquals("") || confirmpassword.contentEquals("")) {
    		
    		error += "You Must Fill out all Forms to Register. ";
    		
    	} else {
    	
	    	if (!password.contentEquals(confirmpassword)){
	    		error += "Passwords Must Match During Registration. ";
	    	}
	    	
			Pattern p = Constant.emailPattern;
			Matcher m = p.matcher(email);
	    	if (!m.matches()) {
	    		error += "Invalid Email. ";    		
	    	}
	    	
	    	
	    	Pattern pname = Constant.namePattern;
	    	Matcher mname = pname.matcher(name);
	    	if (!mname.matches()) {
	    		error += "Invalid Name. ";
	    	}
    	}
    	if (error.contentEquals("")) {

	    		try {

	    			Class.forName("com.mysql.cj.jdbc.Driver");
	    			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurantdata", "root", "root");

	    			PreparedStatement userEmail = con.prepareStatement("SELECT COUNT(*) AS total FROM restaurantdata.accounts WHERE email=?");
	    			userEmail.setString(1, email);
	    			ResultSet ruserEmail = userEmail.executeQuery();

	    			ruserEmail.next();
	    			if (ruserEmail.getInt("total") > 0) {
	    				error += "Email Already in Use. ";
	    		    	request.setAttribute("error", error);
	   
	    				request.getRequestDispatcher("auth.jsp").include(request, response);
	    			} else {
	    				userEmail = con.prepareStatement("INSERT INTO restaurantdata.accounts (email, password, name) VALUES (?, ?, ?)");
	    				userEmail.setString(1, email.trim());
	    				userEmail.setString(2, password);
	    				userEmail.setString(3,  name);
	    				userEmail.executeUpdate();		
	    				
	    				/* Encode so spaces are allowed*/
	    				Cookie UsersName = new Cookie("UsersName", URLEncoder.encode(name, "UTF-8"));
						Cookie loggedin = new Cookie("loggedin", "true");
						UsersName.setMaxAge(60*60*24);
						loggedin.setMaxAge(60*60*24);
						response.addCookie(UsersName);
						response.addCookie(loggedin);
						response.sendRedirect("index.jsp");
	    			}
	    		
	    		} catch(SQLException e) {
	    			System.out.println(e);
	    		}
	    		catch(ClassNotFoundException e) {
	    			System.out.println(e);    			
	    		}
    	}
    	else {
	    	request.setAttribute("error", error);
			request.getRequestDispatcher("auth.jsp").include(request, response);
			
    	}
    
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	doGet(request,response);
    }


}