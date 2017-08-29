package com.amx.bizservice.thrift;

import org.springframework.stereotype.Component;

@Component
public class ThriftClientProxy {
	
	/**
	 * 调用服务端方法
	 * @param ServiceName
	 * @param methodName
	 * @param data 参数对象
	 * @return
	 */
	public Response serviceInvoke(String ServiceName , String methodName , Object data){
		return ThriftClient.serviceInvoke(ServiceName, methodName, data);
	}
}
