package com.amx.bizservice.model.bo;

import java.math.BigDecimal;
import java.util.List;
import java.util.TreeMap;

public class PackageBo{
	

	/**
     * @主键.
     */
    private Long id; 

    /**
     * @人数上限.
     */
    private Integer maxPersonNum; 

    /**
     * @单人起始价.
     */
    private BigDecimal startPrice; 

    /**
     * @时长.
     */
    private Float duration; 

    /**
     * @成人上限.
     */
    private Integer maxAdultNum; 

    /**
     * @介绍.
     */
    private String description; 

    /**
     * @套餐名称.
     */
    private String name; 

    /**
     * @人数下限.
     */
    private Integer minPersonNum; 

    /**
     * @儿童上限.
     */
    private Integer maxChildNum;
    
    /**
     * 成人单价-和语言同序：成人按人数计价时有值
     */
    private List<Double> adultPricePerPersonList;
    /**
    * 成人单人待付价-和语言同序：成人按人数计价时有值
    */
    private List<Double> adultObligationPricePerPersonList;
    /**
     * 成人价格梯度-和语言同序：成人按梯度计价时有值
     */
    private List<TreeMap<Integer,Double>> adultPriceLevelList;
    /**
     * 成人待付价格梯度-和语言同序：成人按梯度计价且为待付产品时有值
     */
    private List<TreeMap<Integer,Double>> adultObligatioPriceLevelList;
    /**
     * 儿童单价-和语言同序：儿童按人数计价时有值
     */
    private List<Double> childPricePerPersonList;
    /**
     * 儿童单人待付价-和语言同序：儿童按人数计价时有值
     */
     private List<Double> childObligationPricePerPersonList;
    /**
     * 儿童价格梯度-和语言同序：儿童按梯度计价时有值
     */
    private List<TreeMap<Integer,Double>> childPriceLevelList;
    /**
     * 儿童待付价格梯度-和语言同序：儿童按梯度计价且为待付产品时有值
     */
    private List<TreeMap<Integer,Double>> childObligatioPriceLevelList;
    
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getMaxPersonNum() {
		return maxPersonNum;
	}

	public void setMaxPersonNum(Integer maxPersonNum) {
		this.maxPersonNum = maxPersonNum;
	}

	public BigDecimal getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(BigDecimal startPrice) {
		this.startPrice = startPrice;
	}

	public Float getDuration() {
		return duration;
	}

	public void setDuration(Float duration) {
		this.duration = duration;
	}

	public Integer getMaxAdultNum() {
		return maxAdultNum;
	}

	public void setMaxAdultNum(Integer maxAdultNum) {
		this.maxAdultNum = maxAdultNum;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getMinPersonNum() {
		return minPersonNum;
	}

	public void setMinPersonNum(Integer minPersonNum) {
		this.minPersonNum = minPersonNum;
	}

	public Integer getMaxChildNum() {
		return maxChildNum;
	}

	public void setMaxChildNum(Integer maxChildNum) {
		this.maxChildNum = maxChildNum;
	}

	public List<Double> getAdultPricePerPersonList() {
		return adultPricePerPersonList;
	}

	public void setAdultPricePerPersonList(List<Double> adultPricePerPersonList) {
		this.adultPricePerPersonList = adultPricePerPersonList;
	}

	public List<TreeMap<Integer,Double>> getAdultPriceLevelList() {
		return adultPriceLevelList;
	}

	public void setAdultPriceLevelList(List<TreeMap<Integer,Double>> adultPriceLevelList) {
		this.adultPriceLevelList = adultPriceLevelList;
	}

	public List<TreeMap<Integer,Double>> getAdultObligatioPriceLevelList() {
		return adultObligatioPriceLevelList;
	}

	public void setAdultObligatioPriceLevelList(
			List<TreeMap<Integer,Double>> adultObligatioPriceLevelList) {
		this.adultObligatioPriceLevelList = adultObligatioPriceLevelList;
	}

	public List<Double> getChildPricePerPersonList() {
		return childPricePerPersonList;
	}

	public void setChildPricePerPersonList(List<Double> childPricePerPersonList) {
		this.childPricePerPersonList = childPricePerPersonList;
	}

	public List<TreeMap<Integer,Double>> getChildPriceLevelList() {
		return childPriceLevelList;
	}

	public void setChildPriceLevelList(List<TreeMap<Integer,Double>> childPriceLevelList) {
		this.childPriceLevelList = childPriceLevelList;
	}

	public List<TreeMap<Integer,Double>> getChildObligatioPriceLevelList() {
		return childObligatioPriceLevelList;
	}

	public void setChildObligatioPriceLevelList(
			List<TreeMap<Integer,Double>> childObligatioPriceLevelList) {
		this.childObligatioPriceLevelList = childObligatioPriceLevelList;
	}

	public List<Double> getAdultObligationPricePerPersonList() {
		return adultObligationPricePerPersonList;
	}

	public void setAdultObligationPricePerPersonList(
			List<Double> adultObligationPricePerPersonList) {
		this.adultObligationPricePerPersonList = adultObligationPricePerPersonList;
	}

	public List<Double> getChildObligationPricePerPersonList() {
		return childObligationPricePerPersonList;
	}

	public void setChildObligationPricePerPersonList(
			List<Double> childObligationPricePerPersonList) {
		this.childObligationPricePerPersonList = childObligationPricePerPersonList;
	}
	
}
