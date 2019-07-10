package com.metalSlime.services;

import com.metalSlime.dao.UserDao;
import com.metalSlime.dao.UserDaoImpl;
import com.metalSlime.pojos.User;
import com.metalSlime.services.UserService;

public class UserServiceImpl implements UserService {
	
	UserDao ud = new UserDaoImpl();

	@Override
	public Integer loginUser(User u) {
		
		return ud.loginUser(u);
	}

}
