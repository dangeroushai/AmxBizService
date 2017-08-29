package com.amx.bizservice.model.vo.web;

import com.amx.bizservice.model.bo.AdvertisementBo;
import com.amx.bizservice.model.bo.AttributeBo;

//Theme && Scene 均使用该model
public class AttributeVo {
	
	/**
     * @主键.
     */
    private Integer id; 

    /**
     * @名称.
     */
    private String name;
    
    /**
     * @封面图.
     */
    private String iconUrl;
    
    /**
     * @效果图.
     */
    private String effectIconUrl;


	public AttributeVo(AdvertisementBo abo , AttributeBo attrBo) {
		if(abo != null && attrBo != null){
			this.id = attrBo.getId();
			//优先使用广告的标题，没有设置则使用属性名称
			this.name = "".equals(abo.getTitle().trim()) ? attrBo.getName() : abo.getTitle();
			this.iconUrl = abo.getCoverPic();
			this.effectIconUrl = abo.getEffectPic();
		}
	}



	public AttributeVo(AttributeBo abo) {
		if(abo != null){
			this.id = abo.getId();
			this.name = abo.getName();
			this.iconUrl = this.effectIconUrl = abo.getCoverPic();
		}
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getIconUrl() {
		return iconUrl;
	}



	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}



	public String getEffectIconUrl() {
		return effectIconUrl;
	}



	public void setEffectIconUrl(String effectIconUrl) {
		this.effectIconUrl = effectIconUrl;
	}

}
