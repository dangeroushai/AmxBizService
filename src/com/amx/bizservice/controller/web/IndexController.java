package com.amx.bizservice.controller.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amx.bizservice.controller.BaseController;
import com.amx.bizservice.enums.ArticleTypeEnum;
import com.amx.bizservice.enums.AttributeTypeEnum;
import com.amx.bizservice.enums.ResponseTypeEnum;
import com.amx.bizservice.enums.ResponseTypeEnum.Response;
import com.amx.bizservice.model.bo.AdvertisementBo;
import com.amx.bizservice.model.bo.ArticleBo;
import com.amx.bizservice.model.bo.AttributeBo;
import com.amx.bizservice.model.bo.ProductBo;
import com.amx.bizservice.model.bo.RegionBo;
import com.amx.bizservice.model.dto.JsonResponseDto;
import com.amx.bizservice.model.qo.ProductQuery;
import com.amx.bizservice.model.vo.web.AdvertisementByLineVo;
import com.amx.bizservice.model.vo.web.ArticleVo;
import com.amx.bizservice.model.vo.web.AttributeVo;
import com.amx.bizservice.model.vo.web.BannerVo;
import com.amx.bizservice.model.vo.web.CategoryHeaderVo;
import com.amx.bizservice.model.vo.web.CategoryHeaderVo.PriceRangeVo;
import com.amx.bizservice.model.vo.web.FooterVo;
import com.amx.bizservice.model.vo.web.ProductRecByAreaVo;
import com.amx.bizservice.model.vo.web.ProductRecBySceneVo;
import com.amx.bizservice.model.vo.web.ProductVo;
import com.amx.bizservice.model.vo.web.SloganVo;
import com.amx.bizservice.service.AdvertisementService;
import com.amx.bizservice.service.ArticleService;
import com.amx.bizservice.service.AttributeService;
import com.amx.bizservice.service.ProductService;
import com.amx.bizservice.service.RegionService;

/**
 * 首页控制器
 * @author DangerousHai
 *
 */
@Controller
public class IndexController extends BaseController{
	
	@Autowired
	private AdvertisementService advertisementService;
	@Autowired
	private AttributeService attributeService;
	@Autowired
	private ProductService productService;
	@Autowired
	private RegionService regionService;
	@Autowired
	private ArticleService articleService;
	

	@ResponseBody
	@RequestMapping(value = "/banner")
	public Map<String, Object> getBanner(){
		Object response = ResponseTypeEnum.PARAMETER_ERROR;
		List<BannerVo> voList = null;
		List<AdvertisementBo> boList = advertisementService.getWebBanner();
		if(boList != null && boList.size() != 0){
			response = ResponseTypeEnum.getNewOkResponse();
			voList = new ArrayList<BannerVo>(); 
			for(AdvertisementBo abo : boList){
				ProductBo productSnap = productService.getProductSnap(abo.getLinkProductId());
				voList.add(new BannerVo(abo,productSnap));
			}
			((Response)response).setData(voList);
		}
		
		return JsonResponseDto.getResult(response);
	}
	
	@ResponseBody
	@RequestMapping(value = "/slogan")
	public Map<String, Object> getSlogan(){
		Object response = ResponseTypeEnum.PARAMETER_ERROR;
		SloganVo vo = null;
		AdvertisementBo bo = advertisementService.getSlogan();
		if(bo != null){
			response = ResponseTypeEnum.getNewOkResponse();
			vo = new SloganVo(bo);
			((Response)response).setData(vo);
		}
		
		return JsonResponseDto.getResult(response);
	}
	
	@ResponseBody
	@RequestMapping(value = "/theme")
	public Map<String, Object> getTheme(){
		Object response = ResponseTypeEnum.PARAMETER_ERROR;
		List<AttributeVo> voList = null;
		List<AdvertisementBo> boList = advertisementService.getTheme();
		if(boList != null && boList.size() != 0){
			response = ResponseTypeEnum.getNewOkResponse();
			voList = new ArrayList<AttributeVo>(); 
			for(AdvertisementBo abo : boList){
				AttributeBo attrBo = attributeService.findOne(abo.getLinkAttributeId());
				voList.add(new AttributeVo(abo,attrBo));
			}
			((Response)response).setData(voList);
		}
		
		return JsonResponseDto.getResult(response);
	}
	
	@ResponseBody
	@RequestMapping(value = "/articlesRecList")
	public Map<String, Object> getArticleRecList(){
		Object response = ResponseTypeEnum.PARAMETER_ERROR;
		List<ArticleVo> voList = null; 
		List<AdvertisementBo> boList = advertisementService.getRecArticles();
		if(boList != null && boList.size() != 0){
			response = ResponseTypeEnum.getNewOkResponse();
			voList = new ArrayList<ArticleVo>(); 
			for(AdvertisementBo abo : boList){
				//查找关联的文章快照
				ArticleBo rbo = articleService.getArticleSnap(abo.getLinkArticleId());
				
				voList.add(new ArticleVo(abo,rbo));
			}
			((Response)response).setData(voList);
		}
		
		return JsonResponseDto.getResult(response);
	}
	
