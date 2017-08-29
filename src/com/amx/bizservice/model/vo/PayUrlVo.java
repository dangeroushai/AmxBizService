package com.amx.bizservice.model.vo;


/**
 * @author DangerousHai
 *
 */
public class PayUrlVo {
	/**
	 * 成功与否
	 */
	private Boolean isSucceeded;
	
	private String tradeNo;
	
	/**
	 * 支付地址
	 */
	private String payUrl;
	
	/**
	 * 消息
	 */
	private String msg;
	

	public Boolean getIsSucceeded() {
		return isSucceeded;
	}

	public void setIsSucceeded(Boolean isSucceeded) {
		this.isSucceeded = isSucceeded;
	}

	public String getPayUrl() {
		return payUrl;
	}

	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
}
