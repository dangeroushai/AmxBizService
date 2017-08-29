package com.amx.bizservice.model.vo;



/**
 * 购物车编辑操作响应对象
 * @author DangerousHai
 *
 */
public class SigninVo {
	/**
	 * 成功与否
	 */
	private Boolean isSucceeded;
	/**
	 * 购物车数量
	 */
	private UserSnapVo userSnap;
	
	/**
	 * 消息
	 */
	private String msg;
	
	public SigninVo(){
		isSucceeded = false;
	}

	public Boolean getIsSucceeded() {
		return isSucceeded;
	}

	public void setIsSucceeded(Boolean isSucceeded) {
		this.isSucceeded = isSucceeded;
	}

	public UserSnapVo getUserSnap() {
		return userSnap;
	}

	public void setUserSnap(UserSnapVo userSnap) {
		this.userSnap = userSnap;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
