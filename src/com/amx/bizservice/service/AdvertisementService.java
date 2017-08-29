package com.amx.bizservice.service;

import java.util.List;

import com.amx.bizservice.model.bo.AdvertisementBo;


public interface AdvertisementService{

	AdvertisementBo getSlogan();
	
	List<AdvertisementBo> getWebBanner();
	
	List<AdvertisementBo> getWapBanner();
	
	List<AdvertisementBo> getTheme();
	
	List<AdvertisementBo> getAdvertisementByLine();
	
	List<AdvertisementBo> getRecArticles();
	
	List<AdvertisementBo> getProductRecByArea();
	
	List<AdvertisementBo> getProductRecByScene();
	
	List<AdvertisementBo> getPriceRegion();

	AdvertisementBo getRandomOneProductListPageHeadPicAd();

	List<String> getHotWords();

	List<AdvertisementBo> getRecDestinations();
	
	List<AdvertisementBo> getHotDestinations();	
}
