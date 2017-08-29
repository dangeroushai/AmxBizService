package com.amx.bizservice.model.dto;

import java.util.List;

/**
 * 封装分页信息
 * @author DangerousHai
 *
 */
public class PageResponseDto<T> {
	
	/**
	 * 总元素数
	 */
	private Long amount;
	/**
	 * 总页数
	 */
	private Integer pageAmount;
	/**
	 * 当前页（start with 0）
	 */
	private Integer pageIndex;
	/**
	 * 数据
	 */
	private List<T> content;
	
	
	public PageResponseDto(){}
	public PageResponseDto(Long amount, Integer pageAmount, Integer pageIndex,
			List<T> content) {
		super();
		this.amount = amount;
		this.pageAmount = pageAmount;
		this.pageIndex = pageIndex;
		this.content = content;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public Integer getPageAmount() {
		return pageAmount;
	}
	public void setPageAmount(Integer pageAmount) {
		this.pageAmount = pageAmount;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public List<T> getContent() {
		return content;
	}
	public void setContent(List<T> content) {
		this.content = content;
	}
	
}
