<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chitarre</title>
    <link href="style/chitarre.css" rel="stylesheet">

</head>
<body>
<jsp:include page="navbar.jsp" />
<div class="container">
    <!-- Main Product Grid -->
    <section class="product-grid">
        <%
            // Simulazione di un array di prodotti
            class Product {
                String name;
                String imageUrl;
                double price;

                Product(String name, String imageUrl, double price) {
                    this.name = name;
                    this.imageUrl = imageUrl;
                    this.price = price;
                }
            }

            Product[] products = {
                    new Product("Chitarra Elettrica 1", "https://via.placeholder.com/300", 499.99),
                    new Product("Chitarra Acustica 2", "https://via.placeholder.com/300", 299.99),
                    new Product("Chitarre per Manicini 1", "https://via.placeholder.com/300", 450.00),
                    new Product("Amplificatore 1", "https://via.placeholder.com/300", 199.99),

            };

            for(Product product : products) {
        %>
        <div class="product-item">
            <img src="<%= product.imageUrl %>" alt="<%= product.name %>" class="product-img">
            <h3 class="product-name"><%= product.name %></h3>
            <p class="product-price">€<%= String.format("%.2f", product.price) %></p>
            <button class="btn-add-to-cart">Aggiungi al Carrello</button>
        </div>
        <%
            }
        %>
    </section>
</div>

<a href="catologo.jsp" class="back-button">
    <i class="fas fa-arrow-left"></i>
</a>
<script src="scripts/chitarre.js"></script>
</body>
</html>
