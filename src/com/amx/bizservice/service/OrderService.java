package com.amx.bizservice.service;

import java.util.ArrayList;
import java.util.List;

import com.amx.bizservice.model.bo.OrderBo;
import com.amx.bizservice.model.bo.UpdateResponseBo;
import com.amx.bizservice.model.dto.PageResponseDto;
import com.amx.bizservice.model.qo.OrderQuery;



public interface OrderService{

	/**
	 * 根据订单号获取关联的产品ID
	 * @param orderId
	 * @return
	 */
	long getProductIdByOrderId(long orderId);
	
	PageResponseDto<OrderBo> getOrderList(OrderQuery query);
	
	UpdateResponseBo insertOrder(OrderBo bo);
	
	UpdateResponseBo cancelOrder(long userId, long orderId);
	
	UpdateResponseBo deleteOrder(long userId, long orderId);

	/**
	 * 获取订单明细
	 * @param userId
	 * @param ordreId
	 * @return
	 */
	OrderBo getOrder(long userId, long ordreId);

	Double getServiceFee(long userId, long id);

	/**
	 * 检查订单信息是否已经过期
	 * @param obo
	 * @return
	 */
	Boolean isOrderExpire(OrderBo obo);

	List<OrderBo> getOrderList(List<Long> orderIdList);

	Boolean getPayState(OrderBo bo);

	UpdateResponseBo insertOrders(ArrayList<OrderBo> inBoList);

	UpdateResponseBo cancelOrders(Long userId, List<Long> orderIdList);

	UpdateResponseBo deleteOrders(Long userId, List<Long> orderIdList);

	List<OrderBo> getOrdersByTradeNo(String tradeNo);
	List<OrderBo> getOrdersByTradeNo(Long userId, String tradeNo);

	/**
	 * 订单完成
	 * @param orderId
	 */
	void doneOrder(Long userId,Long orderId);

	List<OrderBo> getOrders(Long userId, List<Long> orderIds);

	Boolean getPayState(List<OrderBo> orderBoList);

	
	/**
	 * 支付完成后处理订单
	 * @param trade_no 支付宝交易号
	 * @param trade_status 交易状态 
	 * @param out_trade_no 商户交易号
	 * @param total_amount 交易金额
	 */
	boolean payDone(String trade_no, boolean payStatus,String out_trade_no, double total_amount);

	String getPayUrl(List<OrderBo> orderBoList);
	/**
	 * 获取支付链接
	 * @param orderBoList
	 * @param isWap 是否移动端调用
	 * @return
	 */
	String getPayUrl(List<OrderBo> orderBoList, boolean isWap);



}
