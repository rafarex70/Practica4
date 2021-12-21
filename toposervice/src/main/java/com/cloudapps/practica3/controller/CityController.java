package com.cloudapps.practica3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloudapps.practica3.model.City;
import com.cloudapps.practica3.service.CityService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/topographicdetails")
public class CityController {

	@Autowired
	private CityService cityService;

	@GetMapping("/{id}")
    public Mono<City> getCity(@PathVariable String id) {
        return cityService.findById(id);
    }

}
