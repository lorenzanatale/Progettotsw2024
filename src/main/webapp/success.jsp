
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Successo </title>
</head>
<body>
<%
    HttpSession session = request.getSession(false); // false evita la creazione di una nuova sessione se non esiste
    if (session != null && session.getAttribute("username") != null) {
        String username = (String) session.getAttribute("username");
        out.println("Benvenuto, " + username + "!");
    } else {
        response.sendRedirect("login.jsp");
    }

    // Lettura del cookie
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("username")) {
                out.println("Username dal cookie: " + cookie.getValue());
            }
        }
    }
%>

<h1>Login Riuscito</h1>
<p>Successo</p>

</body>
</html>
