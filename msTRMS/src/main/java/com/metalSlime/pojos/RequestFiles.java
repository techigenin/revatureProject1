package com.metalSlime.pojos;

public class RequestFiles {
	
	private int reqNum;
	private String dSupAppFileName;
	private String deptAppFileName;
	private String eventFileNames; // as a reminder, JS array kept as JSON string in DB
	
	public RequestFiles(int reqNum, String dSupAppFileName, String deptAppFileName, String eventFileNames) {
		super();
		this.reqNum = reqNum;
		this.dSupAppFileName = dSupAppFileName;
		this.deptAppFileName = deptAppFileName;
		this.eventFileNames = eventFileNames;
	}
	
	public RequestFiles() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dSupAppFileName == null) ? 0 : dSupAppFileName.hashCode());
		result = prime * result + ((deptAppFileName == null) ? 0 : deptAppFileName.hashCode());
		result = prime * result + ((eventFileNames == null) ? 0 : eventFileNames.hashCode());
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
		RequestFiles other = (RequestFiles) obj;
		if (dSupAppFileName == null) {
			if (other.dSupAppFileName != null)
				return false;
		} else if (!dSupAppFileName.equals(other.dSupAppFileName))
			return false;
		if (deptAppFileName == null) {
			if (other.deptAppFileName != null)
				return false;
		} else if (!deptAppFileName.equals(other.deptAppFileName))
			return false;
		if (eventFileNames == null) {
			if (other.eventFileNames != null)
				return false;
		} else if (!eventFileNames.equals(other.eventFileNames))
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
	public String getdSupAppFileName() {
		return dSupAppFileName;
	}
	public void setdSupAppFileName(String dSupAppFileName) {
		this.dSupAppFileName = dSupAppFileName;
	}
	public String getDeptAppFileName() {
		return deptAppFileName;
	}
	public void setDeptAppFileName(String deptAppFileName) {
		this.deptAppFileName = deptAppFileName;
	}
	public String getEventFileNames() {
		return eventFileNames;
	}
	public void setEventFileNames(String eventFileNames) {
		this.eventFileNames = eventFileNames;
	}
	
	
}
