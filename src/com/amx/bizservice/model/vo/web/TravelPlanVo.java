package com.amx.bizservice.model.vo.web;

import com.amx.bizservice.model.bo.CustomHodometerBo;
import com.amx.bizservice.model.bo.LanguageBo;
import com.amx.bizservice.model.bo.PackageBo;
import com.amx.bizservice.model.bo.ProductBo;
import com.amx.bizservice.util.StringUtil;


/**
 * 
 * @author DangerousHai
 *
 */
public class TravelPlanVo {

	/**
     * @主键.
     */
	private Long id;
	
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
	private String name;
	private String enName;
	private String address;
	private String longitude;
	private String latitude;
	private Double price; 
	private Float duration; 
	private String introduce;
	/**
	 * @封面图.
	 */
	private String coverPicUrl; 
	private Long packageId;
	
	private String packageName;

	private Integer languageId;
	
	private String languageName;
	
	private String goOffDate; 
	private String goOffTime; 
	private Integer adultNum; 
	private Integer childNum; 
	
    private BuyConditionVo buyCondition;

	public TravelPlanVo(CustomHodometerBo bo, ProductBo prodbo,
			PackageBo packbo, LanguageBo langbo) {
		this.id = bo.getId();
		this.productId = bo.getProductId();
		if(prodbo != null){
			this.productTypeId = prodbo.getTypeId();
			this.name = prodbo.getName();
			this.enName = prodbo.getSubTitle();
			this.address = prodbo.getAddress();
			this.longitude = prodbo.getLongitude();
			this.latitude = prodbo.getLatitude();
			this.price = prodbo.getPrice();
			this.duration = prodbo.getDuration();
			this.introduce = prodbo.getIntroduceHtml();
			this.coverPicUrl = prodbo.getCoverPic();
			
		}
		if(packbo != null){
			this.packageName = packbo.getName();
			this.packageId = packbo.getId();
		}
		this.languageId = bo.getLanguageId();
		if(langbo != null){
			this.languageName = langbo.getName();
		}
		this.goOffDate = StringUtil.getSdfDate(bo.getGoOffDate());
		this.goOffTime = StringUtil.getHMTime(bo.getGoOffTime());
		this.adultNum = bo.getAdultNum();
		this.childNum = bo.getChildNum();
		
		this.buyCondition = new BuyConditionVo(prodbo, packbo);
	}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Float getDuration() {
		return duration;
	}

	public void setDuration(Float duration) {
		this.duration = duration;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getCoverPicUrl() {
		return coverPicUrl;
	}

	public void setCoverPicUrl(String coverPicUrl) {
		this.coverPicUrl = coverPicUrl;
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

	public BuyConditionVo getBuyCondition() {
		return buyCondition;
	}

	public void setBuyCondition(BuyConditionVo buyCondition) {
		this.buyCondition = buyCondition;
	} 

}
