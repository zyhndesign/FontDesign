package com.cidic.fontdesign.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cidic.fontdesign.dao.SpecialDesignDao;
import com.cidic.fontdesign.dao.SpecialDesignTagDao;
import com.cidic.fontdesign.dao.TagDao;
import com.cidic.fontdesign.model.SpecialDesign;
import com.cidic.fontdesign.model.SpecialDesignTag;
import com.cidic.fontdesign.model.SpecialPageModel;
import com.cidic.fontdesign.model.Tag;
import com.cidic.fontdesign.service.SpecialDesignService;

@Service
@Component
@Qualifier(value = "specialDesignServiceImpl")
@Transactional
public class SpecialDesignServiceImpl implements SpecialDesignService {

	@Autowired
	@Qualifier(value = "specialDesignDaoImpl")
	private SpecialDesignDao specialDesignDao;

	@Autowired
	@Qualifier(value = "specialDesignTagDaoImpl")
	private SpecialDesignTagDao specialDesignTagDaoImpl;

	@Autowired
	@Qualifier(value = "tagDaoImpl")
	private TagDao tagDao;

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void insertSpecialDesign(SpecialDesign specialDesign, String insertTag) {
		if (insertTag.length() > 0){
			String[] insertTags = insertTag.split(",");

			// insert tag into tag table first:check tag is in the table, second:if
			// tag name in the table then get id,
			List<Tag> tagList = tagDao.selectTagByTagNameList(Arrays.asList(insertTags));
			// otherwise insert the name into table and get id
			for (String str : insertTags) {
				boolean hasTagInTable = false;
				for (Tag tag : tagList) {
					if (str.equals(tag.getTagName())) {
						hasTagInTable = true;
					}
				}
				if (!hasTagInTable) {
					Tag tag = new Tag();
					tag.setTagName(str);
					int tagId = tagDao.insertTag(tag);
					tag.setId(tagId);
					tagList.add(tag);
				}
			}
			List<SpecialDesignTag> specialDesignTagList = new ArrayList<SpecialDesignTag>();
			SpecialDesignTag specialDesignTag = null;
			for (Tag tag : tagList) {
				specialDesignTag = new SpecialDesignTag();
				specialDesignTag.setSpecialDesign(specialDesign);
				specialDesignTag.setTag(tag);
				specialDesignTagList.add(specialDesignTag);
			}
			specialDesign.setSpecialDesignTag(specialDesignTagList);
		}
		
		specialDesignDao.insertSpecialDesign(specialDesign);
	}

	@Override
	@Transactional(readOnly = true)
	public SpecialDesign selectSpecialDesign(int id) {
		return  specialDesignDao.selectSpecialDesign(id);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void updateSpecialDesign(SpecialDesign specialDesign, String updateTag, String deleteTag) {
		// delete tag in course design tag table
		if (deleteTag.length() > 0) {
			String[] deleteTagStr = deleteTag.split(",");
			for (String s : deleteTagStr) {
				specialDesignTagDaoImpl.deleteSpecialDesignTag(specialDesign.getId(), Integer.parseInt(s));
			}
		}
		// insert tag into tag table first:check tag is in the table, second:if
		// tag name in the table then get id,
		// otherwise insert the name into table and get id
		if (updateTag.length() > 0) {
			String[] updateTags = updateTag.split(",");

			// insert tag into tag table first:check tag is in the table,
			// second:if tag name in the table then get id,
			List<Tag> tagList = tagDao.selectTagByTagNameList(Arrays.asList(updateTags));
			// otherwise insert the name into table and get id
			for (String str : updateTags) {
				boolean hasTagInTable = false;
				for (Tag tag : tagList) {
					if (str.equals(tag.getTagName())) {
						hasTagInTable = true;
					}
				}
				if (!hasTagInTable) {
					Tag tag = new Tag();
					tag.setTagName(str);
					int tagId = tagDao.insertTag(tag);
					tag.setId(tagId);
					tagList.add(tag);
				}
			}
			List<SpecialDesignTag> specialDesignTagList = new ArrayList<SpecialDesignTag>();
			SpecialDesignTag specialDesignTag = null;
			for (Tag tag : tagList) {
				specialDesignTag = new SpecialDesignTag();
				specialDesignTag.setSpecialDesign(specialDesign);
				specialDesignTag.setTag(tag);
				specialDesignTagList.add(specialDesignTag);
			}
			specialDesign.setSpecialDesignTag(specialDesignTagList);
		}

		specialDesignDao.updateSpecialDesign(specialDesign);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void deleteSpecialDesign(SpecialDesign specialDesign) {
		specialDesignDao.deleteSpecialDesign(specialDesign);
	}

	@Override
	@Transactional(readOnly = true)
	public List<SpecialDesign> getTopSpecialDesign() {
		return specialDesignDao.getTopSpecialDesign();
	}

	@Override
	@Transactional(readOnly = true)
	public SpecialPageModel getDataByPage(int limit, int offset, String sEcho) {
		SpecialPageModel specialPageModel = new SpecialPageModel();
		List<SpecialDesign> list = specialDesignDao.getDataByPage(limit, offset, sEcho);
		int count = specialDesignDao.getCountData();
		specialPageModel.setList(list);
		specialPageModel.setCount(count);
		return specialPageModel;
	}

	@Override
	public List<SpecialDesign> getFrontDataByPage(int limit, int offset, int choice) {
		return specialDesignDao.getFrontDataByPage(limit, offset, choice);
	}

	
}
