package com.amx.bizservice.model.bo;

public class RequirementBo {
	
	private String destination;
	private String partner;
	private String goOffTime;
	private String remark;
	private String name;
	private String phone;
	private String email;
	private Integer gender;
	private Integer orderStateId;
	private Long userId;
	
	private Double subscription;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Double getSubscription() {
		return subscription;
	}
	public void setSubscription(Double subscription) {
		this.subscription = subscription;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getPartner() {
		return partner;
	}
	public void setPartner(String partner) {
		this.partner = partner;
	}
	public String getGoOffTime() {
		return goOffTime;
	}
	public void setGoOffTime(String goOffTime) {
		this.goOffTime = goOffTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public Integer getOrderStateId() {
		return orderStateId;
	}
	public void setOrderStateId(Integer orderStateId) {
		this.orderStateId = orderStateId;
	}
}
