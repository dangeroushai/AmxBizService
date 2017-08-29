package com.amx.bizservice.model.vo.web.input;
/**
 * 我的行程 - 单项
 * @author DangerousHai
 *
 */
public class ItemVo{
	
	/**
     * @product.id
     */
	private Long id;
	  /**
     * @product.typeId
     */
    private Integer typeId; 

	private Integer languageId;
	private Long packageId;
	
    private String goOffDate; 
    private String goOffTime; 
    private Integer adultNum; 
    private Integer childNum; 
    
    private String remark;
    private Integer itemOrder; 
    

	public Integer getItemOrder() {
		return itemOrder;
	}

	public void setItemOrder(Integer itemOrder) {
		this.itemOrder = itemOrder;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}


	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}

	public Long getPackageId() {
		return packageId;
	}

	public void setPackageId(Long packageId) {
		this.packageId = packageId;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
