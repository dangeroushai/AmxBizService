package com.amx.bizservice.controller.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amx.bizservice.config.CommonConfig;
import com.amx.bizservice.config.CommonConstants;
import com.amx.bizservice.controller.BaseController;
import com.amx.bizservice.enums.ProductTypeEnum;
import com.amx.bizservice.enums.ResponseTypeEnum;
import com.amx.bizservice.enums.ResponseTypeEnum.Response;
import com.amx.bizservice.model.bo.HodometerBo;
import com.amx.bizservice.model.bo.PackageBo;
import com.amx.bizservice.model.bo.PriceBo;
import com.amx.bizservice.model.bo.ProductBo;
import com.amx.bizservice.model.dto.JsonResponseDto;
import com.amx.bizservice.model.qo.ProductQuery;
import com.amx.bizservice.model.vo.PriceVo;
import com.amx.bizservice.model.vo.web.BuyConditionVo;
import com.amx.bizservice.model.vo.web.HodometerVo;
import com.amx.bizservice.model.vo.web.ProductVo;
import com.amx.bizservice.service.HodometerService;
import com.amx.bizservice.service.PackageService;
import com.amx.bizservice.service.ProductService;

@Controller
public class ProductController extends BaseController{
	
	@Autowired
	private ProductService productService;
	@Autowired
	private PackageService packageService;
	@Autowired
	private HodometerService hodometerService;
	
	/**
	 * 产品快照
	 * @param tid
	 * @param prductId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/productSnap/{typeId}/{prductId}")
	public Map<String, Object> getProductSnap(@PathVariable("typeId") int tid, @PathVariable("prductId") int prductId, HttpServletRequest request){
		Object response = ResponseTypeEnum.PARAMETER_ERROR; 
		ProductVo vo = null;
		
		HttpSession session = request.getSession(false);
		Long userId = null;
		if(session != null){
			userId = (Long) session.getAttribute(CommonConstants.SESSION_USER_ID);
		}
		ProductBo bo = productService.getProductSnap(prductId, userId);
		if(bo != null){
			response = ResponseTypeEnum.getNewOkResponse();
			//将Bo转为VO
			vo =  new ProductVo(bo);
			//设置响应数据
			((Response)response).setData(vo);
		}
		
		return JsonResponseDto.getResult(response);
	}
	
	/**
	 * 产品详情
	 * @param tid
	 * @param prductId
	 * @return
	 */
//	@ResponseBody //该注解会导致视图解析器失效
	@RequestMapping("/productInfo/{typeId}/{prductId}")
	public Map<String, Object> getProductDetail(@PathVariable("typeId") int tid, @PathVariable("prductId") int prductId, HttpServletRequest request){
		Object response = ResponseTypeEnum.PARAMETER_ERROR;
		ProductVo vo =  null;
		
		HttpSession session = request.getSession(false);
		Long userId = null;
		if(session != null){
			userId = (Long) session.getAttribute(CommonConstants.SESSION_USER_ID);
		}
		ProductBo bo = productService.getProductDetail(prductId, userId);
		if(bo != null ){
			response = ResponseTypeEnum.getNewOkResponse();
			//将Bo转为VO
			vo =  new ProductVo(bo);
			//行程产品
			/* 处理行程 */
			if( vo.getTypeId() == ProductTypeEnum.TRAVEL.getId()){
				Map<Integer, List<HodometerBo>> map = hodometerService.getHodometerByProductId(prductId);
				List<HodometerVo> hodometer = null;
				if(map != null){
					hodometer = new ArrayList<HodometerVo>(); 
					for(Map.Entry<Integer, List<HodometerBo>> entry : map.entrySet()){
						HodometerVo hvo = new HodometerVo();
						hvo.setDayOrder(entry.getKey() + 1);
						hvo.setDate("");
						
						List<ProductVo> items = new ArrayList<ProductVo>();
						// 根据行程项获取产品快照
						for(HodometerBo hbo : entry.getValue()){
							ProductBo itemProductBo = productService.getProductSnap(hbo.getItemProductId(), userId);
							ProductVo productVo = new ProductVo(itemProductBo);
							productVo.setStartTime(hbo.getGoOffTime());
							items.add(productVo);
						}
						//设置某天行程的具体项
						hvo.setItems(items);
						//将单天的行程安排加到产品行程
						hodometer.add(hvo);
					}
				}
				//设置产品的行程安排
				vo.setHodometer(hodometer );
			}
			//设置响应数据
			((Response)response).setData(vo);
		}
		
		return JsonResponseDto.getResult(response);
	}
	

