package com.cloudapps.practica3.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.cloudapps.practica3.model.City;

import reactor.core.publisher.Mono;

public interface ReactiveCityRepository extends ReactiveCrudRepository<City, String> {
	Mono<City> findByIdIgnoreCase(String id);
}
