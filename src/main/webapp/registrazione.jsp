<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <link href="style/registazione.css" rel="stylesheet">
    <link href="style/button.css" rel="stylesheet">
    <title>Registrazione</title>

</head>
<body>
<img src="icon/logoss.png" id="logo">
<main class="container">
    <h2>Registrazione</h2>
    <form id="registrationForm" action="Register" method="post">
        <div class="input-field">
            <label for="email">Email:</label>
            <input type="email" name="email" id="email" placeholder="Enter Your Email" required>
            <div class="underline"></div>
        </div>
        <div class="input-field">
            <label for="password">Password:</label>
            <input type="password" name="password" id="password" placeholder="Enter Your Password" required>
            <div class="underline"></div>
        </div>
        <div class="input-field">
            <label for="confirmPassword">Conferma Password:</label>
            <input type="password" name="confirmPassword" id="confirmPassword" placeholder="Confirm Your Password" required>
            <div class="underline"></div>
        </div>
        <div class="input-field">
            <label for="via">Via:</label>
            <input type="text" name="via" id="via" placeholder="Enter Your Address" required>
            <div class="underline"></div>
        </div>
        <div class="input-field">
            <label for="nazionalita">Nazionalità:</label>
            <input type="text" name="nazionalita" id="nazionalita" placeholder="Enter Your Nationality" required>
            <div class="underline"></div>
        </div>
        <div class="input-field">
            <label for="citta">Città:</label>
            <input type="text" name="citta" id="citta" placeholder="Enter Your City" required>
            <div class="underline"></div>
        </div>
        <div class="input-field">
            <label for="cap">CAP:</label>
            <input type="text" name="cap" id="cap" placeholder="Enter Your Postal Code" required>
            <div class="underline"></div>
        </div>
        <input type="submit" value="Registrati">
    </form>

    <div class="footer">
        <p>Hai già un account? <a href="login.jsp">Accedi</a></p>
    </div>
	
	<a href="home.jsp" class="back-button">
        <img src="icon/arrow.png" id="arrow">
    </a>

    <script src="scripts/formregistration.js"></script>
</main>
</body>
</html>
