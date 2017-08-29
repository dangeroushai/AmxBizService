package com.amx.bizservice.model.vo.wap;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import com.amx.bizservice.enums.OrderStateEnum;
import com.amx.bizservice.model.bo.CurrencyBo;
import com.amx.bizservice.model.bo.LanguageBo;
import com.amx.bizservice.model.bo.OrderBo;
import com.amx.bizservice.model.bo.PackageBo;
import com.amx.bizservice.model.bo.ProductBo;
import com.amx.bizservice.model.dto.PageResponseDto;

/**
 * 订单列表
 * @author DangerousHai
 *
 */
public class OrderListVo {
    private Integer orderAmount;
    private Integer pageIndex;
    private Integer pageAmount;
    private List<OrderVo> orderList;

	public OrderListVo(PageResponseDto<OrderBo> dto) {
		if(dto != null){
			this.orderAmount = dto.getAmount().intValue();
			this.pageAmount = dto.getPageAmount();
			this.pageIndex = dto.getPageIndex();
			
			//外部处理
			//this.orderList
			
			/*if(dto.getContent() != null){
				this.orderList = new ArrayList<OrderVo>();
				for (OrderBo obo : dto.getContent()) {
					this.orderList.add(new OrderVo(obo));
				}
			}*/
		}
	}
	
	public class OrderVo{
		public OrderVo(OrderBo orderbo,ProductBo prodbo ,PackageBo packbo ,LanguageBo langbo,CurrencyBo currencybo) {
			if(orderbo != null){
				this.id = orderbo.getId();
				this.orderNo = orderbo.getNo();
				this.orderStatusId = orderbo.getOrderStateId();
				this.orderStatusName = OrderStateEnum.getOrderStatusName(this.orderStatusId); 
				
				this.goOffDate = orderbo.getGoOffDate();
				this.goOffTime = orderbo.getGoOffTime();
				this.adultNum = orderbo.getAdultNum();
				this.childNum = orderbo.getChildNum();
				//产品小计
				this.perPrice = this.price = orderbo.getPrice().doubleValue();
				this.payType = orderbo.getPayType();
				this.prePayPrice = orderbo.getPrePayPrice().doubleValue();
				this.adultPrice = orderbo.getAdultPrice().doubleValue();
				this.childPrice = orderbo.getChildPrice().doubleValue();
				this.obligation = orderbo.getObligation().doubleValue();
				this.cancelMsg =  orderbo.getCancelMsg();
			}
			
			if(prodbo != null){
				this.productId = prodbo.getId();
				this.productTypeId = prodbo.getTypeId();
				this.productCoverPicUrl = prodbo.getCoverPic();
				this.productName = prodbo.getName();
				this.startCity = prodbo.getStartCity();
			}
			
			if(packbo != null){
				this.packageId = packbo.getId();
				this.packageName = packbo.getName();
			}
			
			if(langbo != null){
				this.languageId = langbo.getId();
				this.languageName = langbo.getName();
			}
			
			if(currencybo != null){
				this.currencyType = currencybo.getName();
			}
		}	
		
		/**
	     * @主键.
	     */
		private Long id;
		
		private String orderNo;
		
		/**
		 * @产品ID.
		 */
		private Long productId;
		
		/**
		 * @产品类型ID.
		 */
		private Integer productTypeId;
		
	    /**
	     * @名称.
	     */
		private String productName;
		
		private Long packageId;
		
		private String packageName;

		private Integer languageId;
		
		private String languageName;
		
		
		 /**
	     * @封面图.
	     */
	    private String productCoverPicUrl; 
	    private Date goOffDate; 
	    private Time goOffTime; 
	    private Integer adultNum; 
	    private Integer childNum; 
	    
	    private String startCity; 
	    /**
	     * @总价.
	     */
	    private Double price; 
	    private Double adultPrice; 
	    private Double childPrice; 
	    /**
	     * @产品小计
	     */
	    private Double perPrice; 
	    
	    private Integer payType;
	    
	    private Double prePayPrice;
	    
	    private Double obligation;

	    private String currencyType;
	    
	    private String cancelMsg;

	    
	    private Integer orderStatusId;
	    
	    private String orderStatusName;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getOrderNo() {
			return orderNo;
		}

		public void setOrderNo(String orderNo) {
			this.orderNo = orderNo;
		}

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

		public String getProductName() {
			return productName;
		}

		public void setProductName(String productName) {
			this.productName = productName;
		}

		public Long getPackageId() {
			return packageId;
		}

		public void setPackageId(Long packageId) {
			this.packageId = packageId;
		}

		public String getPackageName() {
			return packageName;
		}

		public void setPackageName(String packageName) {
			this.packageName = packageName;
		}

		public Integer getLanguageId() {
			return languageId;
		}

		public void setLanguageId(Integer languageId) {
			this.languageId = languageId;
		}

		public String getLanguageName() {
			return languageName;
		}

		public void setLanguageName(String languageName) {
			this.languageName = languageName;
		}

		public String getProductCoverPicUrl() {
			return productCoverPicUrl;
		}

		public void setProductCoverPicUrl(String productCoverPicUrl) {
			this.productCoverPicUrl = productCoverPicUrl;
		}


		public Date getGoOffDate() {
			return goOffDate;
		}

		public void setGoOffDate(Date goOffDate) {
			this.goOffDate = goOffDate;
		}

		public Time getGoOffTime() {
			return goOffTime;
		}

		public void setGoOffTime(Time goOffTime) {
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

		public String getStartCity() {
			return startCity;
		}

		public void setStartCity(String startCity) {
			this.startCity = startCity;
		}

		public Double getPrice() {
			return price;
		}

		public void setPrice(Double price) {
			this.price = price;
		}

		public Double getAdultPrice() {
			return adultPrice;
		}

		public void setAdultPrice(Double adultPrice) {
			this.adultPrice = adultPrice;
		}

		public Double getChildPrice() {
			return childPrice;
		}

		public void setChildPrice(Double childPrice) {
			this.childPrice = childPrice;
		}

		public Double getPerPrice() {
			return perPrice;
		}

		public void setPerPrice(Double prePrice) {
			this.perPrice = prePrice;
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

		public String getCurrencyType() {
			return currencyType;
		}

		public void setCurrencyType(String currencyType) {
			this.currencyType = currencyType;
		}

		public String getCancelMsg() {
			return cancelMsg;
		}

		public void setCancelMsg(String cancelMsg) {
			this.cancelMsg = cancelMsg;
		}

		public Integer getOrderStatusId() {
			return orderStatusId;
		}

		public void setOrderStatusId(Integer orderStatusId) {
			this.orderStatusId = orderStatusId;
		}

		public String getOrderStatusName() {
			return orderStatusName;
		}

		public void setOrderStatusName(String orderStatusName) {
			this.orderStatusName = orderStatusName;
		}
	}
	
	

	public Integer getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Integer orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageAmount() {
		return pageAmount;
	}

	public void setPageAmount(Integer pageAmount) {
		this.pageAmount = pageAmount;
	}

	public List<OrderVo> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<OrderVo> orderList) {
		this.orderList = orderList;
	}
	
}
