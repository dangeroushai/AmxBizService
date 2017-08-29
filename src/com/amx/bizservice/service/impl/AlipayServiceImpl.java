package com.amx.bizservice.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.amx.bizservice.config.AlipayConfig;
import com.amx.bizservice.model.bo.OrderBo;
import com.amx.bizservice.service.OrderService;
import com.amx.bizservice.service.PayService;
import com.amx.bizservice.util.LogUtil;

@Service
public class AlipayServiceImpl extends BaseService implements PayService{
	
	@Autowired
	private OrderService orderService;
	
	@Override
	public String getPagePayUrl(OrderBo bo){
		if(bo == null){
			return null;
		}
		//获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, AlipayConfig.format, AlipayConfig.charset, AlipayConfig.alipay_public_key,AlipayConfig.sign_type);
		
		//设置请求参数
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		alipayRequest.setReturnUrl(AlipayConfig.return_url);
		alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
		
		//商户订单号，商户网站订单系统中唯一订单号，必填
		String out_trade_no = bo.getNo();
		//付款金额，必填(预付-折扣)
		String total_amount = String.valueOf(bo.getPrePayPrice().subtract(bo.getDiscount()).doubleValue());
		//订单名称，必填
		String subject = bo.getProductName();
		//商品描述，可空
		String body = bo.getPackageName();
		
		alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
				+ "\"total_amount\":\""+ total_amount +"\"," 
				+ "\"subject\":\""+ subject +"\"," 
				+ "\"body\":\""+ body +"\"," 
				+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
		
		//若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
		//alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
		//		+ "\"total_amount\":\""+ total_amount +"\"," 
		//		+ "\"subject\":\""+ subject +"\"," 
		//		+ "\"body\":\""+ body +"\"," 
		//		+ "\"timeout_express\":\"10m\"," 
		//		+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
		//请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节
		
		//请求
		String payUrl = null;
		try {
			// 调用SDK生成表单
			payUrl = alipayClient.sdkExecute(alipayRequest).getBody();
			LogUtil.recordPayLog("订单["+ bo.getNo() +"]发起网页支付，支付链接：" + payUrl);
		} catch (AlipayApiException e) {
			LogUtil.recordPayLog("订单["+ bo.getNo() +"]在获取网页支付地址时发生异常" , e);
		}

		return AlipayConfig.gatewayUrl +"?"+ payUrl;
	}
	
	public String getWapPayUrl(OrderBo bo){
		if(bo == null){
			return null;
		}
		
		// 商户订单号，商户网站订单系统中唯一订单号，必填
		String out_trade_no = bo.getNo();
		// 订单名称，必填
		String subject = bo.getProductName();
		// 付款金额，必填(预付-折扣)
		String total_amount = String.valueOf(bo.getPrePayPrice().subtract(bo.getDiscount()).doubleValue());
		// 商品描述，可空
		String body = bo.getPackageName();
		// 超时时间 可空
		String timeout_express = "2m";
		// 销售产品码 必填
		String product_code = String.valueOf(bo.getProductId());// "QUICK_WAP_PAY";
		/**********************/
		// SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
		// 调用RSA签名方式
		AlipayClient client = new DefaultAlipayClient(AlipayConfig.gatewayUrl,
				AlipayConfig.app_id, AlipayConfig.merchant_private_key,
				AlipayConfig.format, AlipayConfig.charset,
				AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
		AlipayTradeWapPayRequest alipay_request = new AlipayTradeWapPayRequest();
		
		// 封装请求支付信息
		AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
		model.setOutTradeNo(out_trade_no);
		model.setSubject(subject);
		model.setTotalAmount(total_amount);
		model.setBody(body);
		model.setTimeoutExpress(timeout_express);
		model.setProductCode(product_code);

		alipay_request.setBizModel(model);
		// 设置异步通知地址
		alipay_request.setNotifyUrl(AlipayConfig.notify_url);
		// 设置同步地址
		alipay_request.setReturnUrl(AlipayConfig.return_url);

		String payUrl = null;
		try {
			// 调用SDK生成表单
//			String form = client.pageExecute(alipay_request).getBody();
			payUrl = client.sdkExecute(alipay_request).getBody();
			LogUtil.recordPayLog("订单["+ bo.getNo() +"]发起移动支付，支付链接：" + payUrl);
		} catch (AlipayApiException e) {
			LogUtil.recordPayLog("订单["+ bo.getNo() +"]在获取移动支付地址时发生异常" , e);	
		}

		return AlipayConfig.gatewayUrl +"?"+ payUrl;
	}
	
	@Override
	public boolean notifyHandler( Map<String, String> paramsMap){
		boolean result = false;
		try {
			//调用SDK验证签名
			boolean signVerified = AlipaySignature.rsaCheckV1(paramsMap, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type);
			if(signVerified){
				//通知类型
				String notify_type = paramsMap.get("notify_type");
				
				switch (notify_type) {
				case "TRADE_SUCCESS"://交易支付成功
					String trade_no = paramsMap.get("trade_no");
					//交易状态
					String trade_status = paramsMap.get("trade_status");
					String out_trade_no = paramsMap.get("out_trade_no");
					double total_amount = Double.parseDouble(paramsMap.get("total_amount"));
					//支付状态
					boolean payStatus = trade_status == "TRADE_SUCCESS";
					// TODO 验签成功后，按照支付结果异步通知中的描述，对支付结果中的业务内容进行二次校验，校验成功后在response中返回success并继续商户自身业务处理，校验失败返回failure
					if(orderService.payDone( trade_no,  payStatus, out_trade_no,  total_amount)){
						result = true;
						LogUtil.recordPayLog("收到支付宝通知，已完成处理。参数：" + paramsMap.toString());
					}
					
					break;
				case "TRADE_CLOSED"://未付款交易超时关闭，或支付完成后全额退款
					
					break;
				default:
					break;
				}
			}else{
				LogUtil.recordPayLog("收到支付宝通知，但验签失败。参数：" + paramsMap.toString(), null);
			}
		} catch (AlipayApiException e) {
			LogUtil.recordPayLog("收到支付宝通知，但验签出错：", e);
		}
		
		return result;
	}
}
