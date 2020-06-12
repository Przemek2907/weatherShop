'use strict';

import indexScss from '../scss/menu.scss';
import productsScss from '../scss/products.scss';

const WEATHER_DEFINITIONS_API = 'http://localhost:6082/weather/definitions'
const PRODUCTS_CATEGORIES_API = 'http://localhost:6082/products/categories'

const PRODUCT_NAME_ID = 'product_name';
const PRODUCT_DESC_ID = 'product_desc';
const PRODUCT_CATEGORIES_ID = 'product_categories';

const productName = document.getElementById(PRODUCT_NAME_ID);
const productDescription = document.getElementById(PRODUCT_DESC_ID);
const productCategories = document.getElementById(PRODUCT_CATEGORIES_ID);

let productToSave = {
    name: '',
    description: '',
    category: '',
    price: '',
    weatherCategory: []
}

const productForm = document.getElementById('saveProduct');

function composeProductToSave() {
    console.log(productForm)
}

document.getElementById('load_file').addEventListener('click', async () => {
    let data = new FormData();

    data.append('file', document.getElementById('picture').files[0]);

    let response = await ('url', {
        method: 'POST',
        body: data
    })

    response.json().then(
        x => console.log(x),
        error => console.log(error)
    )
})

async function GET_API_CALL(api_url) {

    let response = fetch(api_url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': 'http://localhost:3000'
        }
    })

    return (await response).json()
}


GET_API_CALL(WEATHER_DEFINITIONS_API).then(data => processWeatherCategories(data))
GET_API_CALL(PRODUCTS_CATEGORIES_API).then(data => createDropDownValuesFromApiResponse(data))

function processWeatherCategories(restApiData) {
    if (restApiData === undefined || restApiData.length === 0) {
        throw new Error("There is no data in the API response")
    }

    [...restApiData].forEach(
        checkboxObject => createRadioButtonsBasedOnObject(checkboxObject)
    )
}

function createRadioButtonsBasedOnObject(weatherDefinition) {

    let checkboxesDiv = document.getElementById('fitting_weather_checkboxes');
    let newCheckboxContainer = document.createElement('div');

    if (typeof weatherDefinition !== 'object') {
        throw new Error("There is no object to create radio button from")
    }

    let newCheckBox = document.createElement('input');
    newCheckBox.type = 'checkbox';
    newCheckBox.id = weatherDefinition.id;
    newCheckBox.name = weatherDefinition.definition;
    newCheckBox.value = weatherDefinition.definition;
    newCheckBox.addEventListener('change', event => {
        if (event.target.checked) {
            productToSave.weatherCategory.push(newCheckBox.id)
            console.log(productToSave)
        } else {
            let removed = productToSave.weatherCategory.splice(productToSave.weatherCategory.indexOf(newCheckBox.id), 1);
            console.log('Removing..' + removed)
        }
    })
    let newLabel = document.createElement('label');
    newLabel.setAttribute('for', weatherDefinition.id);
    newLabel.innerText = weatherDefinition.definition;
    newCheckboxContainer.appendChild(newCheckBox);
    newCheckboxContainer.appendChild(newLabel);
    checkboxesDiv.appendChild(newCheckboxContainer)

}

function createDropDownValuesFromApiResponse(dataFromApi) {

    if (dataFromApi === undefined || dataFromApi.length === 0) {
        throw new Error("There is no data to process from API")
    }

    [...dataFromApi].forEach(
        category => {
            let categoryOption = document.createElement('option');
            categoryOption.value = category;
            categoryOption.innerText = category;
            productCategories.appendChild(categoryOption);
        }
    )
}

// TODO dodać serwis front-endowy budowania obiektu productToSave
function updateValue(e) {
    let enteredText = e.target.value;
    switch (e.target.id) {
        case PRODUCT_NAME_ID:
            productToSave.name = enteredText;
            break;
        case PRODUCT_DESC_ID:
            productToSave.description = enteredText;
            break;
        default:
            console.log("Cannot invoke event handler")
    }
}

productName.addEventListener('input', updateValue);
productDescription.addEventListener('input', updateValue);

// TODO przeparsować liste kategorii na dropdown
// TODO zapisywanie pliku multipart na serwis amazon s3





