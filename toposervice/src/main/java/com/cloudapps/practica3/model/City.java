package com.cloudapps.practica3.model;

import org.springframework.data.annotation.Id;

public class City {

	@Id
	private String id;

	private String landscape;
	
	public City() {
	}

	public City(String id, String landscape) {
		super();
		this.id = id;
		this.landscape = landscape;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLandscape() {
		return landscape;
	}

	public void setLandscape(String landscape) {
		this.landscape = landscape;
	}
}
