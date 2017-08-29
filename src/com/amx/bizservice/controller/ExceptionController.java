package com.amx.bizservice.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amx.bizservice.enums.ResponseTypeEnum;
import com.amx.bizservice.exception.AccessForbiddenException;
import com.amx.bizservice.exception.UnauthorizedException;
import com.amx.bizservice.model.dto.JsonResponseDto;
import com.amx.bizservice.util.LogUtil;

/**
 * 全局异常控制器
 * @author DangerousHai
 *
 */
@ControllerAdvice
public class ExceptionController extends BaseController{
	
	/**
	 * 处理需要身份验证（不强制登录）
	 */
	@ResponseBody
	@ExceptionHandler(UnauthorizedException.class) 
	public Map<String, Object> unauthorizedHandler(UnauthorizedException e){
		return JsonResponseDto.getResult(ResponseTypeEnum.UNAUTHORIZED);
	}
	
	/**
	 * 处理需要身份验证（强制登录）
	 */
	@ResponseBody
	@ExceptionHandler(AccessForbiddenException.class) 
	public Map<String, Object> accessForbiddenHandler(AccessForbiddenException e){
		return JsonResponseDto.getResult(ResponseTypeEnum.ACCESS_FORBIDDEN);
	}
	
	/**
	 * 处理所有不能被处理的方法
	 */
	@ResponseBody
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class) // 注解在普通Controller中则只处理其内部的异常
	public Map<String, Object> defaultHandler(HttpServletRequest request){
		ResponseTypeEnum response = ResponseTypeEnum.BAD_REQUEST; 
		return JsonResponseDto.getResult(response);
	}
	
	/**
	 * 全局出现异常时执行该方法
	 */
	@ResponseBody
	@ExceptionHandler(Exception.class) // 注解在普通Controller中则只处理其内部的异常
	public Map<String, Object> exceptionHandler(Exception e,HttpServletRequest request){
		LogUtil.recordExceptionLog(e, request);
		
		//FIXME - delete
		e.printStackTrace();
		
		ResponseTypeEnum response = ResponseTypeEnum.SERVER_ERROR; 
		return JsonResponseDto.getResult(response);
	}
}
