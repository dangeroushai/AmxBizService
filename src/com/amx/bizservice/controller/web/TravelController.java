package com.amx.bizservice.controller.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amx.bizservice.annotation.Authentication;
import com.amx.bizservice.config.CommonConfig;
import com.amx.bizservice.config.CommonConstants;
import com.amx.bizservice.controller.BaseController;
import com.amx.bizservice.enums.ResponseTypeEnum;
import com.amx.bizservice.enums.ResponseTypeEnum.Response;
import com.amx.bizservice.model.bo.CustomHodometerBo;
import com.amx.bizservice.model.bo.LanguageBo;
import com.amx.bizservice.model.bo.PackageBo;
import com.amx.bizservice.model.bo.ProductBo;
import com.amx.bizservice.model.bo.TravelBo;
import com.amx.bizservice.model.bo.UpdateResponseBo;
import com.amx.bizservice.model.dto.JsonResponseDto;
import com.amx.bizservice.model.dto.PageResponseDto;
import com.amx.bizservice.model.qo.PageQuery;
import com.amx.bizservice.model.vo.web.TravelListVo;
import com.amx.bizservice.model.vo.web.TravelVo;
import com.amx.bizservice.model.vo.web.TravelVo.HodometerVo.ItemVo;
import com.amx.bizservice.service.CustomHodometerService;
import com.amx.bizservice.service.LanguageService;
import com.amx.bizservice.service.PackageService;
import com.amx.bizservice.service.ProductService;
import com.amx.bizservice.service.TravelService;
import com.amx.bizservice.util.JsonUtil;
import com.amx.bizservice.util.StringUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * 我的行程控制器
 * @author DangerousHai
 *
 */
@Authentication
@Controller
public class TravelController extends BaseController{
	
	@Autowired
	private TravelService travelService;
	@Autowired
	private CustomHodometerService customHodometerService;
	@Autowired
	private ProductService productService;
	@Autowired
	private PackageService packageService;
	@Autowired
	private LanguageService languageService;
	
	
	/**
	 * 获取行程列表
	 * @param pageIndex
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/travelList/{pageIndex}", method = RequestMethod.GET)
	public Map<String, Object> getTravelList(@PathVariable("pageIndex") int pageIndex, HttpServletRequest request){
		Object response = ResponseTypeEnum.PARAMETER_ERROR; 
		TravelListVo vo = null;
		int pageSize = CommonConfig.WEB_PAGE_SIZE;
		
		//session：登录用户直接获取/未登录用户创建（未保存USERID，根据SessionID判断身份）
		HttpSession session = request.getSession(false);
		Long userId = (Long) session.getAttribute(CommonConstants.SESSION_USER_ID);
		
		PageQuery query = new PageQuery(userId, pageIndex, pageSize );
		PageResponseDto<TravelBo> page = travelService.getTravelList(query );
		if(page != null){
			response = ResponseTypeEnum.getNewOkResponse();
			//将Bo转为VO
			vo = new TravelListVo(page, null);
			if(page.getContent() != null){
				List<TravelListVo.TravelVo> travelList = new ArrayList<TravelListVo.TravelVo>();
				for (TravelBo tbo : page.getContent() ) {
					CustomHodometerBo chbo = customHodometerService.getFirstCustomHodometer(tbo.getId());
					//获取行程第一项活动对应的产品快照信息
					ProductBo prodbo = productService.getProductSnap(chbo.getProductId(), userId);
					travelList.add(vo.new TravelVo(tbo, prodbo));
				}
				vo.setTravelList(travelList);
			}
		}
		//设置响应数据
		((Response)response).setData(vo);
		
		return JsonResponseDto.getResult(response);
	}
	
	/**
	 * 获取我的行程明细
	 * @return
	 */
//	@ResponseBody
	@RequestMapping(value = "travel/{id}", method = RequestMethod.GET)
	public Map<String, Object> getTravel(@PathVariable("id") long travelId, HttpServletRequest request){
		Object response = ResponseTypeEnum.PARAMETER_ERROR; 
		TravelVo travelvo = null;
		
		HttpSession session = request.getSession(false);
		long userId = (Long) session.getAttribute(CommonConstants.SESSION_USER_ID);
		
		TravelBo bo = travelService.getTravel(userId, travelId);
		if(bo != null){
			response = ResponseTypeEnum.getNewOkResponse();
			
			travelvo = new TravelVo();
			travelvo.setId(bo.getId());
			travelvo.setName(bo.getName());
			travelvo.setUpdateTime(StringUtil.getSdfDate(bo.getModifyTime()));
			
			//已按出发日期+时间升序排列
			List<CustomHodometerBo> customHodometerList = customHodometerService.getCustomHodometerListByTravelId(travelId);
			
			if(customHodometerList != null){
				travelvo.setHodometer(new ArrayList<TravelVo.HodometerVo>());

				//当前日期
				Date currDate = null;
				//当前日期-行程
				TravelVo.HodometerVo hodometervo = null;
				//天序
				int dayOrder = 0;
				//项目序
				int itemOrder = 0;
				//遍历所有的行程项
				for(CustomHodometerBo chBo : customHodometerList){
					//新一天
					if(!(chBo.getGoOffDate().equals(currDate))){
						itemOrder = 0;
						currDate = chBo.getGoOffDate();
						hodometervo = travelvo.new HodometerVo(); 
						hodometervo.setDayOrder(++dayOrder);
						hodometervo.setDate(StringUtil.sdf_yyyy_MM_dd.format(currDate));
						hodometervo.setItems(new ArrayList<TravelVo.HodometerVo.ItemVo>());
						
						travelvo.getHodometer().add(hodometervo);
						
					}
					
					/* 处理与行程每项活动相关的信息  */
					{
						ProductBo prodbo = productService.getProductSnap(chBo.getProductId(), userId);
						PackageBo packbo = packageService.findOne(chBo.getPackageId());
						LanguageBo langbo = languageService.findOne(chBo.getLanguageId());
						ItemVo itemVo = hodometervo.new ItemVo(chBo, prodbo, packbo, langbo);
						itemVo.setItemOrder(++itemOrder);
						
						hodometervo.getItems().add(itemVo);
					}
				}
			}
			
			//设置响应数据
			((Response)response).setData(travelvo);
		}
		
		return JsonResponseDto.getResult(response);
	}
	
