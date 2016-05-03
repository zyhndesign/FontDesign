package com.cidic.fontdesign.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cidic.fontdesign.dao.UserDao;
import com.cidic.fontdesign.model.User;
import com.cidic.fontdesign.service.UserService;

@Service
@Component
@Qualifier(value = "userServiceImpl")
@Transactional
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	@Qualifier(value="userDaoImpl")
	private UserDao userDaoImpl;
	
	@Override
	@Transactional(rollbackFor={Exception.class})
	public void insertUser(User user) {
		logger.info("service insert user...");
		this.userDaoImpl.insertUser(user);
	}
	
	// @Transactional(propagation = Propagation.NESTED, timeout = 1000, isolation = Isolation.READ_COMMITTED, 
	// rollbackFor = Exception.class, noRollbackFor = CustomRuntimeException.class)
	
	@Override
	@Transactional(readOnly=true)
	public boolean checkUser(User user) {
		// TODO Auto-generated method stub
		return this.userDaoImpl.checkUser(user);
	}

	@Override
	public boolean checkUserName(String username) {
		// TODO Auto-generated method stub
		return this.userDaoImpl.checkUserName(username);
	}

}
