package com.cidic.fontdesign.dao;

import java.util.List;

import com.cidic.fontdesign.model.Tag;

public interface TagDao {
	
	public int insertTag(Tag tag);
	
	public Tag selectTagById(int id);
	
	public Tag selectTagByTagName(String tagName);
	
	public List<Tag> selectTagByTagNameList(List<String> tagNameList);
}
