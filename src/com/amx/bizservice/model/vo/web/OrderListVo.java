package com.amx.bizservice.model.vo.web;

import java.util.List;

import com.amx.bizservice.model.bo.OrderBo;
import com.amx.bizservice.model.dto.PageResponseDto;

/**
 * 订单列表
 * @author DangerousHai
 *
 */
public class OrderListVo {
    private Integer orderAmount;
    private Integer pageIndex;
    private Integer pageAmount;
    private List<OrderVo> orderList;

	public OrderListVo(PageResponseDto<OrderBo> dto) {
		if(dto != null){
			this.orderAmount = dto.getAmount().intValue();
			this.pageAmount = dto.getPageAmount();
			this.pageIndex = dto.getPageIndex();
			
			//外部处理
			//this.orderList
			
			/*if(dto.getContent() != null){
				this.orderList = new ArrayList<OrderVo>();
				for (OrderBo obo : dto.getContent()) {
					this.orderList.add(new OrderVo(obo));
				}
			}*/
		}
	}
	
	

	public Integer getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Integer orderAmount) {
		this.orderAmount = orderAmount;
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

	public List<OrderVo> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<OrderVo> orderList) {
		this.orderList = orderList;
	}
	
}
