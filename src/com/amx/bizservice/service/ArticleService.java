package com.amx.bizservice.service;

import java.util.List;

import com.amx.bizservice.model.bo.ArticleBo;
import com.amx.bizservice.model.dto.PageResponseDto;
import com.amx.bizservice.model.qo.ArticleQuery;


public interface ArticleService{

	/**
	 * 获取详细的文章信息
	 * @param pid
	 * @return
	 */
	ArticleBo getArticleDetail(int pid);
	
	/**
	 * 获取文章快照信息
	 * @param pid
	 * @return
	 */
	ArticleBo getArticleSnap(int pid);

	/**
	 * 获取文章列表
	 * @param query
	 * @return
	 */
	PageResponseDto<ArticleBo> getArticleList(ArticleQuery query);

	List<ArticleBo> getArticlesByAttrId(Integer attrId);
}
