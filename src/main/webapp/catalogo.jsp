<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Catalogo</title>
<link href="style/catalogo.css" rel="stylesheet">
<script src="scripts/catalogo.js"></script>

</head>
<body>
<jsp:include page="navbar.jsp" />

<header>
	<h1>Catalogo</h1>
</header>



<!--ho fatto delle macro categorie per gli strumenti-->

<!--chitarre-->

<div class="product-container">
	<div class="product-card">
		<h2>Chitarre</h2>
		<a href="chitarre.jsp"><img class="product-image" src="img\chitarraacustica.jpg" alt="Chitarra acustica"></a>
		<h2 class="product-title">Chitarre</h2>
		<p class="product-description">Una nuovissima chitarra bllxm.</p>
		<p class="product-price">100€</p>
	</div>

	<!--bassi elettrici-->

	<div class="product-card">
		<h2>Bassi Elettrici</h2>
		<img class="product-image" src="img\basso.jpg" alt="Basso Elettrico">
		<h2 class="product-title">Basso elettrico</h2>
		<p class="product-price">100€</p>
	</div>

	<!--batteria-->
	<div class="product-card">
		<h2>Batterie e percussioni</h2>
		<img class="product-image" src="img\batteria.jpg" alt="Batteria">
		<h2 class="product-title">Batteria</h2>
		<p class="product-description">Per dare fastidio ai vicini hihi</p>
		<p class="product-price">100€</p>
	</div>


<!--tastiere e synth-->
	<div class="product-card">
		<h2>Tastiere e synth</h2>
		<img class="product-image" src="img\piano.jpg" alt="synth">
		<h2 class="product-title">Maaaaaracas</h2>
		<p class="product-description">Tunz tunz chip chip</p>
		<p class="product-price">100€</p>
	</div>

	<!--software-->
	<div class="product-card">
		<h2>Software</h2>
		<img class="product-image" src="img\softwarearea.png" alt="Software">
		<h2 class="product-title">Software</h2>
		<p class="product-description">Dolce melodia</p>
		<p class="product-price">100€</p>
	</div>

	<!--accessori-->
	<div class="product-card">
		<h2>Accessori</h2>
		<img class="product-image" src="img\tamburo.jpg" alt="Tamburo">
		<h2>Accessori</h2>
		<h2 class="product-title">accessori</h2>
		<p class="product-description">Unga bunga</p>
		<p class="product-price">100€</p>
	</div>

	<!--fiati-->
	<div class="product-card">
		<h2>Fiati</h2>
		<img class="product-image" src="img\tromba.jpg" alt="Pianoforte">
		<h2 class="product-title">strumenti a fiato</h2>
		<p class="product-description">Bellissima melodia</p>
		<p class="product-price">100€</p>
	</div>

	<!--archi-->
	<div class="product-card">
		<h2>Archi</h2>
		<img class="product-image" src="img\arpa.png" alt="arpa">
		<h2 class="product-title">Archi</h2>
		<p class="product-description">non lo so</p>
		<p class="product-price">100€</p>
	</div>

	<!--microfoni-->
	<div class="product-card">
		<h2>Microfoni</h2>
		<img class="product-image" src="img\microfoni.jpg" alt=>
		<h2 class="product-title">boh</h2>
		<p class="product-description">trallallero trallalà</p>
		<p class="product-price">100€</p>
	</div>

	<!--Offerte Speciali-->
	<div class="product-card">
		<h2>Offerte Speciali</h2>
		<img class="product-image" src="img\tromba.png" alt="Tromba">
		<h2 class="product-title">offerte</h2>
		<p class="product-description">Fiesta, que fantastica esta fiesta!</p>
		<p class="product-price">100€</p>
	</div>

<!--bestseller-->
	<div class="product-card">
		<h2>Best Seller</h2>
		<img class="product-image" src="img\sassofono.jpg" alt="Sassofono">
		<h2 class="product-title">Top vendite</h2>
		<p class="product-description">sasso</p>
		<p class="product-price">100€</p>
	</div>

	<!--novità articoli-->
	<div class="product-card">
		<h2>Novità articoli</h2>
		<img class="product-image" src="img\amplificatorechitarra.jpg" alt="Amplificatore chitarra">
		<h2 class="product-title">Nuovi articoli</h2>
		<p class="product-description">Un fantastico amplificatore per la vostra chitarra!</p>
		<p class="product-price">100€</p>
	</div>
</div>

<jsp:include page="footer.jsp" />


</body>
</html>