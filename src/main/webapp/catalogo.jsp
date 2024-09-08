<%@ page import="java.util.List"%>
<%@ page import="Model.prodotto.prodottoBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Catalogo Prodotti</title>
<link href="style/catalogo.css" rel="stylesheet">

</head>
<body>
	<%@ include file="navbar.jsp" %>
	
	<% boolean isAdmin = session.getAttribute("isAdmin") != null && (Boolean) session.getAttribute("isAdmin");
	if (session.getAttribute("user") != null && isAdmin) { %>
	<div id="firstRow">
		<button id="adminButton" class="adminButton">Aggiungi Prodotto</button>
	<% } %>
	</div>
	
	<script>
    document.getElementById('adminButton').addEventListener('click', function () {
        window.location.href = 'aggiungiProdotto.jsp';
    });
	</script>
	
	
	<% List<prodottoBean> prodotti = (List<prodottoBean>) request.getAttribute("prodotti"); %>
	
	<div class="product-container">
	    <% for(prodottoBean prodotto : prodotti) { 
	    	 if(!isAdmin && !prodotto.isVisibile()) {
	    		 continue;
	    	 } %>
			<div class="product-card">
				<a href="prodottoServlet?id=<%= prodotto.getId() %>">
					<img class="product-image" src="<%= prodotto.getImgPath() %>">
				</a>
                <div class="product-details">
					<h2 class="product-title"><%= prodotto.getNome() %></h2>
					<h3 class="product-description"><%= prodotto.getDescrizione() %></h3>
					<p class="product-price"><%= prodotto.getPrezzo() %>€</p>
				</div>
			</div>
	    <% } %>
    </div>
    
	<script src="scripts/catalogo.js"></script>
	<%@ include file="footer.jsp" %>

</body>
</html>