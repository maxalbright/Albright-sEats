package Util;

public class location {
		String address1;
		String address2;
		String address3;
		String city;
		String zip_code;
		String country;
		String state;

		
		public location(String address1, String address2, String address3, String city, String zip_code, String country, String state) {
			this.address1 = address1;
			this.address2 = address2;
			this.address3 = address3;
			this.city = city;
			this.zip_code = zip_code;
			this.country = country;
			this.state = state;
				
		}
		public String get_display_address() {
			boolean isone = true;
			boolean istwo = true;
			boolean isthree = true;
			
			if (address1 == null || address1.contentEquals("")) {address1 = ""; isone = false;}
			if (address2 == null || address2.contentEquals("")) {address2 = ""; istwo = false;} 
			if (address3 == null || address3.contentEquals("")) {address3 = ""; isthree = false;}
			if (isthree) {
				return address1 + " " + address2 + " " + address3 + ", " + city + ", " + state + " " + zip_code + ", " + country;
			} else if (istwo) {
				return address1 + " " + address2 + ", " + city + ", " + state + " " + zip_code + ", " + country;
			} else if (isone) {
				return address1 + ", " + city + ", " + state + " " + zip_code + ", " + country;
			} else {
				return city + ", " + state + " " + zip_code + ", " + country;
			}
			
		}
	}