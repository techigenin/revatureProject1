package com.metalSlime.pojos;

import java.time.*;

public class Request {
	private String empName;
	private Integer reqNum;
	private LocalDate eventDate; 
	private LocalTime eventTime; 
	private String eventType;
	private String eventLoc; 
	private String eventDesc; 
	private Double eventCost; 
	private String gradeFmt; 
	private LocalDate appliedDate;
	private String status;
	private Double rmbAmt;
	private boolean hasGrade;
	
	public Request() {
		super();
	}
	
	public Request(LocalDate eventDate, LocalTime eventTime, String eventLoc, String eventDesc, Double eventCost,
			String gradeFmt, LocalDate appliedDate, String status) {
		super();
		this.eventDate = eventDate;
		this.eventTime = eventTime;
		this.eventLoc = eventLoc;
		this.eventDesc = eventDesc;
		this.eventCost = eventCost;
		this.gradeFmt = gradeFmt;
		this.appliedDate = appliedDate;
		this.status = status;
	}
	
	public Request(String empName, Integer req_num, LocalDate eventDate, LocalTime eventTime, String eventLoc,
			String eventDesc, Double eventCost, String gradeFmt, LocalDate appliedDate, String status) {
		super();
		this.empName = empName;
		this.reqNum = req_num;
		this.eventDate = eventDate;
		this.eventTime = eventTime;
		this.eventLoc = eventLoc;
		this.eventDesc = eventDesc;
		this.eventCost = eventCost;
		this.gradeFmt = gradeFmt;
		this.appliedDate = appliedDate;
		this.status = status;
	}


	
	public Request(String empName, Integer reqNum, LocalDate eventDate, LocalTime eventTime, String eventType,
			String eventLoc, String eventDesc, Double eventCost, String gradeFmt, LocalDate appliedDate, String status,
			Double rmbAmt, boolean hasGrade) {
		super();
		this.empName = empName;
		this.reqNum = reqNum;
		this.eventDate = eventDate;
		this.eventTime = eventTime;
		this.eventType = eventType;
		this.eventLoc = eventLoc;
		this.eventDesc = eventDesc;
		this.eventCost = eventCost;
		this.gradeFmt = gradeFmt;
		this.appliedDate = appliedDate;
		this.status = status;
		this.rmbAmt = rmbAmt;
		this.hasGrade = hasGrade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appliedDate == null) ? 0 : appliedDate.hashCode());
		result = prime * result + ((empName == null) ? 0 : empName.hashCode());
		result = prime * result + ((eventCost == null) ? 0 : eventCost.hashCode());
		result = prime * result + ((eventDate == null) ? 0 : eventDate.hashCode());
		result = prime * result + ((eventDesc == null) ? 0 : eventDesc.hashCode());
		result = prime * result + ((eventLoc == null) ? 0 : eventLoc.hashCode());
		result = prime * result + ((eventTime == null) ? 0 : eventTime.hashCode());
		result = prime * result + ((gradeFmt == null) ? 0 : gradeFmt.hashCode());
		result = prime * result + ((reqNum == null) ? 0 : reqNum.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		Request other = (Request) obj;
		if (appliedDate == null) {
			if (other.appliedDate != null)
				return false;
		} else if (!appliedDate.equals(other.appliedDate))
			return false;
		if (empName == null) {
			if (other.empName != null)
				return false;
		} else if (!empName.equals(other.empName))
			return false;
		if (eventCost == null) {
			if (other.eventCost != null)
				return false;
		} else if (!eventCost.equals(other.eventCost))
			return false;
		if (eventDate == null) {
			if (other.eventDate != null)
				return false;
		} else if (!eventDate.equals(other.eventDate))
			return false;
		if (eventDesc == null) {
			if (other.eventDesc != null)
				return false;
		} else if (!eventDesc.equals(other.eventDesc))
			return false;
		if (eventLoc == null) {
			if (other.eventLoc != null)
				return false;
		} else if (!eventLoc.equals(other.eventLoc))
			return false;
		if (eventTime == null) {
			if (other.eventTime != null)
				return false;
		} else if (!eventTime.equals(other.eventTime))
			return false;
		if (gradeFmt == null) {
			if (other.gradeFmt != null)
				return false;
		} else if (!gradeFmt.equals(other.gradeFmt))
			return false;
		if (reqNum == null) {
			if (other.reqNum != null)
				return false;
		} else if (!reqNum.equals(other.reqNum))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	public LocalDate getEventDate() {
		return eventDate;
	}
	public void setEventDate(LocalDate eventDate) {
		this.eventDate = eventDate;
	}
	public void setEventDate(String str) {
		this.eventDate = LocalDate.parse(str);
	}
	public LocalTime getEventTime() {
		return eventTime;
	}
	public void setEventTime(LocalTime eventTime) {
		this.eventTime = eventTime;
	}
	public void setEventTime(String str) {
		this.eventTime = LocalTime.parse(str);
	}
	public String getEventLoc() {
		return eventLoc;
	}
	public void setEventLoc(String eventLoc) {
		this.eventLoc = eventLoc;
	}
	public String getEventDesc() {
		return eventDesc;
	}
	public void setEventDesc(String eventDesc) {
		this.eventDesc = eventDesc;
	}
	public Double getEventCost() {
		return eventCost;
	}
	public void setEventCost(Double eventCost) {
		this.eventCost = eventCost;
	}
	public String getGradeFmt() {
		return gradeFmt;
	}
	public void setGradeFmt(String gradeFmt) {
		this.gradeFmt = gradeFmt;
	}
	public LocalDate getAppliedDate() {
		return appliedDate;
	}
	public void setAppliedDate(LocalDate appliedDate) {
		this.appliedDate = appliedDate;
	}
	public void setAppliedDate(String str) {
		this.appliedDate = LocalDate.parse(str);
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public Integer getReqNum() {
		return reqNum;
	}

	public void setReqNum(Integer req_num) {
		this.reqNum = req_num;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public Double getRmbAmt() {
		return rmbAmt;
	}

	public void setRmbAmt(Double rmbAmt) {
		this.rmbAmt = rmbAmt;
	}

	public boolean getHasGrade() {
		return hasGrade;
	}

	public void setHasGrade(boolean hasGrade) {
		this.hasGrade = hasGrade;
	}
}
