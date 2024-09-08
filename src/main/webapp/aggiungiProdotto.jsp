<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> Aggiungi Prodotto </title>

    <%@ include file="navbar.jsp" %>
    
    <link href="style/aggiungiProdotto.css" rel="stylesheet">
    
</head>
<body>

	<% boolean isAdmin = session.getAttribute("isAdmin") != null && (Boolean) session.getAttribute("isAdmin");
	if (session.getAttribute("user") != null && isAdmin) { %>

	<form action="aggiungiProdottoServlet" method="post" enctype="multipart/form-data" class="form-container form-grid">

        <h2> Inserisci dettagli prodotto </h2>

        <label for="nome"> Nome: </label>
        <input autofocus type="text" id="nome" name="nome" required>
		<br> 
		
        <label for="descrizione"> Descrizione: </label>
        <textarea id="descrizione" name="descrizione" required></textarea>
		<br> 

        <label for="categoria"> Categoria: </label>
        <select id="categoria" name="categoria">
            <option value="Strumenti">Strumenti</option>
            <option value="Software">Software</option>
            <option value="Attrezzatura">Attrezzatura</option>
        </select>
        <br> 
        
        <label for="prezzo"> Prezzo: </label>
        <input type="number" id="prezzo" name="prezzo" min="0" required>
		<br> 

        <label for="disponibilita"> Disponibilità: </label>
        <input type="number" id="disponibilita" name="disponibilita" min="1" required>
	    <br>
	    
        <label for="imgPath"> Seleziona immagine: </label>
        <input type="file" id="imgPath" name="imgPath" accept="image/*" required>
	    <br>
	    
        <input type="submit" value="Aggiungi prodotto" class="center-button-add">
        
    </form>
    
    <% } else { 
    response.sendRedirect("home.jsp");
   	} %>
    
    

    <%@ include file="footer.jsp" %>
    
</body>
</html>