package com.amx.bizservice.model.bo;

import java.sql.Time;
import java.util.Date;

public class CustomHodometerBo {
	
	  /**
     * @主键.
     */
    private Long id; 

    

    /**
     * @用户备注.
     */
    private String remark; 


    /**
     * @套餐ID.
     */
    private Long packageId; 

    /**
     * @出发日期.
     */
    private Date goOffDate; 


    /**
     * @用户ID.
     */
    private Long userId; 

    /**
     * @成人数.
     */
    private Integer adultNum; 


    /**
     * @儿童数.
     */
    private Integer childNum; 


    /**
     * @语言ID.
     */
    private Integer languageId; 

    /**
     * @产品ID.
     */
    private Long productId; 
    private Long travelId; 


    /**
     * @出发时间.
     */
    private Time goOffTime;

	public Long getId() {
		return id;
	}

	public Long getTravelId() {
		return travelId;
	}


	public void setTravelId(Long travelId) {
		this.travelId = travelId;
	}


	public void setId(Long id) {
		this.id = id;
	}




	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public Long getPackageId() {
		return packageId;
	}


	public void setPackageId(Long packageId) {
		this.packageId = packageId;
	}


	public Date getGoOffDate() {
		return goOffDate;
	}


	public void setGoOffDate(Date goOffDate) {
		this.goOffDate = goOffDate;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public Integer getAdultNum() {
		return adultNum;
	}


	public void setAdultNum(Integer adultNum) {
		this.adultNum = adultNum;
	}


	public Integer getChildNum() {
		return childNum;
	}


	public void setChildNum(Integer childNum) {
		this.childNum = childNum;
	}


	public Integer getLanguageId() {
		return languageId;
	}


	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}


	public Long getProductId() {
		return productId;
	}


	public void setProductId(Long productId) {
		this.productId = productId;
	}


	public Time getGoOffTime() {
		return goOffTime;
	}


	public void setGoOffTime(Time goOffTime) {
		this.goOffTime = goOffTime;
	}

}
