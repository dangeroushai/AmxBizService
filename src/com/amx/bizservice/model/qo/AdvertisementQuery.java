package com.amx.bizservice.model.qo;


/**
 * 查询广告类型
 * @author DangerousHai
 *
 */
public class AdvertisementQuery {

	/**
	 * 调用标识
	 */
	private String udid;
	
	public AdvertisementQuery(String udid){
		this.udid = udid;
	}

	public String getUdid() {
		return udid;
	}

	public void setUdid(String udid) {
		this.udid = udid;
	}

	
}
