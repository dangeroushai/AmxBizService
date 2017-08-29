package com.amx.bizservice.service;

import com.amx.bizservice.model.bo.CommentBo;
import com.amx.bizservice.model.bo.UpdateResponseBo;
import com.amx.bizservice.model.dto.PageResponseDto;
import com.amx.bizservice.model.qo.PageQuery;


public interface CommentService{
	
	/**
	 * 根据查询条件获取购物车产品
	 * @param query
	 * @return
	 */
	PageResponseDto<CommentBo> getCommentList(PageQuery query);
	
	/**
	 * 添加产品
	 * @param bo
	 * @return
	 */
	UpdateResponseBo insertComment(CommentBo bo);
	
}
