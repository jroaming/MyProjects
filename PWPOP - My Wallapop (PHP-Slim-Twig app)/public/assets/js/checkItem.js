var error = false;

function checkItemUpload() {
    error = false;

    // COPYPASTE DEL CHECK REGISTER
    var form = {
        title : '',
        description : '',
        category : '',
        price : 0
    };

    form = getFormInputs(form);
    checkFormInputs(form);

    return !error;
}

function getFormInputs(form) {
    form.title = document.getElementById('inputName').value;
    form.description = document.getElementById('description').value;
    form.category = document.getElementById('inputCategory');
    form.price = document.getElementById('price').value;

    return form;
}

function checkFormInputs(form) {
    document.getElementById('inputCategoryError').style.display = 'none';
    document.getElementById('descriptionError').style.display = 'none';
    document.getElementById('inputNameError').style.display = 'none';
    document.getElementById('priceError').style.display = 'none';

    if (form.title.length <= 0) {
        error = true;
        document.getElementById('inputNameError').style.display = 'inline-block';
    }

    if (form.description.length < 20) {
        error = true;
        document.getElementById('descriptionError').style.display = 'inline-block';
    }

    if (form.category.value < "1") {
        error = true;
        document.getElementById('inputCategoryError').style.display = 'inline-block';
    }

    if (form.price == '') {
        error = true;
        document.getElementById('priceError').style.display = 'inline-block';
    }
}
