package com.amx.bizservice.thrift.pool;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.thrift.transport.TSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionManager implements MethodInterceptor{

	/** 日志记录器 */
    public Logger logger = LoggerFactory.getLogger(ConnectionManager.class);
    /** 保存local对象 */
    ThreadLocal<TSocket> socketThreadSafe = new ThreadLocal<TSocket>();
    /** 连接提供池 */
    public ConnectionProvider connectionProvider;
	
	@Override
	public Object invoke(MethodInvocation mi) throws Throwable {
		TSocket socket = null;
        try {
        	socket = socketThreadSafe.get();
        	if(socket == null){
        		socket = connectionProvider.getConnection();
        		socketThreadSafe.set(socket);
        	}else{
        		/*同一线程中嵌套调用invoke方法，
        		 * 则在ThreadLocal中已经包含连接，此时，如果在连接缓冲区中有未被处理的数据，
        		 * 则会导致反序列化失败，故需在使用连接前先消耗掉连接缓冲区中的数据
        		 */
        		connectionProvider.clearInputBuffer(socket);
        	}
            
            return mi.proceed();
        } catch (Exception e) {
            logger.error("error ConnectionManager.invoke()", e);
            throw new Exception(e);
        } finally {
            connectionProvider.returnConnection(socket);
            socketThreadSafe.remove();
        }
	}
	
	
	/**
     * 取socket
     * @return
     */
    public TSocket getSocket()
    {
        return socketThreadSafe.get();
    }

	public ThreadLocal<TSocket> getSocketThreadSafe() {
		return socketThreadSafe;
	}

	public void setSocketThreadSafe(ThreadLocal<TSocket> socketThreadSafe) {
		this.socketThreadSafe = socketThreadSafe;
	}

	public ConnectionProvider getConnectionProvider() {
		return connectionProvider;
	}

	public void setConnectionProvider(ConnectionProvider connectionProvider) {
		this.connectionProvider = connectionProvider;
	}
	
	

}
