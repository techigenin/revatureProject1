package com.metalSlime.pojos;

import java.math.BigDecimal;
import java.time.*;

public class Event {
	private LocalDate date;
	private LocalTime time;
	private Integer	type;
	private String loc;
	private String desc;
	private double cost;
	private String gfmt;
	private String durr;
	private double rmbAmt;
	
	// Adding a random comment
	// Adding another comment
	
	public Event() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Event(LocalDate date, LocalTime time, String loc, String desc, double cost, String gfmt) {
		super();
		this.date = date;
		this.time = time;
		this.loc = loc;
		this.desc = desc;
		this.cost = cost;
		this.gfmt = gfmt;
	}
	
	public Event(LocalDate date, LocalTime time, Integer type, String loc, String desc, double cost, String gfmt) {
		super();
		this.date = date;
		this.time = time;
		this.type = type;
		this.loc = loc;
		this.desc = desc;
		this.cost = cost;
		this.gfmt = gfmt;
	}
	
	public Event(LocalDate date, LocalTime time, Integer type, String loc,
				String desc, double cost, String gfmt, String durr) {
		super();
		this.date = date;
		this.time = time;
		this.type = type;
		this.loc = loc;
		this.desc = desc;
		this.cost = cost;
		this.gfmt = gfmt;
		this.durr = durr;
	}
	
	@Override
	public String toString() {
		return "Event [date=" + date + ", time=" + time + ", loc=" + loc + ", desc=" + desc + ", cost=" + cost
				+ ", gfmt=" + gfmt + "]";
	}
	
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public void setDate(String str) {
		this.date = LocalDate.parse(str);
	}
	public LocalTime getTime() {
		return time;
	}
	public void setTime(LocalTime time) {
		this.time = time;
	}
	public void setTime(String str) {
		this.time = LocalTime.parse(str);
	}
	public String getLoc() {
		return loc;
	}
	public void setLoc(String loc) {
		this.loc = loc;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public double getCost() {
		return cost;
	}
	public BigDecimal getCostBD() {
		return new BigDecimal(cost);
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public String getGfmt() {
		return gfmt;
	}
	public void setGfmt(String gfmt) {
		this.gfmt = gfmt;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(cost);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((desc == null) ? 0 : desc.hashCode());
		result = prime * result + ((gfmt == null) ? 0 : gfmt.hashCode());
		result = prime * result + ((loc == null) ? 0 : loc.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
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
		Event other = (Event) obj;
		if (Double.doubleToLongBits(cost) != Double.doubleToLongBits(other.cost))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (desc == null) {
			if (other.desc != null)
				return false;
		} else if (!desc.equals(other.desc))
			return false;
		if (gfmt == null) {
			if (other.gfmt != null)
				return false;
		} else if (!gfmt.equals(other.gfmt))
			return false;
		if (loc == null) {
			if (other.loc != null)
				return false;
		} else if (!loc.equals(other.loc))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		return true;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getDurr() {
		return durr;
	}
	public void setDurr(String durr) {
		this.durr = durr;
	}
	public double getRmbAmt() {
		return rmbAmt;
	}
	public void setRmbAmt(double rmbAmt) {
		this.rmbAmt = rmbAmt;
	}
}
