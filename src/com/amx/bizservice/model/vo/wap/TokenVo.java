package com.amx.bizservice.model.vo.wap;

public class TokenVo {
	/**
	 * 成功与否
	 */
	private Boolean isSucceeded;
	
	private String token;
	/**
	 * 消息
	 */
	private String msg;

	public TokenVo(String token) {
		if(token != null){
			this.isSucceeded = true;
			this.token = token; 
		}else{
			this.isSucceeded = false;
			this.msg = "创建临时身份失败，请稍后再试"; 
		}
	}

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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
