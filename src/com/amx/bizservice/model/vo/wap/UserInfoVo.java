package com.amx.bizservice.model.vo.wap;


/**
 * 
 * @author DangerousHai
 *
 */
public class UserInfoVo {

	private Boolean isSucceeded;
	private UserVo userInfo;
	private String msg;
	
	
	public Boolean getIsSucceeded() {
		return isSucceeded;
	}
	public void setIsSucceeded(Boolean isSucceeded) {
		this.isSucceeded = isSucceeded;
	}
	public UserVo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserVo userInfo) {
		this.userInfo = userInfo;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
