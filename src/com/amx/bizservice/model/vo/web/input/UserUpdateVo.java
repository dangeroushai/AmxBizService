package com.amx.bizservice.model.vo.web.input;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * @author DangerousHai
 *
 */
public class UserUpdateVo {

	private String nickName;
	private String name;
	private Integer gender;
	private String address;
	private String passport;
	private String email;
	private String phone;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date brithday;

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

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBrithday() {
		return brithday;
	}

	public void setBrithday(Date birthday) {
		this.brithday = birthday;
	}
	
}
