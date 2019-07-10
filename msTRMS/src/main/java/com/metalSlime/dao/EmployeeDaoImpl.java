package com.metalSlime.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import com.metalSlime.pojos.Request;
import com.metalSlime.util.ConnectionFactory;

public class EmployeeDaoImpl implements EmployeeDao{

	Connection conn = ConnectionFactory.getConnection();

	@Override
	public Request[] getRequestsByEid(Integer eid) {
		ArrayList<Request> requests = new ArrayList<Request>();
		
		try {
			Statement stmt = conn.createStatement();
			String sql = "select * from get_emp_reqs(" + eid + ");";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				requests.add(buildRequest(rs));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return requests.toArray(new Request[0]);
	}

	@Override
	public Request[] getDeptSubsRequestsByEid(Integer eid) {
		ArrayList<Request> requests = new ArrayList<Request>();
		
		try {
			Statement stmt = conn.createStatement();
			String sql = "select * from dept_subs_reqs(" + eid + ");";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				requests.add(buildRequest(rs));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return requests.toArray(new Request[0]);
	}
	
	@Override
	public Request[] getDirectSubsRequestsByEid(Integer eid) {
		ArrayList<Request> requests = new ArrayList<Request>();
		
		try {
			Statement stmt = conn.createStatement();
			String sql = "select * from direct_subs_reqs(" + eid + ");";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				requests.add(buildRequest(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return requests.toArray(new Request[0]);
	}
	
	@Override
	public Request[] getBencoRequests(Integer eid) {
		if (!checkIfBenco(eid))
			return new Request[0];
		
		ArrayList<Request> requests = new ArrayList<Request>();
		
		try {
			Statement stmt = conn.createStatement();
			String sql = "select * from benco_reqs();";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				requests.add(buildRequest(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		checkForGrades(requests);
		
		return requests.toArray(new Request[0]);
	}
	
	private void checkForGrades(ArrayList<Request> requests) {
		
		try {
			for (Request r : requests) {
				PreparedStatement pstmt = conn.prepareStatement("select * from request_grades r where r.req_num = ?");
				pstmt.setInt(1, r.getReqNum());
				
				ResultSet rs = pstmt.executeQuery();
				
				if (rs.next())
					r.setHasGrade(true);
				else
					r.setHasGrade(false);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	@Override
	public String getEmployeeName(Integer eid) {
		try {
			Statement stmt = conn.createStatement();
			String sql = "select * from get_employee_name(" + eid + ");";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) 
				return rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}

	private boolean checkIfBenco(Integer eid) {
		try {
			Statement stmt = conn.createStatement();
			String sql = "select is_benco(" + eid + ");";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				return rs.getBoolean(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private Request buildRequest(ResultSet rs) throws SQLException {
		Request r = new Request();
		
		r.setEventDate(rs.getObject("eventDate", LocalDate.class));
		r.setEventTime(rs.getObject("eventTime", LocalTime.class));
		r.setEventType(rs.getString("eventType"));
		r.setEventLoc(rs.getString("eventLoc"));
		r.setEventDesc(rs.getString("eventDesc"));
		r.setEventCost(rs.getBigDecimal("eventCost").doubleValue());
		r.setGradeFmt(rs.getString("gradeFmt"));
		r.setAppliedDate(rs.getObject("appliedDate", LocalDate.class));
		r.setStatus(rs.getString("status"));
		r.setReqNum(rs.getInt("reqNum"));
		r.setEmpName(rs.getString("empName"));
		r.setRmbAmt(rs.getBigDecimal("rmbAmt").doubleValue());
		return r;
	}

}
