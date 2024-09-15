<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="Model.carrello.carrelloBean, Model.prodottoCarrello.prodottoCarrelloBean, Model.prodottoCarrello.prodottoCarrelloDAO, Model.carrello.carrelloDAO" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.List" %>
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
    <h2>Il Tuo Carrello</h2>

    <%
        Long carrelloId = (Long) session.getAttribute("carrelloId");
        if (carrelloId == null) {
            System.out.println("<p>Il carrello è vuoto.</p>");
        } else {
            // Utilizza try-with-resources per chiudere automaticamente le DAO
            try (carrelloDAO carrelloDAO = new carrelloDAO();
                 prodottoCarrelloDAO prodottoCarrelloDAO = new prodottoCarrelloDAO()) {

                List<prodottoCarrelloBean> prodotti = prodottoCarrelloDAO.getCartItemsAsProducts(carrelloId);

                if (prodotti.isEmpty()) {
                    System.out.println("<p>Il carrello è vuoto.</p>");
                } else {
    %>

    <table>
        <thead>
        <tr>
            <th>Immagine</th>
            <th>Nome Prodotto</th>
            <th>Quantita</th>
            <th>Prezzo</th>
            <th>Totale</th>
        </tr>
        </thead>
        <tbody>
        <%
            double totale = 0.0;
            for (prodottoCarrelloBean prodotto : prodotti) {
                double prezzoTotale = prodotto.getPrezzo() * prodotto.getQuantita(); // Usa getPrezzo() dal bean
                totale += prezzoTotale;
                String imgPath = prodotto.getImgPath(); // Aggiorna il percorso dell'immagine se necessario
        %>
        <tr>
            <td><img src="<%= imgPath %>" alt="<%= prodotto.getNome() %>" class="product-image"></td>
            <td><%= prodotto.getNome() %></td>
            <td><%= prodotto.getQuantita() %></td>
            <td><%= prodotto.getPrezzo() %> €</td>
            <td><%= prezzoTotale %> €</td>
        </tr>
        <%
            }
        %>
        </tbody>
        <tfoot>
        <tr>
            <td colspan="4"><strong>Totale</strong></td>
            <td><strong><%= totale %> €</strong></td>
        </tr>
        </tfoot>
    </table>

    <%
                }
            } catch (SQLException e) {
                System.out.println("<p>Errore nel recupero dei dati del carrello.</p>");
                e.printStackTrace();
            }
        }
    %>

    <a href="checkout.jsp" class="checkout-button">Procedi all'ordine</a>
</main>
</body>
</html>