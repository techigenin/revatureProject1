package com.metalSlime.msExperiment.pojo;

public class EmployeeRequest {
	private String name;
	private String date;
	private String time;
	private String location;
	private String description;
	private String cost;
	private String gradeFormat;
	private String eventType;
	
	
	public EmployeeRequest(String name, String date, String time, String location, String description, String cost,
			String gradeFormat, String eventType) {
		super();
		this.name = name;
		this.date = date;
		this.time = time;
		this.location = location;
		this.description = description;
		this.cost = cost;
		this.gradeFormat = gradeFormat;
		this.eventType = eventType;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getCost() {
		return cost;
	}


	public void setCost(String cost) {
		this.cost = cost;
	}


	public String getGradeFormat() {
		return gradeFormat;
	}


	public void setGradeFormat(String gradeFormat) {
		this.gradeFormat = gradeFormat;
	}


	public String getEventType() {
		return eventType;
	}


	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	
	
}
