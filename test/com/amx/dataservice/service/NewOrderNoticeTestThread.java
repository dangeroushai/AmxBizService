package com.amx.dataservice.service;

import java.util.LinkedList;
/**
 * 新订单通知线程
 * @author DangerousHai
 *
 */
public class NewOrderNoticeTestThread extends Thread{
	private static NewOrderNoticeTestThread instance;
	/**
	 * 等待发送通知的订单队列
	 */
	private LinkedList<String> waitQueue;
	
	private NewOrderNoticeTestThread(){
		this.setName("Thread-NewOrderNotice");
		
		waitQueue = new LinkedList<String>();
	}
	
	public static NewOrderNoticeTestThread getInstance(){
		if(instance == null){
			instance =new NewOrderNoticeTestThread();
			instance.start();
		}
		return instance;
	}
	
	public synchronized void sendNewOrderNotice(String bo){
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
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}else{
				String bo = waitQueue.pop();
				
//				LogUtil.recordWarnLog(bo);
			}
//			LogUtil.recordWarnLog("__________________________________");
		}
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 5; i++) {
			NewOrderNoticeTestThread.getInstance().sendNewOrderNotice(i+"");
		}
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < 5; i++) {
			NewOrderNoticeTestThread.getInstance().sendNewOrderNotice(i+"a");
		}
	}
}