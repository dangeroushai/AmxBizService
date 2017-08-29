package com.amx.bizservice.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.amx.bizservice.enums.AdLinkTypeEnum;
import com.amx.bizservice.enums.AdvertisementUdidEnum;
import com.amx.bizservice.model.bo.AdvertisementBo;
import com.amx.bizservice.model.qo.AdvertisementQuery;
import com.amx.bizservice.service.AdvertisementService;
import com.amx.bizservice.thrift.Response;
import com.amx.bizservice.util.JsonUtil;
import com.amx.bizservice.util.LogUtil;
import com.amx.bizservice.util.PictureUtil;
import com.fasterxml.jackson.databind.JavaType;

@Service("advertisementService")
public class AdvertisementServiceImpl extends BaseService implements AdvertisementService{

	public AdvertisementServiceImpl(){
		this.ServiceName = "Advertisement";		
	}
	
	@Override
	public AdvertisementBo getSlogan() {
		AdvertisementBo bo = null;
		List<AdvertisementBo> boList = findAllByUdid(AdvertisementUdidEnum.INDEX_SLOGAN.getUdid());
		if(boList != null && boList.size() != 0){
			bo = boList.get(0);
		}
		return bo;
	}

	@Override
	public List<AdvertisementBo> getWebBanner() {
		return findAllByUdid(AdvertisementUdidEnum.INDEX_BANNER.getUdid());
	}
	
	@Override
	public List<AdvertisementBo> getWapBanner() {
		return findAllByUdid(AdvertisementUdidEnum.DEFAULT_BANNER.getUdid());
	}

	@Override
	public List<AdvertisementBo> getAdvertisementByLine() {
		return findAllByUdid(AdvertisementUdidEnum.INDEX_LINE.getUdid());
	}

	@Override
	public List<AdvertisementBo> getRecArticles() {
		return findAllByUdid(AdvertisementUdidEnum.INDEX_ARTICLE.getUdid());
	}

	@Override
	public List<AdvertisementBo> getProductRecByArea() {
		return findAllByUdid(AdvertisementUdidEnum.INDEX_REGION.getUdid());
	}

	@Override
	public List<AdvertisementBo> getProductRecByScene() {
		return findAllByUdid(AdvertisementUdidEnum.INDEX_SCENE.getUdid());
	}

	@Override
	public List<AdvertisementBo> getTheme() {
		List<AdvertisementBo> themes = findAllByUdid(AdvertisementUdidEnum.INDEX_THEME.getUdid());
		//处理效果图
		if(themes != null){
			for (AdvertisementBo advertisementBo : themes) {
				advertisementBo.setEffectPic(PictureUtil.getPicUrl(advertisementBo.getEffectPic(),true));
			}
		}
		
		return themes;
	}
	
	@Override
	public List<AdvertisementBo> getPriceRegion() {
		return findAllByUdid(AdvertisementUdidEnum.PRODUCT_REGION.getUdid());
	}
	@Override
	public AdvertisementBo getRandomOneProductListPageHeadPicAd() {
		 AdvertisementBo adBo = null;
		 List<AdvertisementBo> list = findAllByUdid(AdvertisementUdidEnum.PRODUCT_BANNER.getUdid());
		 if(list != null && list.size() > 0){
			 adBo = list.get(new Random().nextInt(list.size() - 1));
		 }
		 
		 return adBo;
	}

	@Override
	public List<String> getHotWords() {
		List<String> result = null;
		List<AdvertisementBo> list = findAllByUdid(AdvertisementUdidEnum.NAVBAR_HOT_WORD.getUdid());
		if(list != null){
			result = new ArrayList<String>();
			for (AdvertisementBo advertisementBo : list) {
				String[] hotWords = advertisementBo.getTitle().split(",");
				for (int i = 0; i < hotWords.length; i++) {
					result.add(hotWords[i]);
				}
			}
		}
		return result;
	}
	
	@Override
	public List<AdvertisementBo> getRecDestinations() {
		return findAllByUdid(AdvertisementUdidEnum.DEFAULT_SIFT_REGION.getUdid());
	}
	
	@Override
	public List<AdvertisementBo> getHotDestinations() {
		return findAllByUdid(AdvertisementUdidEnum.DEFAULT_HOT_REGION.getUdid());
	}
	
	/**
	 * 根据调用标识调用数据服务查找广告
	 * @param udid 调用标识
	 * @return
	 */
	private List<AdvertisementBo> findAllByUdid(String udid) {
		AdvertisementQuery query = new AdvertisementQuery(udid);
		Response response = thriftClient.serviceInvoke(this.ServiceName, "findAllByQuery", query);
		List<AdvertisementBo> boList = null; 
		try {
			if(response != null){
				if(response.isState()){
					JavaType javaType = JsonUtil.getCollectionType(ArrayList.class, AdvertisementBo.class);		
					boList = JsonUtil.mapper.readValue(response.getData(),javaType);
					if(boList != null){
						for (AdvertisementBo advertisementBo : boList) {
							this.inflation(advertisementBo);
						}
					}
				}else{
					LogUtil.recordWarnLog(response.getMsg());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return boList;
	}

	private void inflation(AdvertisementBo advertisementBo) {
		//主题直接请求静态封面图
		if(advertisementBo.getLinkTypeId().equals(AdLinkTypeEnum.THEME.getId())){
			advertisementBo.setCoverPic(PictureUtil.getPicUrl(advertisementBo.getCoverPic(),true));
		}else{
			advertisementBo.setCoverPic(PictureUtil.getPicUrl(advertisementBo.getCoverPic()));
		}
		if(advertisementBo.getSubAdvertisementList() != null){
			for (AdvertisementBo subBo : advertisementBo.getSubAdvertisementList()) {
				this.inflation(subBo);
			}
		}
	}
}
