'use strict';

import indexScss from '../scss/menu.scss';
import productsScss from '../scss/products.scss';

const WEATHER_DEFINITIONS_API = 'http://localhost:6082/weather/definitions'
const PRODUCTS_CATEGORIES_API = 'http://localhost:6082/products/categories'
const PRODUCT_SAVE_API = 'http://localhost:6082/products/save'

const PRODUCT_NAME_ID = 'product_name';
const PRODUCT_DESC_ID = 'product_desc';
const PRODUCT_CATEGORIES_ID = 'product_categories';
const SAVE_PRODUCT_ID = 'saveBtn';
const PRODUCT_IMAGE_INPUT_ID = 'picture';

const productName = document.getElementById(PRODUCT_NAME_ID);
const productDescription = document.getElementById(PRODUCT_DESC_ID);
const productCategories = document.getElementById(PRODUCT_CATEGORIES_ID);
const saveProductBtn = document.getElementById(SAVE_PRODUCT_ID);
const productImageInput = document.getElementById(PRODUCT_IMAGE_INPUT_ID);

let productToSave = {
    name: '',
    description: '',
    category: '',
    price: '',
    currency: '',
    weatherCategory: [],
    productImage: null
}

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

async function POST_API_CALL(api_url, postedData) {

    let response = fetch(api_url, {
        method: 'POST',
        headers: {
            'Access-Control-Allow-Origin': 'http://localhost:3000'
        },
        body: postedData
    })

    return (await response).json();
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

function saveNewProduct() {
    productToSave.category = productCategories.options[productCategories.selectedIndex].value;

    //TODO validate all fields in seperate method
    //TODO pack the object in FormData and send it with the POST method to save

    let productFormData = createFromDataForProduct(productToSave);
    POST_API_CALL(PRODUCT_SAVE_API, productFormData).then(
        data => console.log(data)
    )
}

function createFromDataForProduct(myProduct) {
    let formData = new FormData();

    Object.keys(myProduct).forEach(
        productKey => formData.append(productKey, myProduct[productKey])
    )

    for(let [name, value] of formData) {
        console.log(`${name} = ${value}`);
    }

    return formData;
}

productName.addEventListener('input', updateValue);
productDescription.addEventListener('input', updateValue);
productImageInput.addEventListener('change', (event) => {
    productToSave.productImage = event.target.files[0]
})
saveProductBtn.addEventListener('click', saveNewProduct);





