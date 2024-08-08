<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://kit.fontawesome.com/1ab94d0eba.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <title>Login</title>
    <link rel="stylesheet" href="style/login.css">
</head>
<body>
<img src="icon/logoss.png" id="logo">
<main class="container">
    <h2>Login</h2>
    <form id="loginForm" action="">
        <div class="input-field">
            <label for="email"></label><input type="email" name="email" id="email"
                   placeholder="Enter Your Email">
            <div class="underline"></div>
        </div>
        <div class="input-field">
            <input type="password" name="password" id="password"
                   placeholder="Enter Your Password">
            <div class="underline"></div>
        </div>

        <input type="submit" value="Continua">
    </form>

    <div class="footer">
        <span>O Accedi Tramite Social Media</span>
        <div class="social-fields">
            <div class="social-field twitter">
                <a href="#">
                    <i class="fab fa-twitter"></i>
                    Accedi con Twitter
                </a>
            </div>
            <div class="social-field facebook">
                <a href="#">
                    <i class="fab fa-facebook-f"></i>
                    Accedi con Facebook
                </a>
            </div>
            <p>Non hai un account? <a href="registrazione.jsp">Registrati</a></p>
        </div>
    </div>
    
	<script src="scripts/formlogin.js"></script>
       
</main>
</body>
</html>
