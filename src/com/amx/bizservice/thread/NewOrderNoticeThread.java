package com.amx.bizservice.thread;

import java.util.LinkedList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.amx.bizservice.config.CommonConfig;
import com.amx.bizservice.holder.SpringContextHolder;
import com.amx.bizservice.model.bo.OrderBo;
import com.amx.bizservice.service.SMSService;
import com.amx.bizservice.util.LogUtil;
/**
 * 新订单通知线程
 * @author DangerousHai
 *
 */
public class NewOrderNoticeThread extends Thread{
	private static NewOrderNoticeThread instance;
	private SMSService smsService;
	/**
	 * 等待发送通知的订单队列
	 */
	private LinkedList<OrderBo> waitQueue;
	
	private NewOrderNoticeThread(){
		this.setName("NewOrderNotice");
		
		waitQueue = new LinkedList<OrderBo>();
		smsService = SpringContextHolder.getBean(SMSService.class);
	}
	
	public static NewOrderNoticeThread getInstance(){
		if(instance == null){
			instance =new NewOrderNoticeThread();
			instance.start();
		}
		return instance;
	}
	
	public synchronized void sendNewOrderNotice(OrderBo bo){
		waitQueue.add(bo);
		this.notify();
	}
	
	@Override
	public void run() {
		while (true) {
			//队列为空则等待
			if(waitQueue.isEmpty()){
				try {
					synchronized (this) {
						this.wait();
					}
				} catch (Exception e) {
					LogUtil.recordExceptionLog(e);
				}
			}else{
				OrderBo bo = waitQueue.pop();
				
				//发短��?
				smsService.sendOrderNotice(bo.getNo(), bo.getProductId().toString());
				//发邮��?
				HttpClient client = new DefaultHttpClient();
				String url = CommonConfig.NEW_ORDER_NOTICE_URL.replaceAll("\\{orderId\\}", bo.getId().toString());
				HttpGet method = new HttpGet(url);
				try {
					HttpResponse execute = client.execute(method);
					execute.getEntity().getContent();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
}