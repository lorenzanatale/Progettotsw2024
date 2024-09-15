document.getElementById('loginForm').addEventListener('submit', function(event) {
    var email = document.getElementById('email').value;
    var password = document.getElementById('password').value;

    if(email == '' || password == '') {
        Swal.fire({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 3000,
            icon: 'error',
            text: 'Per favore, riempi tutti i campi.',
        });
        event.preventDefault();
    }
});
