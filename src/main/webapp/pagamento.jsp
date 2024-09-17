<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Pagamento</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script src="scripts/pagamento.js"></script>
    <link rel="stylesheet" href="style/pagamento.css">
</head>
<body>
<main class="container">
    <h1>Pagamento</h1>
    <form action="Pagamento" method="post">
        <label for="cardNumber">Numero di Carta:</label>
        <input type="text" id="cardNumber" name="cardNumber" required><br>

        <label for="expiryDate">Data di Scadenza:</label>
        <input type="text" id="expiryDate" name="expiryDate" placeholder="MM/YY" required><br>

        <label for="cvv">CVV:</label>
        <input type="text" id="cvv" name="cvv" required><br>

        <label for="cardHolder">Nome del Titolare:</label>
        <input type="text" id="cardHolder" name="cardHolder" required><br>

    </form>

    <br><br>
    <a href="#" onclick="validateAndSubmitForm(event)" class="button">Conferma Ordine</a>
</main>
</body>
</html>
