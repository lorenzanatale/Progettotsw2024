<%@ page import="Model.prodottoCarrello.prodottoCarrelloBean" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Checkout</title>
    <link rel="stylesheet" href="style/checkout.css">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>
<main class="container">
    <h1>Checkout</h1>
    <form action="ConfermaOrdine" method="post">
        <section class="delivery-info">
            <h2>Informazioni per la consegna</h2>
            <label for="Nome">Nome</label>
            <input type="text" id="Nome" name="nome" required><br>

            <label for="Cognome">Cognome</label>
            <input type="text" id="Cognome" name="cognome" required><br>

            <label for="address">Indirizzo:</label>
            <input type="text" id="address" name="address" required><br>

            <label for="city">Città:</label>
            <input type="text" id="city" name="city" required><br>

            <label for="postalCode">CAP:</label>
            <input type="text" id="postalCode" name="postalCode" required><br>
        </section>

        <section class="order-summary">
            <h2>Riepilogo Ordine</h2>
            <%
                // Recupera i prodotti e calcola il totale
                List<prodottoCarrelloBean> cartItems = (List<prodottoCarrelloBean>) session.getAttribute("prodotti");
                double totale = 0.0;

                if (cartItems != null && !cartItems.isEmpty()) {
                    for (prodottoCarrelloBean prodotto : cartItems) {
                        int quantity = prodotto.getQuantita();
                        double prezzoTotale = prodotto.getPrezzo() * quantity;
                        totale += prezzoTotale;
            %>
            <p>Prodotto: <%= prodotto.getNome() %> <br></p>
            <p>Quantità: <%= quantity %> <br></p>
            <p>Prezzo: <%= String.format("%.2f", prodotto.getPrezzo()) %>€ <br></p>
            <p>Totale: <%= String.format("%.2f", prezzoTotale) %>€ <br></p>
            <%
                    }
                }
            %>
            <h3>Totale: €<%= String.format("%.2f", totale) %></h3>
            <%
                // Recupera l'Order ID dalla sessione
                Long orderId = (Long) session.getAttribute("orderId");
                if (orderId != null) {
            %>
            <h4>ID Ordine: <%= orderId %></h4>
            <%
            } else {
            %>
            <%
                }
            %>
        </section>

        <!-- Link to payment page -->
        <h3><a href="#" onclick="validateAndSubmitForm(event)" class="checkout-button">Procedi al pagamento</a></h3>
    </form>
    
<script>
function validateAndSubmitForm(event) {
    event.preventDefault(); // Previene l'azione predefinita del click sul link
    var nome = document.getElementById('Nome').value;
    var cognome = document.getElementById('Cognome').value;
    var indirizzo = document.getElementById('address').value;
    var citta = document.getElementById('city').value;
    var cap = document.getElementById('postalCode').value;

    // Controllo del nome
    if (nome.trim() === "") {
        Swal.fire({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 1500,
            icon: 'error',
            title: 'Inserire nome!',
        }).then(() => {
            location.reload(); // Ricarica la pagina corrente
        });
        return;
    }

    // Controllo del cognome
    if (cognome.trim() === "") {
        Swal.fire({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 1500,
            icon: 'error',
            title: 'Inserire cognome!',
        }).then(() => {
            location.reload(); // Ricarica la pagina corrente
        });
        return;
    }

    // Controllo dell'indirizzo
    if (indirizzo.trim() === "") {
        Swal.fire({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 1500,
            icon: 'error',
            title: 'Inserire indirizzo!',
        }).then(() => {
            location.reload(); // Ricarica la pagina corrente
        });
        return;
    }

    // Controllo della città
    if (citta.trim() === "") {
        Swal.fire({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 1500,
            icon: 'error',
            title: 'Inserire città!',
        }).then(() => {
            location.reload(); // Ricarica la pagina corrente
        });
        return;
    }

    // Controllo del CAP
    if (cap.length !== 5 || isNaN(cap)) {
        Swal.fire({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 1500,
            icon: 'error',
            title: 'Inserire un CAP valido di 5 cifre!',
        }).then(() => {
            location.reload(); // Ricarica la pagina corrente
        });
        return;
    } else {
        window.location.href = "pagamento.jsp";
    }
}
    </script>
    
</main>
</body>
</html>
