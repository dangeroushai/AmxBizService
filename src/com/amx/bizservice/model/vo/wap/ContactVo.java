package com.amx.bizservice.model.vo.wap;

import com.amx.bizservice.model.bo.ContactBo;

/**
 * 兼容Traveller
 * @author DangerousHai
 *
 */
public class ContactVo {
	
	private Long id;
    private String firstName;
    private String lastName;
    private String passport;
    private String countryCode;
    private String phone;
    private String email;


    public ContactVo() {}
    
	public ContactVo(ContactBo bo) {
		if(bo != null){
			this.id = bo.getId();
			this.firstName = bo.getFirstName(); 
			this.lastName = bo.getLastName();
			this.passport = bo.getPassport();
			this.email = bo.getEmail();
			this.countryCode = bo.getCountryCode();
			this.phone = bo.getPhone();
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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


	public String getCountryCode() {
		return countryCode;
	}


	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
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

	
}
