package com.amx.bizservice.controller.wap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amx.bizservice.config.CommonConfig;
import com.amx.bizservice.config.CommonConstants;
import com.amx.bizservice.controller.BaseController;
import com.amx.bizservice.enums.AttributeTypeEnum;
import com.amx.bizservice.enums.ProductTypeEnum;
import com.amx.bizservice.enums.ResponseTypeEnum;
import com.amx.bizservice.enums.ResponseTypeEnum.Response;
import com.amx.bizservice.model.bo.AdvertisementBo;
import com.amx.bizservice.model.bo.ArticleBo;
import com.amx.bizservice.model.bo.AttributeBo;
import com.amx.bizservice.model.bo.ProductBo;
import com.amx.bizservice.model.bo.RegionBo;
import com.amx.bizservice.model.bo.RequirementBo;
import com.amx.bizservice.model.bo.UpdateResponseBo;
import com.amx.bizservice.model.dto.JsonResponseDto;
import com.amx.bizservice.model.qo.ProductQuery;
import com.amx.bizservice.model.vo.wap.ArticleVo;
import com.amx.bizservice.model.vo.wap.DestinationVo;
import com.amx.bizservice.model.vo.wap.FilterVo;
import com.amx.bizservice.model.vo.wap.HomePageVo;
import com.amx.bizservice.service.AdvertisementService;
import com.amx.bizservice.service.ArticleService;
import com.amx.bizservice.service.AttributeService;
import com.amx.bizservice.service.ProductService;
import com.amx.bizservice.service.RegionService;
import com.amx.bizservice.service.RequirementService;

@Controller("mIndexController")
public class IndexController extends BaseController{
	
