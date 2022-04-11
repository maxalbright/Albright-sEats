import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet implementation class LoginDispatcher
 */



@WebServlet("/LoginDispatcher")
public class LoginDispatcher extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    	response.setContentType("text/html");
    	String error = "";
    	String loginemail = request.getParameter("loginemail");
    	if (loginemail == null) {
    		if (request.getAttribute("loginemail") != null || !((String)request.getAttribute("loginemail")).contentEquals("")) {
    			loginemail = (String)request.getAttribute("loginemail");
    		} else {loginemail = "";}
    	}
    	
    	
    	
    	String loginpassword = request.getParameter("loginpassword");
    	if (loginpassword == null) {
    		if (request.getAttribute("loginpassword") != null || !((String)request.getAttribute("loginpassword")).contentEquals("")) {
    			loginpassword = (String)request.getAttribute("loginpassword");
    		} else {loginpassword = "";}
    	}
    	
    	
    	
    	if (loginemail.contentEquals("") || loginpassword.contentEquals("")) {
    		error += "You Must Fill out all Forms to Sign In. ";
    		request.setAttribute("error", error);
			request.getRequestDispatcher("auth.jsp").include(request, response);
    	} else {
    		try {
    			Class.forName("com.mysql.cj.jdbc.Driver");
    			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurantdata", "root", "root");
				PreparedStatement UPprepared = con.prepareStatement("SELECT COUNT(*) AS ACCTOTAL FROM restaurantdata.accounts WHERE email = ? AND password = ?");
				UPprepared.setString(1, loginemail);
				UPprepared.setString(2, loginpassword);
				ResultSet UPresult = UPprepared.executeQuery();
				UPresult.next();
				if (UPresult.getInt("ACCTOTAL") == 0) {
					error += "No Account with this Email and/or Password Exists. ";
    				request.setAttribute("error", error);
    				request.getRequestDispatcher("auth.jsp").include(request, response);
					
				}
				else {
					// find the user's name
					PreparedStatement p = con.prepareStatement("Select name from restaurantdata.accounts where email = ?");
					p.setString(1, loginemail);
					UPresult= p.executeQuery();
					UPresult.next();
					Cookie UsersName = new Cookie("UsersName", URLEncoder.encode(UPresult.getString("name"), "UTF-8"));
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
    		
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
