package com.cidic.fontdesign.dao;

import com.cidic.fontdesign.model.User;

public interface UserDao {
	
	public void insertUser(User user);
	
	public boolean checkUser(User user);
	
	public boolean checkUserName(String username);
}
