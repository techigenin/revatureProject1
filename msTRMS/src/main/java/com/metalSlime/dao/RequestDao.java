package com.metalSlime.dao;

import com.metalSlime.pojos.Event;
import com.metalSlime.pojos.Grade;
import com.metalSlime.pojos.RequestFiles;

public interface RequestDao {

	int addRequest(Integer eid, Event event);
	
	void approveRequest(Integer eid, int reqNum);

	void denyRequest(Integer eid, int reqNum);

	void sendRequestFiles(RequestFiles rf);

	RequestFiles getRequestFiles(int reqNum);

}
