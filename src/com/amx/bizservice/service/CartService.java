package com.amx.bizservice.service;

import java.util.List;

import com.amx.bizservice.model.bo.CartBo;
import com.amx.bizservice.model.bo.UpdateResponseBo;
import com.amx.bizservice.model.dto.PageResponseDto;
import com.amx.bizservice.model.qo.PageQuery;


public interface CartService{
	
	/**
	 * 根据查询条件获取购物车产品
	 * @param query
	 * @return
	 */
	PageResponseDto<CartBo> getCartList(PageQuery query);
	
	/**
	 * 添加产品
	 * @param bo
	 * @return
	 */
	UpdateResponseBo insertCart(CartBo bo);
	
	
	/**
	 * 删除产品
	 * @param CartId
	 * @return
	 */
	UpdateResponseBo deleteCart(long userId, long cartId);
	/**
	 * 获取购物车数目
	 * @param CartId
	 * @return
	 */
	Integer countCart(long userId);

	UpdateResponseBo deleteCart(long userId, List<Long> idList);

	UpdateResponseBo updateCart(CartBo bo);

	CartBo getCart(long userId, Long cartId);

	void saveTempCart(Long tempUserId, Long userId);

}