	/**
	 * 产品列表
	 */
	@ResponseBody
	@RequestMapping("/productList/{pageIndex}/{themeId}/{sceneId}/{destId}/{keywords}/{lowPrice}/{highPrice}/{typeId}/{sortType}")
	public Map<String, Object> getProductList(@PathVariable("pageIndex") int pageIndex,   @PathVariable("themeId") int themeId, @PathVariable("sceneId") int sceneId, @PathVariable("destId") int destId, @PathVariable("keywords") String keywords, @PathVariable("lowPrice") int lowPrice, @PathVariable("highPrice") int highPrice, @PathVariable("typeId") int typeId, @PathVariable("sortType") int sortType, HttpServletRequest request){
		Object response = ResponseTypeEnum.PARAMETER_ERROR;
		//封装查询条件
		ProductQuery query = new ProductQuery();
		query.setDestinationId(destId == 0 ? null : destId);
		query.setEndPrice(highPrice == 0 ? null : highPrice);
		query.setStartPrice(lowPrice == 0 ? null : lowPrice);
		query.setSceneId(sceneId == 0 ? null : sceneId);
		query.setThemeId(themeId == 0 ? null : themeId);
		query.setTypeId(typeId == 0 ? null : typeId);
		//0 - 代表全部
		query.setKeyword(keywords.equals("0") ? null : keywords);
		query.setPageIndex(pageIndex);
		query.setPageSize(CommonConfig.WEB_PAGE_SIZE);
		
		//默认按主题排序
		query.setSortByTheme(true);
		
		List<ProductVo> voList =  null;
		
		HttpSession session = request.getSession(false);
		Long userId = null;
		if(session != null){
			userId = (Long) session.getAttribute(CommonConstants.SESSION_USER_ID);
		}
		
		List<ProductBo> boList = productService.getProductList(query, userId);
		if(boList != null ){
			response = ResponseTypeEnum.getNewOkResponse();
			voList = new ArrayList<ProductVo>();
			//将Bo转为VO
			for(ProductBo bo : boList){
				voList.add( new ProductVo(bo));
			}
			
			//设置响应数据
			((Response)response).setData(voList);
		}
		
		return JsonResponseDto.getResult(response);
	}
	
	/**
	 * 实时价格(与wap端共享该Api)
	 * @param tid
	 * @param prductId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = {"/price*/{typeId}/{prductId}/{languageId}/{packageId}/{adultNum}/{childNum}"})
//	@RequestMapping(value = {"/price*/{typeId}/{prductId}/{languageId}/{packageId}/{adultNum}/{childNum}/{udid}"})
	public Map<String, Object> getPrice(@PathVariable("typeId") int typeId, @PathVariable("prductId") long prductId, @PathVariable("languageId") int languageId, @PathVariable("packageId") long packageId, @PathVariable("adultNum") int adultNum, @PathVariable("childNum") int childNum/*, @PathVariable("udid") String udid*/){
		Object response = ResponseTypeEnum.PARAMETER_ERROR;
		PriceVo vo = null;
		String udid = null;
		PriceBo priceBo = packageService.getPrice(prductId, packageId, languageId, adultNum, childNum, udid);  
		if(priceBo != null){
			response = ResponseTypeEnum.getNewOkResponse();
			vo = new PriceVo(priceBo);
			
			//设置响应数据
			((Response)response).setData(vo);
		}
		
		return JsonResponseDto.getResult(response);
	}
	
	/**
	 * 获取套餐的购买条件（考虑废除该API）
	 * @param typeId
	 * @param prductId
	 * @param packageId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/buyCondition/{typeId}/{prductId}/{packageId}")
	public Map<String, Object> getBuyCondition(@PathVariable("typeId") int typeId, @PathVariable("prductId") long prductId, @PathVariable("packageId") long packageId){
		Object response = ResponseTypeEnum.PARAMETER_ERROR;
		BuyConditionVo vo = null;
		ProductBo prodkBo = productService.getProductDetail(prductId);
		PackageBo packBo = packageService.findOne(packageId);
		if(prodkBo != null && packBo != null ){
			response = ResponseTypeEnum.getNewOkResponse();
			vo = new BuyConditionVo(prodkBo, packBo);
			
			//设置响应数据
			((Response)response).setData(vo);
		}
		
		return JsonResponseDto.getResult(response);
	}
	
}
