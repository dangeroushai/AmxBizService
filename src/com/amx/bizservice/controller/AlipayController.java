package com.amx.bizservice.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amx.bizservice.service.PayService;

/**
 * 支付宝控制器
 * @author DangerousHai
 *
 */
@Controller
@RequestMapping(value = "/alipay")
public class AlipayController extends BaseController{
	
	@Autowired
	private PayService aliPayService;

	/**
	 * 接收支付宝异步通知
	 */
	@ResponseBody
	@RequestMapping(value = "/notify")
	public String receiveNotify(@RequestBody Map<String, String> paramsMap){
//		验签成功后，按照支付结果异步通知中的描述，对支付结果中的业务内容进行二次校验，校验成功后在response中返回success并继续商户自身业务处理，校验失败返回failure
		if(aliPayService.notifyHandler(paramsMap)){
			return "success";
		}
		return "failure";
	}
}
