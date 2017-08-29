package com.amx.bizservice.model.vo.web;

import java.util.ArrayList;
import java.util.List;

import com.amx.bizservice.model.bo.ArticleBo;
import com.amx.bizservice.model.dto.PageResponseDto;

/**
 * 文章列表
 * @author DangerousHai
 *
 */
public class ArticleListVo {
    private Integer articleAmount;
    private Integer pageIndex;
    private Integer pageAmount;
    private List<ArticleVo> articleList;

	public ArticleListVo(PageResponseDto<ArticleBo> dto,boolean isShowIntroduce) {
		if(dto != null){
			this.articleAmount = dto.getAmount().intValue();
			this.pageAmount = dto.getPageAmount();
			this.pageIndex = dto.getPageIndex();
			//外部处理
			//this.articleList = 
			
			if(dto.getContent() != null){
				this.articleList = new ArrayList<ArticleVo>();
				for (ArticleBo abo : dto.getContent()) {
					if(! isShowIntroduce){
						abo.setIntroduce(null);
					}
					this.articleList.add(new ArticleVo(abo , true));
				}
			}
		}
	}
	
	public Integer getArticleAmount() {
		return articleAmount;
	}

	public void setArticleAmount(Integer articleAmount) {
		this.articleAmount = articleAmount;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageAmount() {
		return pageAmount;
	}

	public void setPageAmount(Integer pageAmount) {
		this.pageAmount = pageAmount;
	}

	public List<ArticleVo> getArticleList() {
		return articleList;
	}

	public void setArticleList(List<ArticleVo> articleList) {
		this.articleList = articleList;
	}
	
}
