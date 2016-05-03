package com.cidic.fontdesign.service;

import java.util.List;

import com.cidic.fontdesign.model.ChineseCharacterTag;

public interface ChineseCharacterTagService {

	public void insertChineseCharacterTagDao(List<ChineseCharacterTag> list);
	
	public void deleteChineseCharacterTag(int chineseCharacterId, int tagId);
	
	public void updateChineseCharacterTag(ChineseCharacterTag chineseCharacterTag, int chineseCharacterTagId);
	
	public List<ChineseCharacterTag> getChineseCharacterByTagName(List<String> tagName);
}
