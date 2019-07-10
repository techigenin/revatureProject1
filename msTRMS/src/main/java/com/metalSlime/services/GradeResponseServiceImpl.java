package com.metalSlime.services;

import com.metalSlime.dao.GradeResponseDao;
import com.metalSlime.dao.GradeResponseDaoImpl;
import com.metalSlime.pojos.GradeResponse;
import com.metalSlime.servlets.GradeResponseService;

public class GradeResponseServiceImpl implements GradeResponseService {

	GradeResponseDao grd = new GradeResponseDaoImpl();
	
	@Override
	public void sendResponse(GradeResponse gr) {
		grd.sendResponse(gr);

	}

}
