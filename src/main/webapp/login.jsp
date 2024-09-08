<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <title>Login</title>
    <link rel="stylesheet" href="style/login.css">
    <link rel="stylesheet" href="style/button.css">
</head>
<body>
<img src="icon/logoss.png" id="logo">
<main class="container">
    <h2>Login</h2>
    <form id="loginForm" action="${pageContext.request.contextPath}/loginServlet" method="post">
        <div class="input-field">
            <label for="email"></label><input autofocus type="email" name="email" id="email"
                   placeholder="Enter Your Email">
            <div class="underline"></div>
        </div>
        <div class="input-field">
            <input type="password" name="password" id="password"
                   placeholder="Enter Your Password">
            <div class="underline"></div>
        </div>

        <input type="submit" value="Login">
    </form>

    <div class="footer">
        <span>O Accedi Tramite Social Media</span>
        <div class="social-fields">
            <div class="social-field twitter">
                <a href="https://x.com/i/flow/login">
                    <i class="fab fa-twitter"></i>
                    Accedi con Twitter
                </a>
            </div>
            <div class="social-field facebook">
                <a href="https://www.facebook.com/">
                    <i class="fab fa-facebook-f"></i>
                    Accedi con Facebook
                </a>
            </div>
            <p>Non hai un account? <a href="registrazione.jsp">Registrati</a></p>
        </div>
    </div>

    <a href="home.jsp" class="back-button">
        <img src="icon/arrow.png" id="arrow">
    </a>
    
	<script src="scripts/formlogin.js"></script>
       
</main>
</body>
</html>
