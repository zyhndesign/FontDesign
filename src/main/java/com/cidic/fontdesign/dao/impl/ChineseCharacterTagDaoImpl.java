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

import com.cidic.fontdesign.dao.ChineseCharacterTagDao;
import com.cidic.fontdesign.model.ChineseCharacter;
import com.cidic.fontdesign.model.ChineseCharacterTag;
import com.cidic.fontdesign.model.Tag;
import com.cidic.fontdesign.util.DateUtil;

@Repository
@Component
@Qualifier(value = "chineseCharacterTagDao")
public class ChineseCharacterTagDaoImpl implements ChineseCharacterTagDao {

	private static final Logger logger = LoggerFactory.getLogger(ChineseCharacterTagDaoImpl.class);
	
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
	public void insertChineseCharacterTagDao(List<ChineseCharacterTag> list) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteChineseCharacterTag(int chineseCharacterId, int tagId) {
		Session session = this.getSessionFactory().getCurrentSession();
		String hqlDelete = "delete ChineseCharacterTag c where c.chineseCharacter = :chineseCharacter and c.tag = :tag";
		ChineseCharacter chineseCharacter = new ChineseCharacter();
		chineseCharacter.setId(chineseCharacterId);
		Tag tag = new Tag();
		tag.setId(tagId);
		int deletedEntities = session.createQuery(hqlDelete)
		        .setEntity("chineseCharacter", chineseCharacter)
		        .setEntity("tag",tag)
		        .executeUpdate();
		logger.info("delete chineseCharacter tag result is :"+deletedEntities);
	}

	@Override
	public void updateChineseCharacterTag(ChineseCharacterTag chineseCharacterTag, int chineseCharacterTagId) {
		Session session = this.getSessionFactory().getCurrentSession();
		String hqlVersionedUpdate = "update ChineseCharacterTag set tag = :tag, chineseCharacter = :chineseCharacter where id = :id";
		
		int updatedEntities = session.createQuery( hqlVersionedUpdate )
		        .setEntity( "tag", chineseCharacterTag.getTag() )
		        .setEntity( "chineseCharacter", chineseCharacterTag.getChineseCharacter() )
		        .setInteger("id",chineseCharacterTagId)
		        .executeUpdate();
		logger.info("update chineseCharacter tag result is :"+updatedEntities);
	}

	@Override
	public List<ChineseCharacter> getChineseCharacterByTagName(List<String> tagName) {
		Session session = this.getSessionFactory().getCurrentSession();
		String sqlSelected = "select c.id as chineseCharacter_tag_id, t.id as tagId, d.id as chineseCharacter_id, t.tag_name, d.title, d.author,"
				+ " d.thumbnail, d.create_time, d.content, d.top_tag  from chinese_character_tag c cross  join tag t cross  join"
				+ " chinese_character d  where d.id=c.chineseCharacter_id  and c.tag_id=t.id  and ( t.tag_name in ( :tagNames ))";
        
		Query query=session.createSQLQuery(sqlSelected);
		query.setParameterList("tagNames", tagName);
		List<Object[]> roles = query.list();   
		List<ChineseCharacter> chineseCharacterList = new ArrayList<ChineseCharacter>();
		List<ChineseCharacterTag> chineseCharacterTagList = null;
		
		ChineseCharacter chineseCharacter = null;
		ChineseCharacterTag chineseCharacterTag = null;
		
		for(Object[] role : roles){  
			chineseCharacter = new ChineseCharacter();
			chineseCharacter.setId(Integer.parseInt(String.valueOf(role[2])));
			chineseCharacter.setAuthor(String.valueOf(role[5]));
			chineseCharacter.setContent(String.valueOf(role[8]));
			chineseCharacter.setCreateTime(DateUtil.stringToDate(String.valueOf(role[7])));
			chineseCharacter.setTitle(String.valueOf(role[4]));
			chineseCharacter.setThumbnail(String.valueOf(6));
			chineseCharacter.setTopTag(Integer.parseInt(String.valueOf(role[9])));
		    Tag tag = new Tag();
		    tag.setId(Integer.parseInt(String.valueOf(role[1])));
		    tag.setTagName(String.valueOf(role[3]));
		    
		    chineseCharacterTag = new  ChineseCharacterTag();
		    chineseCharacterTag.setTag(tag);
		    chineseCharacterTag.setId(Integer.parseInt(String.valueOf(role[0])));
		    chineseCharacterTag.setChineseCharacter(chineseCharacter);
		    chineseCharacterTagList = new ArrayList<ChineseCharacterTag>();
		    chineseCharacterTagList.add(chineseCharacterTag);
		    chineseCharacter.setChineseCharacterTag(chineseCharacterTagList);
		    chineseCharacterList.add(chineseCharacter);
		}
		
		return chineseCharacterList;
	}

}
