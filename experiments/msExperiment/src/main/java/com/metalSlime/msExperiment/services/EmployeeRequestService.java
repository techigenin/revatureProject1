package com.metalSlime.msExperiment.services;

import org.json.JSONObject;

import com.metalSlime.msExperiment.pojo.EmployeeRequest;

public interface EmployeeRequestService {
	
	EmployeeRequest buildEmployeeRequest(JSONObject obj);
	
	void addEmployeeRequest(EmployeeRequest er);
	EmployeeRequest getEmployeeRequest(String name);
}
