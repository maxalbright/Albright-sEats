package Util;
import java.util.ArrayList;
import java.util.List;



import java.io.Serializable;


/**
 * The class used to model a business
 */
public class Business {
	private String id;
	private String name;
	private String image_url;
	private String rating;
	

	
	private location location;
	
	private Category[] categories;
	
	private String price;
	private String display_phone;
	private String url;
	private String review_count;
	
	
	public Business(String id, String name, String image_url, String rating, location location, Category[] categories, String price, String phone, String url, String review_count) {
       
		this.id = id;
    	this.name = name;
    	this.image_url = image_url;
    	this.rating = rating;
    	this.url = url;
    	this.categories = categories;
    	this.price = price;
    	this.location = location;
    	this.display_phone = phone;
    	this.review_count = review_count;
    }
	public String getName() {
		return name;
	}
	public String getID() {
		return id;
	}
	public String getReviewcount() {
		return review_count;
	}
	
	public String getImageurl() {
		return image_url;
	}
	public String getRating() {
		return rating;
	}
	
	
	// the following two methods are necessary to print the rating as stars using jstl
	public int ishalfstar() {
		if (this.getRating() == null || this.getRating().equals(" ")) {
			return 0;
		}
		
		if (this.getRating().charAt(2) == '5') {
			return 1;
		}
		else {
			return 0;
		}
	}
	
	public int truncatedrating() {
		if (this.getRating() == null || this.getRating().equals(" ")) {
			return 0;
		}
		return (int)(Double.parseDouble(this.getRating()));
	}

	
	
	public String getUrl() {
		return url;
	}
	public String getPrice() {
		if (price == null) {price = "";}
		/*if (price =="") {
			price = "No Price Listed";
		}*/
		return price;
	}
	public String getPricewithNoPriceListed() {
		String s = this.getPrice();
		if (s.contentEquals("")) {
			s = "No Price Listed";
		}
		return s;
	}
	public String getDisplayAddress() {
		return location.get_display_address();
	}
	public Object[] getCategories() {
		return categories;
	}
	
	
	
	public List<String> getTitles() {
		List<String> tobereturned = new ArrayList<String>();
		for (int i = 0; i < categories.length; i++) {
						
			tobereturned.add(categories[i].getTitle());
		}
		return tobereturned;
	}
	
	// return all the category names in one string for display
	public String getAllCats_OneString() {
		String tobereturned ="";
		for (int i = 0; i < categories.length; i++) {
			
			tobereturned += (categories[i].getTitle());
			if (i != categories.length - 1) {
				tobereturned +=", ";
			}
		}
		return tobereturned;
	}
	
	public String getPhone() {
		return display_phone;
	}

}









