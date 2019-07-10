package com.metalSlime.services;

import com.metalSlime.dao.GradeDao;
import com.metalSlime.dao.GradeDaoImpl;
import com.metalSlime.pojos.Grade;

public class GradeServiceImpl implements GradeService {

	GradeDao gd = new GradeDaoImpl();
	
	@Override
	public void sendGradeFiles(Grade g) {
		gd.sendGradeFiles(g);
		
	}

	@Override
	public Grade getGrade(int reqNum) {
		return gd.getGrade(reqNum);
	}
}
