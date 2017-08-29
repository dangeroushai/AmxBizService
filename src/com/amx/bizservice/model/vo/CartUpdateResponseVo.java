package com.amx.bizservice.model.vo;

import com.amx.bizservice.model.bo.UpdateResponseBo;


/**
 * 购物车编辑操作响应对象
 * @author DangerousHai
 *
 */
public class CartUpdateResponseVo {
	/**
	 * 成功与否
	 */
	private Boolean isSucceeded;
	/**
	 * 购物车数量
	 */
	private Integer cartAmount = 0;
	
	/**
	 * 消息
	 */
	private String msg;
	
	public CartUpdateResponseVo(){
		isSucceeded = false;
	}

	public CartUpdateResponseVo(UpdateResponseBo bo) {
		if(bo != null){
			this.isSucceeded = bo.getIsSucceeded();
			this.msg = bo.getMsg();
		}
	}

	public Boolean getIsSucceeded() {
		return isSucceeded;
	}

	public void setIsSucceeded(Boolean isSucceeded) {
		this.isSucceeded = isSucceeded;
	}

	public Integer getCartAmount() {
		return cartAmount;
	}

	public void setCartAmount(Integer cartAmount) {
		this.cartAmount = cartAmount;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
