package com.amx.dataservice.service;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class EhCacheTest {
	
	public static void main(String[] args) {
		
		//获取单例cacheManager
		CacheManager cacheManager = CacheManager.getInstance();
		
		Cache firstLevelCache = cacheManager.getCache("FirstLevel");
		
		String key = "key";
		Element ele = new Element(key, "value");
		
		firstLevelCache.put(ele);
		
		firstLevelCache.get(key);
		
	}

}
