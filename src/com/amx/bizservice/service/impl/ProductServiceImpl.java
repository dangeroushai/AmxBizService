package com.amx.bizservice.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amx.bizservice.config.CommonConfig;
import com.amx.bizservice.enums.ProductTypeEnum;
import com.amx.bizservice.model.bo.PackageBo;
import com.amx.bizservice.model.bo.ProductBo;
import com.amx.bizservice.model.dto.PageResponseDto;
import com.amx.bizservice.model.qo.ProductQuery;
import com.amx.bizservice.service.AttributeService;
import com.amx.bizservice.service.FavoriteService;
import com.amx.bizservice.service.LanguageService;
import com.amx.bizservice.service.PackageService;
import com.amx.bizservice.service.ProductService;
import com.amx.bizservice.service.RegionService;
import com.amx.bizservice.thrift.Response;
import com.amx.bizservice.util.JsonUtil;
import com.amx.bizservice.util.LogUtil;
import com.amx.bizservice.util.PictureUtil;
import com.amx.bizservice.util.StringUtil;
import com.fasterxml.jackson.core.type.TypeReference;

@Service("productService")
public class ProductServiceImpl extends BaseService implements ProductService {

	@Autowired
	private PackageService packageService;
	@Autowired
	private LanguageService languageService;
	@Autowired
	private AttributeService attributeService;
	@Autowired
	private RegionService regionService;
	@Autowired
	private FavoriteService favoriteService;

	public ProductServiceImpl() {
		this.ServiceName = "Product";
	}
	
	@Override
	public ProductBo getProductDetail(long id) {
		return getProductDetail(id,null);
	}
	@Override
	public ProductBo getProductDetail(long pid, Long userId) {
		ProductBo productBo = this.getProductSnap(pid, userId);
		
		if(productBo != null){
			/*详情图片URL*/
			Map<String, String> pictureMap = new LinkedHashMap<String,String>();
			for(String picPath : productBo.getPictureMap().keySet()){
				//pictureMap.put(PictureUtil.getPicUrl(picPath),null);
				pictureMap.put(picPath,null);
			}
			productBo.setPictureMap(pictureMap);
			/*计算起售日期*/
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf = StringUtil.sdf_yyyy_MM_dd;
			calendar.add(Calendar.DATE, productBo.getPresaleDelay());
			productBo.setBaseDate(sdf.format(calendar.getTime()));
			/*根据服务语言ID获取详细语言信息*/
			productBo.setLanguageList(languageService.findAllByIdList(productBo.getLanguageIdList()));
			/*根据套餐ID获取详细信息*/
			productBo.setPackageList(packageService.findAllByProductId(pid));
			
			inflationRecommendProducts(productBo);
		}
		
		return productBo;
	}

	@Override
	public ProductBo getProductSnap(long id) {
		return getProductSnap(id,null);
	}

	@Override
	public ProductBo getProductSnap(long pid, Long userId) {
		ProductBo productBo = this.findOne(pid);
		if(productBo != null){
			this.inflation(productBo, true, userId);
		}
		
		return productBo;
	}

