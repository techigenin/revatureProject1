package com.metalSlime.pojos;

public class RequestResponse {
	private int reqNum;
	private boolean response;
	public RequestResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RequestResponse(int reqNum, boolean response) {
		super();
		this.reqNum = reqNum;
		this.response = response;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + reqNum;
		result = prime * result + (response ? 1231 : 1237);
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
		RequestResponse other = (RequestResponse) obj;
		if (reqNum != other.reqNum)
			return false;
		if (response != other.response)
			return false;
		return true;
	}
	public int getReqNum() {
		return reqNum;
	}
	public void setReqNum(int reqNum) {
		this.reqNum = reqNum;
	}
	public boolean getResponse() {
		return response;
	}
	public void setResponse(boolean response) {
		this.response = response;
	}
}
