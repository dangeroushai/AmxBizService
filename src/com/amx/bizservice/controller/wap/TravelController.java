package com.amx.bizservice.controller.wap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amx.bizservice.annotation.Authentication;
import com.amx.bizservice.config.CommonConfig;
import com.amx.bizservice.config.CommonConstants;
import com.amx.bizservice.controller.BaseController;
import com.amx.bizservice.enums.AccessLevelEnum;
import com.amx.bizservice.enums.ResponseTypeEnum;
import com.amx.bizservice.enums.ResponseTypeEnum.Response;
import com.amx.bizservice.model.bo.CustomHodometerBo;
import com.amx.bizservice.model.bo.LanguageBo;
import com.amx.bizservice.model.bo.PackageBo;
import com.amx.bizservice.model.bo.ProductBo;
import com.amx.bizservice.model.bo.TravelBo;
import com.amx.bizservice.model.dto.JsonResponseDto;
import com.amx.bizservice.model.dto.PageResponseDto;
import com.amx.bizservice.model.qo.PageQuery;
import com.amx.bizservice.model.vo.wap.HodometerVo;
import com.amx.bizservice.model.vo.wap.HodometerVo.ItemVo;
import com.amx.bizservice.model.vo.wap.TravelListVo;
import com.amx.bizservice.model.vo.wap.TravelVo;
import com.amx.bizservice.service.CustomHodometerService;
import com.amx.bizservice.service.LanguageService;
import com.amx.bizservice.service.PackageService;
import com.amx.bizservice.service.ProductService;
import com.amx.bizservice.service.TravelService;
import com.amx.bizservice.util.StringUtil;

/**
 * 我的行程控制器
 * @author DangerousHai
 *
 */
@Authentication(accessLevel = AccessLevelEnum.PROTECTED)
@RequestMapping("/travels")
@Controller("mTravelController")
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
	@RequestMapping( method = RequestMethod.GET)
	public Map<String, Object> getTravelList(@RequestParam(name = "pageindex",defaultValue = "0") int pageIndex,@RequestParam(name = "pagesize",defaultValue = "" + CommonConfig.WAP_PAGE_SIZE) int pageSize, HttpServletRequest request){
		Object  response  = ResponseTypeEnum.PARAMETER_ERROR; 
		TravelListVo vo = null;
		if(pageSize <= 0){
			pageSize = CommonConfig.WAP_PAGE_SIZE;
		}
		
		Long userId = (Long) request.getAttribute(CommonConstants.SESSION_USER_ID);
		
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
					
					List<CustomHodometerBo> customHodometerList = customHodometerService.getCustomHodometerListByTravelId(tbo.getId());
					
					travelList.add(vo.new TravelVo(tbo, prodbo,customHodometerList));
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
	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Map<String, Object> getTravel(@PathVariable("id") long travelId, HttpServletRequest request){
		Object  response  = ResponseTypeEnum.PARAMETER_ERROR; 
		TravelVo travelvo = null;
		
		long userId = (Long) request.getAttribute(CommonConstants.SESSION_USER_ID);
		
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
				travelvo.setHodometer(new ArrayList<HodometerVo>());
				//当前日期
				Date currDate = null;
				//当前日期-行程
				HodometerVo hodometervo = null;
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
						hodometervo = new HodometerVo(); 
						hodometervo.setDayOrder(++dayOrder);
						hodometervo.setDate(currDate);
						hodometervo.setItems(new ArrayList<HodometerVo.ItemVo>());
						
						travelvo.getHodometer().add(hodometervo);
						
					}
					
					/* 处理与行程每项活动相关的信息  */
					{
						ProductBo prodbo = productService.getProductSnap(chBo.getProductId(), userId);
						PackageBo packbo = packageService.findOne(chBo.getPackageId());
						LanguageBo langbo = languageService.findOne(chBo.getLanguageId());
						ItemVo itemVo = hodometervo.new ItemVo(chBo, prodbo,packbo,langbo);
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

}
