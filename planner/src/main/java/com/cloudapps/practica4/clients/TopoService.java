package com.cloudapps.practica4.clients;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.node.ObjectNode;

@Component
public class TopoService {
	public static final Logger log = LoggerFactory.getLogger(TopoService.class);
	
	@Async
	public CompletableFuture<String> getLandscape(@RequestParam String city) {

		RestTemplate restTemplate = new RestTemplate();

		String url = "http://localhost:8089/api/topographicdetails/" + city;

		ObjectNode data = restTemplate.getForObject(url, ObjectNode.class);

		log.info("Response received from toposerver: " + data.get("landscape").asText());
		return CompletableFuture.completedFuture(data.get("landscape").asText());
	}
}
