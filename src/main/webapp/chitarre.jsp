<%--
  Created by IntelliJ IDEA.
  User: loren
  Date: 10/08/2024
  Time: 11:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="style/stylesheet" href="chitarre.css">
    <title>Card Prodotti</title>

</head>
<body>
<div class="container">
    <div class="card">
        <div class="card-img-wrapper">
            <img src="https://via.placeholder.com/300" alt="Prodotto 1" class="card-img">
        </div>
        <div class="card-content">
            <h2 class="card-title">Prodotto 1</h2>
            <p class="card-description">Descrizione breve del prodotto 1. Molto interessante e utile.</p>
            <button class="card-btn">Scopri di più</button>
        </div>
    </div>
    <div class="card">
        <div class="card-img-wrapper">
            <img src="https://via.placeholder.com/300" alt="Prodotto 2" class="card-img">
        </div>
        <div class="card-content">
            <h2 class="card-title">Prodotto 2</h2>
            <p class="card-description">Descrizione breve del prodotto 2. Un altro prodotto interessante.</p>
            <button class="card-btn">Scopri di più</button>
        </div>
    </div>
    <!-- Aggiungi altre card qui -->
</div>
<script src="scripts/chitarre.js"></script>
</body>
</html>

