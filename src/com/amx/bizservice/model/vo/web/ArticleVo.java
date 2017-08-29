package com.amx.bizservice.model.vo.web;

import java.util.ArrayList;
import java.util.List;

import com.amx.bizservice.config.CommonConfig;
import com.amx.bizservice.model.bo.AdvertisementBo;
import com.amx.bizservice.model.bo.ArticleBo;
import com.amx.bizservice.model.bo.AttributeBo;
import com.amx.bizservice.util.StringUtil;

public class ArticleVo {
	
	private Integer id;
    private String title;
    private String subTitle;
    private String intro;
    private String pubDate;
    private String author;
    private String content;
    private String source;
    private Integer readTimes;
    private String coverPicUrl;
    private List<AttributeVo> categories;


	/**
	 * 首页文章推荐
	 * @param abo 首页广告
	 * @param articleBo 广告关联的文章
	 */
	public ArticleVo(AdvertisementBo abo, ArticleBo articleBo) {
		if(abo != null && articleBo != null){
			this.id = abo.getLinkArticleId();
			//优先使用广告的信息，没有设置则使用文章信息
			this.title = "".equals(abo.getTitle().trim()) ? articleBo.getName() : abo.getTitle();
			this.intro = "".equals(abo.getSubTitle().trim()) ? articleBo.getIntroduce() : abo.getSubTitle();
			this.coverPicUrl = abo.getCoverPic();
			if(! this.coverPicUrl.contains(CommonConfig.UPLOAD_DIRECTORY)){
				this.coverPicUrl = articleBo.getCoverPic();
			}
		}
	}

	/**
	 * 
	 * @param abo 文章
	 * @param adbo 用于提供顶部图的广告(isSnap = false 时必传)
	 * @param isSnap 是否只显示快照信息
	 */
	public ArticleVo(ArticleBo abo, boolean isSnap) {
		this.id = abo.getId();
		this.title = abo.getName();
		this.coverPicUrl = abo.getCoverPic();
		this.intro = abo.getIntroduce();
		if(abo.getCreateTime() != null){
			this.pubDate = StringUtil.getSdfDate(abo.getCreateTime());
		}
		if(!isSnap){
			this.author = abo.getAuthor();
			this.subTitle = abo.getSubTitle();
			this.content = abo.getContent();
			this.source = abo.getSource();
			this.readTimes = abo.getPageView();
			if(abo.getAttrList() != null){
				this.categories = new ArrayList<AttributeVo>();
				for (AttributeBo attrbo : abo.getAttrList()) {
					this.categories.add(new AttributeVo(attrbo));
				}
			}
		}
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



	public Integer getReadTimes() {
		return readTimes;
	}



	public void setReadTimes(Integer readTimes) {
		this.readTimes = readTimes;
	}



	public List<AttributeVo> getCategories() {
		return categories;
	}



	public void setCategories(List<AttributeVo> categories) {
		this.categories = categories;
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



	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}



	public String getCoverPicUrl() {
		return coverPicUrl;
	}



	public void setCoverPicUrl(String coverPicUrl) {
		this.coverPicUrl = coverPicUrl;
	}


}
