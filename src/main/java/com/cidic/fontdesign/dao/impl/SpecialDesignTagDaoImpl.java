package com.cidic.fontdesign.dao.impl;

import java.util.ArrayList;
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

import com.cidic.fontdesign.dao.SpecialDesignTagDao;
import com.cidic.fontdesign.model.SpecialDesign;
import com.cidic.fontdesign.model.SpecialDesignTag;
import com.cidic.fontdesign.model.Tag;
import com.cidic.fontdesign.util.DateUtil;

@Repository
@Component
@Qualifier(value = "specialDesignTagDaoImpl")
public class SpecialDesignTagDaoImpl implements SpecialDesignTagDao {

	private static final Logger logger = LoggerFactory.getLogger(SpecialDesignTagDaoImpl.class);
	
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
	public void insertSpecialDesignTagDao(List<SpecialDesignTag> list) {
		
	}

	@Override
	public void deleteSpecialDesignTag(int specialDesignId, int tagId) {
		Session session = this.getSessionFactory().getCurrentSession();
		String hqlDelete = "delete SpecialDesignTag c where c.specialDesign = :specialDesign and c.tag = :tag";
		SpecialDesign specialDesign = new SpecialDesign();
		specialDesign.setId(specialDesignId);
		Tag tag = new Tag();
		tag.setId(tagId);
		int deletedEntities = session.createQuery(hqlDelete)
		        .setEntity("courseDesign", specialDesign)
		        .setEntity("tag",tag)
		        .executeUpdate();
		logger.info("delete special design tag result is :"+deletedEntities);
	}

	@Override
	public void updateSpecialDesignTag(SpecialDesignTag specialDesignTag, int specialDesignTagId) {
		Session session = this.getSessionFactory().getCurrentSession();
		String hqlVersionedUpdate = "update SpecialDesignTag set tag = :tag, specialDesign = :specialDesign where id = :id";
		int updatedEntities = session.createQuery( hqlVersionedUpdate )
		        .setEntity( "tag", specialDesignTag.getTag())
		        .setEntity( "specialDesign", specialDesignTag.getSpecialDesign() )
		        .setInteger("id",specialDesignTagId)
		        .executeUpdate();
		logger.info("update special design tag result is :"+updatedEntities);
	}

	@Override
	public List<SpecialDesign> getSpecialDesignByTagName(List<String> tagName) {
		Session session = this.getSessionFactory().getCurrentSession();
		String sqlSelected = "select c.id as course_design_tag_id, t.id as tagId, d.id as special_design_id,"
				+ "t.tag_name,d.abstract,d.special_detail_id,d.create_time,d.teacher,d.title, d.top_tag from"
				+ "  special_design_tag c cross  join  tag t cross join  special_design d  where"
				+ "  d.id=c.special_design_id  and c.tag_id=t.id  and ( t.tag_name in (:tagNames ) )";
        
		Query query=session.createSQLQuery(sqlSelected);
		query.setParameterList("tagNames", tagName);
		List<Object[]> roles = query.list();   
		List<SpecialDesign> specialDesignList = new ArrayList<SpecialDesign>();
		List<SpecialDesignTag> specialDesignTagList = null;
		
		SpecialDesign specialDesign = null;
		SpecialDesignTag specialDesignTag = null;
		
		for(Object[] role : roles){  
			specialDesign = new SpecialDesign();
			specialDesign.setId(Integer.parseInt(String.valueOf(role[2])));
			specialDesign.setAbstract_(String.valueOf(role[4]));
			specialDesign.setSpecialDetailId(Integer.parseInt(String.valueOf(role[5])));
			specialDesign.setCreateTime(DateUtil.stringToDate(String.valueOf(role[6])));
			specialDesign.setTeacher(String.valueOf(role[7]));
			specialDesign.setTitle(String.valueOf(8));
			specialDesign.setTopTag(Integer.parseInt(String.valueOf(role[9])));
		    Tag tag = new Tag();
		    tag.setId(Integer.parseInt(String.valueOf(role[1])));
		    tag.setTagName(String.valueOf(role[3]));
		    
		    specialDesignTag = new  SpecialDesignTag();
		    specialDesignTag.setTag(tag);
		    specialDesignTag.setId(Integer.parseInt(String.valueOf(role[0])));
		    specialDesignTag.setSpecialDesign(specialDesign);
		    specialDesignTagList = new ArrayList<SpecialDesignTag>();
		    specialDesignTagList.add(specialDesignTag);
		    specialDesign.setSpecialDesignTag(specialDesignTagList);
		    specialDesignList.add(specialDesign);
		}
		
		return specialDesignList;
	}

}
