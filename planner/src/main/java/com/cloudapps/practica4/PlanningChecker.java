package com.cloudapps.practica4;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.cloudapps.practica4.service.TopoService;

@Component
public class PlanningChecker {

	public static final Logger log = LoggerFactory.getLogger(PlanningChecker.class);
	private PlanningProcessor processor;
	private TopoService topoService;

	@Autowired
	public PlanningChecker(PlanningProcessor processor, TopoService topoService) {
		this.processor = processor;
		this.topoService = topoService;
	}

	@StreamListener(PlanningProcessor.INPUT_STREAM)
	@Async
	public void checkAndSortEolicPlant(EolicPlant eolicPlant) throws InterruptedException, ExecutionException {

		log.info("INIT eolicPlant: " + eolicPlant);

		Future<String> future = topoService.getLandscape(eolicPlant.getCity());
		eolicPlant.setCompleted(true);
		eolicPlant.setProgress(100);
		eolicPlant.setPlanning(future.get());
		processor.eoloplantCreationProgressNotifications().send(message(eolicPlant));
		log.info("FIN eolicPlant: " + eolicPlant);

	}

	private static final <T> Message<T> message(T val) {
		return MessageBuilder.withPayload(val).build();
	}
}
