package com.metalSlime.dao;

import com.metalSlime.pojos.Request;

public interface EmployeeDao {
	Request[] getRequestsByEid(Integer eid);

	Request[] getDeptSubsRequestsByEid(Integer eid);

	Request[] getDirectSubsRequestsByEid(Integer eid);
	
	String getEmployeeName(Integer eid);

	Request[] getBencoRequests(Integer eid);
}
