package com.amx.bizservice.service;

public interface SMSService {

	/**
	 * 发送手机验证码
	 * @param phone
	 * @param captcha
	 * @return 
	 */
	boolean sendCaptcha(String phone,String captcha );

	/**
	 * 发送接单通知
	 * @param orderNo
	 * @param productName
	 * @return
	 */
	boolean sendOrderNotice(String orderNo, String productNo);

}
