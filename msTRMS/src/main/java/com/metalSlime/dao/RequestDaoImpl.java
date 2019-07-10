package com.metalSlime.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import com.metalSlime.pojos.Request;
import com.metalSlime.pojos.RequestFiles;
import com.metalSlime.pojos.Event;
import com.metalSlime.pojos.Grade;
import com.metalSlime.util.ConnectionFactory;

public class RequestDaoImpl implements RequestDao {

	Connection conn = ConnectionFactory.getConnection();
	
	@Override
	public int addRequest(Integer eid, Event event) {
		try {
			CallableStatement cstmt = conn.prepareCall("select add_request (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
			cstmt.setObject(1, event.getDate());
			cstmt.setObject(2, event.getTime());
			cstmt.setString(3, event.getLoc());
			cstmt.setString(4, event.getDesc());
			cstmt.setBigDecimal(5, event.getCostBD());
			cstmt.setString(6, event.getGfmt());
			cstmt.setInt(7, eid);
			cstmt.setObject(8, LocalDate.now());
			cstmt.setInt(9, event.getType());
			cstmt.setString(10, event.getDurr());
			
			ResultSet rs = cstmt.executeQuery();
			while (rs.next())
			{
				return rs.getInt(1);
			}
			cstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return 0;
	}

	@Override
	public void approveRequest(Integer eid, int reqNum) {
		try {
			CallableStatement cstmt = conn.prepareCall("select approve_request (?, ?);");
			cstmt.setInt(1, eid);
			cstmt.setInt(2, reqNum);
			
			cstmt.execute();
			cstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	@Override
	public void denyRequest(Integer eid, int reqNum) {
		try {
			CallableStatement cstmt = conn.prepareCall("select deny_request (?, ?);");
			cstmt.setInt(1, eid);
			cstmt.setInt(2, reqNum);
			
			cstmt.execute();
			cstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
	}

	@Override
	public void sendRequestFiles(RequestFiles rf) {
		try {
			PreparedStatement pstmt = conn.prepareStatement("insert into request_files "
					+ "(req_num, dsup_app, dept_app, event_files) "
					+ "values (?, ?, ?, ?);");
			pstmt.setInt(1, rf.getReqNum());
			pstmt.setString(2, rf.getdSupAppFileName());
			pstmt.setString(3, rf.getDeptAppFileName());
			pstmt.setString(4, rf.getEventFileNames());
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public RequestFiles getRequestFiles(int reqNum) {
		RequestFiles rf = new RequestFiles();
		
		try {
			PreparedStatement pstmt = conn.prepareStatement("select * from request_files where req_num = ?;");
			pstmt.setInt(1, reqNum);
			
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next())
			{
				rf = new RequestFiles();
				
				rf.setReqNum(reqNum);
				rf.setdSupAppFileName(rs.getString("dsup_app"));
				rf.setDeptAppFileName(rs.getString("dept_app"));				
				rf.setEventFileNames(rs.getString("event_files"));
				
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rf;
	}
}
