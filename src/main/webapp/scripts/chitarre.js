document.addEventListener('DOMContentLoaded', function() {
    const buttons = document.querySelectorAll('.btn-add-to-cart');

    buttons.forEach(button => {
        button.addEventListener('click', function() {
            alert('Prodotto aggiunto al carrello!');
            // Qui puoi aggiungere logica aggiuntiva come chiamate AJAX per aggiungere l'articolo al carrello
        });
    });
});
