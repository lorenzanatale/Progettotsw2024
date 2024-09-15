<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">
<title>Home</title>

	<link rel="stylesheet" href="style/potrebbePiacerti.css">
    <link rel="stylesheet" href="style/home.css">
    <link rel="stylesheet" type="text/css" href="style/scroll.css">

</head>
<body>

	<%@ include file="navbar.jsp" %>

	<div id="imgBenvenuto" class="imgBenvenuto">
    	<a href="catalogoServlet">
    		<img src="img/welcome.png" class="imgBenvenuto" alt="Benvenuto!">
    	</a>
	</div>


	<h2 class="mayLike" style="font-style: italic">Prodotti in evidenza</h2>
		<div class="scroll-container">
		    <button class="scroll-button left"><img src="icon/leftArrow.png" id="lArrow"></button>
		    <%@include file="prodottiConsigliati.jsp" %>
		    <button class="scroll-button right"><img src="icon/rightArrow.png" id="rArrow"></button>
		</div>    	

	<%@ include file="footer.jsp" %>
	<script src="scripts/scroll.js"></script>

</body>
</html>
