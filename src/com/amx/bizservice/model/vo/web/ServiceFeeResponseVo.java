package com.amx.bizservice.model.vo.web;


/**
 * @author DangerousHai
 *
 */
public class ServiceFeeResponseVo {
	/**
	 * 成功与否
	 */
	private Boolean isSucceeded;
	/**
	 * 服务费
	 */
	private Double serviceFee;
	
	/**
	 * 消息
	 */
	private String msg;
	
	public ServiceFeeResponseVo(){
		isSucceeded = false;
	}

	public Boolean getIsSucceeded() {
		return isSucceeded;
	}

	public void setIsSucceeded(Boolean isSucceeded) {
		this.isSucceeded = isSucceeded;
	}

	public Double getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(Double serviceFee) {
		this.serviceFee = serviceFee;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
