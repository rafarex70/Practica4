import { credentials } from '@grpc/grpc-js';
import { WeatherService } from './proto/proto.js';
import { promisify } from 'util';

export default async function (city){
    var weatherService = new WeatherService('localhost:9090', credentials.createInsecure());

    weatherService.GetWeather = promisify(weatherService.GetWeather.bind(weatherService));

    const response = await weatherService.GetWeather({city: city});

    console.log("WeatherService Response: "+JSON.stringify(response));

    return response.weather;
}
