package com.metalSlime.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.metalSlime.pojos.User;
import com.metalSlime.util.ConnectionFactory;

public class UserDaoImpl implements UserDao {
	
	Connection conn = ConnectionFactory.getConnection();
	
	@Override
	public Integer loginUser(User u) {
		
		try {		
			PreparedStatement pstmt = conn.prepareStatement("select * from check_login(?, ?)");
			pstmt.setString(1, u.getUsername());
			pstmt.setString(2, u.getPassword());
			
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				int val = rs.getInt(1);
				return val;
			}
			else
				return 0;
		} catch (SQLException e) {
				e.printStackTrace();
			}
		return 0;
	}

}
