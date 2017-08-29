package com.amx.bizservice.model.dto;

import java.util.HashMap;
import java.util.Map;

import com.amx.bizservice.enums.ResponseTypeEnum;
import com.amx.bizservice.enums.ResponseTypeEnum.Response;

public class JsonResponseDto {
	private static final String STATUS = "status";
	private static final String DATA = "data";
	private static final String MSG = "msg";
	
	public static Map<String, Object> getResult(boolean state, Object data, String msg){
		Map<String, Object> result = new HashMap<String, Object>(); 
		result.put(STATUS, state);
		result.put(DATA, data);
		result.put(MSG, msg);
		return result;	
	}
	
	/**
	 * 公共返回结果
	 * @param response
	 * @return
	 */
	public static Map<String, Object> getResult(Object response){
		Map<String, Object> result = new HashMap<String, Object>();
		if(response instanceof ResponseTypeEnum){
			ResponseTypeEnum response_ = (ResponseTypeEnum)response;
			
			result.put(STATUS, response_.getStatus());
			result.put(DATA, response_.getData());
			result.put(MSG, response_.getMsg());
		}else if(response instanceof Response){
			Response response_ = (Response)response;
			
			result.put(STATUS, response_.getStatus());
			result.put(DATA, response_.getData());
			result.put(MSG, response_.getMsg());
		}else{
			throw new RuntimeException("can not return result,error type");
		}
		
		return result;	
	}
	
	/**
	 * 此返回结果仅适用与头像上传
	 * @param success
	 * @return
	 */
	public static Map<String, Object> getAvatarResult(boolean success){
		Map<String, Object> result = new HashMap<String, Object>(); 
		result.put("success", success);
		result.put("code", 5);
		result.put("type", 0);
		
		return result;	
	}
	
}
