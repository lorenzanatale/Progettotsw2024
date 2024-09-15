<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="java.util.List" %>
<%@ page import="Model.prodottoCarrello.prodottoCarrelloBean" %>
<%@ page import="Model.prodotto.prodottoBean" %>

<!DOCTYPE html>
<html>
<head>
    <title>Checkout</title>
    <link rel="stylesheet" href="style/checkout.css">
</head>
<body>
<main>
    <h1>Checkout</h1>
    <%
        HttpSession session1 = request.getSession();
        List<prodottoCarrelloBean> prodottiNelCarrello = (List<prodottoCarrelloBean>) session.getAttribute("prodottiCarrello");
        boolean isLoggedIn = session1.getAttribute("user") != null;
        double totale = 0.0;

        if (!isLoggedIn) {
    %>
    <p>Per procedere al checkout, <a href="login.jsp">effettua il login</a>.</p>
    <%
    } else if (prodottiNelCarrello == null || prodottiNelCarrello.isEmpty()) {
    %>
    <p>Il carrello è vuoto.</p>
    <%
    } else {
        totale = (Double) request.getAttribute("totale");
    %>
    <table>
        <thead>
        <tr>
            <th>Nome</th>
            <th>Descrizione</th>
            <th>Prezzo</th>
            <th>Quantità</th>
            <th>Immagine</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (prodottoCarrelloBean carrelloBean : prodottiNelCarrello) {
                prodottoBean prodotto = carrelloBean.getProdottoBean();
                if (prodotto != null) {
                    double prezzoTotale = prodotto.getPrezzo() * carrelloBean.getQuantita();
                    totale += prezzoTotale;
        %>
        <tr>
            <td><%= prodotto.getNome() %></td>
            <td><%= prodotto.getDescrizione() %></td>
            <td>€<%= prodotto.getPrezzo() %></td>
            <td><%= carrelloBean.getQuantita() %></td>
            <td>
                <img src="<%= prodotto.getImgPath() %>" alt="<%= prodotto.getNome() %>">
            </td>
        </tr>
        <%
                } else {
                    System.out.println("Warning: Null prodottoBean found in cart");
                }
            }
        %>
        </tbody>
    </table>
    <p class="total">Totale: €<%= String.format("%.2f", totale) %></p>
    <form action="createOrder" method="post">
        <input type="submit" value="Conferma Ordine">
    </form>
    <%
        }
    %>
</main>
</body>
</html>
