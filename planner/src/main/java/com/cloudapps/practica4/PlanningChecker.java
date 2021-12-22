package com.cloudapps.practica4;

import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.cloudapps.practica4.clients.TopoService;
import com.cloudapps.practica4.clients.WeatherService;

@Component
public class PlanningChecker {

	public static final Logger log = LoggerFactory.getLogger(PlanningChecker.class);
	private PlanningProcessor processor;
	private TopoService topoService;
	@Autowired
	private WeatherService weatherservice;

	@Autowired
	public PlanningChecker(PlanningProcessor processor, TopoService topoService) {
		this.processor = processor;
		this.topoService = topoService;
	}

	@StreamListener(PlanningProcessor.INPUT_STREAM)
	@Async
	public void checkAndSortEolicPlant(EolicPlant eolicPlant) throws Exception {

		log.info("INIT eolicPlant: " + eolicPlant);

		eolicPlant.setProgress(25);

		String planning = eolicPlant.getCity();
		CompletableFuture<String> landscape = getLanscape(eolicPlant);
		CompletableFuture<String> weather =  getWeather(eolicPlant);
		CompletableFuture.allOf(landscape,weather).join();
		eolicPlant.setProgress(25);
		notificar(eolicPlant);
		planning+="-"+landscape.get();
		eolicPlant.setProgress(50);
		notificar(eolicPlant);
		planning+="-"+weather.get();
		eolicPlant.setProgress(75);
		notificar(eolicPlant);
		
		eolicPlant.setCompleted(true);
		eolicPlant.setProgress(100);
		eolicPlant.setPlanning(planning);
		notificar(eolicPlant);
	}
	
	public void notificar(EolicPlant eolicPlant) throws Exception {

		processor.eoloplantCreationProgressNotifications().send(message(eolicPlant));
		log.info("ENVIO eolicPlant: " + eolicPlant);
	}
	
	@Async
	public CompletableFuture<String> getLanscape(EolicPlant eolicPlant) throws Exception {
		CompletableFuture<String> landscape = topoService.getLandscape(eolicPlant.getCity());
		
		return landscape;
	}
	
	@Async
	public CompletableFuture<String> getWeather(EolicPlant eolicPlant) throws Exception {
		CompletableFuture<String> weather = weatherservice.getWeather(eolicPlant.getCity());
		return weather;
	}

	private static final <T> Message<T> message(T val) {
		return MessageBuilder.withPayload(val).build();
	}
}
