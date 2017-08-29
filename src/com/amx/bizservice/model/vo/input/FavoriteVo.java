package com.amx.bizservice.model.vo.input;


/**
 * 
 * @author DangerousHai
 *
 */

public class FavoriteVo {

	/**
	 * 产品ID.
	 */
	private Long productId;

	/**
	 * @产品TYPE_ID.
	 */
	private Integer productTypeId;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(Integer typeId) {
		this.productTypeId = typeId;
	}

}
