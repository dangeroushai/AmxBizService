package com.amx.bizservice.model.bo;

import java.sql.Time;

public class HodometerBo{
	

	/**
     * @主键.
     */
    private Integer id; 

    /**
     * @天序号.
     */
    private Integer dayOrder; 

    /**
     * @出发时间.
     */
    private Time goOffTime; 

    /**
     * @项目产品ID.
     */
    private Integer itemProductId; 

    /**
     * @备注.
     */
    private String remark; 

    /**
     * @项目产品套餐ID.
     */
    private Integer itemPackageId; 

    /**
     * @项目序号.
     */
    private Integer itemOrder; 

    /**
     * @所属产品.
     */
    private Integer productId;
    

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDayOrder() {
		return dayOrder;
	}

	public void setDayOrder(Integer dayOrder) {
		this.dayOrder = dayOrder;
	}

	public Time getGoOffTime() {
		return goOffTime;
	}

	public void setGoOffTime(Time goOffTime) {
		this.goOffTime = goOffTime;
	}

	public Integer getItemProductId() {
		return itemProductId;
	}

	public void setItemProductId(Integer itemProductId) {
		this.itemProductId = itemProductId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getItemPackageId() {
		return itemPackageId;
	}

	public void setItemPackageId(Integer itemPackageId) {
		this.itemPackageId = itemPackageId;
	}

	public Integer getItemOrder() {
		return itemOrder;
	}

	public void setItemOrder(Integer itemOrder) {
		this.itemOrder = itemOrder;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

}