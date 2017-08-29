package com.amx.bizservice.model.vo.web;

import java.util.List;

import com.amx.bizservice.model.bo.AdvertisementBo;

/**
 * 根据线路推荐广告
 * @author DangerousHai
 *
 */
public class AdvertisementByLineVo {
	
    private String zhLineName;
    private String enLineName;
    private String bgPicUrl;
    private List<AdvertisementVo> advertisements;



	public AdvertisementByLineVo(AdvertisementBo abo) {
		if(abo != null){
			this.zhLineName = abo.getTitle();
			this.enLineName = abo.getSubTitle();
			this.bgPicUrl = abo.getCoverPic();
			//advertisements // 在外部设置
		}
	}
	
	/**
	 * 仅为外部类使用
	 * @author DangerousHai
	 *
	 */
	public class AdvertisementVo{
	    private String title;
	    private String description;
	    private String coverPicUrl;
	    private String linkUrl;
	    
	    public AdvertisementVo(AdvertisementBo abo){
	    	this.title = abo.getTitle();
	    	this.description = abo.getSubTitle();
	    	this.coverPicUrl = abo.getCoverPic();
	    	this.linkUrl = abo.getLinkUrl();
	    }
	    
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getCoverPicUrl() {
			return coverPicUrl;
		}
		public void setCoverPicUrl(String coverPicUrl) {
			this.coverPicUrl = coverPicUrl;
		}
		public String getLinkUrl() {
			return linkUrl;
		}
		public void setLinkUrl(String linkUrl) {
			this.linkUrl = linkUrl;
		}
	    
	    
	}
	public String getZhLineName() {
		return zhLineName;
	}
	public void setZhLineName(String zhLineName) {
		this.zhLineName = zhLineName;
	}
	public String getEnLineName() {
		return enLineName;
	}
	public void setEnLineName(String enLineName) {
		this.enLineName = enLineName;
	}
	public String getBgPicUrl() {
		return bgPicUrl;
	}
	public void setBgPicUrl(String bgPicUrl) {
		this.bgPicUrl = bgPicUrl;
	}
	public List<AdvertisementVo> getAdvertisements() {
		return advertisements;
	}
	public void setAdvertisements(List<AdvertisementVo> advertisements) {
		this.advertisements = advertisements;
	}

}

