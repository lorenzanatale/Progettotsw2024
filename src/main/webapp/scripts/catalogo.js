var cards = document.querySelectorAll('.product-image');

cards.forEach(function(card) {
	card.addEventListener('click', function() {
		var productId = card.getAttribute('id');
		window.location.href = '${pageContext.request.contextPath}/prodottoServlet?id=' + productId;
	});
});
