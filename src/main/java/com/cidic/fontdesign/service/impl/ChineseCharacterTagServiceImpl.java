package com.cidic.fontdesign.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cidic.fontdesign.dao.ChineseCharacterTagDao;
import com.cidic.fontdesign.model.ChineseCharacter;
import com.cidic.fontdesign.model.ChineseCharacterTag;
import com.cidic.fontdesign.service.ChineseCharacterTagService;

@Service
@Component
@Qualifier(value = "chineseCharacterTagServiceImpl")
@Transactional
public class ChineseCharacterTagServiceImpl implements ChineseCharacterTagService {

	private static final Logger logger = LoggerFactory.getLogger(SpecialDesignTagServiceImpl.class);
	
	@Autowired
	@Qualifier(value="chineseCharacterTagDaoImpl")
	private ChineseCharacterTagDao chineseCharacterTagDaoImpl;
	
	@Override
	public void insertChineseCharacterTagDao(List<ChineseCharacterTag> list) {
		chineseCharacterTagDaoImpl.insertChineseCharacterTagDao(list);
	}

	@Override
	public void deleteChineseCharacterTag(int chineseCharacterId, int tagId) {
		chineseCharacterTagDaoImpl.deleteChineseCharacterTag(chineseCharacterId, tagId);
	}

	@Override
	public void updateChineseCharacterTag(ChineseCharacterTag chineseCharacterTag, int chineseCharacterTagId) {
		chineseCharacterTagDaoImpl.updateChineseCharacterTag(chineseCharacterTag, chineseCharacterTagId);
	}

	@Override
	public List<ChineseCharacter> getChineseCharacterByTagName(List<String> tagName) {
		
		return chineseCharacterTagDaoImpl.getChineseCharacterByTagName(tagName);
	}

}
