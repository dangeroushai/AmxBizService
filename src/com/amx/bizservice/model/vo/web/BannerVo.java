package com.amx.bizservice.model.vo.web;

import com.amx.bizservice.model.bo.AdvertisementBo;
import com.amx.bizservice.model.bo.ProductBo;


public class BannerVo {
	
    private String picUrl;
    
    private String picAlt;
    
    private Integer linkProdTypeId;
    /**
     * 链接产品Id
     */
    private Long linkProdId;

	public BannerVo(AdvertisementBo abo,ProductBo productSnap) {
		if(productSnap != null){
			this.linkProdTypeId = productSnap.getTypeId();
			this.linkProdId = productSnap.getId();
		}
		if(abo != null){
			this.picUrl = abo.getCoverPic();
			this.picAlt = abo.getTitle();
		}
	}



	public String getPicUrl() {
		return picUrl;
	}



	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}



	public String getPicAlt() {
		return picAlt;
	}



	public void setPicAlt(String picAlt) {
		this.picAlt = picAlt;
	}



	public Long getLinkProdId() {
		return linkProdId;
	}



	public void setLinkProdId(Long linkProdId) {
		this.linkProdId = linkProdId;
	}



	public Integer getLinkProdTypeId() {
		return linkProdTypeId;
	}

	public void setLinkProdTypeId(Integer linkProdTypeId) {
		this.linkProdTypeId = linkProdTypeId;
	}
	
	

}
