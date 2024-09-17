function validateAndSubmitForm(event) {
    event.preventDefault(); // Previene l'azione predefinita del click sul link
    var cardNumber = document.getElementById('cardNumber').value;
    var expiryDate = document.getElementById('expiryDate').value;
    var cvv = document.getElementById('cvv').value;
    var cardHolder = document.getElementById('cardHolder').value;

    // Controllo del formato della data di scadenza
    var expiryDatePattern = /^(0[1-9]|1[0-2])\/?([0-9]{2})$/;
    if (!expiryDatePattern.test(expiryDate)) {
        Swal.fire({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 1500,
            icon: 'error',
            title: 'Inserisci una data di scadenza valida nel formato MM/YY!',
        }).then(() => {
            location.reload(); // Ricarica la pagina corrente
        });
        return;
    }

    // Controllo del numero CVV
    if (cvv.length < 3 || cvv.length > 3 || isNaN(cvv)) {
        Swal.fire({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 1500,
            icon: 'error',
            title: 'Inserisci un CVV valido!',
        }).then(() => {
            location.reload(); // Ricarica la pagina corrente
        });
        return;
    }

    // Controllo del nome del titolare
    if (cardHolder.trim() === "") {
        Swal.fire({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 1500,
            icon: 'error',
            title: 'Inserire nome titolare!',
        }).then(() => {
            location.reload(); // Ricarica la pagina corrente
        });
        return;
    }

    if (cardNumber.length < 16 || cardNumber.length > 16 || isNaN(cardNumber)) {
        Swal.fire({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 1500,
            icon: 'error',
            title: 'Inserisci un numero di carta valido!',
        }).then(() => {
            location.reload(); // Ricarica la pagina corrente
        });
    } else {
        window.location.href = "ConfermaOrdine"; // Redirige a ConfermaOrdine se il numero della carta è valido
    }
}
