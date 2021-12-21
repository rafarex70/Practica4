package com.cloudapps.practica4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding(PlanningProcessor.class)
public class PlannerApplication {
	
	private static final Logger log = LoggerFactory.getLogger(PlannerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(PlannerApplication.class, args);
	}

}