	/**
	 * 添加
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@ResponseBody
	@RequestMapping(value = "/travel", method = RequestMethod.POST)
//	public Map<String, Object> insertTravel( @RequestBody com.amx.bizservice.model.vo.web.TravelVo inVo , HttpServletRequest request){
	public Map<String, Object> insertTravel( @RequestBody MultiValueMap<String	, String> map , HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException{
		Object response = ResponseTypeEnum.PARAMETER_ERROR;
		UpdateResponseBo result = null;
		//由于SpringMvc无法直接接收接收数组，故在前端被处理为json
		//注意：Jackson不能反序列化汗内部类的类
		com.amx.bizservice.model.vo.web.input.TravelVo inVo = JsonUtil.mapper.readValue(map.getFirst("json"), com.amx.bizservice.model.vo.web.input.TravelVo.class);
		
		/*获取用户ID*/
		HttpSession session = request.getSession(false);
		long userId = (Long) session.getAttribute(CommonConstants.SESSION_USER_ID);
		/*封装参数*/
		List<com.amx.bizservice.model.vo.web.input.HodometerVo> hodometerList = inVo.getHodometer();
		
		TravelBo travelBo = new TravelBo();
		travelBo.setUserId(userId);
		travelBo.setName(inVo.getName());
		travelBo.setDayAmount(hodometerList.size());
		
		//插入行程
		Long travelId = travelService.insertTravel(travelBo);
		
		if(travelId != null){
			if(travelId > 0){
				List<CustomHodometerBo> travel_items = new ArrayList<CustomHodometerBo>();
				//处理每天的行程
				for (com.amx.bizservice.model.vo.web.input.HodometerVo hodometerVo : hodometerList){
					Date currDate = hodometerVo.getDate();
					//处理当天的行程活动
					for (com.amx.bizservice.model.vo.web.input.ItemVo itemVo : hodometerVo.getItems()) {
						CustomHodometerBo chBo = new CustomHodometerBo();
						
						chBo.setTravelId(travelId);
						chBo.setGoOffDate(currDate);
						chBo.setGoOffTime(StringUtil.getHMTime(itemVo.getGoOffTime()));
						chBo.setProductId(itemVo.getId());
						chBo.setPackageId(itemVo.getPackageId());
						chBo.setLanguageId(itemVo.getLanguageId());
						chBo.setAdultNum(itemVo.getAdultNum());
						chBo.setChildNum(itemVo.getChildNum());
						chBo.setRemark(itemVo.getRemark());
						
						chBo.setUserId(userId);
						
						travel_items.add(chBo);
					}
				}
				
				//插入行程活动
				result = customHodometerService.insertCustomHodometerInBatch(travel_items);
				
				if(result != null){
					response = ResponseTypeEnum.getNewOkResponse();
					//设置响应数据
					((Response)response).setData(result);
				}else{
					//执行失败，则删除行程
					travelService.deleteTravel(userId, travelId);
				}
			}else{
				response = ResponseTypeEnum.getNewOkResponse();
				result = new UpdateResponseBo();
				result.setIsSucceeded(false);
				result.setMsg("您已有同名行程，请修改名称再提交");
				//设置响应数据
				((Response)response).setData(result);
			}
		}
		
