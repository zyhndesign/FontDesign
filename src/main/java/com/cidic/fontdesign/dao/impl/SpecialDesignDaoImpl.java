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

import com.cidic.fontdesign.dao.SpecialDesignDao;
import com.cidic.fontdesign.model.SpecialDesign;

@Repository
@Component
@Qualifier(value = "specialDesignDaoImpl")
public class SpecialDesignDaoImpl implements SpecialDesignDao {

	private static final Logger logger = LoggerFactory.getLogger(SpecialDesignDaoImpl.class);
	
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
	public void insertSpecialDesign(SpecialDesign specialDesign) {
		Session session = this.getSessionFactory().getCurrentSession();
        session.save(specialDesign);
	}

	@Override
	public SpecialDesign selectSpecialDesign(int id) {
		Session session = this.getSessionFactory().getCurrentSession();
		SpecialDesign specialDesign = (SpecialDesign)session.get(SpecialDesign.class, id);
		return specialDesign;
	}

	@Override
	public void updateSpecialDesign(SpecialDesign specialDesign) {
		Session session = this.getSessionFactory().getCurrentSession();
		session.update(specialDesign);
	}

	@Override
	public void deleteSpecialDesign(SpecialDesign specialDesign) {
		Session session = this.getSessionFactory().getCurrentSession();
		session.delete(specialDesign);
	}

	@Override
	public List<SpecialDesign> getTopSpecialDesign() {
		Session session = this.getSessionFactory().getCurrentSession();
		String hql = "from SpecialDesign order by createTime desc";
		Query query = session.createQuery(hql);
		query.setFirstResult(0);
		query.setMaxResults(10);
		return query.list();
	}

	@Override
	public List<SpecialDesign> getDataByPage(int limit, int offset, String sEcho) {
		Session session = this.getSessionFactory().getCurrentSession();
		final String hql = " from SpecialDesign order by createTime desc"; 
        final Query query = session.createQuery(hql);   
        query.setFirstResult(offset);    
        query.setMaxResults(limit); 
        final List<SpecialDesign> list = query.list();  
		return list;
	}

	@Override
	public List<SpecialDesign> getFrontDataByPage(int limit, int offset, int choice) {
		Session session = this.getSessionFactory().getCurrentSession();
		String hql = null; 
		if (choice == 0){
			hql = "from SpecialDesign order by createTime desc";
		}
		else{
			hql = "from SpecialDesign where topTag=1 order by createTime desc";
		}
	 
        final Query query = session.createQuery(hql);   
        query.setFirstResult(offset);    
        query.setMaxResults(limit); 
        
        final List<SpecialDesign> list = query.list();  
		return list;
	}

	@Override
	public int getCountData() {
		Session session = this.getSessionFactory().getCurrentSession();
		String hql = "select count(*) from SpecialDesign";  
		Query query =  session.createQuery(hql);  
		return ((Number)query.uniqueResult()).intValue();
	}

}
