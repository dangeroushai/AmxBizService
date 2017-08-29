package com.amx.bizservice.model.vo.wap;

import java.sql.Time;
import java.util.Date;

import com.amx.bizservice.enums.OrderStateEnum;
import com.amx.bizservice.model.bo.CurrencyBo;
import com.amx.bizservice.model.bo.LanguageBo;
import com.amx.bizservice.model.bo.OrderBo;
import com.amx.bizservice.model.bo.PackageBo;
import com.amx.bizservice.model.bo.ProductBo;
import com.amx.bizservice.model.bo.TravellerBo;
import com.amx.bizservice.util.StringUtil;

/**
 * 订单
 * @author DangerousHai
 *
 */
public class OrderVo{

	public OrderVo(OrderBo orderbo,ProductBo prodbo ,PackageBo packbo ,LanguageBo langbo,CurrencyBo currencybo) {
		if(orderbo != null){
			this.id = orderbo.getId();
			this.orderTime = StringUtil.getYMDHMSDate(new Date(orderbo.getOrderTime().getTime()));
			this.orderNo = orderbo.getNo();
			this.orderStatusId = orderbo.getOrderStateId();
			this.orderStatusName = OrderStateEnum.getOrderStatusName(this.orderStatusId); 
			this.goOffDate = orderbo.getGoOffDate();
			this.goOffTime = orderbo.getGoOffTime();
			this.adultNum = orderbo.getAdultNum();
			this.childNum = orderbo.getChildNum();
			
			//产品小计
			this.perPrice = orderbo.getPrice().doubleValue();
			this.adultPrice = orderbo.getAdultPrice().doubleValue();
			this.childPrice = orderbo.getChildPrice().doubleValue();
			this.payType = orderbo.getPayType();
			this.prePayPrice = orderbo.getPrePayPrice().doubleValue();
			this.obligation = orderbo.getObligation().doubleValue();
			this.serviceFee = orderbo.getServiceFee().doubleValue();
			this.discount = orderbo.getDiscount().doubleValue();
			//订单合计 = 产品小计 + 服务费(serviceFee ) - 优惠促销(discount )
			this.price = this.perPrice + this.serviceFee -  this.discount;
			
			this.realPayment = orderbo.getRealPayment().doubleValue();
			this.payWayId = orderbo.getPayWay();
			this.payWayName = this.orderStatusId > OrderStateEnum.WAIT_PAY.getId() ? "支付宝" : "未支付";
			
			this.remark = orderbo.getRemark();
			this.cancelMsg =  orderbo.getCancelMsg();
			
			if(orderbo.getTravellerList() != null){
				for (TravellerBo traveller : orderbo.getTravellerList()) {
					if(traveller.getIsPrincipal()){
						/*联系人信息*/
						this.lastName = traveller.getLastName();
						this.firstName = traveller.getFirstName();
						this.passport = traveller.getPassport();
						this.phone = traveller.getPhone();
						this.email = traveller.getEmail();
						
						break;
					}
				}
			}
		}
		
		if(prodbo != null){
			this.productId = prodbo.getId();
			this.productTypeId = prodbo.getTypeId();
			this.productCoverPicUrl = prodbo.getCoverPic();
			this.productName = prodbo.getName();
		}
		
		if(packbo != null){
			this.packageId = packbo.getId();
			this.packageName = packbo.getName();
		}
		
		if(currencybo != null){
			this.currencyType = currencybo.getName();
		}
		
		if(langbo != null){
			this.languageId = langbo.getId();
			this.languageName = langbo.getName();
		}
	}	
	
	/**
     * @主键.
     */
	private Long id;
	
	private String orderTime;
	
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

    private Double serviceFee;
    
    private Double discount;
    
    private Integer orderStatusId;
    
    private String orderStatusName;
   
    private Double realPayment;
    
    private Integer payWayId;
    
    private String payWayName;
    
    private String remark;

    private String firstName;
    
    private String lastName;
    
    private String passport;
    
    private String phone;
    
    private String email;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
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

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getProductCoverPicUrl() {
		return productCoverPicUrl;
	}

	public void setProductCoverPicUrl(String productCoverPicUrl) {
		this.productCoverPicUrl = productCoverPicUrl;
	}

	public Double getPerPrice() {
		return perPrice;
	}

	public void setPerPrice(Double perPrice) {
		this.perPrice = perPrice;
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

	public Double getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(Double serviceFee) {
		this.serviceFee = serviceFee;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
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

	public Double getRealPayment() {
		return realPayment;
	}

	public void setRealPayment(Double realPayment) {
		this.realPayment = realPayment;
	}

	public Integer getPayWayId() {
		return payWayId;
	}

	public void setPayWayId(Integer payWayId) {
		this.payWayId = payWayId;
	}

	public String getPayWayName() {
		return payWayName;
	}

	public void setPayWayName(String payWayName) {
		this.payWayName = payWayName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
}