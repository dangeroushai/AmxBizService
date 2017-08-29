package com.amx.bizservice.model.bo;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


public class OrderBo{
	
	  /**
     * @主键.
     */
    private Long id; 
    

    /**
     * @订单状态.
     */
    private Integer orderStateId; 


    /**
     * @儿童单价.
     */
    private BigDecimal childPrice; 

    /**
     * @产品类型ID.
     */
    private Integer productTypeId; 

    /**
     * @用户备注.
     */
    private String remark;
    
    /**
     * @系统备注.
     */
    private String sysRemark;
    
    /**
     * @取消订单提示.
     */
    private String cancelMsg; 

    /**
     * @订单编号.
     */
    private String no; 

    /**
     * @待付币种.
     */
    private Integer currencyId;
    
    /**
     * @待付币种.
     */
    private String currencyName; 

    /**
     * @套餐ID.
     */
    private Long packageId; 
    /**
     * @套餐ID.
     */
    private String packageName; 

    /**
     * @出发日期.
     */
    private Date goOffDate; 

    /**
     * @付款时间.
     */
    private Timestamp payTime; 

    /**
     * @集合方式.
     */
    private Integer gatherWay; 

    /**
     * @待付金额.
     */
    private BigDecimal obligation; 

    /**
     * @接送酒店.
     */
    private String hotelName; 

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
     * @服务费.
     */
    private BigDecimal serviceFee; 

    /**
     * @交易编号.
     */
    private String tradeNo; 

    /**
     * @成人单价.
     */
    private BigDecimal adultPrice; 

    /**
     * @下单时间.
     */
    private Timestamp orderTime; 

    /**
     * @优惠码.
     */
    private String udid; 

    /**
     * @语言ID.
     */
    private Integer languageId;
    
    /**
     * @语言ID.
     */
    private String languageName; 

    /**
     * @产品ID.
     */
    private Long productId;
    
    private String productName; 

    /**
     * @酒店地址.
     */
    private String hotelAddr; 

    /**
     * @优惠金额.
     */
    private BigDecimal discount; 

    /**
     * @订单总价.
     */
    private BigDecimal price; 

    /**
     * @出发时间.
     */
    private Time goOffTime; 

    /**
     * @预付金额.
     */
    private BigDecimal prePayPrice; 

    /**
     * @付款类型.
     */
    private Integer payType; 

    /**
     * @付款方式.
     */
    private Integer payWay; 

    /**
     * @实付金额.
     */
    private BigDecimal realPayment;
    
    private List<TravellerBo> travellerList; 
    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getOrderStateId() {
		return orderStateId;
	}

	public void setOrderStateId(Integer orderStateId) {
		this.orderStateId = orderStateId;
	}

	public BigDecimal getChildPrice() {
		return childPrice;
	}

	public void setChildPrice(BigDecimal childPrice) {
		this.childPrice = childPrice;
	}

	public Integer getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(Integer productTypeId) {
		this.productTypeId = productTypeId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public Integer getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Integer currencyId) {
		this.currencyId = currencyId;
	}

	public Long getPackageId() {
		return packageId;
	}

	public void setPackageId(Long packageId) {
		this.packageId = packageId;
	}

	public String getCancelMsg() {
		return cancelMsg;
	}

	public void setCancelMsg(String cancelMsg) {
		this.cancelMsg = cancelMsg;
	}

	public Date getGoOffDate() {
		return goOffDate;
	}

	public void setGoOffDate(Date goOffDate) {
		this.goOffDate = goOffDate;
	}

	public Timestamp getPayTime() {
		return payTime;
	}

	public void setPayTime(Timestamp payTime) {
		this.payTime = payTime;
	}

	public Integer getGatherWay() {
		return gatherWay;
	}

	public void setGatherWay(Integer gatherWay) {
		this.gatherWay = gatherWay;
	}

	public BigDecimal getObligation() {
		return obligation;
	}

	public void setObligation(BigDecimal obligation) {
		this.obligation = obligation;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
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

	public BigDecimal getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public BigDecimal getAdultPrice() {
		return adultPrice;
	}

	public void setAdultPrice(BigDecimal adultPrice) {
		this.adultPrice = adultPrice;
	}

	public Timestamp getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Timestamp orderTime) {
		this.orderTime = orderTime;
	}

	public String getUdid() {
		return udid;
	}

	public void setUdid(String udid) {
		this.udid = udid;
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

	public String getHotelAddr() {
		return hotelAddr;
	}

	public void setHotelAddr(String hotelAddr) {
		this.hotelAddr = hotelAddr;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Time getGoOffTime() {
		return goOffTime;
	}

	public void setGoOffTime(Time goOffTime) {
		this.goOffTime = goOffTime;
	}

	public BigDecimal getPrePayPrice() {
		return prePayPrice;
	}

	public void setPrePayPrice(BigDecimal prePayPrice) {
		this.prePayPrice = prePayPrice;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public Integer getPayWay() {
		return payWay;
	}

	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
	}

	public BigDecimal getRealPayment() {
		return realPayment;
	}

	public void setRealPayment(BigDecimal realPayment) {
		this.realPayment = realPayment;
	}
	
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getLanguageName() {
		return languageName;
	}
	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSysRemark() {
		return sysRemark;
	}

	public void setSysRemark(String sysRemark) {
		this.sysRemark = sysRemark;
	}

	public List<TravellerBo> getTravellerList() {
		return travellerList;
	}

	public void setTravellerList(List<TravellerBo> travellerList) {
		this.travellerList = travellerList;
	}

}
