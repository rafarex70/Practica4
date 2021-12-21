package com.cloudapps.practica3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.cloudapps.practica3.model.City;
import com.cloudapps.practica3.repository.ReactiveCityRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CityService {
	
	@Autowired
	private ReactiveCityRepository cityRepository;

	public void save(City city) {
		cityRepository.save(city);		
	}

	public Flux<City> findAll() {
		return cityRepository.findAll();
	}

	public Mono<City> findById(String id) {
		return cityRepository.findById(id.toLowerCase())
				.switchIfEmpty(
                Mono.error(new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "City with id "+id+" not found")));
	}
	
	public Mono<City> deleteUser(String id) {
        Mono<City> deletedUser = cityRepository.findById(id);
        return deletedUser.flatMap(user -> cityRepository.deleteById(id).then(Mono.just(user)));
    }
}
