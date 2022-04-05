import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Util.Business;
import Util.Constant;
import Util.RestaurantDataParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serial;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
/**
 * Servlet implementation class SearchDispatcher
 */

@WebServlet("/SearchDispatcher")
public class SearchDispatcher extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public SearchDispatcher() {
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = getServletContext();
        // get json file as stream, Initialize RestaurantDataParser by calling its initalize
        // method
        
        InputStream inputstream = RestaurantDataParser.class.getClassLoader().getResourceAsStream("/restaurant_data.json");
        String text = "";
		try {
			text = new String(inputstream.readAllBytes(), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
    	
        RestaurantDataParser.Init(text);
    	
        
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	
    	String searchingbyRN = "";
    	if (request.getParameter("Rn_or_Cat").contentEquals("1")) {
    		searchingbyRN = "restaurant name";
    	} else searchingbyRN = "category";
    	String sname = request.getParameter("name");
    	String sortby = request.getParameter("searchtype");
    	if (sortby.contentEquals("review_count")) {
    		sortby = "review count";
    	}
    	
    	
    	ArrayList<Util.Business> allBusinesses = RestaurantDataParser.getBusinesses(sname, sortby, searchingbyRN);
    	request.setAttribute("data", allBusinesses);
    	request.setAttribute("keyword", sname);
    	request.setAttribute("sortby", sortby);
    	request.setAttribute("searchtype", searchingbyRN);
    	
    	
    	request.getRequestDispatcher("search.jsp").forward(request, response);

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }
}