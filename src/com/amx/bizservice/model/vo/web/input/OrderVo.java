package com.amx.bizservice.model.vo.web.input;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.amx.bizservice.model.vo.ContactVo;

/**
 * 
 * @author DangerousHai
 *
 */
public class OrderVo {

	/**
	 * @套餐ID.
	 */
	private Long packageId;

	/**
	 * @出发日期.
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	// 页面提交时的日期格式
	private Date goOffDate;

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
	 * @产品TYPE_ID.
	 */
	private Integer productTypeId;
	/**
	 * @产品ID.
	 */
	private Long productId;

	/**
	 * @出发时间.
	 */
	private Time goOffTime;

	private List<ContactVo> travellers;

	private Integer gatherWay;

	private String hotelName;

	private String hotelAddr;

	private String udid;

	private String remark;

	public Long getPackageId() {
		return packageId;
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

	public String getUdid() {
		return udid;
	}

	public void setUdid(String udid) {
		this.udid = udid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Integer getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(Integer productTypeId) {
		this.productTypeId = productTypeId;
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
