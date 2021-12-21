package com.cloudapps.practica3.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
		System.out.println("---------REQUEST---------");
	    System.out.println(Thread.currentThread()); //Verificación hilo que se ejecuta
	    try {
	        Thread.sleep(1000+(new Random().nextLong(2000))); // simulate thread somehow got blocked
	    } catch (InterruptedException e) {
	    	return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
	    }
        return cityService.findById(id);
    }

}
