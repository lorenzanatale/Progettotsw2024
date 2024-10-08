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
            <p>Non hai un account? <a href="registrazione.jsp">Registrati</a></p>
    </div>

    <a href="home.jsp" class="back-button">
        <img src="icon/arrow.png" id="arrow">
    </a>
    
    <%@ include file="footer.jsp" %>
	<script src="scripts/formlogin.js"></script>
       
    <script>
	window.onload = function() {
	    var url = window.location.href;
	    if (url.indexOf('error') != -1) {
	        Swal.fire({
	            toast: true,
	            position: 'top-end',
	            showConfirmButton: false,
	            timer: 3000,
	            icon: 'error',
	            text: 'Le credenziali inserite sono errate. Riprova.',
	        });
	    }
	};
    </script>
    
</main>
</body>
</html>
