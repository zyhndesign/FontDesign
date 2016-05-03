package com.cidic.fontdesign.service;

import com.cidic.fontdesign.model.User;

public interface UserService {
	
	public void insertUser(User user);
	
	public boolean checkUser(User user);
	
	public boolean checkUserName(String username);
}
