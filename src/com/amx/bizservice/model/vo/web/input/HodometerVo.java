package com.amx.bizservice.model.vo.web.input;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
/**
 * 单天行程安排
 * @author DangerousHai
 *
 */
public  class HodometerVo{
	
    /**
     * @天序号.
     */
    private Integer dayOrder; 

    /**
     * @出发日期.
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date; 


    /**
     * @活动项.
     */
    private List<ItemVo> items;
    

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getDayOrder() {
		return dayOrder;
	}

	public void setDayOrder(Integer dayOrder) {
		this.dayOrder = dayOrder;
	}

	public List<ItemVo> getItems() {
		return items;
	}

	public void setItems(List<ItemVo> items) {
		this.items = items;
	}
}