		return JsonResponseDto.getResult(response);
	}
	
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.PUT)
	public Map<String, Object> updateTravel(@RequestBody MultiValueMap<String, String> map   , HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException{// 使用Bean接收参数
		Object response = ResponseTypeEnum.PARAMETER_ERROR;
		UpdateResponseBo result = null;
		//由于SpringMvc无法直接接收接收数组，故在前端被处理为json
		//注意：Jackson不能反序列化汗内部类的类
		com.amx.bizservice.model.vo.web.input.TravelVo inVo = JsonUtil.mapper.readValue(map.getFirst("json"), com.amx.bizservice.model.vo.web.input.TravelVo.class);
		/*获取用户ID*/
		HttpSession session = request.getSession(false);
		long userId = (Long) session.getAttribute(CommonConstants.SESSION_USER_ID);
		/*封装参数*/
		List<com.amx.bizservice.model.vo.web.input.HodometerVo> hodometerList = inVo.getHodometer();
		
		TravelBo travelBo = new TravelBo();
		travelBo.setId(inVo.getId());
		travelBo.setUserId(userId);
		travelBo.setName(inVo.getName());
		travelBo.setDayAmount(hodometerList.size());
		
		//更新行程
		result = travelService.updateTravel(travelBo);
		
		if(result != null  && result.getIsSucceeded()){
			//删除旧的行程活动
			result = customHodometerService.deleteCustomHodometerByTravelId(travelBo.getId());
			if(result != null  && result.getIsSucceeded()){
				List<CustomHodometerBo> travel_items = new ArrayList<CustomHodometerBo>();
				//处理每天的行程
				for (com.amx.bizservice.model.vo.web.input.HodometerVo hodometerVo : hodometerList){
					Date currDate = hodometerVo.getDate();
					//处理当天的行程活动
					for (com.amx.bizservice.model.vo.web.input.ItemVo itemVo : hodometerVo.getItems()) {
						CustomHodometerBo chBo = new CustomHodometerBo();
						
						chBo.setTravelId(travelBo.getId());
						chBo.setGoOffDate(currDate);
						chBo.setGoOffTime(StringUtil.getHMTime(itemVo.getGoOffTime()));
						chBo.setProductId(itemVo.getId());
						chBo.setPackageId(itemVo.getPackageId());
						chBo.setLanguageId(itemVo.getLanguageId());
						chBo.setAdultNum(itemVo.getAdultNum());
						chBo.setChildNum(itemVo.getChildNum());
						chBo.setRemark(itemVo.getRemark());
						
						chBo.setUserId(userId);
						
						travel_items.add(chBo);
					}
				}
				
				//插入新的行程活动
				result = customHodometerService.insertCustomHodometerInBatch(travel_items);
				if(result != null){
					response = ResponseTypeEnum.getNewOkResponse();
				}
			}
		}
		//设置响应数据
		((Response)response).setData(result);
		
		return JsonResponseDto.getResult(response);
	}
	
	
	/**
	 * 删除我的行程
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/travel/{id}", method = RequestMethod.DELETE)
	public Map<String, Object> deleteTravel(@PathVariable("id") long travelId, HttpServletRequest request){
		Object response = ResponseTypeEnum.PARAMETER_ERROR;
		UpdateResponseBo result = null;
		//session：登录用户直接获取/未登录用户创建（未保存USERID，根据SessionID判断身份）
		HttpSession session = request.getSession(false);
		Long userId = (Long) session.getAttribute(CommonConstants.SESSION_USER_ID);
		
		result = travelService.deleteTravel(userId, travelId);
		if(result != null){
			response = ResponseTypeEnum.getNewOkResponse();
		}
		//设置响应数据
		((Response)response).setData(result);
		
		return JsonResponseDto.getResult(response);
	}
}
