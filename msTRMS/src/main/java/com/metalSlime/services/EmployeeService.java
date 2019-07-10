package com.metalSlime.services;

import com.metalSlime.pojos.Request;

public interface EmployeeService {
	
	Request[] getRequestsByEID(Integer eid);

	Request[] getSubsRequests(Integer eid);

	String getEmployeeName(Integer eid);

	Request[] getBencoRequests(Integer eid);
}
