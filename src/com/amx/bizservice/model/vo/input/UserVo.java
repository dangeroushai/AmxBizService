package com.amx.bizservice.model.vo.input;



public class UserVo {
	
	private String name;
	
	private String phone;
	
	private String authCode;
	
	private String pwd;
	
	private String oldPwd;
	
	private String newPwd;
	
	private boolean remember;

	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getAuthCode() {
		return authCode;
	}


	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPwd() {
		return pwd;
	}


	public void setPwd(String pwd) {
		this.pwd = pwd;
	}


	public String getOldPwd() {
		return oldPwd;
	}


	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}


	public String getNewPwd() {
		return newPwd;
	}


	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}


	public boolean isRemember() {
		return remember;
	}


	public void setRemember(boolean remember) {
		this.remember = remember;
	}
}
