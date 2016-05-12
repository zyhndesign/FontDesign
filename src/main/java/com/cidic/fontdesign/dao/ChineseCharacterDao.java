package com.cidic.fontdesign.dao;

import java.util.List;

import com.cidic.fontdesign.model.ChineseCharacter;


public interface ChineseCharacterDao {
	
	public void insertChineseCharacter(ChineseCharacter chineseCharacter);
	
	public ChineseCharacter selectChineseCharacter(int id);
	
	public List<ChineseCharacter> getTopChineseCharacter();
	
	public void updateChineseCharacter(ChineseCharacter chineseCharacter);
	
	public void deleteChineseCharacter(ChineseCharacter chineseCharacter);
	
	public List<ChineseCharacter> getDataByPage(int limit, int offset, String sEcho,int category);
	
	public List<ChineseCharacter> getFrontDataByPage(int limit, int offset, int choice,int category);
	
	public int getCountData(int category);
}
