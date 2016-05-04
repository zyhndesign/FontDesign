package com.cidic.fontdesign.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cidic.fontdesign.model.ChineseCharacter;
import com.cidic.fontdesign.model.SpecialDesign;
import com.cidic.fontdesign.service.ChineseCharacterService;
import com.cidic.fontdesign.service.ChineseCharacterTagService;
import com.cidic.fontdesign.service.HomeService;
import com.cidic.fontdesign.service.SpecialDesignService;
import com.cidic.fontdesign.service.SpecialDesignTagService;


@Service
@Component
@Qualifier(value = "homeServiceImpl")
@Transactional
public class HomeServiceImpl implements HomeService {

	@Autowired
	@Qualifier(value="chineseCharacterServiceImpl")
	private ChineseCharacterService chineseCharacterServiceImpl;
	
	@Autowired
	@Qualifier(value="chineseCharacterTagServiceImpl")
	private ChineseCharacterTagService chineseCharacterTagServiceImpl;
	
	@Autowired
	@Qualifier(value="specialDesignServiceImpl")
	private SpecialDesignService specialDesignServiceImpl;
	
	@Autowired
	@Qualifier(value="specialDesignTagServiceImpl")
	private SpecialDesignTagService specialDesignTagServiceImpl;
	
	
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<Object> getHomeContentData() {
		
		List<Object> list = new ArrayList<Object>();
		
		List<ChineseCharacter> chineseCharacterList = chineseCharacterServiceImpl.getTopChineseCharacter();
		List<SpecialDesign> specialDesignList = specialDesignServiceImpl.getTopSpecialDesign();
		list.add(chineseCharacterList);
		list.add(specialDesignList);
		
		return list;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public Map<String,Object> getSearchResultByKeywards(List<String> keywords) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		List<ChineseCharacter> chineseCharacterList = chineseCharacterTagServiceImpl.getChineseCharacterByTagName(keywords);
		map.put("ChineseCharacter", chineseCharacterList);
		
		List<SpecialDesign> specialDesignList = specialDesignTagServiceImpl.getSpecialDesignByTagName(keywords);
		map.put("SpecialDesign", specialDesignList);
		
		
		return map;
	}

	
}
