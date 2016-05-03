package com.cidic.fontdesign.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cidic.fontdesign.dao.SpecialDesignTagDao;
import com.cidic.fontdesign.model.SpecialDesignTag;
import com.cidic.fontdesign.service.SpecialDesignTagService;

@Service
@Component
@Qualifier(value = "specialDesignTagServiceImpl")
@Transactional
public class SpecialDesignTagServiceImpl implements SpecialDesignTagService {

	private static final Logger logger = LoggerFactory.getLogger(SpecialDesignTagServiceImpl.class);
	
	@Autowired
	@Qualifier(value="specialDesignTagDaoImpl")
	private SpecialDesignTagDao specialDesignTagDao;
	
	@Override
	public void insertSpecialDesignTagDao(List<SpecialDesignTag> list) {
		
	}

	@Override
	public void deleteSpecialDesignTag(int courseDesignId, int tagId) {
		specialDesignTagDao.deleteSpecialDesignTag(courseDesignId, tagId);
	}

	@Override
	public void updateSpecialDesignTag(SpecialDesignTag courseDesignTag, int courseDesignTagId) {
		specialDesignTagDao.updateSpecialDesignTag(courseDesignTag, courseDesignTagId);
	}

	@Override
	public List<SpecialDesignTag> getSpecialDesignByTagName(List<String> tagName) {
		
		return specialDesignTagDao.getSpecialDesignByTagName(tagName);
	}

}
