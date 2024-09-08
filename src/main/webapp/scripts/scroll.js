document.addEventListener("DOMContentLoaded", function () {
// Seleziona i pulsanti e il container dello scroll
const scrollContainer = document.querySelector('.gallery');
const scrollLeftButton = document.querySelector('.scroll-button.left');
const scrollRightButton = document.querySelector('.scroll-button.right');

// Definisci la quantità di scorrimento in pixel
const scrollAmount = 300;

// Funzione per lo scroll a sinistra
scrollLeftButton.addEventListener('click', () => {
    scrollContainer.scrollBy({
        top: 0,
        left: -scrollAmount,
        behavior: 'smooth'
    });
});

// Funzione per lo scroll a destra
scrollRightButton.addEventListener('click', () => {
    scrollContainer.scrollBy({
        top: 0,
        left: scrollAmount,
        behavior: 'smooth'
    });
});

// Funzione per gestire la visibilità dei pulsanti in base alla posizione dello scroll
function updateButtonVisibility() {
    const maxScrollLeft = scrollContainer.scrollWidth - scrollContainer.clientWidth;

    if (scrollContainer.scrollLeft <= 0) {
        scrollLeftButton.style.visibility = 'hidden';
    } else {
        scrollLeftButton.style.visibility = 'visible';
    }

    if (scrollContainer.scrollLeft >= maxScrollLeft) {
        scrollRightButton.style.visibility = 'hidden';
    } else {
        scrollRightButton.style.visibility = 'visible';
    }
}

// Aggiorna la visibilità dei pulsanti al caricamento della pagina e al momento dello scroll
updateButtonVisibility();
scrollContainer.addEventListener('scroll', updateButtonVisibility);

})