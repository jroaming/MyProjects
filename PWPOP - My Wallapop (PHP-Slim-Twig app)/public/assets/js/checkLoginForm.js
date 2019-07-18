var error = false;

function checkLogin() {
    error = false;

    var form = {
        email : '',
        password : ''
    };

    form = getFormInputs(form);
    //console.log(form);
    checkFormInputs(form);

    return !error;
}

function getFormInputs(form) {
    form.email = document.getElementById('inputName').value;
    form.password = document.getElementById('inputPassword').value;

    return form;
}

function checkFormInputs(form) {
    document.getElementById('inputPasswordError').style.display = 'none';
    document.getElementById('inputEmailError').style.display = 'none';

    if (form.password.length < 6) {
        error = true;
        document.getElementById('inputPasswordError').style.display = 'inline-block';
    }

    if (!checkEmailValid(form.email)) {
        error = true;
        document.getElementById('inputEmailError').style.display = 'inline-block';
    }

}

function checkEmailValid(email) {
    var Achar = 0;
    var Dotchar = 0;

    //we search for '@' and '.'
    Achar = email.indexOf("@");

    // we make sure there's only 1 '@' (if 1st is different than last, then there are more than 1):
    if (Achar !== email.lastIndexOf("@")) return false;

    // we know there's gotta be a '.' after the '@':
    Dotchar = email.lastIndexOf(".");
    if (Dotchar <= Achar) return false;

    // tampooc puede haber un punto al final ni al principio, ni una arroba al principio
    if (Achar < 1 || email.indexOf(".") < 1 || Dotchar === (email.length - 1)) {
        return false;
    }

    // comprobamos que no haya caracteres raros y, si ha llegado hasta aqui, es correcto:
    return email.match(/^[0-9a-zA-Z@.]+$/);
}