
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Details</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Beau+Rivage&family=Roboto:wght@300&display=swap" rel="stylesheet">
    <script src="https://kit.fontawesome.com/3204349982.js" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="index.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <meta name="google-signin-client_id" content="589497735950-k5vh2lugrmn3oad2n915f65u4gppoker.apps.googleusercontent.com">
    <script src="https://apis.google.com/js/platform.js?onload=onLoad" async defer></script> 
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	<script src="https://code.iconify.design/2/2.1.2/iconify.min.js"></script>
	<link rel="stylesheet" href="path/to/font-awesome/css/font-awesome.min.css">
	 
</head>

    
	<%
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
		<a href="auth.jsp"  style=" color: black; margin-top: 15px; margin-left: 15px; font-size: 20px;">Login / Register</a>	
	</div>
<% } else { %>	
	<div style = "position: absolute; top: 23px; left: 335px; font-size: 20px; color: black;">       
		<% out.println("Hi, " + UsersName + "!");  %>
	</div>
	<div style="position: absolute; top: 23px; right: 100px;">
		<a href="index.jsp" style=" color: black;font-size: 20px;">Home </a>
		<!--<a href="index.jsp"  style=" margin-left: 15px; color: #AFAFAF;font-size: 20px;" 	>Logout</a> > -->
		<%if (GEmail.contentEquals("")) {%> 
			<a href="LogoutDispatcher"  style=" margin-left: 15px; color: black;font-size: 20px;" >Logout</a>
 		
 		<%} else {%>
 			<a href="#" onclick = "glogout(); return false;"  style=" margin-left: 15px; color: black;font-size: 20px;" >Logout</a>
 		
 	
 		<%} %>		
 	</div>
<% } %>	
	<div style = "position: absolute; top: 14px; left: 80px;">       
	<a href="index.jsp"   style="font-family: Beau Rivage, cursive;margin-top: 15px;  color: #dd0808; font-size:33px;">Albright's Eats </a>
	</div>
	<hr  style="height: 2px; background-color: red; margin-top: 80px" />

<body>
<c:set var="name" value="${param.name}" scope="request"/>
<c:set var="address" value="${param.address}"/>
<c:set var="phone" value="${param.phone}"/>
<c:set var="categories" value="${param.categories}"/>
<c:set var="imageurl" value="${param.imageurl}"/>
<c:set var="price" value="${param.price}"/>
<c:set var="rating" value="${param.rating}"/>
<c:set var="truncatedrating" value="${param.truncatedrating}"/>
<c:set var="ishalfstar" value="${param.ishalfstar}"/>
<div class=" mx-auto " style="width: 90%" >
	<form name="restaurantInfo" action="SearchDispatcher" method="GET">
		<div class="row my-4">
				
				<input class="form-control" style = width:32% type="text" id="restaurantname" name="name" placeholder="Restaurant Name"  required/> 
		 		<input class="form-control" style = width:32% type="text" id="location" name="location" placeholder="Location"  required/> 
		 		<button style="background-color: #dd0808; width:5%;color: white;" class="form-control" type="submit" name="submit" ><i class=" fa fa-search"></i></button> 
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
	

	<br><br>
	<h2 style="color: #8C8C8C;font-family:Helvetica;">${name}</h2>
	
	<div class="row my-4">
		<img src = "${imageurl}" style=" object-fit: cover; border-radius:25px; height: 200px; width: 220px; margin-left: 85px; margin-right: auto; display: block;"/>
		<div class="col">
			<p>Address: ${address}</p>
			<p>${phone}</p>
			<p>Categories: ${categories}</p>
			<p>Price: ${price}</p>
			<p>Rating: 
				<c:forEach begin="0" end="${truncatedrating - 1}" step ="1" varStatus ="loop">
					<i style="font-size: 75%; color: gray; " class="fa fa-star" aria-hidden="true"></i>
				</c:forEach>
				<c:forEach begin="1" end="${ishalfstar}"  varStatus ="loop">
					<i style="font-size: 75%; color: gray; "class = 'fa fa-star-half'></i>
				</c:forEach>

			</p>

		</div>
	</div>
</div>

	<script>
	 <!-- This code adapted from https://developers.google.com/identity/sign-in/web/sign-in-->
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