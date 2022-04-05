<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



<!DOCTYPE html>


<html>
<head>
    <meta charset="UTF-8">
	<script type='text/javascript' src='config.js'></script>
    <title>Login / Register</title>
	<link href="https://fonts.googleapis.com/css2?family=Lobster&display=swap" rel="stylesheet">
    <link href="index.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <meta name="google-signin-client_id" content="589497735950-k5vh2lugrmn3oad2n915f65u4gppoker.apps.googleusercontent.com">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	
	<script src="https://apis.google.com/js/client:platform.js?onload=init" async defer></script> 
</head>
<body>


<!-- TODO -->
<%
	String email = request.getParameter("Email");
	if (email == null) email = "";
	
	String loginemail = request.getParameter("loginEmail");
	if (loginemail == null) loginemail = "";
	
	String name = request.getParameter("Name");
	if (name == null) name = "";
	
	String error_message = (String) request.getAttribute("error");
	String iserror = "false";
	if (error_message != null) iserror="true";
	
	

%>
<script>

 <!-- This code adapted from https://developers.google.com/identity/sign-in/web/sign-in-->

		
			function onSignIn(googleUser) {
		        // Useful data for your client-side scripts:
		        var profile = googleUser.getBasicProfile();
		        console.log("ID: " + profile.getId()); // Don't send this directly to your server!
		        console.log('Full Name: ' + profile.getName());
		        console.log('Given Name: ' + profile.getGivenName());
		        console.log('Family Name: ' + profile.getFamilyName());
		        console.log("Image URL: " + profile.getImageUrl());
		        console.log("Email: " + profile.getEmail());
		        var name = profile.getName();
		        var email = profile.getEmail();
		        // The ID token you need to pass to your backend:
		        var id_token = googleUser.getAuthResponse().id_token;
		        	
		        var encodedname = encodeURI(name);
		        var encodedemail = encodeURI(email);
		        console.log(encodedname);
		        console.log(encodedemail);
		        document.cookie ="GUsername=" + encodedname;
		        document.cookie ="GEmail=" + encodedemail;
		        window.location.href = 'http://localhost:8080/mdalbrig_CSCI201_Assignment2/index.jsp';		        

		        }
			    function renderButton() {
				      gapi.signin2.render('g-signin2', {
				        'width': 'btn-block',
				        'height': 42,
				        'longtitle': true,
				        'theme': 'dark',
				        'onsuccess': onSignIn
				      });
				}
			
			    
		</script>
		
			
			<script src="https://apis.google.com/js/platform.js?onload=renderButton" 
					onload="this.onload=function(){};handleClientLoad()"
	      			onreadystatechange="if (this.readyState === 'complete') this.onload()" async defer>
			</script>





<% if (iserror.contentEquals("false")) { %>
		<div style="position: absolute; top: 23px; right: 100px;">
			<a href="index.jsp" style=" color: #AFAFAF; margin-top: 15px; font-size: 20px;">Home </a>
			<a href="auth.jsp"  style=" color: #AFAFAF ; margin-top: 15px; margin-left: 15px; font-size: 20px;">Login / Register</a>	
		</div>
		<div style = "position: absolute; top: 14px; left: 80px;">       
			<a href="index.jsp"   style="font-family: Lobster, cursive;margin-top: 15px;  color: #dd0808; font-size:33px;">SalEats! </a>
		</div>
	
		<hr size="1" width="100%" style="color: #af0606; margin-top: 80px" />	



<% } else { %>	
		<div class="alert alert-danger" role="alert" style=" text-align:center">
			<c:out value = "<%=error_message%>" />
		</div>
		<div style="position: absolute; top: 70px; right: 100px;">
			<a href="index.jsp" style=" color: #AFAFAF; margin-top: 15px; font-size: 20px;">Home </a>
			<a href="auth.jsp"  style=" color: #AFAFAF ; margin-top: 15px; margin-left: 15px; font-size: 20px;">Login / Register</a>	
		</div>
		<div style = "position: absolute; top: 61px; left: 80px;">       
			<a href="index.jsp"   style="font-family: Lobster, cursive;margin-top: 15px;  color: #af0606; font-size:33px;">SalEats! </a>
		</div>
	
		<hr size="1" width="100%" style="color: #af0606; margin-top: 80px" />	

<% } %>	


    <div class="mx-auto" style="width: 90%;">
	    <div style="margin-top: 130px;" class="row">
			<div class="col">
				<p class="h2 mb-4"> Login </p>
				<form name="Login" action="LoginDispatcher" method="GET">
					<div class="form-group">	
							<label  for="loginemail"> Email </label>
							<input class="form-control mb-4" type="text" id="loginemail" name="loginemail" value="<%=loginemail %>" /> 		
					</div>
					<div class="form-group">	
							<label  for="loginpassword"> Password </label>
							<input class="form-control mb-4" type="password" name="loginpassword" id="loginpassword" />
					</div>
					
					<button style="background-color: #dd0808; color: white; font-family: Helvetica;" class="btn btn-large btn-block my-4" type="submit" name="submit" ><i class=" fa fa-sign-in"> </i> Sign In</button>		 	
					<div  id="g-signin2" class = "g-signin2" data-onsuccess= "onSignIn"></div>
					
				</form>
			</div>
			<div class="col">
				<p class="h2 mb-4"> Register</p>
				<form name="Register" action="RegisterDispatcher" method="GET">
					<div class="form-group">
						<label for="Email"> Email </label>
						<input class="form-control mb-4"  id="Email" 
							name="Email" value="<%=email%>"> 
					</div>
					<div class="form-group">
						<label for="Name">Name</label>
						<input class="form-control mb-4" type="text" id="Name" name="Name" value="<%=name%>"> 
					</div>
					<div class="form-group">
						<label for="Password">Password </label>
						<input class="form-control mb-4" type="password" name="password" id="password">
					</div>
					<div class="form-group">
						<label for="ConfirmPassword">Confirm Password </label>
						<input class="form-control mb-4" type="password" name="confirmpassword" id="confirmpassword" >
					</div>
					<div class="form-check">
						<input type="checkbox" name="checkbox" value = "checked" id="terms" class="form-check-input" /> 
						<label class="form-check-label" for="terms">  I have read and agree to all terms and conditions of SalEats.</label>
					</div>
						 <button style="background-color: #dd0808; color: white; font-family: Helvetica;" class="btn btn-large btn-block my-4" type="submit" name="submit" ><i class=" fa fa-user-plus"></i> Create Account</button>
				 	
				</form>
					
			</div>
		</div>
	</div>

	
		
		
</body>
</html>