	@Override
	public List<ProductBo> getProductList(List<Long> idList) {
		List<ProductBo> productBoList = null;
		Response response = thriftClient.serviceInvoke(this.ServiceName, "findAllByIdList", idList);
		
		try {
			if (response != null) {
				if (response.isState()) {
					productBoList = JsonUtil.mapper.readValue(response.getData(),new TypeReference<List<ProductBo>>() {});
					if(productBoList != null){
						for (ProductBo productBo : productBoList) {
							reduce(productBo);
							inflation(productBo, false, null);
						}
					}
				} else {
					LogUtil.recordWarnLog(response.getMsg());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return productBoList;
	}
	
	@Override
	public List<ProductBo> getProductList(ProductQuery query) {
		return getProductList(query,null);
	}
	@Override
	public List<ProductBo> getProductList(ProductQuery query, Long userId) {
		Response response = thriftClient.serviceInvoke(this.ServiceName, "findAllByQuery", query);
		PageResponseDto<ProductBo> page = null;
		List<ProductBo> content = null;
		
		boolean isFullSnap = false;
		if(query.getTypeId() != null && query.getTypeId().equals(ProductTypeEnum.TRAVEL.getId())){
			isFullSnap = true;
		}
		
		try {
			if (response != null) {
				if (response.isState()) {
					page = JsonUtil.mapper.readValue(response.getData(),new TypeReference<PageResponseDto<ProductBo>>() {});
					content = page.getContent();
					if(content != null){
						for (ProductBo productBo : content) {
							reduce(productBo);
							inflation(productBo ,isFullSnap, userId);
						}
					}
				} else {
					LogUtil.recordWarnLog(response.getMsg());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return content;
	}
	
	@Override
	public List<Integer> getAllAttrIdsByDestId(int destId) {
		Response response = thriftClient.serviceInvoke(this.ServiceName, "getAllAttrIdsByDestId", destId);
		List<Integer> attrIdList = null;
		try {
			if (response != null) {
				if (response.isState()) {
					attrIdList = JsonUtil.mapper.readValue(response.getData(),JsonUtil.getCollectionType(ArrayList.class, Integer.class));
				} else {
					LogUtil.recordWarnLog(response.getMsg());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return attrIdList;
	}
	
	/**
	 * 削减不需要再非详情场景使用的属性，减少传输量
	 * @param productBo
	 */
	private ProductBo reduce(ProductBo productBo) {
		if(productBo != null){
			productBo.setPictureMap(null);
			productBo.setDescription(null);
			productBo.setRefundRule(null);
			productBo.setBookRule(null);
			productBo.setFeeDes(null);
			productBo.setChildRule(null);
			productBo.setLongitude(null);
			productBo.setLatitude(null);
			productBo.setAddress(null);
			productBo.setSaleRuleList(null);
			productBo.setTimeRuleList(null);
			productBo.setExceptionDateList(null);
		}
		
		return productBo;
	}

	/**
	 * 调用数据服务，获取基础产品业务对象
	 * @param pid
	 * @return
	 */
	private ProductBo findOne(long pid) {
		Response response = thriftClient.serviceInvoke(this.ServiceName, "findOne", pid);
		ProductBo productBo = null;
		try {
			if (response != null) {
				if (response.isState()) {
					productBo = JsonUtil.mapper.readValue(response.getData(),ProductBo.class);
				} else {
					LogUtil.recordWarnLog(response.getMsg());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return productBo;
	}
	
	/**
	 * 填充非数据库属性
	 * @param productBo
	 * @param isFullSnap 在产品列表显示时传false
	 * @param userId 为null表示未登录用户调用
	 */
	private void inflation(ProductBo productBo , boolean isFullSnap, Long userId) {
		// 封面图
		productBo.setCoverPic(PictureUtil.getPicUrl(productBo.getCoverPic()));
		// 产品摘要
		productBo.setIntroduceHtml(this.getIntroHtml(productBo.getIntroduceList()));
		// 出发地名称
		String startCity = regionService.getCityName(productBo.getStartCityIdList());
		productBo.setStartCity(startCity == null ? "" : startCity);
		//语言
		productBo.setLanguageList(languageService.findAllByIdList(productBo.getLanguageIdList()));
		
		String destination = regionService.getCityName(productBo.getDestIdList());
		productBo.setStartCity(destination == null ? "" : destination);

		//获取展示的套餐(起售价最低的套餐)
		PackageBo packBo = packageService.getCheapestPackage(productBo.getId());
		if(packBo != null){
			// 起始价格（四舍五入）
			productBo.setPrice(Double.valueOf(Math.round(packBo.getStartPrice().doubleValue())));
			//时长
			productBo.setDuration(packBo.getDuration());
			
			// 收藏与否
			productBo.setIsCollect(this.isCollect(productBo.getId(), userId));
			
			if(isFullSnap){
				//行程才获取主题
				if(productBo.getTypeId().equals(ProductTypeEnum.TRAVEL.getId())){
					/*根据属性ID获取主题详细信息*/
					productBo.setAttrList(attributeService.findAllThemeByIdList(productBo.getAttrIdList()));
				}
				/* 人数限制    */
				productBo.setMaxAdultNum(packBo.getMaxAdultNum());
				productBo.setMinPersonNum(packBo.getMinPersonNum());
				productBo.setMaxChildNum(packBo.getMaxChildNum());
				productBo.setMaxPersonNum(packBo.getMaxPersonNum());
			}
		}
		/* 对未设置的属性使用默认值  */
		if(StringUtil.isEmpty(productBo.getChildRule())){
			productBo.setChildRule(CommonConfig.DEFAULT_CHILDRULE);
		}
		if(productBo.getMaxAdultNum() == null){
			productBo.setMaxAdultNum(CommonConfig.DEFAULT_MAX_ADULT);
		}
		if(productBo.getMaxChildNum() == null){
			productBo.setMaxChildNum(CommonConfig.DEFAULT_MAX_CHILD);
		}
		if(productBo.getMaxPersonNum() == null){
			productBo.setMaxPersonNum(CommonConfig.DEFAULT_MAX_PERSON);
		}
		if(productBo.getMinPersonNum() == null){
			productBo.setMinPersonNum(CommonConfig.DEFAULT_MIN_PERSON);
		}
		if(productBo.getPresaleDelay() == null || productBo.getPresaleDelay().equals(0)){
			productBo.setPresaleDelay(CommonConfig.DEFAULT_PRESALE_DELAY);
		}
	}
	
	/**
	 * 处理产品的摘要信息
	 */
	private String getIntroHtml(List<String> itemList){
		if(itemList == null || itemList.size() == 0){
			return "";
		}
		
        StringBuilder introduceBuilder = new StringBuilder("<ul>");
        // 容器高px
        int containerHegiht = 210;
        // 单行字数 
        //strlen 一个汉字的长度为3
        int lineWords = 17 * 3;
        // 行高px
        int lineHeight = 20; 
        // 项之间的距离px
        int itemDistance = 10; 
        int contentHeight = 0;

        for (int i = 0; i < itemList.size(); i++) {
            if ( i >= 4) {
                break;
            }
            String item = itemList.get(i); 
            if (item.isEmpty()) {
                continue;
            }
            // 计算当前项高度
            int itemLines = (int)Math.ceil(item.length() / lineWords);
            contentHeight += itemLines * lineHeight + itemDistance;
            // 如果添加<li>... 会不会超高
            if ( contentHeight + itemDistance + lineHeight >= containerHegiht) {
                break;
            }
            introduceBuilder.append("<li><p style='line-height:" + lineHeight + "px;'>" + item + "</li>");
		}
        // 超过4条
        if(itemList.size() > 4){
        	// 截取三条
            itemList = itemList.subList(0, 3);
            introduceBuilder.append("<li style='list-style-type:none;'><p style='line-height:" + lineHeight + "px;'>...</li>");
        }
        introduceBuilder.append("</ul>");
	
		return introduceBuilder.toString();
	}
	
	/**
	 * 为产品设置推荐产品
	 * @param productBo
	 */
	private void inflationRecommendProducts(ProductBo productBo) {
		//每个产品的推荐产品数量
		int recommentNum = 4;
		/*根据推荐产品ID获取详细信息*/
		List<ProductBo> productList = new ArrayList<ProductBo>();
		if(productBo.getRecommendIdList() != null){
			for(Integer id : productBo.getRecommendIdList()){
				ProductBo productSnap = getProductSnap(id);
				if(productSnap != null){
					productList.add(productSnap);
				}
			}
		}
		if(productList.size() <= recommentNum){
			//自动推荐同种类型的产品
			ProductQuery query = new ProductQuery();
			query.setTypeId(productBo.getTypeId());
			List<ProductBo> autoRecProductList = getProductList(query);
			if(autoRecProductList != null){
				for (ProductBo pBo : autoRecProductList) {
					//防止重复推荐
					if(productList.contains(pBo)){
						continue;
					}
					productList.add(pBo);
				}
			}
			//剔除相关度较低的产品
			if(productList.size() > recommentNum){
				
				productList = productList.subList(0, recommentNum);
				/*Iterator<ProductBo> iterator = productList.iterator();
				while (iterator.hasNext()) {
					ProductBo recBo = iterator.next();
					//TODO
					//相关度比较
					//剔除
					iterator.remove();
				}*/
			}
		}
		
		//除不需要的属性
		for (ProductBo pbo : productList) {
			this.reduce(pbo);
		}
		
		productBo.setRecommendList(productList);
	}

	
	private Boolean isCollect(long productId, Long userId) {
		if(userId != null){
			List<Long> collectIdList = favoriteService.getFavoriteProductIdsByUserId(userId);
			if(collectIdList != null){
				return collectIdList.contains(productId);
			}
		}
		
		return false;
	}

}
