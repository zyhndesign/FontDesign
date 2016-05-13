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
import com.cidic.fontdesign.model.ChineseCharacter;
import com.cidic.fontdesign.model.ChineseCharacterPageModel;
import com.cidic.fontdesign.model.ListResultModel;
import com.cidic.fontdesign.model.ResultModel;
import com.cidic.fontdesign.service.ChineseCharacterService;
import com.cidic.fontdesign.util.DateUtil;


@Controller
@RequestMapping("/chineseCharacter")
public class ChineseCharacterController {

	private static final Logger logger = LoggerFactory.getLogger(ChineseCharacterController.class);
	
	@Autowired
	@Qualifier(value="chineseCharacterServiceImpl")
	private ChineseCharacterService chineseCharacterServiceImpl;
	
	private ResultModel resultModel = null;
	
	@ExceptionHandler(FontDesignException.class)
	public @ResponseBody ResultModel handleCustomException(FontDesignException ex) {
		ResultModel resultModel = new ResultModel();
		resultModel.setResultCode(ex.getErrCode());
		resultModel.setMessage(ex.getErrMsg());
		return resultModel;
	}
	
	@RequestMapping(value = {"/chineseCharacterCOR"}, method = RequestMethod.GET)
	public ModelAndView getCourseWareCORView(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		view.setViewName("/admin/chineseCharacterCOR");
		return view;
	}
	
	@RequestMapping(value = {"/chineseCharacterCOR/{id}"}, method = RequestMethod.GET)
	public ModelAndView getCourseWareCOR(HttpServletRequest request,@PathVariable int id) {
		ChineseCharacter chineseCharacter = null;
		if (id > 0){
			chineseCharacter = chineseCharacterServiceImpl.selectChineseCharacter(id);
		}
		ModelAndView view = new ModelAndView();
		view.setViewName("/admin/chineseCharacterCOR");
		view.addObject("chineseCharacter", chineseCharacter);
		return view;
	}
	
	@RequestMapping(value = "/chineseCharacterMgr", method = RequestMethod.GET)
	public ModelAndView getCourseWareMgr(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		view.setViewName("/admin/chineseCharacterMgr");
		return view;
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST, produces="application/json")  
	@ResponseBody 
	public ResultModel insertChinese(@RequestParam String title, @RequestParam String author,
			@RequestParam String thumbnail,@RequestParam String createTime,@RequestParam String content,
			@RequestParam int topTag,@RequestParam String abstract_,@RequestParam String insertTag,
			@RequestParam int category,@RequestParam String bg){
		
		ChineseCharacter chineseCharacter = new ChineseCharacter();
		chineseCharacter.setTitle(title);
		chineseCharacter.setAuthor(author);
		chineseCharacter.setContent(content);
		if (createTime.equals("")){
			chineseCharacter.setCreateTime(new Date());
		}
		else{
			chineseCharacter.setCreateTime(DateUtil.stringToDate(createTime));
		}
		
		chineseCharacter.setThumbnail(thumbnail);
		chineseCharacter.setTopTag(topTag);
		chineseCharacter.setAbstract_(abstract_);
		chineseCharacter.setCategory(category);
		chineseCharacter.setBg(bg);
		try{
			chineseCharacterServiceImpl.insertChineseCharacter(chineseCharacter,insertTag);
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
	public ResultModel selectChinese(HttpServletRequest request,HttpServletResponse response,@PathVariable int id) throws Exception{
		ChineseCharacter chineseCharacter = null;
		try{
			response.setContentType("text/html;charset=UTF-8");
			response.addHeader("Access-Control-Allow-Origin","*");
		    if("IE".equals(request.getParameter("type"))){
		    	response.addHeader("XDomainRequestAllowed","1");
		    }
		    chineseCharacter = chineseCharacterServiceImpl.selectChineseCharacter(id);
			resultModel = new ResultModel();
			resultModel.setResultCode(200);
			resultModel.setObject(chineseCharacter);
		}
		catch(Exception e){
			throw new FontDesignException(500, "获取数据出错");
		}
		return resultModel;
	}
	
	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST, produces="application/json")  
	@ResponseBody 
	public ResultModel updateChinese(@RequestParam String title, @RequestParam String author,
			@RequestParam String thumbnail,@RequestParam String createTime,@RequestParam String content,
			@RequestParam int topTag,@RequestParam String abstract_,@PathVariable int id,@RequestParam String insertTag,
			@RequestParam String deleteTag,@RequestParam int category,@RequestParam String bg){
		ChineseCharacter chineseCharacter = new ChineseCharacter();
		chineseCharacter.setId(id);
		chineseCharacter.setTitle(title);
		chineseCharacter.setAuthor(author);
		chineseCharacter.setContent(content);
		if (createTime.equals("")){
			chineseCharacter.setCreateTime(new Date());
		}
		else{
			chineseCharacter.setCreateTime(DateUtil.stringToDate(createTime));
		}
		
		chineseCharacter.setThumbnail(thumbnail);
		chineseCharacter.setTopTag(topTag);
		chineseCharacter.setAbstract_(abstract_);
		chineseCharacter.setCategory(category);
		chineseCharacter.setBg(bg);
		try{
			chineseCharacterServiceImpl.updateChineseCharacter(chineseCharacter,insertTag,deleteTag);
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
	public ResultModel deleteChinese(@PathVariable int id){
		ChineseCharacter chineseCharacter = new ChineseCharacter();
		chineseCharacter.setId(id);
		try{
			chineseCharacterServiceImpl.deleteChineseCharacter(chineseCharacter);
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
	public ListResultModel listChinese(@RequestParam int iDisplayLength, @RequestParam int iDisplayStart,@RequestParam String sEcho,@RequestParam int category){
		ListResultModel listResultModel = new ListResultModel();
		try{
			ChineseCharacterPageModel chineseCharacterPageModel  = chineseCharacterServiceImpl.getDataByPage(iDisplayLength, iDisplayStart, sEcho,category);
			List<ChineseCharacter> list = chineseCharacterPageModel.getList();
			listResultModel.setAaData(list);
			listResultModel.setsEcho(sEcho);
			listResultModel.setiTotalRecords(chineseCharacterPageModel.getCount());
			listResultModel.setiTotalDisplayRecords(chineseCharacterPageModel.getCount());
			listResultModel.setSuccess(true);
		}
		catch(Exception e){
			listResultModel.setSuccess(false);
		}
		return listResultModel;
	}
	
	@RequestMapping(value = "/frontList", method = RequestMethod.GET, produces="application/json")  
	@ResponseBody 
	public ListResultModel frontListChinese(HttpServletRequest request,HttpServletResponse response,@RequestParam int limit, @RequestParam int offset,
			@RequestParam int choice,@RequestParam int category){
		ListResultModel listResultModel = new ListResultModel();
		try{
			response.setContentType("text/html;charset=UTF-8");
			response.addHeader("Access-Control-Allow-Origin","*");
		    if("IE".equals(request.getParameter("type"))){
		    	response.addHeader("XDomainRequestAllowed","1");
		    }
			List<ChineseCharacter> list = chineseCharacterServiceImpl.getFrontDataByPage(limit, offset, choice,category);
			
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
