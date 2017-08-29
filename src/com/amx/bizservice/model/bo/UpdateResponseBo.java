package com.amx.bizservice.model.bo;


/**
 * 封装数据更新的执行结果
 * @author DangerousHai
 *
 */
public class UpdateResponseBo {
	/**
	 * 成功与否
	 */
	private Boolean isSucceeded;
	
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

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
