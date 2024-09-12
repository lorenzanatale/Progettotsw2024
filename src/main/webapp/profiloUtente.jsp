<%@ page import="java.util.List" %>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="tswProj.RuntimeSQLException" %>
<%@ page import="Model.utente.*" %>
<%@ page import="Model.ordine.*" %>
<%@ page import="Model.prodottoOrdine.prodottoOrdineBean" %>
<%@ page import="Model.prodottoOrdine.prodottoOrdineDAO" %>
<%@ page import="Model.prodotto.*" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="scripts/profiloUtente.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<meta charset="UTF-8">
<title>Profilo Utente</title>
<link href="style/profiloUtente.css" rel="stylesheet">
</head>
<body>

	<%@ include file="navbar.jsp" %>

	<% boolean isAdmin = session.getAttribute("isAdmin") != null && (Boolean) session.getAttribute("isAdmin");
	if (session.getAttribute("user") != null && isAdmin) {
		response.sendRedirect("profiloAdmin.jsp");
	} else { %>
	
	<% try (utenteDAO userDAO = new utenteDAO()) {
    long id = (long) session.getAttribute("id");
    utenteBean user = userDAO.doRetrieveByKey(id);%>
  	
    <div class="info">
        <h1>Informazioni Utente</h1>    
        <div class="user-info">      
	        <p>Username: <%= user.getUsername() %></p>
	        <button id="editButton1" class="edit-button"
            onclick="document.getElementById('editUsernameForm').style.display='block'
                     document.getElementById('editButton1').style.display='none'">Modifica
            </button>
            <form id="editUsernameForm" action="profiloUtenteServlet" method="post" style="display: none;">
            	<input type="hidden" name="action" value="editUsername">
                <input type="text" id="newUsername" name="newUsername" placeholder="Nuovo username" class="edit-input">
                <span id="usernameError" style="color:red; display: none;">Username non valido. Riprovare.</span>
                <input type="submit" value="Conferma" class="edit-submit">
            </form>
        </div>    
        <div class="user-info">
	        <p>Email: <%= user.getEmail() %></p>
			<button id="editButton2" class="edit-button"
				onclick="document.getElementById('editEmailForm').style.display='block'
                     	 document.getElementById('editButton2').style.display='none'">Modifica
			</button>
			<form id="editEmailForm" action="profiloUtenteServlet" method="post" style="display: none;">
				<input type="hidden" name="action" value="editEmail">
				<input type="email" id="newEmail" name="newEmail" placeholder="Nuova email" class="edit-input">
				<span id="emailError" style="color red; display: none;">Email non valida. Riprovare.</span>
				<input type="submit" value="Conferma" class="edit-submit">
			</form>
		</div>
    
    <script>
    
	document.getElementById('editEmailForm').addEventListener('submit', function(event) {
	    event.preventDefault();
	    var newEmail = document.getElementById('newEmail').value;
	    var xhr = new XMLHttpRequest();
	    xhr.open('POST', 'profiloUtenteServlet', true);
	    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	    xhr.send('newEmail=' + encodeURIComponent(newEmail) + '&action=editEmail');
	    xhr.onload = function() {
	        if (xhr.status === 200 && xhr.responseText === 'valid') {
	            document.getElementById('emailError').style.display = 'none';
	            window.location.href = 'profiloUtente.jsp';
	        } else {
	            document.getElementById('emailError').style.display = 'block';
	        }
	    };
	});

    
	document.getElementById('editUsernameForm').addEventListener('submit', function(event) {
	    event.preventDefault();
	    var newUsername = document.getElementById('newUsername').value;
	    var xhr = new XMLHttpRequest();
	    xhr.open('POST', 'profiloUtenteServlet', true);
	    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	    xhr.send('newUsername=' + encodeURIComponent(newUsername) + '&action=editUsername');
	    xhr.onload = function() {
	        if (xhr.status === 200 && xhr.responseText === 'valid') {
	            document.getElementById('usernameError').style.display = 'none';
	            window.location.href = 'profiloUtente.jsp';
	        } else {
	            document.getElementById('usernameError').style.display = 'block';
	        }
	    };
	});
</script>
    
    <%  } catch (SQLException s) {
        s.printStackTrace();
        throw new RuntimeSQLException("Errore durante il recupero delle informazioni utente", s);
        } %>
        
   <% } %>    

    <div class="ordersSection">
    	<h1>Storico Ordini</h1>
    <% try (ordineDAO ordineDAO = new ordineDAO(); prodottoOrdineDAO prodottoOrdineDAO = new prodottoOrdineDAO()) {
       Collection<ordineBean> ordini = ordineDAO.doRetrieveByUser((long) session.getAttribute("id"));
       if (ordini.isEmpty()) { %>
	    <div class="empty-order">
	    	<p>Non hai effettuato nessun ordine...</p>
	    </div>
	    <% } %>
		<% Collection<prodottoOrdineBean> orderItems = null;
	       for (ordineBean ordine : ordini) {
	       	orderItems = prodottoOrdineDAO.doRetrieveByOrder(ordine.getId()); 
	    %>
	     <div class="order-box">
	     	<div class="order-header">
	        	<p>Data Ordine: <%= ordine.getData() %></p>
	            <p>Totale Ordine: <%= orderItems.stream()
	                                    .map(orderItem -> orderItem.getPrezzo() * orderItem.getQuantita())
	                                    .reduce(Double::sum)
	                                    .orElse(0.0) %>€
	            </p>
	            <p>ID Ordine: <%= ordine.getId() %></p>
	        </div>
	    <% for (prodottoOrdineBean orderItem : orderItems) { %>
	    <% try (prodottoDAO prodottoDAO = new prodottoDAO()) {
				prodottoBean prodotto = prodottoDAO.doRetrieveByKey(orderItem.getIdProdotto());
	    %>
	    <div class="order-item">
		    <% if (prodotto.isVisibile()) { %>
		    <a href="prodottoServlet?id=<%= prodotto.getId() %>">
		    	<img src="<%= prodotto.getImgPath() %>" alt="<%= prodotto.getNome() %>">
		    </a>
		    <% } else { %>
		    <img src="<%= prodotto.getImgPath() %>" alt="<%= prodotto.getNome() %>">
		    <% } %>
		    <div class="order-item-details">
		    	<p><%= prodotto.getNome() %></p>
		        <p><%= orderItem.getPrezzo() %>€</p>
		        <p>Quantità: <%= orderItem.getQuantita() %></p>
		    </div>
	    </div>
        <% } catch (SQLException s) {
            	s.printStackTrace();
                throw new RuntimeSQLException("Errore durante il recupero delle informazioni del prodotto", s);
           } %>
        <%
           }
        %>
         </div>

    <%
           }
        } catch (SQLException s) {
            s.printStackTrace();
            throw new RuntimeSQLException("Errore durante il recupero degli ordini", s);
        }
    %>
    </div>
    </div>
 

		<%@ include file="footer.jsp"%>
</body>
</html>