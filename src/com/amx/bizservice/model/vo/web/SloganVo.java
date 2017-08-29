package com.amx.bizservice.model.vo.web;

import com.amx.bizservice.model.bo.AdvertisementBo;


public class SloganVo {
	
    private String zhDes;
    
    private String enDes;

	public SloganVo(AdvertisementBo abo) {
		if(abo != null){
			this.zhDes = abo.getTitle();
			this.enDes = abo.getSubTitle();
		}
	}

	public String getZhDes() {
		return zhDes;
	}

	public void setZhDes(String zhDes) {
		this.zhDes = zhDes;
	}



	public String getEnDes() {
		return enDes;
	}



	public void setEnDes(String enDes) {
		this.enDes = enDes;
	}


}
