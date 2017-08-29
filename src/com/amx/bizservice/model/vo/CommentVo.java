package com.amx.bizservice.model.vo;

import java.util.ArrayList;
import java.util.List;

import com.amx.bizservice.model.bo.CommentBo;
import com.amx.bizservice.util.PictureUtil;
import com.amx.bizservice.util.StringUtil;

/**
 * 购物车商品
 * @author DangerousHai
 *
 */
public class CommentVo{

	public CommentVo(CommentBo commentbo) {
		if(commentbo != null){
			this.commentator = commentbo.getUserName();
			this.portraitUrl = PictureUtil.getPicUrl(commentbo.getUserPortrait());
			this.content = commentbo.getContent();
			this.pubTime = StringUtil.getSdfDate(commentbo.getCreateTime());
			if(commentbo.getPictureList() != null){
				this.attachPics = new ArrayList<PictureVo>();
				for (String pic : commentbo.getPictureList()) {
					this.attachPics.add(new PictureVo(pic));
				}
			}
		}
	}	
	
	private String commentator;

	private String portraitUrl;
	
	private String content;
	
	private String pubTime;
	
	private List<PictureVo> attachPics;

	public String getCommentator() {
		return commentator;
	}

	public void setCommentator(String commentator) {
		this.commentator = commentator;
	}

	public String getPortraitUrl() {
		return portraitUrl;
	}

	public void setPortraitUrl(String portraitUrl) {
		this.portraitUrl = portraitUrl;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPubTime() {
		return pubTime;
	}

	public void setPubTime(String pubTime) {
		this.pubTime = pubTime;
	}

	public List<PictureVo> getAttachPics() {
		return attachPics;
	}

	public void setAttachPics(List<PictureVo> attachPics) {
		this.attachPics = attachPics;
	}
	
}