import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;

/**
 * Servlet implementation class LogoutDispatcher
 */
@WebServlet("/LogoutDispatcher")
public class LogoutDispatcher extends HttpServlet {


	
	
	
	@Serial
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       // delete cookies because they logged out
    	Cookie ck[] = request.getCookies();
    	for(int i=0;i<ck.length;i++){  
    		if (ck[i].getName().contentEquals("UsersName") 
    				||ck[i].getName().contentEquals("loggedin")
    				|| ck[i].getName().contentEquals("GUsername")
    				|| ck[i].getName().contentEquals("GEmail"))
    		{
    			ck[i].setValue("");
    			ck[i].setMaxAge(0);
    			response.addCookie(ck[i]);
    		}
    	} 
    	// go back to index page
    	request.getRequestDispatcher("index.jsp").include(request, response);
    	
    }

    /**
     * @throws ServletException 
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doGet(request, response);
    }

}
