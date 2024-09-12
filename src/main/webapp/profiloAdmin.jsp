<%@ page import="java.util.List" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="Model.prodotto.*" %>
<%@ page import="Model.ordine.*" %>
<%@ page import="Model.utente.*" %>
<%@ page import="Model.prodottoOrdine.*" %>
<%@ page import="java.util.Collection" %>
<%@ page import="tswProj.RuntimeSQLException" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Profilo Utente</title>
<link rel="stylesheet" type="text/css" href="style/profiloAdmin.css">
</head>
<body>
<%@ include file="navbar.jsp" %>

<div class="content profile-container">
    <div class="menu-row">
        <button id="infoButton">Elenco utenti</button>
        <button id="allOrderButton">Ordini</button>
        <button id="availabilityButton">Disponibilità</button>
    </div>

    <div class="profile-content">
        <div class="profile-column">
            <div id="infoSection" style="display: none;">
                <div class="utenti">
                    <div class="utente-header">
                        <h1>Elenco utenti</h1>
                    </div>
                    <% try (utenteDAO utenteDAO = new utenteDAO()) { %>
                    <% List<utenteBean> utenti = (List<utenteBean>) utenteDAO.doRetrieveAll("ASC"); %>
                    <% if (!utenti.isEmpty()) { %>
                    <table>
                        <thead>
                        <tr>
                            <th></th> <!-- Colonna per l'immagine -->
                            <th>Username</th>
                            <th>Email</th>
                            <th>Admin</th>
                            <th>Storico</th>
                        </tr>
                        </thead>
                        <tbody>
                        <% for (utenteBean utente : utenti) {
                            if (utente.getId() != (long) session.getAttribute("id"))  {%>
                        <tr>
                            <td><img class="utente-image" src="icon/profile.png"
                                     alt="<%= utente.getUsername() %>">
                            </td> <!-- Immagine dell'utente -->
                            <td><%= utente.getUsername() %>
                            </td>
                            <td><%= utente.getEmail() %>
                            </td>
                            <td>
                                <!-- button modifica admin -->
                                <form action="cambioRuoloServlet" method="post">
                                    <input type="hidden" name="userId" value="<%= utente.getId() %>">
                                    <input type="hidden" name="makeAdmin" value="<%= !utente.getIsAdmin() %>">
                                    <input type="submit" class="changeAdminButton"
                                           value="<%= utente.getIsAdmin() ? "Rimuovi admin" : "Rendi admin" %>"
                                           onclick="confirmChangeAdmin(event, '<%= utente.getId() %>', '<%= !utente.getIsAdmin() %>')">
                                </form>
                            </td>
                            <td>
                                <!-- Form che porta alla pagina di storico ordini dell'utente cliccato -->
                                <form action="storicoOrdiniServlet" method="post">
                                    <input type="hidden" name="userId" value="<%= utente.getId() %>">
                                    <input type="submit" class="orderHistoryButton" value="Storico ordini">
                                </form>
                            </td>
                        </tr>
                        <% }
                        } %>
                        </tbody>
                    </table>
                    <% }
                    } catch (SQLException e) {
                        throw new RuntimeSQLException("Errore durante il recupero delle informazioni degli utenti", e);
                    } %>
                </div>
            </div>
            <div id="availabilitySection" class="dispSection" style="display: none;">
                <h1>Disponibilità</h1>
                <% try (prodottoDAO prodottoDAO = new prodottoDAO()) { %>
                <% List<prodottoBean> prodotti = (List<prodottoBean>) prodottoDAO.doRetrieveAll("ASC"); %>
                <% if (!prodotti.isEmpty()) { %>
                <% for (prodottoBean prodotto : prodotti) { %>
                <div class="item-box">
                    <div class="item-image">
                        <img src="<%= prodotto.getImgPath() %>" alt="<%= prodotto.getNome() %>">
                    </div>
                    <div class="item-details">
                        <h2 class="item-name"><%= prodotto.getNome() %>
                        </h2>
                        <p class="item-price"><%= prodotto.getPrezzo() %>€
                        </p>
                        <p class="item-quantity">Disponibilità: <%= prodotto.getDisponibilita() %>
                    </div>
                </div>
                <% } %>
                <% } %>
                <% } catch (SQLException e) {
                    throw new RuntimeSQLException("Errore durante il recupero delle informazioni dei prodotti", e);
                } %>
            </div>

            <div id="allOrderSection" class="allOrderSection" style="display: none;">
                <div class="allOrderHeader">
                    <h1>Ordini</h1>
                </div>
                <% try (ordineDAO ordineDAO = new ordineDAO(); prodottoOrdineDAO orderItemDAO = new prodottoOrdineDAO()) {
                    String startDate = request.getParameter("startDate");
                    String endDate = request.getParameter("endDate");

                    if (startDate == null || endDate == null) {
                        startDate = "";
                        endDate = "";
                    }

                    List<ordineBean> ordini = (List<ordineBean>) ordineDAO.doRetrieveByDate(startDate, endDate);
                    if (ordini.isEmpty()) { %>
                        <div class="empty-order">
                            <p>I clienti non hanno effettuato ordini...</p>
                        </div>
                 <% } %>
                <%

                    Collection<prodottoOrdineBean> orderItems = null;
                    for (ordineBean ordine : ordini) {
                        orderItems = orderItemDAO.doRetrieveByOrder(ordine.getId());
                %>
                <div class="order-box">
                    <div class="order-header">
                        <p>Data Ordine: <%= ordine.getData() %>
                        </p>
                       	<p>Totale Ordine: <%= orderItems.stream()
					    .map(item -> item.getPrezzo() * item.getQuantita())
					    .reduce(Double::sum)
					    .orElse(0.0) %>€</p>
                        <p>ID Ordine: <%= ordine.getId() %>
                        </p>
                    </div>
                    <%
                        for (prodottoOrdineBean orderItem : orderItems) {
                            try (prodottoDAO prodottoDAO = new prodottoDAO()) {
                                prodottoBean prodotto = prodottoDAO.doRetrieveByKey(orderItem.getIdProdotto());
                    %>
                    <div class="order-item">
                        <% if (prodotto.isVisibile()) { %>
                        <a href="prodottoServlet?id=<%= prodotto.getId() %>">
                            <img src="<%= prodotto.getImgPath() %>" alt="<%= prodotto.getNome() %>">
                        </a>
                        <% } else { %>
                        <img src="<%= prodotto.getImgPath() %>" alt="<%= prodotto.getNome() %>">
                        <% } %>
                        <div class="order-item-details">
                            <p><%= orderItem.getNome() %>
                            </p>
                            <p><%= orderItem.getPrezzo() %>€
                            </p>
                            <p>Quantità: <%= orderItem.getQuantita() %>
                            </p>
                        </div>
                    </div>
                    <%
                            } catch (SQLException s) {
                                throw new RuntimeSQLException("Errore durante il recupero delle informazioni del prodotto", s);
                            }
                        }
                    %>
                </div>
                <%
                        }
                    } catch (SQLException s) {
                        throw new RuntimeSQLException("Errore durante il recupero degli ordini", s);
                    }
                %>
            </div>
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>

