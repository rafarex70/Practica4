package com.cloudapps.practica4.clients;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class TopoService {
	public static final Logger log = LoggerFactory.getLogger(TopoService.class);
	
	private final WebClient webClient;

	public TopoService(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.baseUrl("http://localhost:8089/api/topographicdetails/").build();
	}

	@Async
	public CompletableFuture<String> getLandscape(String city) {
		return this.webClient.get().uri("/{city}", city)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(Topo.class)
				.map(respuesta -> respuesta.getLandscape())
				.onErrorResume(e -> Mono.just("nd"))
				.toFuture();
	}
	
}
