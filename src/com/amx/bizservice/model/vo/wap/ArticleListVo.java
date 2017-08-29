package com.amx.bizservice.model.vo.wap;

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
    private Integer amount;
    private Integer pageIndex;
    private Integer pageAmount;
    private List<ArticleVo> articleList;

	public ArticleListVo(PageResponseDto<ArticleBo> dto) {
		if(dto != null){
			this.amount = dto.getAmount().intValue();
			this.pageAmount = dto.getPageAmount();
			this.pageIndex = dto.getPageIndex();
			
			if(dto.getContent() != null){
				this.articleList = new ArrayList<ArticleVo>();
				for (ArticleBo abo : dto.getContent()) {
					this.articleList.add(new ArticleVo(abo));
				}
			}
		}
	}
	
	class ArticleVo{
		private Integer id;
		private Integer typeId;
		private String title;
		private String coverPicUrl;
		public ArticleVo(ArticleBo abo) {
			this.id = abo.getId();
			this.typeId = abo.getTypeId();
			this.title = abo.getName();
			this.coverPicUrl = abo.getCoverPic();
		}
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public Integer getTypeId() {
			return typeId;
		}
		public void setTypeId(Integer typeId) {
			this.typeId = typeId;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getCoverPicUrl() {
			return coverPicUrl;
		}
		public void setCoverPicUrl(String coverPicUrl) {
			this.coverPicUrl = coverPicUrl;
		}
		
	}
	
	public Integer getArticleAmount() {
		return amount;
	}

	public void setArticleAmount(Integer articleAmount) {
		this.amount = articleAmount;
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
