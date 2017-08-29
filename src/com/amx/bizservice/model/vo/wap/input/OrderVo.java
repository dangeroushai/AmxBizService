package com.amx.bizservice.model.vo.wap.input;

import java.util.List;

/**
 * 接收订单
 * @author DangerousHai
 *
 */
public class OrderVo{

	private List<TravelInfoVo> orderList;
	
    private Long contactId;

    private String firstName;
    
    private String lastName;
    
    private String passport;
    
    private String phone;
    
    private String email;
    
    private String udid;
	
    
    


	public List<TravelInfoVo> getOrderList() {
		return orderList;
	}


	public void setOrderList(List<TravelInfoVo> orderList) {
		this.orderList = orderList;
	}


	public Long getContactId() {
		return contactId;
	}


	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getPassport() {
		return passport;
	}


	public void setPassport(String passport) {
		this.passport = passport;
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


	public String getUdid() {
		return udid;
	}


	public void setUdid(String udid) {
		this.udid = udid;
	}
}