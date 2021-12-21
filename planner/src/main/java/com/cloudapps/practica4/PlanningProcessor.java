package com.cloudapps.practica4;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

@Component
public interface PlanningProcessor {

  String INPUT_STREAM = "eoloplantCreationRequests";
  String OUTPUT_STREAM = "eoloplantCreationProgressNotifications";

  @Input(INPUT_STREAM)
  SubscribableChannel eoloplantCreationRequests();

  @Output(OUTPUT_STREAM)
  MessageChannel eoloplantCreationProgressNotifications();

}
