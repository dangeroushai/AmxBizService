package com.amx.bizservice.model.vo;

import com.amx.bizservice.util.PictureUtil;


/**
 * 产品业务对象，所有关联对象都只指明ID
 * @author DangerousHai
 *
 */
public class PictureVo{
	
	public PictureVo(String url){
		if(url != null){
			this.picUrl = PictureUtil.getPicUrl(url);
		}
	}

    /**
     * @图片地址.
     */
    private String picUrl;

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	} 
    
    

}
