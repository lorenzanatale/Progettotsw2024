document.getElementById('loginForm').addEventListener('submit', function(event) {
    var email = document.getElementById('email').value;
    var password = document.getElementById('password').value;

    if(email == '' || password == '') {
        Swal.fire({
            icon: 'error',
            title: 'Ops...',
            text: 'Per favore, riempi tutti i campi.',
            width: '400px'
        });
        event.preventDefault();
    }
});
