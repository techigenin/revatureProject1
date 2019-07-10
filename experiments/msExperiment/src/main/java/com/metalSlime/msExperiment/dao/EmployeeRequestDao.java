package com.metalSlime.msExperiment.dao;

import com.metalSlime.msExperiment.pojo.EmployeeRequest;

public interface EmployeeRequestDao {
	
	public boolean requestExists(EmployeeRequest er);
	
	public boolean createRequest(EmployeeRequest er);
	
	public boolean deleteRequest(EmployeeRequest er);
	
	public EmployeeRequest getEmployeeRequestByName(String name);

}
