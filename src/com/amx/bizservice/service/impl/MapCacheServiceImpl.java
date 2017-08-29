package com.amx.bizservice.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.amx.bizservice.service.CacheService;

//@Service("mapCacheService")
@Deprecated
public class MapCacheServiceImpl extends BaseService implements CacheService {
	
	private static Map<Object, Object> cacheProvider = null;
	
	static{
		cacheProvider = new HashMap<Object, Object>();
	}

	@Override
	public void put(Object key, Object value) {
		cacheProvider.put(key, value);
	}

	@Override
	public Object get(Object key) {
		return cacheProvider.get(key);
	}

	@Override
	public void clear() {
		cacheProvider.clear();
	}

	@Override
	public void remove(Object key) {
		cacheProvider.remove(key);
	}
}
