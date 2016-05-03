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


@Service
@Component
@Qualifier(value = "homeServiceImpl")
@Transactional
public class HomeServiceImpl implements HomeService {

	@Autowired
	@Qualifier(value="coursewareServiceImpl")
	private CoursewareService coursewareServiceImpl;
	
	@Autowired
	@Qualifier(value="courseDesignServiceImpl")
	private CourseDesignService courseDesignServiceImpl;
	
	@Autowired
	@Qualifier(value="videoCourseServiceImpl")
	private VideoCourseService videoCourseServiceImpl;
	
	@Autowired
	@Qualifier(value="courseDesignTagServiceImpl")
	private CourseDesignTagService courseDesignTagServiceImpl;
	
	@Autowired
	@Qualifier(value="coursewareTagServiceImpl")
	private CoursewareTagService coursewareTagServiceImpl;
	
	@Autowired
	@Qualifier(value="videoCourseTagServiceImpl")
	private VideoCourseTagService videoCourseTagServiceImpl;
	
	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<Object> getHomeContentData() {
		
		List<Object> list = new ArrayList<Object>();
		
		List<Courseware> courseWareList = coursewareServiceImpl.getTopCourseware();
		List<CourseDesign> courseDesignList = courseDesignServiceImpl.getTopCourseDesign();
		List<VideoCourse> videoCourseList = videoCourseServiceImpl.getTopVideoCourse();
		list.add(courseWareList);
		list.add(courseDesignList);
		list.add(videoCourseList);
		
		return list;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public Map<String,Object> getSearchResultByKeywards(List<String> keywords) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		List<CourseDesign> courseDesignList = courseDesignTagServiceImpl.getCourseDesignByTagName(keywords);
		map.put("CourseDesign", courseDesignList);
		
		List<Courseware> coursewareList = coursewareTagServiceImpl.getCoursewareByTagName(keywords);
		map.put("Courseware", coursewareList);
		
		List<VideoCourse> videoCourseList = videoCourseTagServiceImpl.getVideoCourseByTagName(keywords);
		map.put("VideoCourse", videoCourseList);
		
		return map;
	}

	
}
