package com.cloudapps.practica3.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudapps.practica3.model.City;
import com.cloudapps.practica3.repository.ReactiveCityRepository;

import reactor.core.publisher.Flux;

@Service
public class SampleDataService {

	@Autowired
	private ReactiveCityRepository cityRepository; 
	
	@PostConstruct
	public void init() {

		Flux<City> cities = Flux.just(
	        	new City("madrid", "flat"),
	        	new City("barcelona", "flat"),
	        	new City("valencia", "flat"),
	        	new City("sevilla", "flat"),
	        	new City("albacete", "flat"),
	        	new City("segovia", "mountain"),
	        	new City("avila", "mountain"),
	        	new City("cuenca", "mountain"),
	        	new City("toledo", "mountain"),
	        	new City("guadalajara", "mountain"),
				new City("bilbao", "mountain"));

	        cities
	            .flatMap(this.cityRepository::save)
	            .blockLast();
		
	}
	
}

