package com.cidic.fontdesign.dao;

import java.util.List;

import com.cidic.fontdesign.model.ChineseCharacterTag;

public interface ChineseCharacterTagDao {
	
	public void insertChineseCharacterTagDao(List<ChineseCharacterTag> list);
	
	public void deleteChineseCharacterTag(int chineseCharacterId, int tagId);
	
	public void updateChineseCharacterTag(ChineseCharacterTag coursewareTag, int coursewareTagId);
	
	public List<ChineseCharacterTag> getChineseCharacterByTagName(List<String> tagName);
}
