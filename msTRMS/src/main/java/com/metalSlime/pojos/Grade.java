package com.metalSlime.pojos;

public class Grade {
	private int reqNum;
	private String grade;
	private String gradeFiles; // JSON string of grade URLS
	
	public Grade(int reqNum, String grade, String gradeFiles) {
		super();
		this.reqNum = reqNum;
		this.grade = grade;
		this.gradeFiles = gradeFiles;
	}
	
	public Grade() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((grade == null) ? 0 : grade.hashCode());
		result = prime * result + ((gradeFiles == null) ? 0 : gradeFiles.hashCode());
		result = prime * result + reqNum;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Grade other = (Grade) obj;
		if (grade == null) {
			if (other.grade != null)
				return false;
		} else if (!grade.equals(other.grade))
			return false;
		if (gradeFiles == null) {
			if (other.gradeFiles != null)
				return false;
		} else if (!gradeFiles.equals(other.gradeFiles))
			return false;
		if (reqNum != other.reqNum)
			return false;
		return true;
	}
	
	public int getReqNum() {
		return reqNum;
	}
	public void setReqNum(int reqNum) {
		this.reqNum = reqNum;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getGradeFiles() {
		return gradeFiles;
	}
	public void setGradeFiles(String gradeFiles) {
		this.gradeFiles = gradeFiles;
	}
}
