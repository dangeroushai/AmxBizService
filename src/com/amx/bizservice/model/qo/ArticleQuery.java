package com.amx.bizservice.model.qo;



/**
 * 查询产品
 * @author DangerousHai
 *
 */
public class ArticleQuery {

	/**
	 * 关键字
	 */
	private String keyword;
	/**
	 * 页码
	 */
	private Integer pageIndex;
	/**
	 * 页容量
	 */
	private Integer pageSize;
	/**
	 * 文章类型
	 */
	private Integer typeId;
	/**
	 * 保留字段
	 */
	private Integer categroyId;
	
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public Integer getCategroyId() {
		return categroyId;
	}
	public void setCategroyId(Integer categroyId) {
		this.categroyId = categroyId;
	}
}
