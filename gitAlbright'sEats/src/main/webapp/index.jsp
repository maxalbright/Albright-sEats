
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <!DOCTYPE html>
    <html>

    <head>
    	
        <meta charset="UTF-8">
        <title>Home</title>
		<link href="https://fonts.googleapis.com/css2?family=Beau+Rivage&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="index.css">
       	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <script src="https://kit.fontawesome.com/3204349982.js" crossorigin="anonymous"></script>
         <meta name="google-signin-client_id" content="589497735950-k5vh2lugrmn3oad2n915f65u4gppoker.apps.googleusercontent.com">
         <script src="https://apis.google.com/js/platform.js?onload=onLoad" async defer></script> 
    </head>

    <body>
    
    
     
	
	
	
	<%
	
	// get cookies to see if user is logged in
	
	Cookie ck[] = request.getCookies();
	String loggedin = "false";
	String UsersName = "";
	
	String GEmail = "";
	if (ck != null){
		for(int i=0;i<ck.length;i++){  
			if (ck[i].getName().contentEquals("GEmail") && !(ck[i].getValue().contentEquals(""))) {
				GEmail = java.net.URLDecoder.decode(ck[i].getValue(), "UTF-8");
				loggedin = "true";
			} 
			if (ck[i].getName().contentEquals("GUsername") && !(ck[i].getValue().contentEquals(""))) {
				
				UsersName = java.net.URLDecoder.decode(ck[i].getValue(), "UTF-8");
				
			} else {
				if (ck[i].getName().contentEquals("UsersName") && !(ck[i].getValue().contentEquals(""))) {
					UsersName = ck[i].getValue();
					loggedin = "true";
				}
				if (ck[i].getName().contentEquals("loggedin") && !(ck[i].getValue().contentEquals("") || ck[i].getValue().contentEquals("false"))) {
					loggedin = ck[i].getValue();
				}
			}
		}
	}
	
	
	// because tomcat does not allow spaces in cookies, replace the +'s with spaces that'
	// were used as placeholders
	UsersName = UsersName.replace("+", " ");

	

	if (loggedin.contentEquals("false")) { %>
		<div style="position: absolute; top: 23px; right: 100px;">
			<a href="index.jsp" style=" color: black; margin-top: 15px; font-size: 20px;">Home </a>
			<a href="auth.jsp"  style=" color: black ; margin-top: 15px; margin-left: 15px; font-size: 20px;">Login / Register</a>	
		</div>
	<% } else { %>	
		<div style = "position: absolute; top: 28px; left: 335px; font-size: 20px; color: black;">       
			<% out.println("Hi, " + UsersName + "!");  %>
		</div>
		<div style="position: absolute; top: 23px; right: 100px;">
			<a href="index.jsp" style=" color: black;font-size: 20px;">Home </a>
			<!--<a href="index.jsp"  style=" margin-left: 15px; color: #AFAFAF;font-size: 20px;" 	>Logout</a> > -->
			
			<%if (GEmail.contentEquals("")) {%> 
				<a href="LogoutDispatcher"  style="margin-left: 15px; color: black;font-size: 20px;" >Logout</a>
	 		
	 		<%} else {%>
	 			<a href="#" onclick = "glogout(); return false;"  style=" margin-left: 15px; color: black;font-size: 20px;" >Logout</a>
	 		
	 	
	 		<%} %>
	 	</div>
	<% } %>	
	<div style = "position: absolute; top: 14px; left: 80px;">       
		<a href="index.jsp"   style="font-family: Beau Rivage, cursive;margin-top: 15px;  color: #dd0808; font-size:38px;">Albright's Eats </a>
	</div>
	<hr  style="height: 2px; background-color: red; margin-top: 80px" />
		<br> <br>
		<div class = "gradient-border" id = "box">
			<img src = "mainpicture.jpeg" style="border-radius:25px; height: 500px; width: 1500px; margin-left: auto; margin-right: auto; display: block;"/>
		</div>
		<br>
		 
	
		 
		<form name="restaurantInfo" action="SearchDispatcher" method="GET">
			
			<div class="row d-flex justify-content-center">
				
					<input class="form-control" style = width:32% type="text" id="restaurantname" name="name" placeholder="Restaurant Name"  required/> 
			 		<input class="form-control" style = "width:32%; color:black;" type="text" id="location" name="location" placeholder="Location" required/> 
		 		
			</div> 
			<div class = "row d-flex justify-content-center">
				<button style="background-color: #dd0808; width:64%;color: white;" class="form-control" type="submit" name="submit" ><i class=" fa fa-search"></i></button> 
			</div><br>
			<div class = "row d-flex justify-content-center">
		 		<div class="form-check ml-4">
		 		
			 		 <input class="form-check-input" type="radio" name="searchtype" value="rating" id="rating" required/> 	  
					 <label class="form-check-label" for="price">  Rating</label>
				</div>
				<div class="form-check ml-4">
					 <input class="form-check-input" type="radio" name="searchtype" value="review_count" id="review_count" required/> 
					 <label class="form-check-label" for="rating">  Review Count </label>
				</div>	
				<div class="form-check ml-4">	 
				 	 <input class="form-check-input" type="radio" name="searchtype" value="distance" id="distance" required/> 
					 <label class="form-check-label" for="count">  Distance </label>
				</div>			
				<div class="form-check ml-4">	 
				 	 <input class="form-check-input" type="radio" name="searchtype" value="best_match" id="best_match" required/> 
					 <label class="form-check-label" for="count">  Best Match </label>
				</div>				
			</div>

		 </form>
	 <br><br><br><br>
	<div class=" mx-auto " style="width: 90%" >
		<footer class="page-footer font-small special-color-dark pt-4">
		
		  <!-- Footer Elements -->
		  <div class="container">
	        <div class="row d-flex justify-content-center">
		       
		        	<h5 ><i class="fa fa-user-circle-o"></i>  Max Albright</h5><br>
	        	
	        </div>
   	        <div class="row d-flex justify-content-center">
   	        	
	        	
			        
	                    <a href="https://github.com/maxalbright" class="nav-link"><i class="fa fa-github fa-lg" style="color:red;"></i></a>
	                    <a href="https://www.linkedin.com/in/maxalbright/" class="nav-link"><i class="fa fa-linkedin fa-lg"style="color:red;"></i></a>
	             
                
	        </div>
		   
		    <!--Grid row-->
		
		  </div>
		  <!-- Footer Elements -->
		
		  <!-- Copyright -->
		  <div class="footer-copyright text-center py-3"> 
		  <a href = "mailto:maxdanielalbright@gmail.com" style="color:black">Email Me</a>
		  </div>
		  <!-- Copyright -->
		
		</footer>
		</div>

	 

	<script>
	 <!-- This code adapted from https://developers.google.com/identity/sign-in/web/sign-in-->
	 // removes cookies for the user that is logged in through Google
    function handleClientLoad() {
        gapi.load('client:auth2', initClient);
      }
		function glogout(){
			
			console.log('Made it here');
			document.cookie ="GUsername=; expires=0;";
		    document.cookie ="GEmail=; expires=0";
			var auth2 = gapi.auth2.getAuthInstance();
	    	auth2.signOut().then(function () {
	    		console.log('User signed out.');
	    	});
	    	 window.location.href = 'http://localhost:8080/mdalbrig_CSCI201_Assignment2/index.jsp';
	    	
	    	
	 	 }
	    function onLoad() {
	        gapi.load('auth2', function() {
	          gapi.auth2.init();
	        });
	      }
	   
	
	</script>
	
	  
    </body>

    </html>