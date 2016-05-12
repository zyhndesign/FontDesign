package com.cidic.fontdesign.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.cidic.fontdesign.dao.ChineseCharacterDao;
import com.cidic.fontdesign.model.ChineseCharacter;

@Repository
@Component
@Qualifier(value = "chineseCharacterDaoImpl")
public class ChineseCharacterDaoImpl implements ChineseCharacterDao {

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
	public void insertChineseCharacter(ChineseCharacter chineseCharacter) {
		Session session = this.getSessionFactory().getCurrentSession();
		session.save(chineseCharacter);
	}

	@Override
	public ChineseCharacter selectChineseCharacter(int id) {
		Session session = this.getSessionFactory().getCurrentSession();
		ChineseCharacter chineseCharacter = (ChineseCharacter)session.get(ChineseCharacter.class, id);
		return chineseCharacter;
	}

	@Override
	public List<ChineseCharacter> getTopChineseCharacter() {
		Session session = this.getSessionFactory().getCurrentSession();
		String hql = "from Courseware order by createTime desc";
		Query query = session.createQuery(hql);
		query.setFirstResult(0);
		query.setMaxResults(10);
		return query.list();
	}

	@Override
	public void updateChineseCharacter(ChineseCharacter chineseCharacter) {
		Session session = this.getSessionFactory().getCurrentSession();
		session.update(chineseCharacter);
	}

	@Override
	public void deleteChineseCharacter(ChineseCharacter chineseCharacter) {
		Session session = this.getSessionFactory().getCurrentSession();
		session.delete(chineseCharacter);
	}

	@Override
	public List<ChineseCharacter> getDataByPage(int limit, int offset, String sEcho,int category) {
		Session session = this.getSessionFactory().getCurrentSession();
		String hql; 
		if (category == 0){
			hql = " from ChineseCharacter order by createTime desc "; 
		}
		else{
			hql = " from ChineseCharacter where category=:category order by createTime desc ";
		}
	
        final Query query = session.createQuery(hql);   
        query.setFirstResult(offset);    
        query.setMaxResults(limit); 
        if (category == 0){
        	query.setParameter("category", category);
        }
        final List<ChineseCharacter> list = query.list();  
		return list;
	}

	@Override
	public List<ChineseCharacter> getFrontDataByPage(int limit, int offset, int choice,int category) {
		Session session = this.getSessionFactory().getCurrentSession();
		String hql = null; 
		if (choice == 0){
			if (category == 0){
				hql = " from ChineseCharacter order by createTime desc ";
			}
			else{
				hql = " from ChineseCharacter where category=:category order by createTime desc ";
			}
		}
		else{
			if (category == 0){
				hql = " from ChineseCharacter where topTag=1 order by createTime desc ";
			}
			else{
				hql = " from ChineseCharacter where topTag=1 and category=:category order by createTime desc ";
			}
			
		}
	 
        final Query query = session.createQuery(hql);   
        query.setFirstResult(offset);    
        query.setMaxResults(limit); 
        query.setParameter("category", category);
        final List<ChineseCharacter> list = query.list();  
		return list;
	}

	@Override
	public int getCountData(int category) {
		Session session = this.getSessionFactory().getCurrentSession();
		String hql = "select count(*) from ChineseCharacter where category=:category";  
		if (category == 0){
			hql = "select count(*) from ChineseCharacter ";  
		}
		Query query =  session.createQuery(hql);  
		if (category == 0){
			query.setParameter("category", category);
		}
		return ((Number)query.uniqueResult()).intValue();
	}

}