	@ResponseBody
	@RequestMapping(value = "/productRecListByArea")
	public Map<String, Object> getProductRecListByArea(){
		Object response = ResponseTypeEnum.PARAMETER_ERROR;
		List<ProductRecByAreaVo> voList = null; 
		List<AdvertisementBo> boList = advertisementService.getProductRecByArea();
		if(boList != null && boList.size() != 0){
			response = ResponseTypeEnum.getNewOkResponse();
			voList = new ArrayList<ProductRecByAreaVo>(); 
			for(AdvertisementBo abo : boList){
				//查找关联的地域
				RegionBo rbo = regionService.getRegion(abo.getLinkRegionId()); 
				ProductRecByAreaVo praVo = new ProductRecByAreaVo(abo,rbo);
				/*设置地域下的推荐产品信息*/
				List<AdvertisementBo> aboSubList = abo.getSubAdvertisementList();
				praVo.setProducts(new ArrayList<ProductVo>());
				if(aboSubList != null && aboSubList.size() != 0){
					for(AdvertisementBo product_abo : aboSubList){
						//根据广告查找链接的产品
						if(product_abo.getLinkProductId() != null){
							ProductVo Product = new ProductVo(productService.getProductSnap(product_abo.getLinkProductId()));
							praVo.getProducts().add(Product);
						}
					}
				}
				//每个地区的推荐产品数
				int productNumPerScene = 4;
				//每个地区的备用产品数
				int productNumObligatePerScene = 6;
				//手动设置推荐数少于设置的个数，则自动填充符合条件的产品
				if(praVo.getProducts().size() < productNumPerScene){
					//差值
					int diffNum = productNumPerScene - praVo.getProducts().size();
					ProductQuery query = new ProductQuery();
					query.setDestinationId(abo.getLinkRegionId());
					query.setSortByDest(true);
					query.setPageIndex(0);
					query.setPageSize(diffNum + productNumObligatePerScene);
					List<ProductBo> recProductList = productService.getProductList(query);
					if(recProductList != null){
						for (ProductBo recProductBo : recProductList) {
							ProductVo recVo =  new ProductVo(recProductBo);
							if(praVo.getProducts().size() < productNumPerScene){
								//防止重复推荐
								if(!praVo.getProducts().contains(recVo)){
									praVo.getProducts().add(recVo);
								}
							}else{
								break;
							}
						}
					}
				}
				voList.add(praVo);
			}
			((Response)response).setData(voList);
		}
		
		return JsonResponseDto.getResult(response);
	}
	
	@ResponseBody
	@RequestMapping(value = "/productRecListByScene")
	public Map<String, Object> getProductRecListByScene(){
		Object response = ResponseTypeEnum.PARAMETER_ERROR;
		List<ProductRecBySceneVo> voList = null; 
		List<AdvertisementBo> boList = advertisementService.getProductRecByScene();
		if(boList != null && boList.size() != 0){
			response = ResponseTypeEnum.getNewOkResponse();
			voList = new ArrayList<ProductRecBySceneVo>(); 
			for(AdvertisementBo abo : boList){
				//查找关联的属性
				AttributeBo attrbo = attributeService.findOne(abo.getLinkAttributeId());
				ProductRecBySceneVo praVo = new ProductRecBySceneVo(abo,attrbo);
				/*设置场景下的推荐产品信息*/
				praVo.setProducts(new ArrayList<ProductVo>());
				List<AdvertisementBo> aboSubList = abo.getSubAdvertisementList();
				if(aboSubList != null && aboSubList.size() != 0){
					for(AdvertisementBo product_abo : aboSubList){
						//根据广告查找链接的产品
						if(product_abo.getLinkProductId() != null){
							ProductVo Product = new ProductVo(productService.getProductSnap(product_abo.getLinkProductId()));
							praVo.getProducts().add(Product);
						}
					}
				}
				//每个场景的推荐产品数
				int productNumPerScene = 12;
				//每个场景的备用产品数
				int productNumObligatePerScene = 8;
				//手动设置推荐数少于设置的个数，则自动填充符合条件的产品
				if(praVo.getProducts().size() < productNumPerScene){
					//差值
					int diffNum = productNumPerScene - praVo.getProducts().size();
					ProductQuery query = new ProductQuery();
					query.setSceneId(attrbo.getId());
					query.setSortByAttr(true);
					query.setPageIndex(0);
					query.setPageSize(diffNum + productNumObligatePerScene);
					List<ProductBo> recProductList = productService.getProductList(query);
					if(recProductList != null){
						for (ProductBo recProductBo : recProductList) {
							ProductVo recVo =  new ProductVo(recProductBo);
							if(praVo.getProducts().size() < productNumPerScene){
								//防止重复推荐
								if(!praVo.getProducts().contains(recVo)){
									praVo.getProducts().add(recVo);
								}
							}else{
								break;
							}
						}
					}
				}
				voList.add(praVo);
			}
			((Response)response).setData(voList);
		}
		
		return JsonResponseDto.getResult(response);
	}
	
