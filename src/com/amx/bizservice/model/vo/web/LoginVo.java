package com.amx.bizservice.model.vo.web;

import com.amx.bizservice.model.vo.UserSnapVo;


/**
 * 购物车编辑操作响应对象
 * @author DangerousHai
 *
 */
public class LoginVo {
	/**
	 * 成功与否
	 */
	private Boolean isSucceeded;
	/**
	 * 下次登陆是否需要验证码
	 */
	private Boolean isValidate;
	/**
	 * 购物车数量
	 */
	private UserSnapVo userSnap;
	
	/**
	 * 消息
	 */
	private String msg;
	
	public LoginVo(){
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

	public Boolean getIsValidate() {
		return isValidate;
	}

	public void setIsValidate(Boolean isValidate) {
		this.isValidate = isValidate;
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
