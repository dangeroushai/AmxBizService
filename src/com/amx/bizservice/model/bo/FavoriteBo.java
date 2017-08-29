package com.amx.bizservice.model.bo;


/**
 * 
 * @author DangerousHai
 *
 */
public class FavoriteBo{
	

   /**
    * @主键.
    */
   private Long id; 


   /**
    * @用户ID.
    */
   private Long userId; 

   /**
    * @产品ID.
    */
   private Long productId; 

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
}
