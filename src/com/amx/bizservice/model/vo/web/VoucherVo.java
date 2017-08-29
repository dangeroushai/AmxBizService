package com.amx.bizservice.model.vo.web;

import java.util.List;

/**
 * 确认单
 * @author DangerousHai
 *
 */
public class VoucherVo {
	private Boolean canPay;
	/**
	 * 无效订单
	 */
	private List<InvalidOrderVo> invalidOrderList;
    private Integer amount;
    private Double totalPrice;
    private List<OrderVo> orderList;

	public class InvalidOrderVo{
		private Long orderId;
		private String orderNo;
		private String msg;
		public InvalidOrderVo(Long id, String no, String msg) {
			this.orderId = id;
			this.orderNo = no;
			this.msg = msg;
		}
		public Long getOrderId() {
			return orderId;
		}
		public void setOrderId(Long orderId) {
			this.orderId = orderId;
		}
		public String getOrderNo() {
			return orderNo;
		}
		public void setOrderNo(String orderNo) {
			this.orderNo = orderNo;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}

	public Boolean getCanPay() {
		return canPay;
	}

	public void setCanPay(Boolean canPay) {
		this.canPay = canPay;
	}

	public List<InvalidOrderVo> getInvalidOrderList() {
		return invalidOrderList;
	}

	public void setInvalidOrderList(List<InvalidOrderVo> invalidOrderList) {
		this.invalidOrderList = invalidOrderList;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<OrderVo> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<OrderVo> orderList) {
		this.orderList = orderList;
	}
	
}
