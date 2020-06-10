'use strict';

import indexScss from '../scss/menu.scss';

const PRODUCTS_FOR_WEATHER = 'http://localhost:6083/products/weather';
let positionObject = {
    latitude : '',
    longitude: ''
}

// function getLocation() {
//      window.navigator.geolocation.getCurrentPosition(geolocationPermitted, geolocationDenied)
// }
//
// function geolocationPermitted(position) {
//     positionObject.latitude  = position.coords.latitude;
//     positionObject.longitude = position.coords.longitude;
//
//     console.log(positionObject);
//
//     requestProductsBasedOnCurrentWeather()
// }

function geolocationDenied() {
    console.log("You did not allow geolocation to work");
}

//`${PRODUCTS_FOR_WEATHER}/${positionObject.latitude}/${positionObject.longitude}`
async function requestProductsBasedOnCurrentWeather() {
    console.log(positionObject.longitude);
    console.log('----> ' + positionObject.latitude);
    console.log(`${PRODUCTS_FOR_WEATHER}/${positionObject.latitude}/${positionObject.longitude}`)
    let response = await fetch(`${PRODUCTS_FOR_WEATHER}/${positionObject.latitude}/${positionObject.longitude}`, {
        method: 'GET',
        headers: {
            'Content-Type' : 'application/json',
            'Access-Control-Allow-Origin' : 'http://localhost:3000'
        }
    })

    let products = await response.json();
    console.log(products)

    return products;
}

// calling functions
// getLocation();

