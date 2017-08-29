package com.amx.bizservice.util;

import com.amx.bizservice.holder.SpringContextHolder;
import com.amx.bizservice.mapper.CustomObjectMapper;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	public static ObjectMapper mapper = new ObjectMapper();
	
	public static ObjectMapper customMapper = SpringContextHolder.getBean(CustomObjectMapper.class);
	/**
	 * 获取泛型集合的类型
	 * @param collectionClass 集合类类型
	 * @param elementClass 元素类类型
	 * @return
	 */
	public static JavaType getCollectionType(Class<?> collectionClass,Class<?> elementClass){
		return JsonUtil.mapper.getTypeFactory().constructParametricType(collectionClass, elementClass);
	}
}
