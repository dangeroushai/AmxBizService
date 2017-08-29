package com.amx.bizservice.model.vo.input;

public class CommentVo {
	
	private Long orderId;
	
	private String content;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
