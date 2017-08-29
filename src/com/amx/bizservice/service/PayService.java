package com.amx.bizservice.service;

import java.util.Map;

import com.amx.bizservice.model.bo.OrderBo;



public interface PayService{
	
	/**
	 * 网站支付连接
	 * @param bo
	 * @return
	 */
	String getPagePayUrl(OrderBo bo);
	
	/**
	 * 手机支付连接
	 * @param bo
	 * @return
	 */
	String getWapPayUrl(OrderBo bo);

	boolean notifyHandler(Map<String, String> paramsMap);

}
