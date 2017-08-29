package com.amx.bizservice.thrift.pool;

import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;
import org.apache.tomcat.dbcp.pool.ObjectPool;
import org.apache.tomcat.dbcp.pool.impl.GenericObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class GenericConnectionProvider implements ConnectionProvider,
InitializingBean, DisposableBean{
	
	public static final Logger logger = LoggerFactory
            .getLogger(GenericConnectionProvider.class);
    /** 服务的IP地址 */
    private String serviceIP;
    /** 服务的端口 */
    private int servicePort;
    /** 连接超时配置 */
    private int conTimeOut;	
    /** 可以从缓存池中分配对象的最大数量 */
    private int maxActive = GenericObjectPool.DEFAULT_MAX_ACTIVE;
    /** 缓存池中最大空闲对象数量 */
    private int maxIdle = GenericObjectPool.DEFAULT_MAX_IDLE;
    /** 缓存池中最小空闲对象数量 */
    private int minIdle = GenericObjectPool.DEFAULT_MIN_IDLE;
    /** 阻塞的最大数量 */
    private long maxWait = GenericObjectPool.DEFAULT_MAX_WAIT;
    /** 从缓存池中分配对象，是否执行PoolableObjectFactory.validateObject方法 */
    private boolean testOnBorrow = GenericObjectPool.DEFAULT_TEST_ON_BORROW;
    private boolean testOnReturn = GenericObjectPool.DEFAULT_TEST_ON_RETURN;
    private boolean testWhileIdle = GenericObjectPool.DEFAULT_TEST_WHILE_IDLE;
    
    /** 对象缓存池 */
    private ObjectPool objectPool = null;

	@Override
	public void afterPropertiesSet() throws Exception {
        // 设置factory
		ThriftPoolableObjectFactory thriftPoolableObjectFactory = new ThriftPoolableObjectFactory(serviceIP, servicePort, conTimeOut);
        // 对象池
        objectPool = new GenericObjectPool(thriftPoolableObjectFactory);
        //配置
        ((GenericObjectPool) objectPool).setMaxActive(maxActive);
        ((GenericObjectPool) objectPool).setMaxIdle(maxIdle);
        ((GenericObjectPool) objectPool).setMinIdle(minIdle);
        ((GenericObjectPool) objectPool).setMaxWait(maxWait);
        ((GenericObjectPool) objectPool).setTestOnBorrow(testOnBorrow);
        ((GenericObjectPool) objectPool).setTestOnReturn(testOnReturn);
        ((GenericObjectPool) objectPool).setTestWhileIdle(testWhileIdle);
        //链接耗尽时的动作
        ((GenericObjectPool) objectPool).setWhenExhaustedAction(GenericObjectPool.WHEN_EXHAUSTED_BLOCK);
	}
	

	@Override
	public TSocket getConnection() {
		 try{
	            TSocket socket = (TSocket) objectPool.borrowObject();
	            if(!socket.isOpen()){
	            	socket.open();
	            }
//	            clearInputBuffer(socket);
	            return socket;
	     } catch (Exception e) {
            throw new RuntimeException("error getConnection()", e);
         }
	}

	@Override
	public void returnConnection(TSocket socket) {
		 try {
			/*归还前先消耗掉缓冲区的内容，避免从连中读到脏数据*/
			 clearInputBuffer(socket);
//			 socket.getSocket().setKeepAlive(false);
             objectPool.returnObject(socket);
	     } catch (Exception e) {
            throw new RuntimeException("error returnConnection()", e);
         }
	}
	
	/**
	 * 消耗掉输入缓冲区里未被处理的数据
	 * @param socket
	 */
	@Override
	public void clearInputBuffer(TSocket socket){
		//FIXME- 清空缓存 而不是重启连接
		if(socket.isOpen()){
			socket.close();
		}
		try {
			socket.open();
		} catch (TTransportException e) {
			throw new RuntimeException(e);
		}
		/*InputStream inputStream = socket.getSocket().getInputStream();
		inputStream.skip(inputStream.available());
	
		socket.consumeBuffer(inputStream.available());*/
	}
	
	@Override
	public void destroy() throws Exception {
		try {
            objectPool.close();
        } catch (Exception e){
            throw new RuntimeException("erorr destroy()", e);
        }
	}


	public String getServiceIP() {
		return serviceIP;
	}


	public void setServiceIP(String serviceIP) {
		this.serviceIP = serviceIP;
	}


	public int getServicePort() {
		return servicePort;
	}


	public void setServicePort(int servicePort) {
		this.servicePort = servicePort;
	}


	public int getConTimeOut() {
		return conTimeOut;
	}


	public void setConTimeOut(int conTimeOut) {
		this.conTimeOut = conTimeOut;
	}


	public int getMaxActive() {
		return maxActive;
	}


	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}


	public int getMaxIdle() {
		return maxIdle;
	}


	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}


	public int getMinIdle() {
		return minIdle;
	}


	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}


	public long getMaxWait() {
		return maxWait;
	}


	public void setMaxWait(long maxWait) {
		this.maxWait = maxWait;
	}


	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}


	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}


	public boolean isTestOnReturn() {
		return testOnReturn;
	}


	public void setTestOnReturn(boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}


	public boolean isTestWhileIdle() {
		return testWhileIdle;
	}


	public void setTestWhileIdle(boolean testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}


	public ObjectPool getObjectPool() {
		return objectPool;
	}


	public void setObjectPool(ObjectPool objectPool) {
		this.objectPool = objectPool;
	}
}
