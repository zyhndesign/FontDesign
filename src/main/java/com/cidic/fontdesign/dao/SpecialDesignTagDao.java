package com.cidic.fontdesign.dao;

import java.util.List;

import com.cidic.fontdesign.model.SpecialDesign;
import com.cidic.fontdesign.model.SpecialDesignTag;

public interface SpecialDesignTagDao {

	public void insertSpecialDesignTagDao(List<SpecialDesignTag> list);
	
	public void deleteSpecialDesignTag(int courseDesignId, int tagId);
	
	public void updateSpecialDesignTag(SpecialDesignTag courseDesignTag, int courseDesignTagId);
	
	public List<SpecialDesign> getSpecialDesignByTagName(List<String> tagName);
	
}
