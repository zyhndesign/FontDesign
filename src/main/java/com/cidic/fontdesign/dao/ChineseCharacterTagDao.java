package com.cidic.fontdesign.dao;

import java.util.List;

import com.cidic.fontdesign.model.ChineseCharacter;
import com.cidic.fontdesign.model.ChineseCharacterTag;

public interface ChineseCharacterTagDao {
	
	public void insertChineseCharacterTagDao(List<ChineseCharacterTag> list);
	
	public void deleteChineseCharacterTag(int chineseCharacterId, int tagId);
	
	public void updateChineseCharacterTag(ChineseCharacterTag chineseCharacterTag, int chineseCharacterTagId);
	
	public List<ChineseCharacter> getChineseCharacterByTagName(List<String> tagName);
}
