package com.amx.bizservice.model.vo;

import com.amx.bizservice.model.bo.UserBo;

public class UserSnapVo{
	private String nickName;
	private String portraitUrl;
	private Integer gender;
	private Integer cartAmount;
	
	public UserSnapVo(UserBo user) {
		this.nickName = user.getNickName();
		this.portraitUrl = user.getPortrait();
		this.gender = user.getGender();
		this.cartAmount = 0;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPortraitUrl() {
		return portraitUrl;
	}
	public void setPortraitUrl(String portraitUrl) {
		this.portraitUrl = portraitUrl;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public Integer getCartAmount() {
		return cartAmount;
	}
	public void setCartAmount(Integer cartAmount) {
		this.cartAmount = cartAmount;
	}
}
