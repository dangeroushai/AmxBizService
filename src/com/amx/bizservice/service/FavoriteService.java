package com.amx.bizservice.service;

import java.util.List;

import com.amx.bizservice.model.bo.FavoriteBo;
import com.amx.bizservice.model.bo.UpdateResponseBo;
import com.amx.bizservice.model.dto.PageResponseDto;
import com.amx.bizservice.model.qo.PageQuery;


public interface FavoriteService{
	
	/**
	 * 根据查询条件获取收藏产品
	 * @param query
	 * @return
	 */
	PageResponseDto<FavoriteBo> getFavoriteList(PageQuery query);
	
	/**
	 * 收藏产品
	 * @param productId
	 * @return
	 */
	UpdateResponseBo insertFavorite(long userId , long productId);
	
	
	/**
	 * 取消收藏产品
	 * @param favoriteId
	 * @return
	 */
	UpdateResponseBo deleteFavoriteById(long userId , long favoriteId);

	/**
	 * 取消收藏产品
	 * @param productId
	 * @return
	 */
	UpdateResponseBo deleteFavoriteByProductId(long userId, long productId);

	List<Long> getFavoriteProductIdsByUserId(Long userId);
}
