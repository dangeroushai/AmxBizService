package com.amx.bizservice.model.vo.wap;

import java.util.ArrayList;
import java.util.List;

import com.amx.bizservice.model.bo.CommentBo;
import com.amx.bizservice.model.dto.PageResponseDto;
import com.amx.bizservice.model.vo.CommentVo;

/**
 * 收藏列表
 * @author DangerousHai
 *
 */
public class CommentListVo {
    private Integer amount;
    private Integer pageIndex;
    private Integer pageAmount;
    private List<CommentVo> commentList;

	public CommentListVo(PageResponseDto<CommentBo> dto) {
		if(dto != null){
			this.amount = dto.getAmount().intValue();
			this.pageAmount = dto.getPageAmount();
			this.pageIndex = dto.getPageIndex();
			List<CommentBo> content = dto.getContent();
			if(content != null){
				this.commentList = new ArrayList<CommentVo>();
				for (CommentBo commentBo : content) {
					this.commentList.add(new CommentVo(commentBo));
				}
			}
		}
	}
	
	public Integer gettAmount() {
		return amount;
	}

	public void setAmount(Integer commentAmount) {
		this.amount = commentAmount;
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

	public List<CommentVo> getcommentList() {
		return commentList;
	}

	public void setcommentList(List<CommentVo> commentList) {
		this.commentList = commentList;
	}
	
}
