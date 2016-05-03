package com.cidic.fontdesign.service;

import java.util.List;

import com.cidic.fontdesign.model.SpecialDesign;
import com.cidic.fontdesign.model.SpecialPageModel;

public interface SpecialDesignService {
	
	public void insertSpecialDesign(SpecialDesign specialDesign,String insertTag);
	
	public SpecialDesign selectSpecialDesign(int id);
	
	public void updateSpecialDesign(SpecialDesign specialDesign,String updateTag, String deleteTag);
	
	public void deleteSpecialDesign(SpecialDesign specialDesign);
	
	public List<SpecialDesign> getTopSpecialDesign();
	
	public SpecialPageModel getDataByPage(int limit, int offset, String sEcho);
	
	public List<SpecialDesign> getFrontDataByPage(int limit, int offset, int choice);
}
