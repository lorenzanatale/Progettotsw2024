<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Carrello</title>
    <link rel="stylesheet" href="style/carrello.css">
</head>
<body>
<div class="cart-container">
    <h1>Il tuo carrello</h1>
    <div id="cart-message" class="message"></div>
    <div id="cart-items" class="cart-items">
        <!-- Gli articoli del carrello verranno inseriti qui dinamicamente -->
    </div>
    <div id="cart-total" class="cart-total">
        <!-- Il totale del carrello verrà aggiornato qui -->
    </div>
    <button id="add-items-btn" class="hidden">Aggiungi articoli</button>
</div>
<script src="scripts/carrello.js"></script>
</body>
</html>
