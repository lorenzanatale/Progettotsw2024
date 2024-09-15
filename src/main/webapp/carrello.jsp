<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Model.prodottoCarrello.prodottoCarrelloBean" %>
<%@ page import="Model.prodotto.prodottoBean" %>
<%@ page import="java.util.List" %>
<%@ page import="Control.CarrelloAnonimo" %>


<!DOCTYPE html>
<html>
<head>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@100;200;300;400;500;600;700&display=swap');

        /* Stile di base per la pagina */
        body {
            font-family: "Poppins", sans-serif;
            background: linear-gradient(45deg, #e7e5f1, #8737DAFF) no-repeat;
            margin: 0;
            display: flex;
            align-items: center;
            justify-content: center;
            min-height: 100vh;
        }

        /* Contenitore principale del carrello */
        main.container {
            background: white;
            width: 90%;
            max-width: 1200px;
            padding: 2rem;
            box-shadow: 5px 5px 15px rgba(0, 0, 0, 0.2);
            border-radius: 8px;
            margin-top: 20px;
        }

        /* Stile per il titolo */
        main h1 {
            font-weight: 600;
            margin-bottom: 1rem;
            text-align: center;
            color: #333;
        }

        /* Stile per la tabella */
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 1rem;
        }

        thead th {
            background: linear-gradient(45deg, #8e2de2, #ebe1fb);
            color: white;
            padding: 0.75rem;
            text-align: left;
        }

        tbody tr {
            border-bottom: 1px solid #ddd;
        }

        tbody tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        tbody tr:hover {
            background-color: #f1f1f1;
        }

        td {
            padding: 0.75rem;
            text-align: left;
        }

        img {
            max-width: 100px;
            height: auto;
            border-radius: 4px;
        }

        /* Stile per i pulsanti */
        .action-button {
            display: inline-block;
            padding: 5px 10px;
            margin: 5px;
            border: none;
            border-radius: 5px;
            background: linear-gradient(45deg, #8e2de2, #ebe1fb);
            color: white;
            font-size: 0.9rem;
            cursor: pointer;
            text-align: center;
            text-decoration: none;
        }

        .action-button:hover {
            background: linear-gradient(45deg, #7a1fcb, #d2b8ff);
        }

        /* Stile per il messaggio quando il carrello è vuoto */
        p.empty-message {
            text-align: center;
            color: #666;
            font-size: 1.2rem;
        }

        .total {
            font-weight: bold;
            margin-top: 1rem;
            text-align: right;
            color: #333;
        }

        .checkout-button {
            display: block;
            width: 200px;
            padding: 10px;
            margin: 1rem auto;
            border: none;
            border-radius: 5px;
            background: linear-gradient(45deg, #8e2de2, #ebe1fb);
            color: white;
            font-size: 1rem;
            cursor: pointer;
            text-align: center;
            text-decoration: none;
        }

        .checkout-button:hover {
            background: linear-gradient(45deg, #7a1fcb, #d2b8ff);
        }

        /* Responsività */
        @media (max-width: 768px) {
            main.container {
                padding: 1rem;
            }

            table {
                font-size: 0.9rem;
            }

            td, th {
                padding: 0.5rem;
            }

            img {
                max-width: 80px;
            }
        }

        @media (max-width: 480px) {
            main.container {
                width: 95%;
                padding: 0.5rem;
            }

            table {
                font-size: 0.8rem;
            }

            td, th {
                padding: 0.25rem;
            }

            img {
                max-width: 60px;
            }

            p.empty-message {
                font-size: 1rem;
            }
        }

    </style>

    <title>Il Tuo Carrello</title>
</head>
<body>
<main class="container">
    <h1>Il Tuo Carrello</h1>

        <%

    List<prodottoCarrelloBean> prodottiNelCarrello = (List<prodottoCarrelloBean>) request.getAttribute("prodottiCarrello");
    double totale = 0.0;

    if (prodottiNelCarrello == null || prodottiNelCarrello.isEmpty()) {
    %>
    <p class="empty-message">Il carrello è vuoto.</p>
        <%
    } else {
    %>
    <table>
        <thead>
        <tr>
            <th>Nome</th>
            <th>Descrizione</th>
            <th>Prezzo</th>
            <th>Quantità</th>
            <th>Immagine</th>
            <th>Azioni</th>
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
            <td><%=prodotto.getNome()%></td>
            <td><%=prodotto.getDescrizione()%></td>
            <td>€<%=prodotto.getPrezzo()%></td>
            <td>
                <input type="number" class="quantity" value="<%=carrelloBean.getQuantita()%>" min="1">
            </td>
            <td>
                <img src="<%=prodotto.getImgPath()%>" alt="<%=prodotto.getNome()%>">
            </td>
            <td>
                <form action="rimuovi-dal-carrello-servlet" method="post" style="display:inline;">
                    <input type="hidden" name="productId" value="<%=prodotto.getId()%>">
                    <input type="hidden" name="quantity" value="<%=prodottoCarrelloBean.getQuantita()%>">
                    <button type="submit" class="action-button">Rimuovi</button>
                </form>
            </td>
        </tr>
        <%
                } else {
                    // Handle the case where prodotto is null
                    System.out.println("Warning: Null prodottoBean found in cart");
                }
            }
        %>
        </tbody>
    </table>
    <p class="total">Totale: €<%= String.format("%.2f", totale) %></p>
    <a href="${pageContext.request.contextPath}/checkout.jsp" class="checkout-button">Procedi al Checkout</a>
        <%
    }
    %>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const table = document.querySelector('table');
            const totalElement = document.querySelector('.total');

            function updateTotal() {
                let totale = 0;
                const rows = table.querySelectorAll('tbody tr');

                rows.forEach(row => {
                    const prezzo = parseFloat(row.querySelector('td:nth-child(3)').textContent.trim().replace('€', ''));
                    const quantita = parseInt(row.querySelector('input.quantity').value);
                    totale += prezzo * quantita;
                });

                totalElement.textContent = `Totale: €${totale.toFixed(2)}`;
            }

            function handleQuantityChange(event) {
                if (event.target.matches('input.quantity')) {
                    updateTotal();
                }
            }

            table.addEventListener('input', handleQuantityChange);

            updateTotal();

        });
    </script>
</main>
</body>
</html>

