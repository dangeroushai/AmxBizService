package com.amx.bizservice.model.vo;

import com.amx.bizservice.model.bo.PackageBo;


public class PackageVo{

	/**
     * @主键.
     */
    private Long id; 

    /**
     * @套餐名称.
     */
    private String name; 
    /**
     * @时长.
     */
    private Float duration; 
    /**
     * @介绍.
     */
    private String sellPoint; 
    /**
     * @人数上限.
     */
    private Integer maxPersonNum; 

    /**
     * @成人上限.
     */
    private Integer maxAdultNum; 

    /**
     * @人数下限.
     */
    private Integer minPersonNum; 

    /**
     * @儿童上限.
     */
    private Integer maxChildNum;

    
    public PackageVo(){ }
    
	public PackageVo(PackageBo pbo) {
		if(pbo != null){
			this.id = pbo.getId();
			this.name = pbo.getName();
			this.duration = pbo.getDuration();
			this.sellPoint = pbo.getDescription();
			this.minPersonNum = pbo.getMinPersonNum();
			this.maxPersonNum = pbo.getMaxPersonNum();
			this.maxAdultNum = pbo.getMaxAdultNum();
			this.maxChildNum = pbo.getMaxChildNum();
		}
	}

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

	public String getSellPoint() {
		return sellPoint;
	}

	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
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
	
}
