package com.amx.bizservice.model.bo;




public class TravellerBo {
	
	/**
	 * @主键.
	 */
	private Long id;

    /**
     * @姓.
     */
    private String lastName; 

    /**
     * @电话.
     */
    private String phone; 

    /**
     * @邮箱.
     */
    private String email; 

    /**
     * @是否负责人.
     */
    private Boolean isPrincipal; 

    /**
     * @性别.
     */
    private Integer gender; 

    /**
     * @护照.
     */
    private String passport; 

    /**
     * @名.
     */
    private String firstName; 

    /**
     * @订单ID.
     */
    private Long orderId;


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


	public Boolean getIsPrincipal() {
		return isPrincipal;
	}


	public void setIsPrincipal(Boolean isPrincipal) {
		this.isPrincipal = isPrincipal;
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


	public Long getOrderId() {
		return orderId;
	}


	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
    
}
