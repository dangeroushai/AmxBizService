package com.amx.bizservice.model.vo.wap;

import com.amx.bizservice.model.bo.ArticleBo;
import com.amx.bizservice.util.StringUtil;

public class ArticleVo {
	
	private Integer id;
	private Integer typeId;
    private String title;
    private String subTitle;
    private String pubDate;
    private String author;
    private String content;
    private String source;
    private String coverPicUrl;

	/**
	 * 
	 * @param abo 文章
	 * @param adbo 用于提供顶部图的广告(isSnap = false 时必传)
	 * @param isSnap 是否只显示快照信息
	 */
	public ArticleVo(ArticleBo abo) {
		this.id = abo.getId();
		this.title = abo.getName();
		this.coverPicUrl = abo.getCoverPic();
		if(abo.getCreateTime() != null){
			this.pubDate = StringUtil.getSdfDate(abo.getCreateTime());
		}
		this.author = abo.getAuthor();
		this.subTitle = abo.getSubTitle();
		this.content = abo.getContent();
		this.source = abo.getSource();
		this.typeId = abo.getTypeId();
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}



	public String getPubDate() {
		return pubDate;
	}



	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public Integer getTypeId() {
		return typeId;
	}



	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}



	public String getAuthor() {
		return author;
	}



	public void setAuthor(String author) {
		this.author = author;
	}



	public String getSource() {
		return source;
	}



	public void setSource(String source) {
		this.source = source;
	}



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
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
