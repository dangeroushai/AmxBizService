package com.amx.bizservice.model.vo.input;

public class ShareVo {
	/**
	 * 分享用户ID
	 */
	private String uid;
	
	/**
	 * 过期时间
	 */
	private Long expire;
	
	/**
	 * 签名
	 */
	private String sign;


	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Long getExpire() {
		return expire;
	}

	public void setExpire(Long expire) {
		this.expire = expire;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
}
