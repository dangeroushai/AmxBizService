package com.amx.bizservice.model.vo.web;

import java.util.List;

/**
 * 单天行程安排
 * @author DangerousHai
 *
 */
public class HodometerVo{
	
    /**
     * @天序号.
     */
    private Integer dayOrder; 

    /**
     * @出发日期.
     */
    private String date; 


    /**
     * @活动项.
     */
    private List<ProductVo> items;
    

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<ProductVo> getItems() {
		return items;
	}

	public void setItems(List<ProductVo> items) {
		this.items = items;
	}

	public Integer getDayOrder() {
		return dayOrder;
	}

	public void setDayOrder(Integer dayOrder) {
		this.dayOrder = dayOrder;
	}

}