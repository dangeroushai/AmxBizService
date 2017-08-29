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

import com.amx.bizservice.thrift.ThriftClientProxy;

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
}
