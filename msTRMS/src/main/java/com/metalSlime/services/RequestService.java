package com.metalSlime.services;

import com.metalSlime.pojos.Event;
import com.metalSlime.pojos.Grade;
import com.metalSlime.pojos.RequestFiles;
import com.metalSlime.pojos.RequestResponse;

public interface RequestService {
	
	int addRequest(Integer eid, Event event);
	
	void submitReqResp(Integer eid, RequestResponse[] responses);

	void sendRequestFiles(RequestFiles rf);

	RequestFiles getRequestFiles(int reqNum);

}