<script>
    // Seleziona i pulsanti e le sezioni
    let infoButton = document.getElementById('infoButton');
    // let ordersButton = document.getElementById('ordersButton');

    let infoSection = document.getElementById('infoSection');
    // let ordersSection = document.getElementById('ordersSection');

    let availabilityButton = document.getElementById('availabilityButton');
    let availabilitySection = document.getElementById('availabilitySection');

    let allOrderButton = document.getElementById('allOrderButton');
    let allOrderSection = document.getElementById('allOrderSection');

    availabilityButton.addEventListener('click', function () {
        showSection('availabilitySection');
    });

    allOrderButton.addEventListener('click', function () {
        showSection('allOrderSection');
    });

    // Funzione per nascondere tutte le sezioni
    function hideAllSections() {
        infoSection.style.display = 'none';
        availabilitySection.style.display = 'none';
        allOrderSection.style.display = 'none';
        // ordersSection.style.display = 'none';
    }

    // Funzione per mostrare una sezione specifica
    function showSection(sectionId) {
        hideAllSections();
        let section = document.getElementById(sectionId);
        section.style.display = 'block';
    }

    // Aggiungi gestori di eventi di click ai pulsanti
    infoButton.addEventListener('click', function () {
        showSection('infoSection');
    });

    // ordersButton.addEventListener('click', function () {
    //     showSection('ordersSection');
    // });

    showSection('allOrderSection');
</script>

<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>

<script>
    function confirmChangeAdmin(event, userId, makeAdmin) {
        event.preventDefault();  // Previene l'invio del form
        var form = event.target.form;  // Ottiene il form

        swal({
            title: "Sei sicuro?",
            text: "Vuoi davvero cambiare il ruolo di questo utente?",
            icon: "warning",
            buttons: true,
            dangerMode: true,
        })
            .then((willChange) => {
                if (willChange) {
                    form.submit();  // Invia il form
                }
            });
    }
</script>

</body>
</html>