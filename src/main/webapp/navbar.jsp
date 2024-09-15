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
	            <div class="ricerca">
		            <form class="d-flex" role="search">
		                <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search" onkeyup="searchFunction(this.value)">
		                <button class="btn btn-outline-success">Search</button>
		            </form>
		            <div id="searchResults" class="search-results"></div>  
		        </div>
		            
		            <script>
					var results = []; // Variabile globale per memorizzare i risultati della ricerca
					
					function searchFunction(query) {
					    if (query.length === 0) {
					        document.getElementById('searchResults').innerHTML = "";
					        document.getElementById('searchResults').classList.remove("show");
					        return;
					    }
					
					    var xhr = new XMLHttpRequest();
					    xhr.onreadystatechange = function() {
					        if (this.readyState == 4 && this.status == 200) {
					            results = JSON.parse(this.responseText); // Memorizza i risultati nella variabile globale
					            var html = "";
					
					            // Se i risultati sono più di 0, li mostro, altrimenti nascondo la lista
					            if (results.length > 0) {
					                html = "<ul>";
					                for (var i = 0; i < results.length; i++) {
					                    html += "<li><a href='prodottoServlet?id=" + results[i].id + "'>" + results[i].nome + "</a></li>";
					                }
					                html += "</ul>";
					                document.getElementById('searchResults').innerHTML = html;
					                document.getElementById('searchResults').classList.add("show");
					            } else {
					                // Nessun risultato, nascondo la lista
					                document.getElementById('searchResults').innerHTML = "";
					                document.getElementById('searchResults').classList.remove("show");
					            }
					        }
					    };
					    xhr.open("GET", "searchServlet?query=" + encodeURIComponent(query), true);
					    xhr.send();
					}
					
					// Aggiungi un event listener al pulsante "Search"
					document.querySelector('.btn-outline-success').addEventListener('click', function(event) {
					    event.preventDefault(); // Previene il comportamento di default del pulsante
					    if (results.length > 0) {
					        // Reindirizza al primo risultato
					        window.location.href = 'prodottoServlet?id=' + results[0].id;
					    }
					});
		            </script>
		            
		    <!-- Icona del carrello -->
		    <div class="navbar-cart">
				<form>
				<a href="${pageContext.request.contextPath}/Visualcarrello-servlet" class="navbar-cart">
					<img src="img/carrello.png" id="cart-icon" alt="Carrello">
				</a>
				</form>
			</div>
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
