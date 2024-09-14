<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="java.util.List" %>    
<%@ page import="Model.prodotto.prodottoBean" %>
<%@ page import="Model.prodotto.prodottoDAO" %>
<%@ page import="Model.recensione.recensioneBean" %>
<%@ page import="Model.recensione.recensioneDAO" %>
<%@ page import="Model.ordine.*" %>
<%@ page import="Model.utente.utenteBean" %>
<%@ page import="Model.utente.utenteDAO" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css" rel="stylesheet"/>
<script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>

   	<% prodottoBean prodotto = (prodottoBean) request.getAttribute("prodotto"); %>
	<title><%=prodotto.getNome()%></title>
    <link rel="stylesheet" type="text/css" href="style/prodotto.css">
    <link rel="stylesheet" type="text/css" href="style/valutazioneStelle.css">
    <link rel="stylesheet" type="text/css" href="style/potrebbePiacerti.css">
    <link rel="stylesheet" type="text/css" href="style/scroll.css">

</head>
<body>
	<%@ include file="navbar.jsp" %>
	<div class="firstRow">
		<a href="${pageContext.request.contextPath}/catalogoServlet" class="back-button">
        	<img src="icon/arrow.png" id="arrow">
    	</a>
    </div>
    
	<% boolean isAdmin = session.getAttribute("isAdmin") != null && (Boolean) session.getAttribute("isAdmin");
	if (session.getAttribute("user") != null && isAdmin) { %>
		<div class="admin-button">
	    	<button id="editButton" class="adminButton">Modifica Prodotto</button>
	    	<button id="visibilityButton" class="adminButton"><%= prodotto.isVisibile() ? "Nascondi" : "Rendi visibile" %></button>
		</div>
	
	<script>
	window.onload = function() {
		//Gestione del pulsante VisibilityButton con ajax
		    var visibilityButton = document.getElementById('visibilityButton');
		    var productId = "<%=prodotto.getId()%>";
		    var isVisible = "<%= !prodotto.isVisibile() %>";
		
		    visibilityButton.onclick = function () {
		        swal({
		            title: "Sei sicuro?",
		            text: "Sicuro di voler cambiare la visibilità del prodotto?",
		            icon: "warning",
		            buttons: true,
		            dangerMode: true,
		        })
		            .then((willDelete) => {
		                if (willDelete) {
		                    var xhr = new XMLHttpRequest();
		                    xhr.open("POST", 'cambioVisibilitaServlet', true);
		                    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		                    xhr.onreadystatechange = function () {
		                        if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
		                            // Reindirizza il browser alla nuova pagina dopo aver ricevuto la risposta
		                            window.location.href = "prodottoServlet?id=" + productId;
		                        }
		                    }
		                    xhr.send("productId=" + productId + "&isVisible=" + isVisible);
		                }
		            });
		    };
	};
	</script>
	<% } %>
	
	   <div id="editProductForm" class="product-info-mod">
       <form action="${pageContext.request.contextPath}/modificaProdottoServlet" method="post">
	
	            <label for="nome">Nome:</label><input autofocus type="text" id="nome" name="nome" value="<%=prodotto.getNome()%>">
	
	            <label for="descrizione">Descrizione:</label>
	            <input type="text" id="descrizione" name="descrizione" value="<%=prodotto.getDescrizione()%>">
	
	            <label for="prezzo">Prezzo: </label>
	            <input type="number" id="prezzo" name="prezzo" value="<%=prodotto.getPrezzo()%>">
	
	            <input type="hidden" name="id" value="<%=prodotto.getId()%>">
	
	            <label for="categoria">Categoria:</label>
	            <select id="categoria" name="categoria">
	                <option value="Strumenti" <%=prodotto.getCategoria().equals("Strumenti") ? "selected" : ""%>>Strumenti</option>
	                <option value="Software" <%=prodotto.getCategoria().equals("Software") ? "selected" : ""%>>Software</option>
	                <option value="Attrezzatura" <%=prodotto.getCategoria().equals("Attrezzatura") ? "selected" : ""%>>Attrezzatura</option>
				</select>
	        	<input type="submit" value="Conferma Modifiche">
	        	
		</form>
		</div>
    
	<div class="product-details">
        <img class="product-image" src="<%=prodotto.getImgPath()%>">
  	</div>
    <div class="product-info-specs">
        <div id="product-info" class="product-info">
            <h1><%=prodotto.getNome()%></h1>
            <p><%=prodotto.getDescrizione()%></p>
        </div>
        
        <div id="product-specs" class="product-specs">
            <h2>Dettagli prodotto</h2>
            <h5>Prezzo: <%=prodotto.getPrezzo()%>€</h5>
            <h6>Categoria: <%=prodotto.getCategoria() %></h6>
        </div>
    </div>
    
    <div class="addToCart">
            <h3>Disponibilità: <%= prodotto.getDisponibilita() %></h3>
	        <label for="quantity">Quantità: </label>
	        <input type="number" id="quantity" name="quantity" min="1" max="<%= prodotto.getDisponibilita() %>" value="1">
	        <button class="addToCartButton" data-product-id="<%= prodotto.getId() %>">Aggiungi al Carrello</button>
	</div>
	
	<div class="reviewOrModify">
		<% utenteBean user = (utenteBean) session.getAttribute("user");
		if (session.getAttribute("user") != null) {
			long userId = user.getId();
			try (recensioneDAO recensioneDAO = new recensioneDAO();) {
				
				boolean hasReviewed = recensioneDAO.hasUserReviewedProduct(user.getId(), prodotto.getId());
				
				if (!hasReviewed) { %>
		 <button id="addReviewButton" class="addReviewButton">Aggiungi recensione</button>
	</div>
	
		<div id="addReview" class="review-form">
	    <form action="aggiuntaRecensioneServlet" method="post">
	        <div class="review-form-header">
	            <label for="titolo">Titolo:</label>
	            <input autofocus type="text" id="titolo" name="titolo" required>
	            <label class="rating-label">
	                <input class="rating" max="5" oninput="this.style.setProperty('--value', this.value)"
	                       step="0.5" type="range" value="1" name="valutazione">
	            </label>
	        </div>
	        <label for="commento">Commento:</label>
	        <textarea id="commento" name="commento" required></textarea>
	        <input type="hidden" id="idProdotto" name="idProdotto" value="<%=prodotto.getId()%>">
	        <input type="hidden" id="idUtente" name="idUtente" value="<%=user.getId()%>">
	        <input type="submit" value="Invia">
	    </form>
    	<% }
		} catch (Exception e) {
			e.printStackTrace();
    	}
        }
		%>
    	</div>
    	
    	<div class="reviews">
    		<h2 style="font-style: italic">Gli utenti dicono di noi...</h2>
    		
    		 <% try (recensioneDAO recensioneDAO = new recensioneDAO()) {
    				List<recensioneBean> recensioni = (List<recensioneBean>) recensioneDAO.doRetrieveByProduct(prodotto.getId());

    				if(recensioni.isEmpty()) { %>
    		<div class="emptyReviews">
	        	<h3 style="font-style: italic">Non ci sono recensioni per questo prodotto. Lascia una recensione!</h3><br>
	    	</div>
	  		<% } else {
	        	for (recensioneBean recensione : recensioni) { %>
		    <div class="review">
		        <h4>Utente n.<%= recensione.getIdUtente() %></h4>
		        <h3 style="display: inline-block"><%=recensione.getTitolo()%></h3>
		        <input class="rating inline" max="5" step="0.5" type="range" name="valutazione" disabled style="
		        --starsize: 1.5rem; --value: <%=recensione.getValutazione() %>">
		        <p><%=recensione.getCommento()%></p>
		        <p><%=recensione.getData()%></p>
		        
		     <% if (session.getAttribute("user") != null && isAdmin) { %>
			    <form action="cancellaRecensioneServlet" method="post">
		            <input type="hidden" id="idProdotto" name="idProdotto" value="<%=prodotto.getId()%>">
		            <input type="hidden" name="idRecensione" value="<%=recensione.getId()%>">
		            <input type="submit" class="deleteReviewButton" value="Elimina">
		        </form>
		     <% } %>
		    </div>  
	        <% }
            }
    		} catch (Exception e) {
		        e.printStackTrace();
		    }%>
		    </div>

		
		<h2 class="mayLike" style="font-style: italic">Potrebbe piacerti...</h2>
		<div class="scroll-container">
		    <button class="scroll-button left"><img src="icon/leftArrow.png" id="lArrow"></button>
		    <%@include file="prodottiConsigliati.jsp" %>
		    <button class="scroll-button right"><img src="icon/rightArrow.png" id="rArrow"></button>
		</div>    		
    
     <%@ include file="footer.jsp" %>
     
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
	<script src="scripts/prodotto.js"></script>
	<script src="scripts/scroll.js"></script>

</body>
</html>