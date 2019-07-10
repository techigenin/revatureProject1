package com.metalSlime.msExperiment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.metalSlime.msExperiment.pojo.EmployeeRequest;
import com.metalSlime.msExperiment.util.ConnectionFactory;

public class EmployeeRequestDaoImpl implements EmployeeRequestDao {

	private static Connection conn = ConnectionFactory.getConnection();
	
	public boolean requestExists(EmployeeRequest er) {
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "select * from test_table where fullname = '" + er.getName() + "';";
			ResultSet rs = stmt.executeQuery(sql);
			
			if (rs.next())
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean createRequest(EmployeeRequest er) {
		
		if (requestExists(er))
			return true;
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(
					"insert into test_table "
					+ "(fullname, eventdate, eventtime, "
					+ "eventloc, description, eventcost,"
					+ "gradeformat, eventtype) values "
				+ "(?, ?, ?, ?, ?, ?, ?, ?)");
		
			pstmt.setString(1, er.getName());
			pstmt.setString(2, er.getDate());
			pstmt.setString(3, er.getTime());
			pstmt.setString(4, er.getLocation());
			pstmt.setString(5, er.getDescription());
			pstmt.setString(6, er.getCost());
			pstmt.setString(7, er.getGradeFormat());
			pstmt.setString(8, er.getEventType());
			
			
			if (pstmt.executeUpdate() != 0)
				return true; // We got here without exception
			} catch (SQLException e) {
				e.printStackTrace();
		}
		return false;
	}

	public boolean deleteRequest(EmployeeRequest er) {
		
		try {
		PreparedStatement pstmt = conn.prepareStatement(
				"delete from test_table "
				+ "where fullname = ?");
		pstmt.setString(1, er.getName());
		
		if (pstmt.executeUpdate() != 0)
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public EmployeeRequest getEmployeeRequestByName(String name) {
		try{Statement stmt = conn.createStatement();
		
		String sql = "select * from test_table "
				+ "where fullname = '" + name + "';";
		
		ResultSet rs = stmt.executeQuery(sql);
		
		if (rs.next())
			return buildRequestFromRS(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	private EmployeeRequest buildRequestFromRS(ResultSet rs) throws SQLException {
		String name = rs.getString("fullname");
		String date = rs.getString("eventdate");
		String time = rs.getString("eventtime");
		String location = rs.getString("eventloc");
		String description = rs.getString("description");
		String cost = rs.getString("eventcost");
		String gradeFormat = rs.getString("gradeformat");
		String eventType = rs.getString("eventtype");
		
		return new EmployeeRequest(name, date, time, 
						location, description, cost, 
						gradeFormat, eventType);
	}

}
