<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="Model.prodotto.prodottoDAO" %>
<%@ page import="Model.prodotto.prodottoBean" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Prodotti Consigliati</title>
</head>
<body>

	<div class="product-container gallery">
	    <%try(prodottoDAO prodottoDAO = new prodottoDAO()){
			List<prodottoBean> prodottiConsigliati = (List<prodottoBean>) prodottoDAO.doRetrieveAll("ASC");
	
	        for (prodottoBean prod : prodottiConsigliati) {
	            if (prod.isVisibile()){}%>
	    <div class="product">
	        <a href="prodottoServlet?id=<%=prod.getId()%>">
	            <img src="<%=prod.getImgPath()%>" alt="<%=prod.getNome()%>">
	            <h4><%=prod.getNome()%></h4>
	        </a>
	        <p>Prezzo: <%=prod.getPrezzo()%>&#8364;</p>
	    </div>
	    <% } %>
	    <% }catch(Exception e){ %>
	    <h3>Errore di visualizzazione</h3>
	    <% }%>
	</div>
</body>
</html>