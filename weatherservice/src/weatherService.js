export async function GetWeather(call, callback){

    console.log('Request received: '+JSON.stringify(call));

    let city = call.request.city;
    if (!city || city.length <= 0) {
        callback({
            code: 400,
            message: "invalid input"
          })
    } else {
        let response = {}
        if (noStartWithVocal(city)) {
            response = { city: city, weather: "Sunny" };
        } else {
            response = { city: city, weather: "Rainy" };
        }

        await sleep();
        callback(null, response);
    }    
}

function sleep() {
    return new Promise(resolve => {
        setTimeout(() => resolve(), (Math.random() * 2000) + 1000);
    });
}

function noStartWithVocal(city) {
    const vocals = ["a", "e", "i", "o", "u"];
    return vocals.indexOf(city.toLowerCase().charAt(0)) === -1;
}
    