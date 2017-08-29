package com.amx.bizservice.model.vo.wap.input;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class TravelInfoVo{
		private Long productId;
		
		private Integer productTypeId;
		
		private Long packageId;

		private Integer languageId;
		
		@DateTimeFormat(pattern = "yyyy-MM-dd")
	    private Date goOffDate; 
	    private String goOffTime; 
	    private Integer adultNum; 
	    private Integer childNum; 
	    
	    private Integer gatherWay;
	    private String hotelName;
	    private String hotelAddr;
	    
	    private String remark;

		public Long getProductId() {
			return productId;
		}

		public void setProductId(Long productId) {
			this.productId = productId;
		}

		public Integer getProductTypeId() {
			return productTypeId;
		}

		public void setProductTypeId(Integer productTypeId) {
			this.productTypeId = productTypeId;
		}

		public Long getPackageId() {
			return packageId;
		}

		public void setPackageId(Long packageId) {
			this.packageId = packageId;
		}

		public Integer getLanguageId() {
			return languageId;
		}

		public void setLanguageId(Integer languageId) {
			this.languageId = languageId;
		}

		public Date getGoOffDate() {
			return goOffDate;
		}

		public void setGoOffDate(Date goOffDate) {
			this.goOffDate = goOffDate;
		}

		public String getGoOffTime() {
			return goOffTime;
		}

		public void setGoOffTime(String goOffTime) {
			this.goOffTime = goOffTime;
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

		public Integer getGatherWay() {
			return gatherWay;
		}

		public void setGatherWay(Integer gatherWay) {
			this.gatherWay = gatherWay;
		}

		public String getHotelName() {
			return hotelName;
		}

		public void setHotelName(String hotelName) {
			this.hotelName = hotelName;
		}

		public String getHotelAddr() {
			return hotelAddr;
		}

		public void setHotelAddr(String hotelAddr) {
			this.hotelAddr = hotelAddr;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}
	}