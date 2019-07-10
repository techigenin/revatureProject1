package com.metalSlime.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.metalSlime.pojos.Grade;
import com.metalSlime.util.ConnectionFactory;

public class GradeDaoImpl implements GradeDao {

	Connection conn = ConnectionFactory.getConnection();
	
	@Override
	public void sendGradeFiles(Grade g) {
		
		try {
		PreparedStatement pstmt = conn.prepareStatement("insert into request_grades"
				+ "(req_num, grade, grade_files) "
				+ "values (?, ?, ?);");
		pstmt.setInt(1, g.getReqNum());
		pstmt.setString(2, g.getGrade());
		pstmt.setString(3, g.getGradeFiles());
		
		pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Grade getGrade(int reqNum) {
		Grade g = new Grade();
		
		try {
			PreparedStatement pstmt = conn.prepareStatement("select * from request_grades g where g.req_num = ?;");
			pstmt.setInt(1, reqNum);
			
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				g.setReqNum(reqNum);
				g.setGrade(rs.getString("grade"));
				g.setGradeFiles(rs.getString("grade_files"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return g;
	}
}
