package com.metalSlime.dao;

import com.metalSlime.pojos.Grade;

public interface GradeDao {
	void sendGradeFiles(Grade g);

	Grade getGrade(int reqNum);
}
