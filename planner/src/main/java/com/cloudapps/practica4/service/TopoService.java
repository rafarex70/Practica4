package com.cloudapps.practica4.service;

import java.util.concurrent.Future;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Component
public class TopoService {
	
	@Async
	public Future<String> getLandscape(@RequestParam String city) {

		RestTemplate restTemplate = new RestTemplate();

		String url = "http://localhost:8089/api/topographicdetails/" + city;

		ObjectNode data = restTemplate.getForObject(url, ObjectNode.class);

		return new AsyncResult<String>(data.get("landscape").asText());
	}
}
