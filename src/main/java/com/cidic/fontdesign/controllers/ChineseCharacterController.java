package com.cidic.fontdesign.controllers;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/chineseCharacter")
public class ChineseCharacterController {

	private static final Logger logger = LoggerFactory.getLogger(ChineseCharacterController.class);
	
	@Autowired
	@Qualifier(value="coursewareServiceImpl")
	private CoursewareService coursewareServiceImpl;
	
	private ResultModel resultModel = null;
	
	@ExceptionHandler(UIDesignException.class)
	public @ResponseBody ResultModel handleCustomException(UIDesignException ex) {
		ResultModel resultModel = new ResultModel();
		resultModel.setResultCode(ex.getErrCode());
		resultModel.setMessage(ex.getErrMsg());
		return resultModel;
	}
	
	@RequestMapping(value = {"/courseWareCOR"}, method = RequestMethod.GET)
	public ModelAndView getCourseWareCORView(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		view.setViewName("/admin/courseWareCOR");
		return view;
	}
	
	@RequestMapping(value = {"/courseWareCOR/{id}"}, method = RequestMethod.GET)
	public ModelAndView getCourseWareCOR(HttpServletRequest request,@PathVariable int id) {
		Courseware courseware = null;
		if (id > 0){
			courseware = coursewareServiceImpl.selectCourseware(id);
		}
		ModelAndView view = new ModelAndView();
		view.setViewName("/admin/courseWareCOR");
		view.addObject("courseware", courseware);
		return view;
	}
	
	@RequestMapping(value = "/courseWareMgr", method = RequestMethod.GET)
	public ModelAndView getCourseWareMgr(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		view.setViewName("/admin/courseWareMgr");
		return view;
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST, produces="application/json")  
	@ResponseBody 
	public ResultModel insertCourseware(@RequestParam String title, @RequestParam String author,
			@RequestParam String thumbnail,@RequestParam String createTime,@RequestParam String content,
			@RequestParam int topTag,@RequestParam String abstract_,@RequestParam String insertTag){
		
		Courseware courseware = new Courseware();
		courseware.setTitle(title);
		courseware.setAuthor(author);
		courseware.setContent(content);
		if (createTime.equals("")){
			courseware.setCreateTime(new Date());
		}
		else{
			courseware.setCreateTime(DateUtil.stringToDate(createTime));
		}
		
		courseware.setThumbnail(thumbnail);
		courseware.setTopTag(topTag);
		courseware.setAbstract_(abstract_);
		try{
			coursewareServiceImpl.insertCourseware(courseware,insertTag);
			resultModel = new ResultModel();
			resultModel.setResultCode(200);
		}
		catch(Exception e){
			throw new UIDesignException(500, "写入数据出错");
		}
		return resultModel;
	}
	
	@RequestMapping(value = "/select/{id}", method = RequestMethod.GET, produces="application/json")  
	@ResponseBody 
	public ResultModel selectCourseware(HttpServletRequest request,HttpServletResponse response,@PathVariable int id) throws Exception{
		Courseware courseware = null;
		try{
			response.setContentType("text/html;charset=UTF-8");
			response.addHeader("Access-Control-Allow-Origin","*");
		    if("IE".equals(request.getParameter("type"))){
		    	response.addHeader("XDomainRequestAllowed","1");
		    }
			courseware = coursewareServiceImpl.selectCourseware(id);
			resultModel = new ResultModel();
			resultModel.setResultCode(200);
			resultModel.setObject(courseware);
		}
		catch(Exception e){
			throw new UIDesignException(500, "获取数据出错");
		}
		return resultModel;
	}
	
	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST, produces="application/json")  
	@ResponseBody 
	public ResultModel updateCourseware(@RequestParam String title, @RequestParam String author,
			@RequestParam String thumbnail,@RequestParam String createTime,@RequestParam String content,
			@RequestParam int topTag,@RequestParam String abstract_,@PathVariable int id,@RequestParam String insertTag,@RequestParam String deleteTag){
		Courseware courseware = new Courseware();
		courseware.setId(id);
		courseware.setTitle(title);
		courseware.setAuthor(author);
		courseware.setContent(content);
		if (createTime.equals("")){
			courseware.setCreateTime(new Date());
		}
		else{
			courseware.setCreateTime(DateUtil.stringToDate(createTime));
		}
		
		courseware.setThumbnail(thumbnail);
		courseware.setTopTag(topTag);
		courseware.setAbstract_(abstract_);
		try{
			coursewareServiceImpl.updateCourseware(courseware,insertTag,deleteTag);
			resultModel = new ResultModel();
			resultModel.setResultCode(200);
		}
		catch(Exception e){
			throw new UIDesignException(500, "更新数据出错");
		}
		return resultModel;
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces="application/json")  
	@ResponseBody 
	public ResultModel deleteCourseware(@PathVariable int id){
		Courseware courseware = new Courseware();
		courseware.setId(id);
		try{
			coursewareServiceImpl.deleteCourseware(courseware);
			resultModel = new ResultModel();
			resultModel.setResultCode(200);
		}
		catch(Exception e){
			throw new UIDesignException(500, "删除数据出错");
		}
		return resultModel;
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces="application/json")  
	@ResponseBody 
	public ListResultModel listCourseware(@RequestParam int iDisplayLength, @RequestParam int iDisplayStart,@RequestParam String sEcho){
		ListResultModel listResultModel = new ListResultModel();
		try{
			CoursewarePageModel coursewarePageModel  = coursewareServiceImpl.getDataByPage(iDisplayLength, iDisplayStart, sEcho);
			List<Courseware> list = coursewarePageModel.getList();
			listResultModel.setAaData(list);
			listResultModel.setsEcho(sEcho);
			listResultModel.setiTotalRecords(coursewarePageModel.getCount());
			listResultModel.setiTotalDisplayRecords(coursewarePageModel.getCount());
			listResultModel.setSuccess(true);
		}
		catch(Exception e){
			listResultModel.setSuccess(false);
		}
		return listResultModel;
	}
	
	@RequestMapping(value = "/frontList", method = RequestMethod.GET, produces="application/json")  
	@ResponseBody 
	public ListResultModel frontListCourseware(HttpServletRequest request,HttpServletResponse response,@RequestParam int limit, @RequestParam int offset,@RequestParam int choice){
		ListResultModel listResultModel = new ListResultModel();
		try{
			response.setContentType("text/html;charset=UTF-8");
			response.addHeader("Access-Control-Allow-Origin","*");
		    if("IE".equals(request.getParameter("type"))){
		    	response.addHeader("XDomainRequestAllowed","1");
		    }
			List<Courseware> list = coursewareServiceImpl.getFrontDataByPage(limit, offset, choice);
			listResultModel.setAaData(list);
			listResultModel.setiTotalRecords(list.size());
			listResultModel.setiTotalDisplayRecords(offset + list.size());
			listResultModel.setSuccess(true);
		}
		catch(Exception e){
			listResultModel.setSuccess(false);
		}
		return listResultModel;
	}
}
