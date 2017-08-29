package com.amx.bizservice.controller.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amx.bizservice.annotation.Authentication;
import com.amx.bizservice.config.CommonConstants;
import com.amx.bizservice.controller.BaseController;
import com.amx.bizservice.enums.AccessLevelEnum;
import com.amx.bizservice.enums.ResponseTypeEnum;
import com.amx.bizservice.enums.ResponseTypeEnum.Response;
import com.amx.bizservice.model.bo.CustomHodometerBo;
import com.amx.bizservice.model.bo.LanguageBo;
import com.amx.bizservice.model.bo.PackageBo;
import com.amx.bizservice.model.bo.ProductBo;
import com.amx.bizservice.model.bo.UpdateResponseBo;
import com.amx.bizservice.model.dto.JsonResponseDto;
import com.amx.bizservice.model.vo.web.TravelPlanVo;
import com.amx.bizservice.service.CustomHodometerService;
import com.amx.bizservice.service.LanguageService;
import com.amx.bizservice.service.PackageService;
import com.amx.bizservice.service.ProductService;
import com.amx.bizservice.util.AccessLevelUtil;
import com.amx.bizservice.util.SessionUtil;

/**
 * 行程规划器控制器
 * @author DangerousHai
 *
 */
@Authentication(accessLevel = AccessLevelEnum.PROTECTED)
@Controller
@RequestMapping("/travelPlan")
public class TravelPlanController extends BaseController{
	
	@Autowired
	private CustomHodometerService customHodometerService;
	@Autowired
	private ProductService productService;
	@Autowired
	private PackageService packageService;
	@Autowired
	private LanguageService languageService;
	
	
	/**
	 * 获取收藏列表
	 * @param pageIndex
	 * @return
	 */
	@Authentication(accessLevel = AccessLevelEnum.PUBLIC)
//	@ResponseBody
	//XXX - 使用了注解@ResponseBody的由SpringMVC默认的ViewReslover转换为Json，未使用的由配置文件的ViewReslover处理
	@RequestMapping( method = RequestMethod.GET)
	public Map<String, Object> getCustomHodometerList( HttpServletRequest request){
		Object response = ResponseTypeEnum.PARAMETER_ERROR; 
		List<TravelPlanVo> voList = null;
		
		AccessLevelEnum userAccessLevel = AccessLevelUtil.getUserAccessLevel(request);
		//未认证用户
		if(userAccessLevel.equals(AccessLevelEnum.PUBLIC)){
			response = ResponseTypeEnum.getNewOkResponse();
			voList =  new ArrayList<TravelPlanVo>(0);
		}else{
			HttpSession session = request.getSession(false);
			Long userId = (Long) session.getAttribute(CommonConstants.SESSION_USER_ID);
			List<CustomHodometerBo> bolist = customHodometerService.getCustomHodometerListByUserId(userId);
			if(bolist != null){
				response = ResponseTypeEnum.getNewOkResponse();
				//添加自由行占位产品
				CustomHodometerBo placeholderBo = customHodometerService.getPlaceholderHodometer();
				bolist.add(0, placeholderBo );
				//将Bo转为VO
				voList =  new ArrayList<TravelPlanVo>();
				for (CustomHodometerBo bo : bolist ) {
					if(bo == null) { continue; }
					//将收藏信息转化为产品快照信息
					ProductBo prodbo = productService.getProductSnap(bo.getProductId());
					PackageBo packbo = null;
					if(bo.getPackageId() != null){
						packbo = packageService.findOne(bo.getPackageId());
					}
					LanguageBo langbo = null;
					if(bo.getLanguageId() != null){
						langbo = languageService.findOne(bo.getLanguageId());
					}
					voList.add(new TravelPlanVo(bo, prodbo, packbo, langbo));
				}
			}
		}
		//设置响应数据
		((Response)response).setData(voList);
		
		return JsonResponseDto.getResult(response);
	}
	
	/**
	 * 添加
	 * @return
	 */
	@Authentication(accessLevel = AccessLevelEnum.PUBLIC)
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> insertTravelPlan( com.amx.bizservice.model.vo.web.input.TravelPlanVo inVo , HttpServletRequest request, HttpServletResponse response){// 使用Bean接收参数
		Object responseBody = ResponseTypeEnum.PARAMETER_ERROR;
		UpdateResponseBo result = null;
		/*封装参数*/
		CustomHodometerBo bo = new CustomHodometerBo();
		bo.setProductId(inVo.getProductId());
		bo.setPackageId(inVo.getPackageId());
		bo.setLanguageId(inVo.getLanguageId());
		bo.setAdultNum(inVo.getAdultNum());
		bo.setChildNum(inVo.getChildNum());
		bo.setGoOffDate(inVo.getGoOffDate());
		bo.setGoOffTime(inVo.getGoOffTime());
		bo.setRemark(inVo.getRemark());
		
		HttpSession session = SessionUtil.createUserSession(request, response);
		Long userId = (Long) session.getAttribute(CommonConstants.SESSION_USER_ID);
		
		bo.setUserId(userId);
		
		result = customHodometerService.insertCustomHodometer(bo);
		if(result != null){
			responseBody = ResponseTypeEnum.getNewOkResponse();
		}
		//设置响应数据
		((Response)responseBody).setData(result);
		
		return JsonResponseDto.getResult(responseBody);
	}
	
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.PUT)
	public Map<String, Object> updateTravelPlan( com.amx.bizservice.model.vo.web.input.TravelPlanVo inVo , HttpServletRequest request){// 使用Bean接收参数
		Object response = ResponseTypeEnum.PARAMETER_ERROR;
		UpdateResponseBo result = null;
		/*封装参数*/
		CustomHodometerBo bo = new CustomHodometerBo();
		bo.setId(inVo.getId());
		//bo.setProductId(inVo.getProductId());
		bo.setPackageId(inVo.getPackageId());
		bo.setLanguageId(inVo.getLanguageId());
		bo.setAdultNum(inVo.getAdultNum());
		bo.setChildNum(inVo.getChildNum());
		bo.setGoOffDate(inVo.getGoOffDate());
		bo.setGoOffTime(inVo.getGoOffTime());
		bo.setRemark(inVo.getRemark());
		
		HttpSession session = request.getSession(false);
		Long userId = (Long) session.getAttribute(CommonConstants.SESSION_USER_ID);
		
		bo.setUserId(userId);
		
		result = customHodometerService.updateCustomHodometer(bo);
		if(result != null){
			response = ResponseTypeEnum.getNewOkResponse();
		}
		//设置响应数据
		((Response)response).setData(result);
		
		return JsonResponseDto.getResult(response);
	}
	
	
	/**
	 * 批量删除行程个i话项目
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.DELETE)
	public Map<String, Object> deleteCustomHodometer(HttpServletRequest request){
		Object response = ResponseTypeEnum.PARAMETER_ERROR;
		UpdateResponseBo result = null;
		List<Long> idList = new ArrayList<Long>();
		try{
			for(String idStr : request.getParameterValues("itemIdList[]")){
				idList.add(Long.parseLong(idStr));
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		//session：登录用户直接获取/未登录用户创建（未保存USERID，根据SessionID判断身份）
		HttpSession session = request.getSession(false);
		Long userId = (Long) session.getAttribute(CommonConstants.SESSION_USER_ID);
		result = customHodometerService.deleteCustomHodometer(userId, idList);
		if(result != null){
			response = ResponseTypeEnum.getNewOkResponse();
		}
		//设置响应数据
		((Response)response).setData(result);
		
		return JsonResponseDto.getResult(response);
	}
}
