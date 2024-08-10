<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">
<title>Home</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="style/stylesheet" href="slideshow.css">
    <link rel="style/stylesheet" href="button.css">
    <link rel="style/stylesheet" href="home.css">
    <link rel="style/stylesheet" href="navbar.css"
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <link href="style/slideshow.css" rel="stylesheet">
    <script src="slideshow.js"></script>
</head>
<body>
<jsp:include page="navbar.jsp" />

<body>
<div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel">
    <div class="carousel-indicators">
        <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
        <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1" aria-label="Slide 2"></button>
        <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2" aria-label="Slide 3"></button>
    </div>
    <div class="carousel-inner">
        <div class="carousel-item active">
            <a href="catalogo.jsp">
                <img src="img/welcome.png" class="d-block w-100" alt="Slide 1"></a>


            <div class="carousel-caption d-none d-md-block">
                <h5>Immagine 1</h5>
                <p>Descrizione dell'immagine 1.</p>
            </div>
        </div>
        <div class="carousel-item">
            <img src="img/batteria.jpg" class="d-block w-100" alt="Slide 2">
            <div class="carousel-caption d-none d-md-block">
                <h5>Immagine 2</h5>
                <p>Descrizione dell'immagine 2.</p>
            </div>
        </div>
        <div class="carousel-item">
            <img src="img/piano.jpg" class="d-block w-100" alt="Slide 3">
            <div class="carousel-caption d-none d-md-block">
                <h5>Immagine 3</h5>
                <p>Descrizione dell'immagine 3.</p>
            </div>
        </div>
    </div>
    <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Previous</span>
    </button>
    <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Next</span>
    </button>
</div>

<div class="container my-5">
    <div class="row text-center">
        <h2>I nostri best seller</h2>
        <div class="col-md-4 mb-4">
            <div class="feature-box">
                <h3>Feature 1</h3>
                <img src ="img/basso.jpg">
                <p>Descrizione della Feature 1.</p>
            </div>
        </div>
        <div class="col-md-4 mb-4">
            <div class="feature-box">
                <h3>Feature 2</h3>
                <img src ="img/synth.jpg">
                <p>Descrizione della Feature 2.</p>
            </div>
        </div>
        <div class="col-md-4 mb-4">
            <div class="feature-box">
                <h3>Feature 3</h3>
                <img src ="img/chitarraelettrica.png">
                <p>Descrizione della Feature 3.</p>
            </div>
        </div>
    </div>
</div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>


<jsp:include page="footer.jsp" />

</body>
</html>