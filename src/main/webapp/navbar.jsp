<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Navbar</title>
<link href="style/navbar.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="scripts/searchbar.js"></script>
</head>
<body>
	<nav class="navbar navbar-expand-lg bg-body-tertiary">
	    <div class="container-fluid">
	        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarTogglerDemo01" aria-controls="navbarTogglerDemo01" aria-expanded="false" aria-label="Toggle navigation">
	            <span class="navbar-toggler-icon"></span>
	        </button>
	        <div class="collapse navbar-collapse" id="navbarTogglerDemo01">
	            <a class="navbar-logo"><img src="icon/logoss.png" id="logo"></a>
	            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
	                <li class="nav-item">
	                    <a class="nav-link active" aria-current="page" href="home.jsp">Home</a>
	                </li>
	                <li class="nav-item">
	                    <a class="nav-link" href="${pageContext.request.contextPath}/catalogoServlet">Prodotti</a>
	                </li>
	            </ul>
		            <form class="d-flex" role="search">
		                <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
		                <button class="btn btn-outline-success">Search</button>
		            </form>  
	           	<a href="#" class="navbar-profile" id="profile-link">
	        		<img src="icon/profile.png" id="profile">
	        		<div id="dropdown-menu" class="dropdown-menu" aria-labelledby="profile-link" style="display: none;">
	        		<% if (session != null && session.getAttribute("user") != null) { %>
	        			<a class="dropdown-item" href="profiloUtente.jsp">Profilo</a>
	        			<div class="dropdown-divider"></div>
	        			<a class="dropdown-item" href="${pageContext.request.contextPath}/logoutServlet">Logout</a>
	        		<% } else { %>
	        		<%	if (session.getAttribute("user") == null) { %>
            				<a class="dropdown-item" href="login.jsp">Login</a>
            		<% } 
            			}%>
	    			</div>
	    		</a>
	    	</div>    		
	    </div>
	</nav>
	
	<script src="scripts/navbar.js"></script>
	
</body>
</html>