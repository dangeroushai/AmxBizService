package com.amx.bizservice.model.vo.web;

import java.util.ArrayList;
import java.util.List;

import com.amx.bizservice.model.bo.ArticleBo;

public class FooterVo {
	
    private String columnTitle;
    private List<ArticleVo> columnList;
    
	public FooterVo(String typeName, List<ArticleBo> articleList) {
		this.columnTitle = typeName;
		if(articleList != null){
			this.columnList = new ArrayList<ArticleVo>();
			for (ArticleBo articleBo : articleList) {
				articleBo.setCoverPic(null);
				articleBo.setCreateTime(null);
				this.columnList.add(new ArticleVo(articleBo, true));
			}
			
		}
	}
	public String getColumnTitle() {
		return columnTitle;
	}
	public void setColumnTitle(String columnTitle) {
		this.columnTitle = columnTitle;
	}
	public List<ArticleVo> getColumnList() {
		return columnList;
	}
	public void setColumnList(List<ArticleVo> columnList) {
		this.columnList = columnList;
	}
}
