package com.metalSlime.services;

import com.metalSlime.pojos.Grade;

public interface GradeService {

	void sendGradeFiles(Grade g);

	Grade getGrade(int reqNum);
}
