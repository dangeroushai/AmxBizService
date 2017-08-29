package com.amx.bizservice.service.impl;

import org.springframework.stereotype.Service;

import com.amx.bizservice.config.CommonConfig;
import com.amx.bizservice.service.SMSService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

@Service("smsService")
public class SMSServiceImpl extends BaseService implements SMSService{

	private String serverUrl ;
	private String appkey ;
	private String secretKey ;
	
	private String signName ;
	private String smsType ;
	private String exetend;
	
	private TaobaoClient client ;
	private AlibabaAliqinFcSmsNumSendRequest req ;
	
	public SMSServiceImpl(){
		signName = "爱漫行科技";
		smsType = "normal";
		
		serverUrl = "http://gw.api.taobao.com/router/rest";
		appkey = "23401724";
		secretKey = "83073ee2fccba87acf506e6787297b56";
		
		client = new DefaultTaobaoClient(serverUrl , appkey, secretKey);	
		req = new AlibabaAliqinFcSmsNumSendRequest();
	}

	@Override
	public boolean sendCaptcha(String phone, String captcha) {
		
		return sendMsg(phone, "{\"code\":\"" + captcha + "\"}", "SMS_11520710");
	}
	
	@Override
	public boolean sendOrderNotice(String orderNo,String productName) {
		
		return sendMsg(CommonConfig.RECEIVE_NOTICE_PHONE, "{\"orderNo\":\"" + orderNo + "\",\"productName\":\"" + productName + "\"}", "SMS_67465034");
	}
	
	private boolean sendMsg(String phone, String templateContent, String templateCode){
		this.req.setExtend(this.exetend);//公共回传参数，在“消息返回”中会透传回该参数；
		this.req.setSmsType(this.smsType);
		this.req.setSmsFreeSignName(this.signName);
		
		this.req.setSmsParamString(templateContent);
		//接收者，多个已逗号隔开
		this.req.setRecNum(phone);
		this.req.setSmsTemplateCode(templateCode);
		
		try {
			AlibabaAliqinFcSmsNumSendResponse rsp = this.client.execute(this.req);
			
			return rsp.isSuccess();
		} catch (ApiException e) {
			throw new RuntimeException(e);
		}
//		return false;
	}
}
