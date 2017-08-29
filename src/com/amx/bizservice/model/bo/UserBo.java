package com.amx.bizservice.model.bo;

import java.sql.Timestamp;
import java.util.Date;


public class UserBo {
	/**
	 * @主键.
	 */
	private Long id; 
	
    private String region; 

    /**
     * @电话.
     */
    private String phone; 

    /**
     * @最后登录时间.
     */
    private Timestamp loginTime; 

    /**
     * @登陆状态.
     */
    private Integer loginStatus; 

    /**
     * @积分.
     */
    private Integer score; 

    /**
     * @密码.
     */
    private String password; 

    /**
     * @登陆类型.
     */
    private Integer loginType; 

    /**
     * @城市.
     */
    private String city; 

    /**
     * @最后登录IP.
     */
    private Integer loginIp; 

    /**
     * @用户名.
     */
    private String nickName; 

    /**
     * @省（市）.
     */
    private String province; 

    /**
     * @性别.
     */
    private Integer gender; 

    /**
     * @名.
     */
    private String firstName; 


    /**
     * @用户角色.
     */
    private Integer roleId; 

    /**
     * @姓.
     */
    private String lastName; 

    /**
     * @国家代码.
     */
    private String countryCode; 

    /**
     * @头像.
     */
    private String portrait; 

    /**
     * @国家.
     */
    private String country; 

    /**
     * @邮箱.
     */
    
    private String email;
    
    private String passport;
    
    private Date birthday; 
    

    /**
     * @注册时间.
     */
    private Timestamp registTime;

	private String openId;
    

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRegion() {
		return region == null ? "" : region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Timestamp getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Timestamp loginTime) {
		this.loginTime = loginTime;
	}

	public Integer getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(Integer loginStatus) {
		this.loginStatus = loginStatus;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getLoginType() {
		return loginType;
	}

	public void setLoginType(Integer loginType) {
		this.loginType = loginType;
	}

	public String getCity() {
		return city == null ? "" : city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(Integer loginIp) {
		this.loginIp = loginIp;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getProvince() {
		return province == null ? "" : province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getFirstName() {
		return firstName == null ? "" : firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getLastName() {
		return lastName == null ? "" : lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	public String getCountry() {
		return country == null ? "" : country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Timestamp getRegistTime() {
		return registTime;
	}

	public void setRegistTime(Timestamp registTime) {
		this.registTime = registTime;
	}
	
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	} 
}
