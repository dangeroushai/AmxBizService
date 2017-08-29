package com.amx.bizservice.model.vo.wap.input;

import java.util.List;

/**
 * 接收订单
 * @author DangerousHai
 *
 */
public class OrderUpdateVo{

	private List<Long> orderIdList;

	public List<Long> getOrderIdList() {
		return orderIdList;
	}

	public void setOrderIdList(List<Long> orderIdList) {
		this.orderIdList = orderIdList;
	}
	
}