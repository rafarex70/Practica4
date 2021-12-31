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
	@Autowired
	private PlanningProcessor processor;
	@Autowired
	private TopoService topoService;
	@Autowired
	private WeatherService weatherservice;

	@StreamListener(PlanningProcessor.INPUT_STREAM)
	@Async
	public void checkAndSortEolicPlant(EolicPlant eolicPlant) throws Exception {

		log.info("INIT eolicPlant: " + eolicPlant);

		Planning planning = new Planning();
		planning.setPlanning(eolicPlant.getCity());
		CompletableFuture<Void> landscapeCF = topoService.getLandscape(eolicPlant.getCity())
				.thenAccept(landscape -> {
					planning.proccessPlanning(landscape,eolicPlant);
					notificar(eolicPlant);
				});
		CompletableFuture<Void> weatherCF = weatherservice.getWeather(eolicPlant.getCity())
				.thenAccept(weather -> {
					planning.proccessPlanning(weather,eolicPlant);
					notificar(eolicPlant);
				});
		eolicPlant.setProgress(25);
		notificar(eolicPlant);
		CompletableFuture.allOf(landscapeCF,weatherCF).join();

		eolicPlant.setCompleted(true);
		eolicPlant.setProgress(100);
		eolicPlant.setPlanning(planning.getFormatPlanning());
		notificar(eolicPlant);
	}
	
	public void notificar(EolicPlant eolicPlant) {

		processor.eoloplantCreationProgressNotifications().send(message(eolicPlant));
		log.info("ENVIO eolicPlant: " + eolicPlant);
	}


	private static final <T> Message<T> message(T val) {
		return MessageBuilder.withPayload(val).build();
	}
}
