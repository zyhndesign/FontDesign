package com.cidic.fontdesign.service;

import com.cidic.fontdesign.model.Tag;

public interface TagService {
	public void insertTag(Tag tag);
	
	public Tag selectTagById(int id);
	
	public Tag selectTagByTagName(String tagName);
}