	@Autowired
	private ProductService productService;
	@Autowired
	private AdvertisementService advertisementService;
	@Autowired
	private RequirementService requirementService;
	@Autowired
	private RegionService regionService;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private AttributeService attributeService;
	
	
	@ResponseBody
	@RequestMapping("/hotWords")
	public Map<String, Object> getHotWords(){
		Object  response  = ResponseTypeEnum.getNewOkResponse();
		
		((Response)response).setData(advertisementService.getHotWords());
		
		return JsonResponseDto.getResult(response);
	}
	
	
	@ResponseBody
	@RequestMapping("/homePage")
	public Map<String, Object> getHomePage(){
		Object  response  = ResponseTypeEnum.getNewOkResponse();
		
		HomePageVo vo = null;
		
		List<AdvertisementBo> bannerList = advertisementService.getWapBanner();
		//banner 涉及的产品
		List<ProductBo> bannerRealList = null;
		if(bannerList != null){
			bannerRealList = new ArrayList<ProductBo>(bannerList.size());
			for (AdvertisementBo adbo : bannerList) {
				bannerRealList.add(productService.getProductSnap(adbo.getLinkProductId()));
			}
		}
		
		//热门地区
		List<AdvertisementBo> hotDestinations = advertisementService.getHotDestinations();
		//推荐地区：固定大小3
		List<AdvertisementBo> destRecList = advertisementService.getRecDestinations();
		
		ProductQuery query = new ProductQuery();
		query.setPageIndex(0);
		//固定大小5
		query.setPageSize(5);
		query.setTypeId(ProductTypeEnum.HOUR.getId());
		List<ProductBo> hourRecList = productService.getProductList(query);
		query.setTypeId(ProductTypeEnum.TRAVEL.getId());
		List<ProductBo> lineRecList = productService.getProductList(query);
		
		List<ArticleBo> articlesRecList = null;
		List<AdvertisementBo> boList = advertisementService.getRecArticles();
		if(boList != null && boList.size() != 0){
			articlesRecList = new ArrayList<ArticleBo>();
			for(AdvertisementBo abo : boList){
				//查找关联的文章快照
				ArticleBo rbo = articleService.getArticleSnap(abo.getLinkArticleId());
				
				articlesRecList.add(rbo);
			}
		}
		
		vo = new HomePageVo(bannerList,bannerRealList,hotDestinations,destRecList,hourRecList,lineRecList,articlesRecList);
		
		((Response)response).setData(vo);
		
		return JsonResponseDto.getResult(response);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/requirements", method = RequestMethod.POST)
	public Map<String, Object> insertRequirement(@RequestBody com.amx.bizservice.model.vo.wap.input.RequirementVo inVo,HttpServletRequest request){
		Object  response  = ResponseTypeEnum.PARAMETER_ERROR;
		
		/*封装参数*/
		RequirementBo bo = new RequirementBo();
		BeanUtils.copyProperties(inVo, bo);
		//登录用户设置ID，未登录用户ID为null
		bo.setUserId((Long) request.getAttribute(CommonConstants.SESSION_USER_ID));
		
		UpdateResponseBo result = requirementService.insertRequirement(bo);
		
		if(result != null){
			response = ResponseTypeEnum.getNewOkResponse();
			//设置响应数据
			((Response)response).setData(result);
		}
		
		return JsonResponseDto.getResult(response);
	}
	
	@ResponseBody
	@RequestMapping("/destinations")
	public Map<String, Object> getDestinations(@RequestParam("pageindex") int pageIndex, @RequestParam("pagesize") int pageSize){
		Object  response  = ResponseTypeEnum.getNewOkResponse();

		List<DestinationVo> result = new ArrayList<DestinationVo>();
		//一次返回所有数据，不分页
		if(pageIndex == 0 ){
			/* 提取所有国家 */
			List<RegionBo> regions = regionService.findAll(2);
			if(regions != null){
				for (RegionBo regionBo : regions) {
					for (RegionBo countryRegionBo : regionBo.getSubRegionList()) {
						result.add(new DestinationVo(countryRegionBo));
					}
				}
			}
		}
		//设置响应数据
		((Response)response).setData(result);
		
		return JsonResponseDto.getResult(response);
	}
	
	@ResponseBody
	@RequestMapping("/filter/{destId}")
	public Map<String, Object> getFilter(@PathVariable("destId") int destId ){
		Object  response  = ResponseTypeEnum.PARAMETER_ERROR;
		FilterVo vo =  null;
		
		RegionBo destBo = regionService.getRegion(destId);
		
		if(destBo != null ){
			response = ResponseTypeEnum.getNewOkResponse();
			//将Bo转为VO
			vo =  new FilterVo(destBo);
			List<Integer> attrIdList = productService.getAllAttrIdsByDestId(destId);
			if(attrIdList != null){
				List<AttributeBo> attrList = attributeService.findAllByIdList(attrIdList);
				if(attrList != null){
					for (AttributeBo attributeBo : attrList) {
						//非顶级属性
						if(!attributeBo.getParentId().equals(AttributeTypeEnum.TOP_LEVEL.getId())){
							com.amx.bizservice.model.vo.wap.FilterVo.AttributeVo attrVo = vo.new AttributeVo(attributeBo);
							//主题属性
							if(attributeBo.getTypeId().equals(AttributeTypeEnum.THEME.getId())){
								vo.getThemeList().add(attrVo );
							}else if(attributeBo.getTypeId().equals(AttributeTypeEnum.SCENE.getId())){//场景属性
								vo.getSceneList().add(attrVo );
							}else{
								attrVo = null;
							}
						}
					}
				}
			}
			//设置响应数据
			((Response)response).setData(vo);
		}
		
		return JsonResponseDto.getResult(response);
	}
	
	@ResponseBody
	@RequestMapping("/articles/aboutus")
	public Map<String, Object> getAboutUS(){
		Object  response  = ResponseTypeEnum.getNewOkResponse();
		
		ArticleBo aboutUs = articleService.getArticleDetail(CommonConfig.ABOUT_US_ARTICLE_ID);
		
		((Response)response).setData(new ArticleVo(aboutUs));
		return JsonResponseDto.getResult(response);
	}
}
