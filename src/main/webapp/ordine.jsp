<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order Confirmation</title>
</head>
<body>
<h1>Order Confirmation</h1>
<p>Thank you for your order! Your order number is: <%= request.getParameter("orderId") %></p>
<p>You will receive an email confirmation shortly.</p>
<a href= "home.jsp">Return to Home</a>
</body>
</html>
