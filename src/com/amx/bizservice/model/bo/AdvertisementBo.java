package com.amx.bizservice.model.bo;

import java.util.List;


public class AdvertisementBo {
	
   /**
    * @主键.
    */
   private Integer id; 

    /**
     * @链接类型.
     */
    private Integer linkTypeId; 


    /**
     * @广告副标题.
     */
    private String subTitle; 


    /**
     * @广告标题.
     */
    private String title; 

    /**
     * @封面图.
     */
    private String coverPic; 
    private String effectPic; 

    /**
     * 链接产品
     */
    private Integer linkProductId;
    
    /**
     * 链接地址
     */
    private String linkUrl;
    
    /**
     * 链接文章ID
     */
    private Integer linkArticleId;
    /**
     * 链接地区ID
     */
    private Integer linkRegionId;
    /**
     * 链接属性ID
     */
    private Integer linkAttributeId;
    
    /**
     * 下属广告
     */
    private List<AdvertisementBo> subAdvertisementList;
    

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}



	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCoverPic() {
		return coverPic;
	}

	public void setCoverPic(String coverPic) {
		this.coverPic = coverPic;
	}

	public Integer getLinkTypeId() {
		return linkTypeId;
	}

	public void setLinkTypeId(Integer linkTypeId) {
		this.linkTypeId = linkTypeId;
	}

	public String getEffectPic() {
		return effectPic;
	}

	public void setEffectPic(String effectPic) {
		this.effectPic = effectPic;
	}

	public Integer getLinkProductId() {
		return linkProductId;
	}



	public void setLinkProductId(Integer linkProductId) {
		this.linkProductId = linkProductId;
	}




	public String getLinkUrl() {
		return linkUrl;
	}



	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}



	public Integer getLinkArticleId() {
		return linkArticleId;
	}



	public void setLinkArticleId(Integer linkArticleId) {
		this.linkArticleId = linkArticleId;
	}



	public Integer getLinkRegionId() {
		return linkRegionId;
	}



	public void setLinkRegionId(Integer linkRegionId) {
		this.linkRegionId = linkRegionId;
	}



	public Integer getLinkAttributeId() {
		return linkAttributeId;
	}



	public void setLinkAttributeId(Integer linkAttributeId) {
		this.linkAttributeId = linkAttributeId;
	}



	public List<AdvertisementBo> getSubAdvertisementList() {
		return subAdvertisementList;
	}



	public void setSubAdvertisementList(List<AdvertisementBo> subAdvertisementList) {
		this.subAdvertisementList = subAdvertisementList;
	} 
	
	
 
}
