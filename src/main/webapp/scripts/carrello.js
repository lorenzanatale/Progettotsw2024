document.addEventListener('DOMContentLoaded', function() {
    // Recupera la tabella del carrello e il campo del totale
    const table = document.querySelector('table');
    const totalElement = document.querySelector('.total');

    // Funzione per aggiornare il totale
    function updateTotal() {
        let totale = 0;
        const rows = table.querySelectorAll('tbody tr');

        rows.forEach(row => {
            const prezzo = parseFloat(row.querySelector('td:nth-child(3)').textContent);
            const quantita = parseInt(row.querySelector('td:nth-child(4)').textContent);
            totale += prezzo * quantita;
        });

        totalElement.textContent = `Totale: €${totale.toFixed(2)}`;
    }

    // Funzione per gestire la modifica della quantità
    function handleQuantityChange(event) {
        const target = event.target;
        if (target && target.matches('input.quantity')) {
            const row = target.closest('tr');
            const prezzo = parseFloat(row.querySelector('td:nth-child(3)').textContent);
            const quantita = parseInt(target.value);
            const quantitaCell = row.querySelector('td:nth-child(4)');

            // Aggiorna la quantità e ricalcola il totale
            quantitaCell.textContent = quantita;
            updateTotal();
        }
    }

    // Aggiungi un listener per la modifica della quantità
    table.addEventListener('input', handleQuantityChange);

    // Inizializza il totale
    updateTotal();
});
