document.addEventListener('DOMContentLoaded', function() {
    // Funzione per ottenere i dati dal server (può essere adattata se necessario)
    function fetchCartData() {
        fetch('checkoutDataServlet') // Modifica l'URL con il percorso corretto
            .then(response => response.json())
            .then(data => {
                const cartContainer = document.getElementById('checkout-container');
                const cartMessage = document.getElementById('cart-message');
                const cartTable = document.getElementById('cart-table');
                const cartBody = document.getElementById('cart-body');
                const totalElement = document.getElementById('total');
                const paymentForm = document.getElementById('payment-form');

                if (data.prodottiCarrello && data.prodottiCarrello.length > 0) {
                    cartMessage.style.display = 'none';
                    cartTable.style.display = 'table';
                    totalElement.style.display = 'block';
                    paymentForm.style.display = 'block';

                    data.prodottiCarrello.forEach(prodotto => {
                        const row = document.createElement('tr');
                        row.innerHTML = `
                            <td>${prodotto.nome}</td>
                            <td>${prodotto.quantita}</td>
                            <td>${prodotto.prezzo} €</td>
                            <td>${(prodotto.quantita * prodotto.prezzo).toFixed(2)} €</td>
                        `;
                        cartBody.appendChild(row);
                    });

                    totalElement.textContent = `Totale: ${data.totale.toFixed(2)} €`;
                } else {
                    cartMessage.textContent = data.messaggio || 'Il carrello è vuoto.';
                    cartMessage.style.display = 'block';
                    cartTable.style.display = 'none';
                    totalElement.style.display = 'none';
                    paymentForm.style.display = 'none';
                }
            })
            .catch(error => console.error('Errore nel recupero dei dati:', error));
    }

    fetchCartData(); // Chiama la funzione per caricare i dati del carrello
});
