document.getElementById('registrationForm').addEventListener('submit', function(event) {
    var username = document.getElementById('username').value;
    var password = document.getElementById('password').value;

    if(username === '' || password === '') {
        alert('Per favore, riempi tutti i campi.');
        event.preventDefault();
    }
});
