package com.amx.bizservice.model.vo.wap;

import java.util.List;

import com.amx.bizservice.model.bo.UpdateResponseBo;


/**
 * 新增订单操作响应对象
 * @author DangerousHai
 *
 */
public class InsertOrderResponseVo {
	/**
	 * 成功与否
	 */
	private Boolean isSucceeded;
	
	private List<Long> orderId;
	private List<String> orderNo;
	private String msg;
	
	public InsertOrderResponseVo(){
		isSucceeded = false;
	}

	public InsertOrderResponseVo(UpdateResponseBo bo) {
		if(bo != null){
			this.isSucceeded = bo.getIsSucceeded();
			this.msg = bo.getMsg();
		}
	}

	public Boolean getIsSucceeded() {
		return isSucceeded;
	}

	public void setIsSucceeded(Boolean isSucceeded) {
		this.isSucceeded = isSucceeded;
	}

	public List<Long> getOrderId() {
		return orderId;
	}

	public void setOrderId(List<Long> orderId) {
		this.orderId = orderId;
	}

	public List<String> getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(List<String> orderNo) {
		this.orderNo = orderNo;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
