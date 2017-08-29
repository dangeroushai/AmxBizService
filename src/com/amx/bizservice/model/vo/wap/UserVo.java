package com.amx.bizservice.model.vo.wap;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.amx.bizservice.model.bo.UserBo;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * @author DangerousHai
 *
 */
public class UserVo {

	private Long id;
	private String nickName;
	private String name;
	private String protraitUrl;
	private Integer gender;
	private String address;
	private String passport;
	private Integer score;
	private String email;
	private String phone;
	
	
	//XXX - 指定数据输出的格式
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
	//XXX - 指定数据绑定的格式
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date brithday;

	public UserVo(){}
	public UserVo(UserBo user) {
		if(user != null){
			this.nickName = user.getNickName();
			this.name = (user.getLastName() + " " + user.getFirstName()).trim();
			this.protraitUrl = user.getPortrait();
			this.gender = user.getGender();
			this.address = (user.getCountry() + " " + user.getProvince() + " " + user.getCity() + " " + user.getRegion()).trim();
			this.passport = user.getPassport();
			this.score = user.getScore();
			this.email = user.getEmail();
			this.phone = user.getPhone();
			this.brithday = user.getBirthday();
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

	public String getProtraitUrl() {
		return protraitUrl;
	}

	public void setProtraitUrl(String protraitUrl) {
		this.protraitUrl = protraitUrl;
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
	public Date getBrithday() {
		return brithday;
	}
	public void setBrithday(Date brithday) {
		this.brithday = brithday;
	}


}
