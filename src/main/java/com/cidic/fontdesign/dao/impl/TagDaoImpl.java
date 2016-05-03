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

import com.cidic.fontdesign.dao.TagDao;
import com.cidic.fontdesign.model.Tag;


@Repository
@Component
@Qualifier(value = "tagDaoImpl")
public class TagDaoImpl implements TagDao {

	private static final Logger logger = LoggerFactory.getLogger(TagDaoImpl.class);
	
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
	public int insertTag(Tag tag) {
		Session session = this.getSessionFactory().getCurrentSession();
		session.save(tag);
		return tag.getId();
	}

	@Override
	public Tag selectTagById(int id) {
		Session session = this.getSessionFactory().getCurrentSession();
		return (Tag)session.get(Tag.class, id);
	}

	@Override
	public Tag selectTagByTagName(String tagName) {
		Session session = this.getSessionFactory().getCurrentSession();
		String hqlVersionedSelect = "from Tag where tagName = :tagName";
		Query query = session.createQuery(hqlVersionedSelect);
		List<Tag> tagList = query.list();
		if (tagList.size() > 0){
			return tagList.get(0);
		}
		return null;
	}

	@Override
	public List<Tag> selectTagByTagNameList(List<String> tagNameList) {
		Session session = this.getSessionFactory().getCurrentSession();
		Query query=session.createQuery("from Tag where tagName in (:tagNames)"); 
		query.setParameterList("tagNames", tagNameList);
		return query.list();
	}

}
