package com.amx.bizservice.service;


public interface CacheService{

	void put(Object key,Object value);
	
	Object get(Object key);
	
	void clear();
	 
	void remove(Object key);
}
