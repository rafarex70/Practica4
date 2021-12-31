package com.cloudapps.practica4;

public class Planning {
	String planning;

	public String getPlanning() {
		return planning;
	}

	public void setPlanning(String planning) {
		this.planning = planning;
	}
	
	public void addPlanning(String adicional) {
		this.planning += "-"+adicional;
	}
	
	public void proccessPlanning(String adicional, EolicPlant eolicplant) {
		this.planning += "-"+adicional;
		eolicplant.setProgress(eolicplant.getProgress()+25);
	}
	
	public String getFormatPlanning() {
	    if (this.planning.toLowerCase().charAt(0)>"m".charAt(0)) {
	    	return this.planning.toUpperCase();
	    } else return this.planning.toLowerCase();
	}

	@Override
	public String toString() {
		return "Planning [planning=" + planning + "]";
	}
	
}
