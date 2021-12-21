package com.cloudapps.practica4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class PlanningChecker {

  public static final Logger log = LoggerFactory.getLogger(PlanningChecker.class);
  private PlanningProcessor processor;

  @Autowired
  public PlanningChecker(PlanningProcessor processor) {
	  this.processor = processor;
  }

  @StreamListener(PlanningProcessor.INPUT_STREAM)
  public void checkAndSortEolicPlant(EolicPlant eolicPlant) {
	  
	  log.info("INIT eolicPlant: "+eolicPlant);


	  eolicPlant.setCompleted(true);
	  eolicPlant.setProgress(100);
	  eolicPlant.setPlanning("planificadoooo");
	  processor.eoloplantCreationProgressNotifications().send(message(eolicPlant));
   

	  log.info("FIN eolicPlant: "+eolicPlant);

  }

  private static final <T> Message<T> message(T val) {
    return MessageBuilder.withPayload(val).build();
  }
}
