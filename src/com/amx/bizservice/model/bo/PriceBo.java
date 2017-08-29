package com.amx.bizservice.model.bo;

import java.math.BigDecimal;



/**
 * @author DangerousHai
 *
 */
public class PriceBo{
	
	public PriceBo(){
		canBook = false;
		isUdidValid = false;
		udidMsg = "未识别的优惠码";
		msg = "未知错误";
	}
	
	private Boolean canBook;

	private Boolean isUdidValid;

    private BigDecimal price;
    private BigDecimal childPrice;
    private BigDecimal adultPrice;
    private Integer payType;
    private BigDecimal prePayPrice;
    private BigDecimal obligation;
    private BigDecimal discount;
    private String currencyType;
	
    private String udidMsg;
    private String msg;
    
	public Boolean getCanBook() {
		return canBook;
	}
	public void setCanBook(Boolean canBook) {
		this.canBook = canBook;
	}
	public Boolean getIsUdidValid() {
		return isUdidValid;
	}
	public void setIsUdidValid(Boolean isUdidValid) {
		this.isUdidValid = isUdidValid;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getChildPrice() {
		return childPrice;
	}
	public void setChildPrice(BigDecimal childPrice) {
		this.childPrice = childPrice;
	}
	public BigDecimal getAdultPrice() {
		return adultPrice;
	}
	public void setAdultPrice(BigDecimal adultPrice) {
		this.adultPrice = adultPrice;
	}
	public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public BigDecimal getPrePayPrice() {
		return prePayPrice;
	}
	public void setPrePayPrice(BigDecimal prePayPrice) {
		this.prePayPrice = prePayPrice;
	}
	public BigDecimal getObligation() {
		return obligation;
	}
	public void setObligation(BigDecimal obligation) {
		this.obligation = obligation;
	}
	public BigDecimal getDiscount() {
		return discount;
	}
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
	public String getCurrencyType() {
		return currencyType;
	}
	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}
	public String getUdidMsg() {
		return udidMsg;
	}
	public void setUdidMsg(String udidMsg) {
		this.udidMsg = udidMsg;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

}
