var error = false;

function checkRegister() {
    error = false;
    var form = {
        name : '',
        username : '',
        email : '',
        birth_date : '',
        phone_number : '',
        password : '',
        confirm_password : ''
    };

    form = getFormInputs(form);
    //console.log(form);
    checkFormInputs(form);

    return !error;
}

function getFormInputs(form) {
    form.name = document.getElementById('inputName').value;
    form.username = document.getElementById('inputUsername').value;
    form.email = document.getElementById('inputEmail').value;
    form.birth_date = document.getElementById('inputBirthDate').value;
    form.phone_number = document.getElementById('inputNumber').value;
    form.password = document.getElementById('inputPassword').value;
    form.confirm_password = document.getElementById('inputConfirmPassword').value;

    return form;
}

function checkFormInputs(form) {
    document.getElementById('inputNameError').style.display = 'none';
    document.getElementById('inputUsernameError1').style.display = 'none';
    document.getElementById('inputUsernameError2').style.display = 'none';
    document.getElementById('inputEmailError').style.display = 'none';
    //document.getElementById('inputBirthDateError').style.display = 'none';
    //document.getElementById('inputNumber').style.display = 'none';
    document.getElementById('inputPasswordError').style.display = 'none';
    document.getElementById('inputConfirmPasswordError').style.display = 'none';

    if (!form.name.match(/^[0-9a-zA-Z]+$/)) {
        error = true;
        document.getElementById('inputNameError').style.display = 'inline-block';
    }

    if (!form.username.match(/^[0-9a-zA-Z]+$/)) {
        error = true;
        document.getElementById('inputUsernameError1').style.display = 'inline-block';
    }

    if (form.username.length > 20) {
        error = true;
        document.getElementById('inputUsernameError2').style.display = 'inline-block';
    }

    if (!checkEmailValid(form.email)) {
        error = true;
        document.getElementById('inputEmailError').style.display = 'inline-block';
    }

    if (form.password.length < 6) {
        error = true;
        document.getElementById('inputPasswordError').style.display = 'inline-block';
    }

    if (form.password.localeCompare(form.confirm_password) !== 0) {
        error = true;
        document.getElementById('inputConfirmPasswordError').style.display = 'inline-block';
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