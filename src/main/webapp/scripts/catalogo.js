var cards = document.querySelectorAll('.product-image');

cards.forEach(function(card) {
  card.addEventListener('click', function() {
    window.location.href = 'https://www.google.com';
  });
});
