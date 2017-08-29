package com.amx.bizservice.model.vo.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.amx.bizservice.enums.OrderStateEnum;
import com.amx.bizservice.model.bo.CurrencyBo;
import com.amx.bizservice.model.bo.LanguageBo;
import com.amx.bizservice.model.bo.OrderBo;
import com.amx.bizservice.model.bo.PackageBo;
import com.amx.bizservice.model.bo.ProductBo;
import com.amx.bizservice.model.bo.TravellerBo;
import com.amx.bizservice.model.vo.ContactVo;
import com.amx.bizservice.util.StringUtil;

/**
 * 订单
 * @author DangerousHai
 *
 */
public class OrderVo{

	public OrderVo(OrderBo orderbo,ProductBo prodbo ,PackageBo packbo ,LanguageBo langbo,CurrencyBo currencybo) {
		if(prodbo != null){
			this.id = orderbo.getId();
			this.orderTime = StringUtil.getSdfDate(new Date(orderbo.getOrderTime().getTime()));
			this.orderNo = orderbo.getNo();
			this.orderStatusId = orderbo.getOrderStateId();
			this.orderStatusName = OrderStateEnum.getOrderStatusName(this.orderStatusId); 
			
			this.productId = orderbo.getProductId();
			this.productTypeId = prodbo.getTypeId();
			this.productCoverPicUrl = prodbo.getCoverPic();
			this.productName = prodbo.getName();
			this.childRule = prodbo.getChildRule();
			
			this.packageId = orderbo.getPackageId();
			this.packageName = packbo.getName();
			
			this.languageId = orderbo.getLanguageId();
			this.languageName = langbo.getName();
			
			this.goOffDate = StringUtil.getSdfDate(orderbo.getGoOffDate());
			this.goOffTime = StringUtil.getHMTime(orderbo.getGoOffTime());
			this.adultNum = orderbo.getAdultNum();
			this.childNum = orderbo.getChildNum();
			this.childRule = prodbo.getChildRule();
			this.gatherWay = orderbo.getGatherWay();
			this.hotelAddr = orderbo.getHotelAddr();
			this.hotelName = orderbo.getHotelName();
			
			//产品小计
			this.perPrice = orderbo.getPrice().doubleValue();
			this.payType = orderbo.getPayType();
			this.prePayPrice = orderbo.getPrePayPrice().doubleValue();
			this.obligation = orderbo.getObligation().doubleValue();
			this.currencyType = currencybo.getName();
			this.serviceFee = orderbo.getServiceFee().doubleValue();
			this.discount = orderbo.getDiscount().doubleValue();
			//订单合计 = 产品小计 + 服务费(serviceFee ) - 优惠促销(discount )
			this.price = this.perPrice + this.serviceFee -  this.discount;
			
			this.realPayment = orderbo.getRealPayment().doubleValue();
			this.payWayId = orderbo.getPayWay();
			this.payWayName = this.orderStatusId > OrderStateEnum.WAIT_PAY.getId() ? "支付宝" : "未支付";
			
			this.remark = orderbo.getRemark();
			this.cancelMsg =  orderbo.getCancelMsg();
			this.cancelReason = orderbo.getSysRemark();
			
			if(orderbo.getTravellerList() != null){
				this.travellers = new ArrayList<ContactVo>(); 
				for(TravellerBo tbo : orderbo.getTravellerList()) {
					this.travellers .add(new ContactVo(tbo));
				}
			}
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
    private String goOffDate; 
    private String goOffTime; 
    private Integer adultNum; 
    private Integer childNum; 
    
    private String childRule;
	
    /**
     * @总价.
     */
    private Double price; 
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
    
    private List<ContactVo> travellers;
    
    private Integer gatherWay;

    private String hotelName;
    
    private String hotelAddr;
    
    private String remark;
    
    private String cancelReason;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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


	public String getGoOffDate() {
		return goOffDate;
	}

	public void setGoOffDate(String goOffDate) {
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

	public String getChildRule() {
		return childRule;
	}

	public void setChildRule(String childRule) {
		this.childRule = childRule;
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

	public List<ContactVo> getTravellers() {
		return travellers;
	}

	public void setTravellers(List<ContactVo> travellers) {
		this.travellers = travellers;
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

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cacelReason) {
		this.cancelReason = cacelReason;
	}
	
}