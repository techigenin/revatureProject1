package com.metalSlime.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.metalSlime.pojos.GradeResponse;
import com.metalSlime.dao.GradeResponseDao;
import com.metalSlime.util.ConnectionFactory;

public class GradeResponseDaoImpl implements GradeResponseDao {

	Connection conn = ConnectionFactory.getConnection();
	
	@Override
	public void sendResponse(GradeResponse gr) {
		try {
			PreparedStatement pstmt = conn.prepareStatement("insert into request_grades_response values (?, ?, ?);");
			pstmt.setInt(1, gr.getReqNum());
			pstmt.setBoolean(2, gr.getPresentation());
			pstmt.setInt(3, gr.getResponse());
			
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
