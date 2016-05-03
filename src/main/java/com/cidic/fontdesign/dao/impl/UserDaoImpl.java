package com.cidic.fontdesign.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.cidic.fontdesign.dao.UserDao;
import com.cidic.fontdesign.model.User;

@Repository
@Component
@Qualifier(value = "userDaoImpl")
public class UserDaoImpl implements UserDao {

	private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
	
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void insertUser(User user) {
		// TODO Auto-generated method stub
		Session session = this.getSessionFactory().getCurrentSession();
       
        logger.info("save user :" +user.getUsername());
        session.save(user);
	}

	@Override
	public boolean checkUser(User user) {
		Session session = this.getSessionFactory().getCurrentSession();
		String sql="from User u where u.username=? and u.password=?";
	    Query query= session.createQuery(sql);
	    query.setParameter(0, user.getUsername());
	    query.setParameter(1, user.getPassword());
	    List<User> users=query.list();
	    if(users.size()!=0){
	    	return true;
	    }
	    else{
	    	return false;
	    }
	}

	@Override
	public boolean checkUserName(String username) {
		// TODO Auto-generated method stub
		Session session = this.getSessionFactory().getCurrentSession();
		Object object = session.byNaturalId(User.class).using("username", username).getReference();
		if (object != null){
			return true;
		}
		else
		{
			return false;
		}
	}

}
