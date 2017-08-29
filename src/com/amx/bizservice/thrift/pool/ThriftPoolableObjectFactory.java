package com.amx.bizservice.thrift.pool;

import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.tomcat.dbcp.pool.PoolableObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThriftPoolableObjectFactory  implements PoolableObjectFactory{

	/** 日志记录器 */
    public static final Logger logger = LoggerFactory.getLogger(ThriftPoolableObjectFactory.class);
    /** 服务的IP */
    private String serviceIP;
    /** 服务的端口 */
    private int servicePort;
    /** 超时设置 */
    private int timeOut;
	
	public ThriftPoolableObjectFactory(String serviceIP, int servicePort, int timeOut) {
		this.serviceIP = serviceIP;
        this.servicePort = servicePort;
        this.timeOut = timeOut;
	}

	@Override
	public void activateObject(Object arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public void passivateObject(Object arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroyObject(Object arg0) throws Exception {
		if (arg0 instanceof TSocket) {
            TSocket socket = (TSocket) arg0;
            if (socket.isOpen()) {
                socket.close();
            }
        }
	}

	@Override
	public Object makeObject() throws Exception {
		try {
            TTransport transport = new TSocket(this.serviceIP, this.servicePort, this.timeOut);
            
            String info = "[ThriftPool]：A new socket created : " + transport;
            logger.info(info);
            transport.open();
            
            return transport;
        } catch (Exception e) {
            logger.error("error ThriftPoolableObjectFactory()", e);
        }
		return null;
	}


	@Override
	public boolean validateObject(Object arg0) {
		try {
            if (arg0 instanceof TSocket) {
                TSocket thriftSocket = (TSocket) arg0;
                if (thriftSocket.isOpen()) {
                    return true;
                } else  {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
	}
}
