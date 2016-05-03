package com.cidic.fontdesign.service;

import java.util.List;
import java.util.Map;


public interface HomeService {
	
	public List<Object> getHomeContentData();
	
	public Map<String,Object> getSearchResultByKeywards(List<String> keywords);
}
