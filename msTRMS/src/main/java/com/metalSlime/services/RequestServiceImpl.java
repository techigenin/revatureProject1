package com.metalSlime.services;

import java.util.Arrays;
import java.util.HashSet;

import com.metalSlime.dao.RequestDao;
import com.metalSlime.dao.RequestDaoImpl;
import com.metalSlime.pojos.Request;
import com.metalSlime.pojos.RequestFiles;
import com.metalSlime.pojos.Event;
import com.metalSlime.pojos.Grade;
import com.metalSlime.pojos.RequestResponse;

public class RequestServiceImpl implements RequestService {

	private RequestDao rd = new RequestDaoImpl();
	
	
	@Override
	public int addRequest(Integer eid, Event event) {
	
		return rd.addRequest(eid, event);
		// Eventually send the update list back to view
	}


	@Override
	public void submitReqResp(Integer eid, RequestResponse[] responses) {
		
		for (RequestResponse r : responses) {
			if (r.getResponse() == true)
				rd.approveRequest(eid, r.getReqNum());
			else if (r.getResponse() == false)
				rd.denyRequest(eid, r.getReqNum());
		}
	}


	@Override
	public void sendRequestFiles(RequestFiles rf) {
		rd.sendRequestFiles(rf);
		
	}


	@Override
	public RequestFiles getRequestFiles(int reqNum) {
		return rd.getRequestFiles(reqNum);
	}

}
