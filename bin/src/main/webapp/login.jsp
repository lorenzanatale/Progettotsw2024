<%--
  Created by IntelliJ IDEA.
  User: loren
  Date: 05/08/2024
  Time: 19:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://kit.fontawesome.com/1ab94d0eba.js" crossorigin="anonymous"></script>
    <script src="formlogin.js"></script>
    <title>Login</title>
    <link rel="stylesheet" href="login.css">
</head>
<body>
<main class="container">
    <h2>Login</h2>
    <form action="">
        <div class="input-field">
            <label for="username"></label><input type="text" name="username" id="username"
                                                 placeholder="Enter Your Username">
            <div class="underline"></div>
        </div>
        <div class="input-field">
            <input type="password" name="password" id="password"
                   placeholder="Enter Your Password">
            <div class="underline"></div>
        </div>

        <input type="submit" value="Continue">
    </form>

    <div class="footer">
        <span>Or Connect With Social Media</span>
        <div class="social-fields">
            <div class="social-field twitter">
                <a href="#">
                    <i class="fab fa-twitter"></i>
                    Sign in with Twitter
                </a>
            </div>
            <div class="social-field facebook">
                <a href="#">
                    <i class="fab fa-facebook-f"></i>
                    Sign in with Facebook
                </a>
            </div>
        </div>
    </div>
</main>
</body>
</html>
