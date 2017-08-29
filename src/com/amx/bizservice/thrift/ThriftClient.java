package com.amx.bizservice.thrift;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;

import com.amx.bizservice.holder.SpringContextHolder;
import com.amx.bizservice.thrift.CommonThriftService.Client;
import com.amx.bizservice.thrift.pool.ConnectionManager;
import com.amx.bizservice.util.JsonUtil;

public class ThriftClient {
	private static ConnectionManager connectionManager = null;
	
	static{
		connectionManager = SpringContextHolder.getBean(ConnectionManager.class);
		System.out.println("Initializing ThriClient ...");
	}

	/**
	 *	获取新客户端
	 */
	private static Client getNewClient() {
		Client client = null;
		try {
			TSocket transport = connectionManager.getSocket();
			// 协议要和服务端一致
			TProtocol protocol = new TBinaryProtocol(transport);
			// TProtocol protocol = new TCompactProtocol(transport);
			// TProtocol protocol = new TJSONProtocol(transport);
			client = new CommonThriftService.Client(protocol);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return client;
	}
	
	/**
	 * 调用服务端方法
	 * @param ServiceName
	 * @param methodName
	 * @param data 参数对象
	 * @return
	 */
	public static Response serviceInvoke(String ServiceName , String methodName , Object data){
		Response response  = null;
		try {
			Client client = getNewClient();
			Request request = new Request(ServiceName,methodName,JsonUtil.mapper.writeValueAsString(data));
			response = client.invoke(request);
		} catch (Exception e) {
//			throw new RuntimeException(e);
			throw new RuntimeException(e);
		}finally{
			//FIXME
		}
		
		return response;
	}
}
