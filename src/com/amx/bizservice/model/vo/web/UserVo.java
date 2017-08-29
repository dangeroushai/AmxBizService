package com.amx.bizservice.model.vo.web;

import java.util.List;

import com.amx.bizservice.model.bo.UserBo;
import com.amx.bizservice.model.vo.ContactVo;
import com.amx.bizservice.util.StringUtil;

/**
 * 
 * @author DangerousHai
 *
 */
public class UserVo {

	private Long id;
	private String nickName;
	private String name;
	private String portraitUrl;
	private Integer gender;
	private String address;
	private String passport;
	private Integer score;
	private String email;
	private String phone;
	private String brithday;
	private String shareUrl;
	private List<ContactVo> contacts;

	public UserVo(){}
	public UserVo(UserBo user) {
		if(user != null){
			this.nickName = user.getNickName();
			this.name = (user.getLastName() + " " + user.getFirstName()).trim();
			this.portraitUrl = user.getPortrait();
			this.gender = user.getGender();
			this.address = user.getCountry() + " " + user.getProvince() + " " + user.getCity() + " " + user.getRegion();
			this.passport = user.getPassport();
			this.score = user.getScore();
			this.email = user.getEmail();
			this.phone = user.getPhone();
			this.brithday = StringUtil.getSdfDate(user.getBirthday());
//			this.shareUrl = user.getShareUrl();
			//this.contacts = 
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBrithday() {
		return brithday;
	}

	public void setBrithday(String birthday) {
		this.brithday = birthday;
	}

	public String getShareUrl() {
		return shareUrl;
	}

	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
	}

	public List<ContactVo> getContacts() {
		return contacts;
	}

	public void setContacts(List<ContactVo> contacts) {
		this.contacts = contacts;
	}

}
