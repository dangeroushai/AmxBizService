package com.amx.bizservice.controller.wap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.amx.bizservice.config.CommonConfig;
import com.amx.bizservice.controller.BaseController;
import com.amx.bizservice.enums.ProductTypeEnum;
import com.amx.bizservice.enums.ResponseTypeEnum;
import com.amx.bizservice.enums.ResponseTypeEnum.Response;
import com.amx.bizservice.model.bo.HodometerBo;
import com.amx.bizservice.model.bo.ProductBo;
import com.amx.bizservice.model.dto.JsonResponseDto;
import com.amx.bizservice.model.qo.ProductQuery;
import com.amx.bizservice.model.vo.wap.HodometerVo;
import com.amx.bizservice.model.vo.wap.ProductListVo;
import com.amx.bizservice.model.vo.wap.ProductVo;
import com.amx.bizservice.service.HodometerService;
import com.amx.bizservice.service.ProductService;
import com.amx.bizservice.util.StringUtil;

@Controller("mProductController")
@RequestMapping("/products")
public class ProductController extends BaseController{
	
	@Autowired
	private ProductService productService;
	@Autowired
	private HodometerService hodometerService;
	
/*	public String index(){
		logger.debug("this is a test log");
		return System.getProperty("amxDataService.root");
	}*/
	
	
	@RequestMapping()
	public Map<String, Object> getProducts(
			@RequestParam(value = "pageindex",required = false,defaultValue = "0") int pageIndex,
			@RequestParam(value = "pagesize",required = false,defaultValue = "" + CommonConfig.WAP_PAGE_SIZE) int pageSize,
			@RequestParam(value = "themeid",required = false) Integer themeId,
			@RequestParam(value = "sceneid",required = false) Integer sceneId,
			@RequestParam(value = "destid",required = false) Integer destId,
			@RequestParam(value = "keyword",required = false) String keyword,
			@RequestParam(value = "lowprice",required = false) Integer lowPrice,
			@RequestParam(value = "highprice",required = false) Integer highPrice,
			@RequestParam(value = "typeid",required = false) String typeIdsStr){

		Object  response  = ResponseTypeEnum.getNewOkResponse();
		if(pageSize <= 0){
			pageSize = CommonConfig.WAP_PAGE_SIZE;
		}
		ProductListVo vo = null;
		
		//封装查询条件
		ProductQuery query = new ProductQuery();
		query.setDestinationId(destId);
		query.setEndPrice(highPrice);
		query.setStartPrice(lowPrice);
		query.setSceneId(sceneId);
		query.setThemeId(themeId );
		query.setKeyword(keyword);
		query.setPageIndex(pageIndex);
		query.setPageSize(CommonConfig.WAP_PAGE_SIZE);
		
		List<ProductBo> productList = null;
		//根据产品类型查询
		if(!StringUtil.isEmpty(typeIdsStr)){
			String[] typeIdArr = typeIdsStr.split(",");
			//遍历多个类型分别查询
			for (int i = 0; i < typeIdArr.length; i++) {
				query.setTypeId(Integer.parseInt(typeIdArr[i]));
				
				List<ProductBo> boList = productService.getProductList(query);
				if(boList != null && boList.size() != 0){
					if(productList == null){
						productList = new ArrayList<ProductBo>();
					}
					productList.addAll(boList);
				}
			}
		}else{
			productList = productService.getProductList(query, null);
		}
		
		vo = new ProductListVo(productList);
		vo.setPageIndex(pageIndex);
		
		//设置响应数据
		((Response)response).setData(vo);
			
		return JsonResponseDto.getResult(response);
	}
	
	
	@RequestMapping("/{typeId}/{prductId}")
	public Map<String, Object> getProduct(@PathVariable("typeId") int tid, @PathVariable("prductId") int prductId){
		Object  response  = ResponseTypeEnum.PARAMETER_ERROR;
		ProductVo vo = null;
		ProductBo bo = productService.getProductDetail(prductId);
		if(bo != null ){
			response = ResponseTypeEnum.getNewOkResponse();
			//将Bo转为VO
			vo = new ProductVo(bo);
			
			((Response)response).setData(vo);
		}
		
		return JsonResponseDto.getResult(response);
	}
	
	
	
	@RequestMapping("/hodometer/{typeId}/{prductId}")
	public Map<String, Object> getHodometer(@PathVariable("typeId") int typeId, @PathVariable("prductId") int prductId){
		Object  response  = ResponseTypeEnum.PARAMETER_ERROR;
		List<HodometerVo> hodometer = null;
		
		if( ProductTypeEnum.TRAVEL.getId().equals(typeId)){
			Map<Integer, List<HodometerBo>> map = hodometerService.getHodometerByProductId(prductId);
			if(map != null){
				response = ResponseTypeEnum.getNewOkResponse();
				
				hodometer = new ArrayList<HodometerVo>(); 
				for(Map.Entry<Integer, List<HodometerBo>> entry : map.entrySet()){
					HodometerVo hvo = new HodometerVo();
					hvo.setDayOrder(entry.getKey() + 1);
					//hvo.setDate(date);
					
					List<HodometerVo.ItemVo> items = new ArrayList<HodometerVo.ItemVo>();
					// 根据行程项获取产品快照
					for(HodometerBo hbo : entry.getValue()){
						ProductBo itemProductBo = productService.getProductSnap(hbo.getItemProductId());
						items.add(hvo.new ItemVo(hbo,itemProductBo));
					}
					//设置某天行程的具体项
					hvo.setItems(items);
					//将单天的行程安排加到产品行程
					hodometer.add(hvo);
				}
				((Response)response).setData(hodometer);
			}
		}
		//设置响应数据
		
		return JsonResponseDto.getResult(response);
	}
}
