<%@ page import="Model.ordine.*" %>
<%@ page import="Model.ordine.ordineBean" %>
<%@ page import="java.util.Collection" %>
<%@ page import="Model.prodottoOrdine.prodottoOrdineDAO" %>
<%@ page import="Model.prodottoOrdine.prodottoOrdineBean" %>
<%@ page import="Model.prodotto.prodottoDAO" %>
<%@ page import="Model.prodotto.prodottoBean" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="tswProj.RuntimeSQLException" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Storico Ordini Utente</title>
<link rel="stylesheet" type="text/css" href="style/profiloAdmin.css">
</head>
<body>

<%@ include file="navbar.jsp" %>

<div class="content profile-container">
    <div class="menu-row">
        <button onclick="window.location.href='profiloAdmin.jsp'">Torna alla pagina admin</button>
    </div>

    <div class="profile-content">
        <div class="profile-column">
            <div id="ordersSection">
                <h1>Storico Ordini</h1>
                <%
                    try (prodottoOrdineDAO prodottoOrdineDAO = new prodottoOrdineDAO()) {
                        Collection<ordineBean> ordini = (Collection<ordineBean>) request.getAttribute("ordini");
                        if (ordini.isEmpty()) { %>
                            <div class="empty-order">
                                <p>Il cliente non ha effettuato nessun ordine</p>
                            </div>
                        <% } %>
                <%
                    Collection<prodottoOrdineBean> orderItems = null;
                    for (ordineBean ordine : ordini) {
                        orderItems = prodottoOrdineDAO.doRetrieveByOrder(ordine.getId());
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
                    %>
                    <%
                        try (prodottoDAO prodottoDAO = new prodottoDAO()) {
                            prodottoBean prodotto = prodottoDAO.doRetrieveByKey(orderItem.getIdProdotto());
                    %>
                    <div class="order-item">
                        <% if (prodotto.isVisibile()) { %>
                        <a href="product?id=<%= prodotto.getId() %>">
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
                    %>
                    <%
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
</body>
</html>