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

import com.cidic.fontdesign.exception.FontDesignException;
import com.cidic.fontdesign.model.ListResultModel;
import com.cidic.fontdesign.model.ResultModel;
import com.cidic.fontdesign.model.SpecialDesign;
import com.cidic.fontdesign.model.SpecialPageModel;
import com.cidic.fontdesign.service.SpecialDesignService;
import com.cidic.fontdesign.util.DateUtil;

@Controller
@RequestMapping("/specialdesign")
public class SpecialDesignController {
	
	private static final Logger logger = LoggerFactory.getLogger(SpecialDesignController.class);
	
	@Autowired
	@Qualifier(value="specialDesignServiceImpl")
	private SpecialDesignService specialDesignServiceImpl;
	
	private ResultModel resultModel = null;
	
	@ExceptionHandler(FontDesignException.class)
	public @ResponseBody ResultModel handleCustomException(FontDesignException ex) {
		ResultModel resultModel = new ResultModel();
		resultModel.setResultCode(ex.getErrCode());
		resultModel.setMessage(ex.getErrMsg());
		return resultModel;
	}
	
	@RequestMapping(value = {"/specialDesignCOR"}, method = RequestMethod.GET)
	public ModelAndView getCourseCORView(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		view.setViewName("/admin/specialDesignCOR");
		return view;
	}
	
	@RequestMapping(value = {"/specialDesignCOR/{id}"}, method = RequestMethod.GET)
	public ModelAndView getCourseCOR(HttpServletRequest request,@PathVariable int id) {
		SpecialDesign specialDesign = null;
		if (id > 0){
			specialDesign = specialDesignServiceImpl.selectSpecialDesign(id);
		}
		ModelAndView view = new ModelAndView();
		view.setViewName("/admin/specialDesignCOR");
		view.addObject("specialDesign", specialDesign);
		return view;
	}
	
	@RequestMapping(value = "/specialDesignMgr", method = RequestMethod.GET)
	public ModelAndView getCourseMgr(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		view.setViewName("/admin/specialDesignMgr");
		return view;
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST)  
	@ResponseBody 
	public ResultModel insertCourseDesign(@RequestParam String title, @RequestParam String abstract_,
			@RequestParam String teacher,@RequestParam String createTime,@RequestParam int topTag,
			@RequestParam int specialDetailId,@RequestParam String insertTag,@RequestParam String bg,@RequestParam String thumbnail){
		
		logger.info("/specialdesign/insert/"+title);
		SpecialDesign specialDesign;
		try{
			specialDesign = new SpecialDesign();
			specialDesign.setAbstract_(abstract_);
			specialDesign.setTeacher(teacher);
			specialDesign.setTitle(title);
			if (createTime.equals("")){
				specialDesign.setCreateTime(new Date());
			}
			else{
				specialDesign.setCreateTime(DateUtil.stringToDate(createTime));
			}
			
			specialDesign.setTopTag(topTag);
			specialDesign.setSpecialDetailId(specialDetailId);
			specialDesign.setBg(bg);
			specialDesign.setThumbnail(thumbnail);
			specialDesignServiceImpl.insertSpecialDesign(specialDesign,insertTag);
			
			resultModel = new ResultModel();
			resultModel.setResultCode(200);
			
		}
		catch(Exception e){
			throw new FontDesignException(500, "写入数据出错");
		}
		return resultModel;
	}
	
	@RequestMapping(value = "/select/{id}", method = RequestMethod.GET, produces="application/json")  
	@ResponseBody 
	public ResultModel selectSpecialDesign(HttpServletRequest request,HttpServletResponse response,@PathVariable int id) throws Exception{
		SpecialDesign specialDesign = null;
		try{
			response.setContentType("text/html;charset=UTF-8");
			response.addHeader("Access-Control-Allow-Origin","*");
		    if("IE".equals(request.getParameter("type"))){
		    	response.addHeader("XDomainRequestAllowed","1");
		    }
		    specialDesign = specialDesignServiceImpl.selectSpecialDesign(id);
			resultModel = new ResultModel();
			resultModel.setResultCode(200);
			resultModel.setObject(specialDesign);
		}
		catch(Exception e){
			
			throw new FontDesignException(500, "获取数据出错");
		}
		return resultModel;
	}
	
	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST)  
	@ResponseBody 
	public ResultModel updateCourseDesign(@PathVariable int id, @RequestParam String title, @RequestParam String abstract_,
			@RequestParam String teacher,@RequestParam String createTime,@RequestParam int topTag,@RequestParam String bg,
			@RequestParam int specialDetailId,@RequestParam String insertTag,@RequestParam String deleteTag,@RequestParam String thumbnail){
		
		SpecialDesign specialDesign;
		try{
			specialDesign = new SpecialDesign();
			specialDesign.setId(id);
			specialDesign.setAbstract_(abstract_);
			specialDesign.setTeacher(teacher);
			specialDesign.setTitle(title);
			if (createTime.equals("")){
				specialDesign.setCreateTime(new Date());
			}
			else{
				specialDesign.setCreateTime(DateUtil.stringToDate(createTime));
			}
			specialDesign.setTopTag(topTag);
			specialDesign.setSpecialDetailId(specialDetailId);
			specialDesign.setBg(bg);
			specialDesign.setThumbnail(thumbnail);
			specialDesignServiceImpl.updateSpecialDesign(specialDesign,insertTag,deleteTag);
			
			resultModel = new ResultModel();
			resultModel.setResultCode(200);
			
		}
		catch(Exception e){
			throw new FontDesignException(500, "更新数据出错");
		}
		return resultModel;
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces="application/json")  
	@ResponseBody 
	public ResultModel deleteCourseDesign(@PathVariable int id){
		SpecialDesign specialDesign = new SpecialDesign();
		specialDesign.setId(id);
		try{
			specialDesignServiceImpl.deleteSpecialDesign(specialDesign);
			resultModel = new ResultModel();
			resultModel.setResultCode(200);
		}
		catch(Exception e){
			throw new FontDesignException(500, "删除数据出错");
		}
		return resultModel;
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces="application/json")  
	@ResponseBody 
	public ListResultModel listCourseDesign(@RequestParam int iDisplayLength, @RequestParam int iDisplayStart,@RequestParam String sEcho){
		ListResultModel listResultModel = new ListResultModel();
		try{
			SpecialPageModel coursePageModel = specialDesignServiceImpl.getDataByPage(iDisplayLength, iDisplayStart, sEcho);
			List<SpecialDesign> list = coursePageModel.getList();
			listResultModel.setAaData(list);
			listResultModel.setsEcho(sEcho);
			listResultModel.setiTotalRecords(coursePageModel.getCount());
			listResultModel.setiTotalDisplayRecords(coursePageModel.getCount());
			listResultModel.setSuccess(true);
		}
		catch(Exception e){
			listResultModel.setSuccess(false);
		}
		return listResultModel;
	}
	
	@RequestMapping(value = "/frontList", method = RequestMethod.GET, produces="application/json")  
	@ResponseBody 
	public ListResultModel frontListCourseDesign(HttpServletRequest request,HttpServletResponse response,@RequestParam int limit, @RequestParam int offset,@RequestParam int choice){
		ListResultModel listResultModel = new ListResultModel();
		try{
			response.setContentType("text/html;charset=UTF-8");
			response.addHeader("Access-Control-Allow-Origin","*");
		    if("IE".equals(request.getParameter("type"))){
		    	response.addHeader("XDomainRequestAllowed","1");
		    }
			List<SpecialDesign> list = specialDesignServiceImpl.getFrontDataByPage(limit, offset, choice);
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
