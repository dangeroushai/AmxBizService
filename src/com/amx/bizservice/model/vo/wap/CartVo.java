package com.amx.bizservice.model.vo.wap;

import com.amx.bizservice.model.bo.CartBo;
import com.amx.bizservice.model.bo.LanguageBo;
import com.amx.bizservice.model.bo.PackageBo;
import com.amx.bizservice.model.bo.PriceBo;
import com.amx.bizservice.model.bo.ProductBo;
import com.amx.bizservice.util.StringUtil;

/**
 * @author DangerousHai
 *
 */
public class CartVo {
	/**
	 * @主键.
	 */
	private Long id;

	private Integer languageId;
	private String languageName;

	private Long packageId;
	private String packageName;

	private String goOffDate;
	private String goOffTime;
	private Integer adultNum;
	private Integer childNum;
	/**
	 * 总价（优惠前价格）
	 */
	private Double price;
	private Double childPrice;
	private Double adultPrice;
	private Integer payType;
	private Double prePayPrice;
	private Double obligation;
	private String currencyType;

	private ProductVo product;

	public CartVo(CartBo cbo, ProductBo prodbo, PackageBo packbo,
			LanguageBo lbo, PriceBo pricebo) {
		if (cbo != null) {
			this.id = cbo.getId();
			this.languageId = cbo.getLanguageId();
			this.packageId = cbo.getPackageId();
			this.goOffDate = StringUtil.getSdfDate(cbo.getGoOffDate());
			this.goOffTime = StringUtil.getHMTime(cbo.getGoOffTime());
			this.adultNum = cbo.getAdultNum();
			this.childNum = cbo.getChildNum();
		}

		if (packbo != null) {
			this.packageName = packbo.getName();
		}

		if (lbo != null) {
			this.languageName = lbo.getName();
		}

		if (pricebo != null) {
			this.price = pricebo.getPrice().doubleValue();
			this.childPrice = pricebo.getChildPrice().doubleValue();
			this.adultPrice = pricebo.getAdultPrice().doubleValue();
			this.payType = pricebo.getPayType();
			this.prePayPrice = pricebo.getPrePayPrice().doubleValue();
			this.obligation = pricebo.getObligation().doubleValue();
			this.currencyType = pricebo.getCurrencyType();
		}

		if (prodbo != null) {
			this.product = new ProductVo(prodbo);
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public ProductVo getProduct() {
		return product;
	}

	public void setProduct(ProductVo product) {
		this.product = product;
	}

}
