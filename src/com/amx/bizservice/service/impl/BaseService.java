/*
 * 文件名：BaseController.java
 * 版权：Copyright 2007-2016 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： BaseController.java
 * 修改人：hanrui
 * 修改时间：2016年4月7日
 * 修改内容：新增
 */
package com.amx.bizservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.amx.bizservice.thrift.Response;
import com.amx.bizservice.thrift.ThriftClientProxy;
import com.amx.bizservice.util.JsonUtil;
import com.amx.bizservice.util.LogUtil;
import com.fasterxml.jackson.core.type.TypeReference;

public class BaseService {
	protected Logger logger;
	
	@Autowired
	protected ThriftClientProxy thriftClient;
	
	/**
	 * 远程服务名称
	 */
	protected String ServiceName;
	
	public BaseService() {
		this.logger = LoggerFactory.getLogger(this.getClass());
	}
	
	/**
	 * 获取数据服务(AmxDataService)返回的结果数据
	 * @Author leihaijun
	 * @Description 
	 * @param methodName 方法名
	 * @param param 方法参数
	 * @param resultType 返回类型
	 * @return
	 * @throws Exception
	 * @Date 2018年9月17日 上午11:55:53
	 */
	public <T> T getResultFromRemote(String methodName, Object param, TypeReference<T> resultType) throws Exception{
		Response response = thriftClient.serviceInvoke(this.ServiceName, methodName, param);
		
		if (response == null) {
			throw new RuntimeException("AmxDataService Error");
		}
		T result = null;
		if (response.isState()) {
			result = JsonUtil.mapper.readValue(response.getData(),resultType);
		} else {
			LogUtil.recordWarnLog(response.getMsg());
		}
		
		return result;
	}
}
