package com.cidic.fontdesign.dao;

import java.util.List;

import com.cidic.fontdesign.model.SpecialDesign;

public interface SpecialDesignDao {

	public void insertSpecialDesign(SpecialDesign courseDesign);
	
	public SpecialDesign selectSpecialDesign(int id);
	
	public void updateSpecialDesign(SpecialDesign courseDesign);
	
	public void deleteSpecialDesign(SpecialDesign courseDesign);
	
	public List<SpecialDesign> getTopSpecialDesign();
	
	public List<SpecialDesign> getDataByPage(int limit, int offset, String sEcho);
	
	public List<SpecialDesign> getFrontDataByPage(int limit, int offset, int choice);
	
	public int getCountData();
}
