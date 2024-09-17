<%@ page import="Model.prodottoCarrello.prodottoCarrelloBean" %>
<%@ page import="Model.carrello.carrelloDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="Model.prodottoCarrello.prodottoCarrelloDAO" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Carrello</title>
    <link rel="stylesheet" href="style/carrello.css"> <!-- Aggiorna il percorso del CSS -->
</head>
<body>

<main class="container">
    <a href="<%= request.getContextPath() %>/catalogoServlet" class="back-button">Torna Indietro</a>
    <!-- Pulsante per tornare indietro -->

    <h2>Il Tuo Carrello</h2>

   <%
    Long carrelloId = (Long) session.getAttribute("carrelloId");
    List<prodottoCarrelloBean> prodotti = null;

    try (carrelloDAO carrelloDAO = new carrelloDAO();
         prodottoCarrelloDAO prodottoCarrelloDAO = new prodottoCarrelloDAO()) {
        if (carrelloId == null) {
%>
    <p>Il carrello è vuoto. <a href='<%= request.getContextPath() %>/catalogoServlet' class="continue-shopping-button">Continua
        lo shopping</a></p>
        <%
    } else {
        // Recupera i prodotti nel carrello
        prodotti = prodottoCarrelloDAO.doRetrieveByCarrelloId(carrelloId);

        if (prodotti.isEmpty()) {
%>
    <p>Il carrello è vuoto. <a href='<%= request.getContextPath() %>/catalogo.jsp' class="continue-shopping-button">Continua
        lo shopping</a></p>
        <%
        } else {
            // Salva la lista di prodotti nella sessione
            session.setAttribute("cartItems", prodotti);
%>

    <table>
        <thead>
        <tr>
            <th>Immagine</th>
            <th>Nome Prodotto</th>
            <th>Quantità</th>
            <th>Prezzo</th>
            <th>Totale</th>
            <th>Azioni</th> <!-- Colonna per le azioni -->
        </tr>
        </thead>
        <tbody>
        <%
            double totale = 0.0;
            for (prodottoCarrelloBean prodotto : prodotti) {
                double prezzoTotale = prodotto.getPrezzo() * prodotto.getQuantita();
                totale += prezzoTotale;
                String imgPath = prodotto.getImgPath();
        %>
        <tr>
            <td><img src="<%=imgPath%>" alt="<%=prodotto.getNome()%>" class="product-image"></td>
            <td><%=prodotto.getNome()%></td>
            <td><%=prodotto.getQuantita()%></td>
            <td><%=prodotto.getPrezzo()%> €</td>
            <td><%=String.format("%.2f", prezzoTotale)%> €</td>
            <td>
                <!-- Form per rimuovere il prodotto -->
                <form action="<%= request.getContextPath() %>/RimuoviCarrelloServlet" method="post">
                    <input type="hidden" name="productId" value="<%=prodotto.getIdProdotto()%>">
                    <button type="submit" class="removeFromCartButton">Rimuovi</button>
                </form>
            </td>
        </tr>
        <%
            }
            // Salva il totale nella sessione
            session.setAttribute("carrelloTotale", totale);
        %>
        </tbody>
        <tfoot>
        <tr>
            <td colspan="4"><strong>Totale</strong></td>
            <td><strong><%=String.format("%.2f", totale)%> €</strong></td>
        </tr>
        </tfoot>
    </table>

    <div class="checkout-section">
        <form action="<%= request.getContextPath() %>/OrdineServlet" method="post">
            <input type="hidden" name="totale" value="<%= String.format("%.2f", totale) %>">
            <input type="hidden" name="carrelloId" value="<%= carrelloId %>">
            <button type="submit" class="checkout-button">Procedi all'ordine</button>
        </form>
        <a href="<%= request.getContextPath() %>/catalogoServlet" class="continue-shopping-button">Continua lo shopping</a>
    </div>

        <%
        }
    }
} catch (Exception e) {
    System.out.println("<p>Errore inaspettato. Contattare l'amministratore del sistema.</p>");
    e.printStackTrace();
}
%>
</main>
</body>
</html>
