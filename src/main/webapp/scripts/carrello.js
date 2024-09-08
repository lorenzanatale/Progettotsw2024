// Simuliamo alcuni articoli del carrello
const cartItems = [
    { id: 1, name: "Articolo 1", price: 25.00 },
    { id: 2, name: "Articolo 2", price: 40.00 }
];

function updateCart() {
    const cartContainer = document.getElementById('cart-items');
    const cartTotal = document.getElementById('cart-total');
    const cartMessage = document.getElementById('cart-message');
    const addItemsBtn = document.getElementById('add-items-btn');

    cartContainer.innerHTML = '';
    let total = 0;

    if (cartItems.length === 0) {
        cartMessage.innerHTML = 'Il tuo carrello è vuoto';
        addItemsBtn.classList.remove('hidden');
    } else {
        cartMessage.innerHTML = '';
        addItemsBtn.classList.add('hidden');

        cartItems.forEach(item => {
            const cartItem = document.createElement('div');
            cartItem.className = 'cart-item';
            cartItem.innerHTML = `
                <span>${item.name} - €${item.price.toFixed(2)}</span>
                <button onclick="removeItem(${item.id})">Rimuovi</button>
            `;
            cartContainer.appendChild(cartItem);
            total += item.price;
        });
    }

    cartTotal.innerHTML = `Totale: €${total.toFixed(2)}`;
}

function removeItem(id) {
    const index = cartItems.findIndex(item => item.id === id);
    if (index > -1) {
        cartItems.splice(index, 1);
    }
    updateCart();
}

document.getElementById('add-items-btn').addEventListener('click', () => {
    // Simuliamo l'aggiunta di un nuovo articolo
    cartItems.push({ id: cartItems.length + 1, name: `Articolo ${cartItems.length + 1}`, price: 30.00 });
    updateCart();
});


const cartItems = [
    { id: 1, name: "Articolo 1", price: 25.00 },
    { id: 2, name: "Articolo 2", price: 40.00 }
];

function updateCart() {
    const cartContainer = document.getElementById('cart-items');
    const cartTotal = document.getElementById('cart-total');
    const cartMessage = document.getElementById('cart-message');
    const addItemsBtn = document.getElementById('add-items-btn');
    const notification = document.getElementById('notification');

    cartContainer.innerHTML = '';
    let total = 0;

    if (cartItems.length === 0) {
        cartMessage.innerHTML = 'Il tuo carrello è vuoto';
        addItemsBtn.classList.remove('hidden');
    } else {
        cartMessage.innerHTML = '';
        addItemsBtn.classList.add('hidden');

        cartItems.forEach(item => {
            const cartItem = document.createElement('div');
            cartItem.className = 'cart-item';
            cartItem.innerHTML = `
                <span>${item.name} - €${item.price.toFixed(2)}</span>
                <button onclick="removeItem(${item.id})">Rimuovi</button>
            `;
            cartContainer.appendChild(cartItem);
            total += item.price;
        });
    }

    cartTotal.innerHTML = `Totale: €${total.toFixed(2)}`;
}

function showNotification(message) {
    const notification = document.getElementById('notification');
    notification.innerHTML = message;
    notification.style.display = 'block';
    setTimeout(() => {
        notification.style.display = 'none';
    }, 3000);
}

function removeItem(id) {
    const index = cartItems.findIndex(item => item.id === id);
    if (index > -1) {
        cartItems.splice(index, 1);
        updateCart();
        showNotification('Articolo rimosso');
    }
}

document.getElementById('add-items-btn').addEventListener('click', () => {
    // Simuliamo l'aggiunta di un nuovo articolo
    cartItems.push({ id: cartItems.length + 1, name: `Articolo ${cartItems.length + 1}`, price: 30.00 });
    updateCart();
    showNotification('Articolo aggiunto');
});

// Iniziamo con l'aggiornare il carrello
updateCart();
