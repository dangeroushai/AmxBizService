package com.amx.bizservice.model.vo;

import com.amx.bizservice.model.bo.PriceBo;



/**
 * @author DangerousHai
 *
 */
public class PriceVo{
	
	public PriceVo(PriceBo bo){
		if(bo != null){
			//精度控制
			//TODO - 
			
			this.canBook = bo.getCanBook();
			this.isUdidValid = bo.getIsUdidValid();
			if(this.canBook){
				this.payType = bo.getPayType();
				this.price = bo.getPrice().doubleValue();
				this.childPrice = bo.getChildPrice().doubleValue();
				this.adultPrice = bo.getAdultPrice().doubleValue();
				if(bo.getObligation() != null && bo.getPrePayPrice() != null){
					this.prePayPrice = bo.getPrePayPrice().doubleValue();
					this.obligation = bo.getObligation().doubleValue();
					this.currencyType = bo.getCurrencyType();
				}
				this.discount = bo.getDiscount().doubleValue();
				this.udidMsg = bo.getUdidMsg();
			}
			this.msg = bo.getMsg();
		}
	}
	private Boolean canBook;

	private Boolean isUdidValid;

    private Double price;
    private Double childPrice;
    private Double adultPrice;
    private Integer payType;
    private Double prePayPrice;
    private Double obligation;
    private Double discount;
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
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getChildPrice() {
		return childPrice;
	}
	public void setChildPrice(Double childPrice) {
		this.childPrice = childPrice;
	}
	public Double getAdultPrice() {
		return adultPrice;
	}
	public void setAdultPrice(Double adultPrice) {
		this.adultPrice = adultPrice;
	}
	public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public Double getPrePayPrice() {
		return prePayPrice;
	}
	public void setPrePayPrice(Double prePayPrice) {
		this.prePayPrice = prePayPrice;
	}
	public Double getObligation() {
		return obligation;
	}
	public void setObligation(Double obligation) {
		this.obligation = obligation;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
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
