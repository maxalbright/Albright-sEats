package Util;



import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
import java.util.Objects;
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
    private static String API_KEY = "2RWbycKHLQ0LlnRD2POew2OQKTDsz5aEnsvKEvibSfzwsiVEvcUFalrt_QEFWn86GIyRKROj78KIq2DsiwpN7tVJhuzIFBjifjL_9WE22cWSTcdD3sYY2FW0U7pMYnYx";
    

    /**
     * Initializes the DB with json data
     *
     * @param responseString the Yelp json string
     * 
     */
    public static List<Business> searchRestaurants(String restaurant, String location, String sort) {
        try {

            OkHttpClient client = new OkHttpClient().newBuilder().build();
            String builder = "https://api.yelp.com/v3/businesses/search" + "?term=" + restaurant 
            		+ "&location=" + location
            		+ "&sort_by=" + sort
            		+ "&limit=10";
            
            Request request = new Request.Builder().url(builder).method("GET",null).addHeader("Authorization","Bearer "+API_KEY).build();
            Response response = client.newCall(request).execute();

            String responseString = Objects.requireNonNull(response.body()).string();
            if (responseString.contains("error")) {
            	System.out.println("Yelp API Failure.");
            	return new ArrayList<Business>();
            }
            
            JsonElement content = JsonParser.parseString(responseString).getAsJsonObject().getAsJsonArray("businesses");
            return Arrays.asList(new Gson().fromJson(content, Business[].class));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return new ArrayList<Business>();
    }



    public static Business getRestaurant(String id) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            String builder = "https://api.yelp.com/v3/businesses/" + id;
            
            Request request = new Request.Builder().url(builder).method("GET",null).addHeader("Authorization","Bearer "+API_KEY).build();
            Response response = client.newCall(request).execute();

            String responseString = Objects.requireNonNull(response.body()).string();
            if (responseString.contains("error")) {
            	System.out.println("Yelp API Failure.");
            	return null;
            }
            
            JsonElement content = JsonParser.parseString(responseString).getAsJsonObject();
            return new Gson().fromJson(content, Business.class);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }
}
