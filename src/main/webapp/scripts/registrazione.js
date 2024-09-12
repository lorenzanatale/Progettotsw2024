document.getElementById('registrationForm').addEventListener('submit', function(event) {
    var password = document.getElementById('password').value;
    var passwordValidationResult = PasswordisValid(password);
    var confirmPassword = document.getElementById('confirmPassword').value;

    // Controllo se le password coincidono
    if (password !== confirmPassword) {
        event.preventDefault();
        Swal.fire({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 3000,
            icon: 'error',
            title: 'Le password non coincidono.',
        });
        return;
    }

    // Controllo validità della password
    if (!passwordValidationResult.isValid) {
        event.preventDefault();
        Swal.fire({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 3000,
            icon: 'error',
            title: passwordValidationResult.message,
        });
    }

});  // Chiusura della funzione di callback

// Funzione per la validazione della password
function PasswordisValid(password) {
    var hasLowerCase = /[a-z]/.test(password);
    var hasUpperCase = /[A-Z]/.test(password);
    var hasNumber = /\d/.test(password);
    var hasMinLength = password.length >= 8;

    var result = {
        isValid: hasLowerCase && hasUpperCase && hasNumber && hasMinLength,
        message: ''
    };

    if (!hasLowerCase) {
        result.message = 'La password deve contenere almeno una lettera minuscola.';
    } else if (!hasUpperCase) {
        result.message = 'La password deve contenere almeno una lettera maiuscola.';
    } else if (!hasNumber) {
        result.message = 'La password deve contenere almeno un numero.';
    } else if (!hasMinLength) {
        result.message = 'La password deve essere lunga almeno 8 caratteri.';
    }

    return result;
}
