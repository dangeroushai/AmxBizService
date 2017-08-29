package com.amx.bizservice.model.bo;

import java.util.List;

/**
 * 产品扩展业务对象，所有关联对象都只指明ID
 * @author DangerousHai
 *
 */
public class ProductExtBo {
	

	/**
     * @主键.
     */
	private Long id;
	
	/**
	 * 关联产品
	 */
	private Long productId;
	
    /**
     * @车导产品（Car And Tour).
     */
    private List<Long> catProductIdList;
    /**
     * @车导产品ID（Car And Tour).
     */
    private List<ProductBo> catProductList;
    
    /**
     * @酒店产品ID
     */
    private List<Long> hotelProductIdList;
    /**
     * @酒店产品
     */
    private List<ProductBo> hotelProductList;
    
    /**
     * @是否包含机票
     */
    private Boolean  isTicket;
    
    /**
     * @机票产品ID
     */
    private List<Long> ticketProductIdList;
    /**
     * @机票产品
     */
    private List<ProductBo> ticketProductList;
    
    /**
     * @周边产品
     */
    private List<Long> additionProductIdList; 
    /**
     * @周边产品ID
     */
    private List<ProductBo> additionProductList; 
    
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

	public List<Long> getCatProductIdList() {
		return catProductIdList;
	}

	public void setCatProductIdList(List<Long> catProductIdList) {
		this.catProductIdList = catProductIdList;
	}

	public List<Long> getHotelProductIdList() {
		return hotelProductIdList;
	}

	public void setHotelProductIdList(List<Long> hotelProductIdList) {
		this.hotelProductIdList = hotelProductIdList;
	}

	public Boolean getIsTicket() {
		return isTicket;
	}

	public void setIsTicket(Boolean isTicket) {
		this.isTicket = isTicket;
	}

	public List<Long> getTicketProductIdList() {
		return ticketProductIdList;
	}

	public void setTicketProductIdList(List<Long> ticketProductIdList) {
		this.ticketProductIdList = ticketProductIdList;
	}

	public List<Long> getAdditionProductIdList() {
		return additionProductIdList;
	}

	public void setAdditionProductIdList(List<Long> additionProductIdList) {
		this.additionProductIdList = additionProductIdList;
	}


	public List<ProductBo> getCatProductList() {
		return catProductList;
	}


	public void setCatProductList(List<ProductBo> catProductList) {
		this.catProductList = catProductList;
	}


	public List<ProductBo> getHotelProductList() {
		return hotelProductList;
	}


	public void setHotelProductList(List<ProductBo> hotelProductList) {
		this.hotelProductList = hotelProductList;
	}


	public List<ProductBo> getTicketProductList() {
		return ticketProductList;
	}


	public void setTicketProductList(List<ProductBo> ticketProductList) {
		this.ticketProductList = ticketProductList;
	}


	public List<ProductBo> getAdditionProductList() {
		return additionProductList;
	}


	public void setAdditionProductList(List<ProductBo> additionProductList) {
		this.additionProductList = additionProductList;
	}

}
