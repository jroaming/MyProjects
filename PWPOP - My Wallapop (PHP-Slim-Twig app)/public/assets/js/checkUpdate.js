function checkName() {
    var error = false;

    var form = {
        name : ''
    };
    form.name = document.getElementById('inlineFormInputName1').value;

    if (form.name.length === 0 || !form.name.match(/^[0-9a-zA-Z]+$/)) {
        document.getElementById('inputNameError').style.display = 'inline-block';
        error = true;
    }

    return !error;
}
function checkEmail() {
    var error = false;

    var form = {
        email : ''
    };
    form.email = document.getElementById('inlineFormInputEmail').value;
    if (!checkEmailValid(form.email)) {
        error = true;
        document.getElementById('inputEmailError').style.display = 'inline-block';
    }
    return !error;
}

function checkPassword() {
    var e = false;

    var form = {
        password : ''
    };
    form.password = document.getElementById('inputPassword').value;
    if (form.password.length < 6) {
        e = true;
        document.getElementById('inputPasswordError').style.display = 'inline-block';
    }
    return !e;
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