package com.metalSlime.msExperiment.services;

import org.json.JSONObject;

import com.metalSlime.msExperiment.dao.EmployeeRequestDao;
import com.metalSlime.msExperiment.dao.EmployeeRequestDaoImpl;
import com.metalSlime.msExperiment.pojo.EmployeeRequest;

public class EmployeeRequestServiceImpl implements EmployeeRequestService {
	private static EmployeeRequestDao eDao = new EmployeeRequestDaoImpl();

	public EmployeeRequest buildEmployeeRequest(JSONObject obj) {
		
		String name = obj.getString("name");
		String date = obj.getString("date");
		String time = obj.getString("time");
		String location = obj.getString("location");
		String description = obj.getString("description");
		String cost = obj.getString("cost");
		String gradeFormat = obj.getString("gradeFormat");
		String eventType = obj.getString("eventType");
		
		return new EmployeeRequest(name, date, time, 
				location, description, cost, 
				gradeFormat, eventType);
	}

	@Override
	public void addEmployeeRequest(EmployeeRequest er) {
		eDao.createRequest(er);
	}

	@Override
	public EmployeeRequest getEmployeeRequest(String name) {
		return eDao.getEmployeeRequestByName(name);
	}

}
