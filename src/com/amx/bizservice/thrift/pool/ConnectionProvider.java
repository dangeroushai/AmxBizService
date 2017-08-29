package com.amx.bizservice.thrift.pool;

import org.apache.thrift.transport.TSocket;

public interface ConnectionProvider {
	 /**
     * 取链接池中的一个链接
     * 
     * @return
     */
    public TSocket getConnection();
    /**
     * 归还链接
     * 
     * @param socket
     */
    public void returnConnection(TSocket socket);
    
    /**
     * 清空输入缓冲区
     * @param socket
     */
    public void clearInputBuffer(TSocket socket);
}
