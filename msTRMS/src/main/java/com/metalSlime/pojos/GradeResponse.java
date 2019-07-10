package com.metalSlime.pojos;

public class GradeResponse {
	private int reqNum;
	private boolean presentation;
	private int response;
	
	
	public GradeResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GradeResponse(int reqNum, boolean presentation, int response) {
		super();
		this.reqNum = reqNum;
		this.presentation = presentation;
		this.response = response;
	}
	public int getReqNum() {
		return reqNum;
	}
	public void setReqNum(int reqNum) {
		this.reqNum = reqNum;
	}
	public boolean getPresentation() {
		return presentation;
	}
	public void setPresentation(boolean presentation) {
		this.presentation = presentation;
	}
	public int getResponse() {
		return response;
	}
	public void setResponse(int response) {
		this.response = response;
	}
	
	

}
