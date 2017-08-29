package com.amx.bizservice.model.qo;



/**
 * 查询产品
 * @author DangerousHai
 *
 */
public class ProductQuery {

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
	 * 目的地
	 */
	private Integer destinationId;
	/**
	 * 主题
	 */
	private Integer themeId;
	/**
	 * 场景
	 */
	private Integer sceneId;
	/**
	 * 关键字
	 */
	private Integer startPrice;
	/**
	 * 关键字
	 */
	private Integer endPrice;
	/**
	 * 关键字
	 */
	private Integer typeId;
	/**
	 * 是否按主题排序
	 */
	private boolean isSortByTheme; 
	/**
	 * 是否按地区排序
	 */
	private boolean isSortByDest; 
	/**
	 * 是否按属性（场景）排序
	 */
	private boolean isSortByAttr; 
	
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
	public Integer getDestinationId() {
		return destinationId;
	}
	public void setDestinationId(Integer destinationId) {
		this.destinationId = destinationId;
	}
	public Integer getThemeId() {
		return themeId;
	}
	public void setThemeId(Integer themeId) {
		this.themeId = themeId;
	}
	public Integer getSceneId() {
		return sceneId;
	}
	public void setSceneId(Integer sceneId) {
		this.sceneId = sceneId;
	}
	public Integer getStartPrice() {
		return startPrice;
	}
	public void setStartPrice(Integer startPrice) {
		this.startPrice = startPrice;
	}
	public Integer getEndPrice() {
		return endPrice;
	}
	public void setEndPrice(Integer endPrice) {
		this.endPrice = endPrice;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public boolean isSortByTheme() {
		return isSortByTheme;
	}
	public void setSortByTheme(boolean isSortByTheme) {
		this.isSortByTheme = isSortByTheme;
	}
	public boolean isSortByDest() {
		return isSortByDest;
	}
	public void setSortByDest(boolean isSortByDest) {
		this.isSortByDest = isSortByDest;
	}
	public boolean isSortByAttr() {
		return isSortByAttr;
	}
	public void setSortByAttr(boolean isSortByAttr) {
		this.isSortByAttr = isSortByAttr;
	}
}
