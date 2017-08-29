package com.amx.bizservice.service.impl;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amx.bizservice.config.CommonConstants;
import com.amx.bizservice.service.CacheService;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * 管理一级缓存
 * @author DangerousHai
 *
 */
@Service("ehCacheService")
public class EhCacheServiceImpl extends BaseService implements CacheService ,InitializingBean{
	
	/**
	 * 单例cacheManager
	 */
	@Autowired
	private CacheManager cacheManager;
	/**
	 * 一級緩存
	 */
	private Cache firstLevelCache;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		firstLevelCache = cacheManager.getCache(CommonConstants.CACHE_NAME.FIRST_LEVEL);
	}
	
	@Override
	public void put(Object key, Object value) {
		firstLevelCache.put(new Element(key, value));
	}

	@Override
	public Object get(Object key) {
		Element element = firstLevelCache.get(key);
		if(element != null){
			return element.getObjectValue();
		}
		return null;
	}

	@Override
	public void clear() {
		firstLevelCache.removeAll();
	}

	@Override
	public void remove(Object key) {
		firstLevelCache.remove(key);
	}	
	
}
