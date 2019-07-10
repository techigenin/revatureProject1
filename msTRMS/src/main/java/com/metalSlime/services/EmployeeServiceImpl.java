package com.metalSlime.services;

import java.util.Arrays;
import java.util.HashSet;

import com.metalSlime.dao.EmployeeDao;
import com.metalSlime.dao.EmployeeDaoImpl;
import com.metalSlime.pojos.Request;

public class EmployeeServiceImpl implements EmployeeService {

	EmployeeDao ed = new EmployeeDaoImpl();
	
	@Override
	public Request[] getRequestsByEID(Integer eid) {
		return ed.getRequestsByEid(eid);
	}

	@Override
	public Request[] getSubsRequests(Integer eid) {
		HashSet<Request> requests = new HashSet<Request>();
		requests.addAll(Arrays.asList(ed.getDeptSubsRequestsByEid(eid))); // Returns empty if not dept head. =)
		requests.addAll(Arrays.asList(ed.getDirectSubsRequestsByEid(eid))); // Also returns empty if has no direct subs.
		
		return requests.toArray(new Request[0]); 
	}
	
	@Override
	public String getEmployeeName(Integer eid) {
		return ed.getEmployeeName(eid);
	}

	@Override
	public Request[] getBencoRequests(Integer eid) {
		return ed.getBencoRequests(eid); 
	}
}