	@ResponseBody
	@RequestMapping(value = "/advertisementListByLine")
	public Map<String, Object> getAdvertisementListByLine(){
		Object response = ResponseTypeEnum.PARAMETER_ERROR;
		List<AdvertisementByLineVo> voList = null; 
		List<AdvertisementBo> boList = advertisementService.getAdvertisementByLine();
		if(boList != null && boList.size() != 0){
			response = ResponseTypeEnum.getNewOkResponse();
			voList = new ArrayList<AdvertisementByLineVo>(); 
			for(AdvertisementBo abo : boList){
				AdvertisementByLineVo praVo = new AdvertisementByLineVo(abo);
				/*设置线路下的广告信息*/
				List<AdvertisementBo> aboSubList = abo.getSubAdvertisementList();
				if(aboSubList != null && aboSubList.size() != 0){
					praVo.setAdvertisements(new ArrayList<AdvertisementByLineVo.AdvertisementVo>());
					//每显示广告数量
					int pageCapacity = 6;
					if(aboSubList.size() > pageCapacity){
						aboSubList = aboSubList.subList(0, pageCapacity);
					}
					for(AdvertisementBo adbo : aboSubList){
						//设置子广告列表项
						AdvertisementByLineVo.AdvertisementVo avo = praVo.new AdvertisementVo(adbo);
						praVo.getAdvertisements().add(avo);
					}
				}
				voList.add(praVo);
			}
			((Response)response).setData(voList);
		}
		
		return JsonResponseDto.getResult(response);
	}
	
	@ResponseBody
	@RequestMapping(value = "/categoryHeader")
	public Map<String, Object> getCategoryHeader(){
		Object response = ResponseTypeEnum.getNewOkResponse();
		CategoryHeaderVo vo = null;
		//随机取一个广告
		AdvertisementBo oneProductListPageHeadPicAd = advertisementService.getRandomOneProductListPageHeadPicAd();
		//查询所有主题
		List<AttributeBo> themeList = attributeService.findAllByTypeId(AttributeTypeEnum.THEME.getId());
		//查询所有场景
		List<AttributeBo> sceneList = attributeService.findAllByTypeId(AttributeTypeEnum.SCENE.getId());
		//查询所有目的地(只查找3级)
		List<RegionBo> regionList = regionService.findAll(3);
		
		vo = new CategoryHeaderVo(oneProductListPageHeadPicAd,themeList,sceneList,regionList);
		
		/* 将广告处理为价格区间 */	
		List<AdvertisementBo> priceRegion = advertisementService.getPriceRegion();
		if(priceRegion != null){
			ArrayList<PriceRangeVo> priceRangeList = new ArrayList<PriceRangeVo>();
			List<Integer> priceList = new ArrayList<Integer>();
			for (AdvertisementBo advertisementBo : priceRegion) {
				priceList.add(Integer.parseInt(advertisementBo.getTitle()));
			}
			//对价格排序
			Collections.sort(priceList);
			for(int i = 0 ; i< priceList.size() ; i++){
				String lowPrice = priceList.get(i).toString();
				String highPrice;
				if(i< priceList.size() - 1){
					highPrice = ((Integer)(priceList.get(i + 1) - 1)).toString();
				}else{
					highPrice = "";
				}
				priceRangeList.add(vo.new PriceRangeVo(lowPrice,highPrice));
			}
			vo.setPriceRangeList(priceRangeList);
		}
		
		((Response)response).setData(vo);
		
		return JsonResponseDto.getResult(response);
	}
	
	@ResponseBody
	@RequestMapping(value = "/footer")
	public Map<String, Object> getFooter(){
		Object response = ResponseTypeEnum.getNewOkResponse();
		List<FooterVo> voList = new ArrayList<FooterVo>(3);
		
		ArticleTypeEnum articleTypeEnum = ArticleTypeEnum.ABOUT_US; 
		List<ArticleBo> aboutUsList = articleService.getArticlesByAttrId(articleTypeEnum.getAttrId());
		voList.add(new FooterVo(articleTypeEnum.getName(),aboutUsList));
		
		articleTypeEnum = ArticleTypeEnum.HELP_CENTER; 
		List<ArticleBo> helpCenterList = articleService.getArticlesByAttrId(articleTypeEnum.getAttrId());
		voList.add(new FooterVo(articleTypeEnum.getName(),helpCenterList));
		
		articleTypeEnum = ArticleTypeEnum.WEB_POLICY; 
		List<ArticleBo> webPolicyList = articleService.getArticlesByAttrId(articleTypeEnum.getAttrId());
		voList.add(new FooterVo(articleTypeEnum.getName(),webPolicyList));
		
		((Response)response).setData(voList);
		
		return JsonResponseDto.getResult(response);
	}
}
