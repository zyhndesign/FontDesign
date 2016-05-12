package com.cidic.fontdesign.service;

import java.util.List;

import com.cidic.fontdesign.model.ChineseCharacter;
import com.cidic.fontdesign.model.ChineseCharacterPageModel;

public interface ChineseCharacterService {

	public void insertChineseCharacter(ChineseCharacter chineseCharacter,String insertTag);
	
	public ChineseCharacter selectChineseCharacter(int id);
	
	public void updateChineseCharacter(ChineseCharacter chineseCharacter,String updateTag, String deleteTag);
	
	public void deleteChineseCharacter(ChineseCharacter chineseCharacter);
	
	public List<ChineseCharacter> getTopChineseCharacter();
	
	public ChineseCharacterPageModel getDataByPage(int limit, int offset, String sEcho,int category);
	
	public List<ChineseCharacter> getFrontDataByPage(int limit, int offset, int choice,int category);
}
