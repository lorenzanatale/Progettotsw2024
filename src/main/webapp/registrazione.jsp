<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <link href="style/registrazione.css" rel="stylesheet">
    <link href="style/button.css" rel="stylesheet">

    <title>Registrazione</title>

</head>
<body>
<img src="icon/logoss.png" id="logo">

<div class="container">
    <h3>Registrazione</h3>
    <form id="registrationForm" action="${pageContext.request.contextPath}/registrazioneServlet" method="post">
        <div class="input-field">
            <label for="username">Nome Utente:</label>
            <input autofocus type="text" name="username" id="username" placeholder="Enter Your Username" required>
            <div class="underline"></div>
        </div>
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
        <input type="submit" value="Registrati">
    </form>

    <div class="footer">
        <p>Hai già un account? <a href="login.jsp">Accedi</a></p>
    </div>

	<%@ include file="footer.jsp" %>

		<%
		if (session.getAttribute("emailExists") != null && (boolean) session.getAttribute("emailExists")) {
			session.removeAttribute("emailExists"); // Rimuovi l'attributo dopo averlo utilizzato
		%>
		<script>
       	 	Swal.fire({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 3000,
            icon: 'error',
            title: 'L\'email inserita è già utilizzata!',
			})
		</script>
		<%
		}
		%>


		<a href="home.jsp" class="back-button">
        <i class="fas fa-arrow-left"></i>
        <img src="icon/arrow.png" id="arrow">
    </a>
</div>


<script src="scripts/registrazione.js"></script>
<script>
	window.onload = function() {
		var url = window.location.href;
		if (url.indexOf('error=2') != -1) {
			Swal.fire({
	            toast: true,
	            position: 'top-end',
	            showConfirmButton: false,
	            timer: 3000,
	            icon: 'error',
	            title : 'Il nome utente inserito è troppo corto. Inserisci almeno 3 caratteri!',
			});
		}
	};
</script>
</body>
</html>
