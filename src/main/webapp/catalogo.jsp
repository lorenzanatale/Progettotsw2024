<%@ page import="java.util.List"%>
<%@ page import="Model.prodotto.prodottoBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Catalogo</title>
<link href="style/catalogo.css" rel="stylesheet">

</head>
<body>
	<jsp:include page="navbar.jsp" />
	
	<% List<prodottoBean> prodotti = (List<prodottoBean>) request.getAttribute("prodotti");%>
	
	<div class="product-container">
	    <%for(prodottoBean prodotto : prodotti) { %>
			<div class="product-card">
				<img class="product-image" src="<%= prodotto.getImgPath()%>">
					<a href="product?id=<%=prodotto.getId()%>"></a>
                <div class="product-details">
					<h2 class="product-title"><%= prodotto.getNome()%></h2>
					<h3 class="product-description"><%= prodotto.getDescrizione()%></h3>
					<p class="product-price"><%=prodotto.getPrezzo()%>€</p>
				</div>
			</div>
	    <% } %>
    </div>
	<script src="scripts/catalogo.js"></script>
	<jsp:include page="footer.jsp" />

</body>
</html>