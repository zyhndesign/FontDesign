package com.cidic.fontdesign.service;

import java.util.List;

import com.cidic.fontdesign.model.SpecialDesignTag;

public interface SpecialDesignTagService {
	
	public void insertSpecialDesignTagDao(List<SpecialDesignTag> list);
	
	public void deleteSpecialDesignTag(int courseDesignId, int tagId);
	
	public void updateSpecialDesignTag(SpecialDesignTag courseDesignTag, int courseDesignTagId);
	
	public List<SpecialDesignTag> getSpecialDesignByTagName(List<String> tagName);
}
