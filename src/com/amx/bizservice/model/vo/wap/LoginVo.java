package com.amx.bizservice.model.vo.wap;

import com.amx.bizservice.model.bo.UserBo;
import com.amx.bizservice.model.vo.UserSnapVo;
import com.amx.bizservice.util.JWTUtil;


public class LoginVo {
	/**
	 * 成功与否
	 */
	private Boolean isSucceeded;
	
	private String token;
	
	private UserSnapVo userSnap;
	
	/**
	 * 消息
	 */
	private String msg;
	
	public LoginVo(UserBo user) {
		if(user != null){
			this.isSucceeded = true;
			this.token = JWTUtil.createToken(user);
			this.userSnap = new UserSnapVo(user);
			
		}else{
			this.isSucceeded = false;
			this.msg = "用户名或密码错误";
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

	public UserSnapVo getUserSnap() {
		return userSnap;
	}

	public void setUserSnap(UserSnapVo userSnap) {
		this.userSnap = userSnap;
	}
	
}
