'use strict';

import indexScss from '../scss/menu.scss';
import productsScss from '../scss/products.scss';

const WEATHER_DEFINITIONS_API = 'http://localhost:6082/weather/definitions'

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

async function loadWeatherDefinitions() {

    let response = fetch(WEATHER_DEFINITIONS_API, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': 'http://localhost:3000'
        }
    })

    response.then(
        x => x.json(),
        err => console.log(err)
    ).then(
        data => processApiData(data)
    )
}

loadWeatherDefinitions()

function processApiData(restApiData) {
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
        }
    })
    let newLabel = document.createElement('label');
    newLabel.setAttribute('for', weatherDefinition.id);
    newLabel.innerText = weatherDefinition.definition;
    newCheckboxContainer.appendChild(newCheckBox);
    newCheckboxContainer.appendChild(newLabel);
    checkboxesDiv.appendChild(newCheckboxContainer)

}




