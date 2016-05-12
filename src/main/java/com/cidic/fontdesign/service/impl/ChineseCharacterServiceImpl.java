package com.cidic.fontdesign.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cidic.fontdesign.dao.ChineseCharacterDao;
import com.cidic.fontdesign.dao.ChineseCharacterTagDao;
import com.cidic.fontdesign.dao.TagDao;
import com.cidic.fontdesign.model.ChineseCharacter;
import com.cidic.fontdesign.model.ChineseCharacterPageModel;
import com.cidic.fontdesign.model.ChineseCharacterTag;
import com.cidic.fontdesign.model.Tag;
import com.cidic.fontdesign.service.ChineseCharacterService;

@Service
@Component
@Qualifier(value = "chineseCharacterServiceImpl")
@Transactional
public class ChineseCharacterServiceImpl implements ChineseCharacterService {

	private static final Logger logger = LoggerFactory.getLogger(ChineseCharacterServiceImpl.class);

	@Autowired
	@Qualifier(value = "chineseCharacterDaoImpl")
	private ChineseCharacterDao chineseCharacterDaoImpl;

	@Autowired
	@Qualifier(value = "chineseCharacterTagDaoImpl")
	private ChineseCharacterTagDao chineseCharacterTagDaoImpl;

	@Autowired
	@Qualifier(value = "tagDaoImpl")
	private TagDao tagDao;
	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void insertChineseCharacter(ChineseCharacter chineseCharacter, String insertTag) {
		if (insertTag.length() > 0) {
			String[] insertTags = insertTag.split(",");

			// insert tag into tag table first:check tag is in the table,
			// second:if tag name in the table then get id,
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
			List<ChineseCharacterTag> chineseCharacterTagList = new ArrayList<ChineseCharacterTag>();
			ChineseCharacterTag chineseCharacterTag = null;
			for (Tag tag : tagList) {
				chineseCharacterTag = new ChineseCharacterTag();
				chineseCharacterTag.setChineseCharacter(chineseCharacter);
				chineseCharacterTag.setTag(tag);
				chineseCharacterTagList.add(chineseCharacterTag);
			}
			chineseCharacter.setChineseCharacterTag(chineseCharacterTagList);
		}
		chineseCharacterDaoImpl.insertChineseCharacter(chineseCharacter);
	}

	@Override
	@Transactional(readOnly = true)
	public ChineseCharacter selectChineseCharacter(int id) {
		return chineseCharacterDaoImpl.selectChineseCharacter(id);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void updateChineseCharacter(ChineseCharacter chineseCharacter, String updateTag, String deleteTag) {
		if (deleteTag.length() > 0) {
			String[] deleteTagStr = deleteTag.split(",");
			for (String s : deleteTagStr) {
				chineseCharacterTagDaoImpl.deleteChineseCharacterTag(chineseCharacter.getId(), Integer.parseInt(s));
			}
		}
		if (updateTag.length() > 0) {
			String[] insertTags = updateTag.split(",");

			// insert tag into tag table first:check tag is in the table,
			// second:if tag name in the table then get id,
			List<Tag> tagList = tagDao.selectTagByTagNameList(Arrays.asList(updateTag));
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
			List<ChineseCharacterTag> chineseCharacterTagList = new ArrayList<ChineseCharacterTag>();
			ChineseCharacterTag chineseCharacterTag = null;
			for (Tag tag : tagList) {
				chineseCharacterTag = new ChineseCharacterTag();
				chineseCharacterTag.setChineseCharacter(chineseCharacter);
				chineseCharacterTag.setTag(tag);
				chineseCharacterTagList.add(chineseCharacterTag);
			}
			chineseCharacter.setChineseCharacterTag(chineseCharacterTagList);
		}
		chineseCharacterDaoImpl.updateChineseCharacter(chineseCharacter);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void deleteChineseCharacter(ChineseCharacter chineseCharacter) {
		chineseCharacterDaoImpl.deleteChineseCharacter(chineseCharacter);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ChineseCharacter> getTopChineseCharacter() {
		return chineseCharacterDaoImpl.getTopChineseCharacter();
	}

	@Override
	@Transactional(readOnly = true)
	public ChineseCharacterPageModel getDataByPage(int limit, int offset, String sEcho,int category) {
		ChineseCharacterPageModel coursewarePageModel = new ChineseCharacterPageModel();
		List<ChineseCharacter> list = chineseCharacterDaoImpl.getDataByPage(limit, offset, sEcho,category);
		int count = chineseCharacterDaoImpl.getCountData(category);
		coursewarePageModel.setList(list);
		coursewarePageModel.setCount(count);
		return coursewarePageModel;
	}

	@Override
	public List<ChineseCharacter> getFrontDataByPage(int limit, int offset, int choice,int category) {
		return chineseCharacterDaoImpl.getFrontDataByPage(limit, offset, choice,category);
	}

}
