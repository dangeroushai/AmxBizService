package com.amx.bizservice.util;

import java.util.List;

public class ArrayUtil {
	
	public static <T> boolean contains(List<T> list,T element){
		
		if(list == null || element == null){
			return false;
		}
		
		if(element instanceof Long){
			for (T t : list) {
				Long ele = (Long) t;
				if(ele.equals(element)){
					return true;
				}
			}
		}
		
		return false;
	}
	
}
