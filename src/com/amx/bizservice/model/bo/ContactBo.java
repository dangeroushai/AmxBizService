package com.amx.bizservice.model.bo;

public class ContactBo {
	

	/**
	 * @主键.
	 */
	private Long id;
	
	/**
	 * @名.
	 */
	private String firstName;
	

    /**
     * @姓.
     */
    private String lastName; 

    /**
     * @国家代码.
     */
    private String countryCode; 
    
    /**
     * @护照.
     */
    private String passport;
    
    /**
     * @电话.
     */
    private String phone; 

    /**
     * @邮箱.
     */
    private String email; 

    /**
     * @所属用户ID.
     */
    private Long userId; 

    /**
     * @性别.
     */
    private Integer gender; 


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

}
