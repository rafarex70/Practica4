package com.cloudapps.practica4.clients;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import net.devh.boot.grpc.client.inject.GrpcClient;
import weatherservice.GetWeatherRequest;
import weatherservice.Weather;
import weatherservice.WeatherServiceGrpc.WeatherServiceBlockingStub;

@Component
public class WeatherService {
	public static final Logger log = LoggerFactory.getLogger(WeatherService.class);

	@GrpcClient("weatherServer")
	private WeatherServiceBlockingStub client;
	

	@Async
	public CompletableFuture<String> getWeather(String city) throws Exception {
		
		GetWeatherRequest request = GetWeatherRequest.newBuilder()
	            .setCity("Madrid")
	            .build();
	        
		Weather response = client.getWeather(request);

		log.info("Response received from weatherserver: " + response.getWeather());
	    return CompletableFuture.completedFuture(response.getWeather());
		
	}	
}
