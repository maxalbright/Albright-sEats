package Util;



import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import javax.print.DocFlavor.URL;

import org.apache.tomcat.util.json.JSONParser;

/**
 * A class that pretends to be the Yelp API
 */
public class RestaurantDataParser {
    private static Boolean ready = false;
    private static Business[] allBusinesses;
    private static ParentBusiness pb;
    private static HashMap<String, Business> idtoBiz = new HashMap<String, Business>();

    /**
     * Initializes the DB with json data
     *
     * @param responseString the Yelp json string
     * 
     */
    public static void Init(String responseString) {
        if (ready) {
            return;
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //TODO check if you've done the initialization
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ready = true;

        

    	// must be done this way because the working direcgtory of this project is not src by default
  
        pb = new Gson().fromJson(responseString, ParentBusiness.class);
      
        allBusinesses = pb.businesses;
        for (int i = 0; i < allBusinesses.length; ++i) {
        	// map the restaurant id to the restaurant object to be used later
        	
        	idtoBiz.put(allBusinesses[i].getID(), allBusinesses[i]);
        	
        }

        
        //iterate the businessHelper array and insert every business into the DB
        try {
	        
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurantdata", "root", "root");
	        PreparedStatement ps;
	        
	        // disableing safe_updates allows easier deletion of data
	        ps = con.prepareStatement("SET SQL_SAFE_UPDATES = 0;");
	        ps.execute();
	        // must  be done to prevent errors when deleting things
	        ps = con.prepareStatement("SET FOREIGN_KEY_CHECKS=0;");
	        ps.execute();
	        
	        // delete all data in the tables because we don't want duplicate data
	        ps = con.prepareStatement("Delete From restaurantdata.category;");
	        ps.execute();
	        ps = con.prepareStatement("Delete From restaurantdata.rating_details;");
	        ps.execute();
	        ps = con.prepareStatement("Delete From restaurantdata.restaurant;");
	        ps.execute();
	        ps = con.prepareStatement("Delete From restaurantdata.restaurant_details;");
	        ps.execute();
	        
	        //set auto_increment back to 1 for readibility's sake
	        ps = con.prepareStatement("Alter Table restaurantdata.category AUTO_INCREMENT = 1;");
	        ps.execute();
	        
	        for (int i = 0; i < allBusinesses.length; i++) {
	        	
	        	
	        	ps = con.prepareStatement("INSERT INTO restaurantdata.Restaurant (restaurant_id, restaurant_name, details_id, rating_id) VALUES (?, ?, ?, ?)");
	        	ps.setString(1, allBusinesses[i].getID());
	        	ps.setString(2,  allBusinesses[i].getName());
	        	ps.setInt(3,  (i+1));
	        	ps.setInt(4,  (i+1));
	        	ps.execute();
	        	
	        	
	        	
	        	ps = con.prepareStatement("INSERT INTO restaurantdata.Restaurant_details (details_id, image_url, address, phone_no, estimated_price, yelp_url) VALUES (?, ?, ?, ?, ?, ?)");
	        	ps.setInt(1,  (i+1));
	        	ps.setString(2,  allBusinesses[i].getImageurl());
	        	ps.setString(3, allBusinesses[i].getDisplayAddress());
	        	ps.setString(4,  allBusinesses[i].getPhone());
	        	ps.setInt(5, allBusinesses[i].getPrice().length());
	        	ps.setString(6,  allBusinesses[i].getUrl());
	        	ps.execute();
	        	
	        	
	        	for (int j = 0; j < allBusinesses[i].getTitles().size(); j++) {
	        		
	            	ps = con.prepareStatement("INSERT INTO restaurantdata.Category (category_name, restaurant_id) VALUES (?, ?)");
	            	
	            	ps.setString(1, allBusinesses[i].getTitles().get(j)); 
	            	ps.setString(2, allBusinesses[i].getID());
	            	try {
						ps.execute();
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
	        	}
	
	        	
	        	ps = con.prepareStatement("INSERT INTO restaurantdata.Rating_details (rating_id, review_count, rating) VALUES (?, ?, ?)");
	        	ps.setInt(1,  (i+1));
	        	ps.setInt(2, Integer.parseInt(allBusinesses[i].getReviewcount()));
	        	ps.setDouble(3,  Double.parseDouble(allBusinesses[i].getRating()));
	        	ps.execute();
	        }
        } catch (SQLException e) {
        	System.out.println(e);
        } catch(ClassNotFoundException e) {
        	System.out.println(e);
        }
    }


    /**
     * @param keyWord    the search keyword
     * @param sort       the sort option (price, review count, rating)
     * @param searchType search in category or name
     * @return the list of business matching the criteria
     */
    public static ArrayList<Business> getBusinesses(String keyWord, String sort, String searchType) {
        ArrayList<Business> businesses = new ArrayList<Business>();
        try {
            if (searchType.contentEquals("restaurant name")) { //searching by restaurant name
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurantdata", "root", "root");
                PreparedStatement ps;
                String sforps = "SELECT r.restaurant_id, rd.estimated_price, rtd.review_count, rtd.rating \n"
            			+ "	FROM restaurantdata.Restaurant r,  restaurantdata.Restaurant_details rd, restaurantdata.Rating_details rtd\n"
            			+ "    WHERE r.restaurant_name LIKE '%" + keyWord + "%'\n"
            			+ "    AND r.details_id = rd.details_id\n"
            			+ "    AND r.rating_id = rtd.rating_id\n"
            			+ "    Order By ";
            	if (sort.contentEquals("price")) { // sort price increasing
            		sforps += "rd.estimated_price ASC;";
            	} else if (sort.contentEquals("rating")) { // sort rating decreasing
            		sforps += "rtd.rating DESC;";
 
            	} else { // sort by review count dereasing
            		sforps += "rtd.review_count DESC;";
            	}
                
            	ps = con.prepareStatement(sforps);


            	ResultSet rs = ps.executeQuery();
            	
            	while (rs.next()) {
            		
        			businesses.add(idtoBiz.get(rs.getString("restaurant_id")));
            		
            	}
            	
            	return businesses;	
            	
            } else { // searching by category
            	
            	
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurantdata", "root", "root");
                PreparedStatement ps;
                String sforps = "SELECT r.restaurant_id, c.category_name, rd.estimated_price, rtd.review_count, rtd.rating \n"
            			+ "	FROM restaurantdata.Restaurant r, restaurantdata.Category c, restaurantdata.Restaurant_details rd, restaurantdata.Rating_details rtd\n"
            			+ "    WHERE c.category_name LIKE '%" + keyWord + "%'\n"
            			+ "    AND c.restaurant_id = r.restaurant_id \n"
            			+ "    AND r.details_id = rd.details_id\n"
            			+ "    AND r.rating_id = rtd.rating_id\n"
            			+ "    Order By ";
            	if (sort.contentEquals("price")) { // sort price increasing
            		sforps += "rd.estimated_price ASC;";
            	} else if (sort.contentEquals("rating")) { // sort rating decreasing
            		sforps += "rtd.rating DESC;";
 
            	} else { // sort by review count dereasing
            		sforps += "rtd.review_count DESC;";
            	}
                
            	ps = con.prepareStatement(sforps);

            	ResultSet rs = ps.executeQuery();
            	
            	while (rs.next()) {
            		//remove duplicates because a business can be in more than one category
            		if(!businesses.contains(idtoBiz.get(rs.getString("restaurant_id")))) {
            			businesses.add(idtoBiz.get(rs.getString("restaurant_id")));
            		}
            	}
            	return businesses;
            	
            }
            
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
        	e.printStackTrace();
        }
        
        return businesses;
    }
}
