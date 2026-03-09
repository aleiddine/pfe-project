// Elements
const form = document.getElementById('inscriptionForm');

const nomField = document.getElementById('nom');
const prenomField = document.getElementById('prenom');
const emailField = document.getElementById('email');
const telField = document.getElementById('tel');
const dateField = document.getElementById('dateNaissance');
const passwordField = document.getElementById('password');
const confirmField = document.getElementById('confirmpassword');

const nomError = document.getElementById('nomError');
const prenomError = document.getElementById('prenomError');
const emailError = document.getElementById('emailError');
const telError = document.getElementById('telError');
const passwordError = document.getElementById('passwordError');
const confirmError = document.getElementById('confirmError');
const strength = document.getElementById('strength');
const successMessage = document.getElementById('successMessage');

// Password strength
passwordField.addEventListener('input', function () {
    const val = passwordField.value;
    if (val.length < 6) {
        strength.textContent = "Faible";
        strength.style.color = "red";
    } else if (val.match(/[0-9]/) && val.match(/[A-Z]/)) {
        strength.textContent = "Forte";
        strength.style.color = "green";
    } else {
        strength.textContent = "Moyenne";
        strength.style.color = "orange";
    }

    if (val.length >= 6) passwordError.textContent = '';
});

// Live validation - disparition erreur après input correct
nomField.addEventListener('input', () => { if (nomField.value.trim() !== '') nomError.textContent = ''; });
prenomField.addEventListener('input', () => { if (prenomField.value.trim() !== '') prenomError.textContent = ''; });
emailField.addEventListener('input', () => { if (emailField.value.trim() !== '') emailError.textContent = ''; });
telField.addEventListener('input', () => { if (/^\d{8}$/.test(telField.value.trim())) telError.textContent = ''; });
confirmField.addEventListener('input', () => { if (confirmField.value === passwordField.value) confirmError.textContent = ''; });

// Form submit
form.addEventListener('submit', function (e) {
    e.preventDefault();
    let valid = true;

    // Nom
    if (nomField.value.trim() === '') {
        nomError.textContent = "Veuillez remplir ce champ !";
        valid = false;
    }

    // Prénom
    if (prenomField.value.trim() === '') {
        prenomError.textContent = "Veuillez remplir ce champ !";
        valid = false;
    }

    // Email
    if (emailField.value.trim() === '') {
        emailError.textContent = "Veuillez entrer un email !";
        valid = false;
    }

    // Téléphone
    const telRegex = /^[2579]\d{7}$/;
    if (!telRegex.test(telField.value.trim())) {
        telError.textContent = "Le numéro doit être tunisien et contenir 8 chiffres !";
        valid = false;
    } else {
        telError.textContent = '';
    }

    // Password
    if (passwordField.value.length < 6) {
        passwordError.textContent = "Mot de passe trop court !";
        valid = false;
    }

    // Confirmation password
    if (passwordField.value !== confirmField.value) {
        confirmError.textContent = "Les mots de passe ne correspondent pas !";
        valid = false;
    }

    // Formulaire valide
    if (valid) {
        // Envoi des données au backend (SignInServlet) sans recharger la page
        const params = new URLSearchParams();
        params.append("name", nomField.value.trim() + " " + prenomField.value.trim());
        params.append("email", emailField.value.trim());
        params.append("password", passwordField.value);
        params.append("role", "patient"); // pour SignInServlet
        params.append("specialty", ""); // non utilisé pour les patients

        fetch('signin', {
            method: 'POST',
            body: params
        }).then(response => {
            if (response.ok) {
                successMessage.textContent = "Inscription réussie ! ✔";
                successMessage.style.color = "green";
                form.reset();
                strength.textContent = '';
                document.querySelectorAll('.error').forEach(el => el.textContent = '');
            } else {
                successMessage.textContent = "Erreur lors de l'enregistrement.";
                successMessage.style.color = "red";
            }
        }).catch(err => {
            successMessage.textContent = "Erreur réseau. Serveur indisponible.";
            successMessage.style.color = "red";
        });
    }
});