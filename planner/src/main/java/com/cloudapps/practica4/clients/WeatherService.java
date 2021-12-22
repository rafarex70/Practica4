package com.cloudapps.practica4.clients;

import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import net.devh.boot.grpc.client.inject.GrpcClient;
import weatherservice.GetWeatherRequest;
import weatherservice.Weather;
import weatherservice.WeatherServiceGrpc.WeatherServiceBlockingStub;

@Component
public class WeatherService {

	@GrpcClient("weatherServer")
	private WeatherServiceBlockingStub client;
	

	@Async
	public Future<Weather> getWeather(String city) throws Exception {
		
		GetWeatherRequest request = GetWeatherRequest.newBuilder()
	            .setCity("Madrid")
	            .build();
	        
		Weather response = client.getWeather(request);

	    System.out.println("Response received from server:\n" + response);
	    return new AsyncResult<Weather>(response);
		
	}	
}
