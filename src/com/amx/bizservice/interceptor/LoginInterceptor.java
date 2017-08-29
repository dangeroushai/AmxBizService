package com.amx.bizservice.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.amx.bizservice.enums.AccessLevelEnum;
import com.amx.bizservice.exception.AccessForbiddenException;
import com.amx.bizservice.exception.UnauthorizedException;
import com.amx.bizservice.util.AccessLevelUtil;

/**
 * 登录拦截器
 * @author DangerousHai
 *
 */
public class LoginInterceptor implements HandlerInterceptor {
    /** 
     * Handler执行之前调用这个方法 
     */  
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {		
		//获取访问Handler所需要的权限级别
		AccessLevelEnum handlerAccLevel = AccessLevelUtil.getHandlerAccessLevel(handler);
		//获取用户所拥有的权限级别
		AccessLevelEnum userAccLevel = AccessLevelUtil.getUserAccessLevel(request);
		
		/*检查权限*/
		if (handlerAccLevel.equals(AccessLevelEnum.PROTECTED)){//需创建临时身份
			if (userAccLevel.equals(AccessLevelEnum.PUBLIC)){//用户只拥有最低权限
				throw new UnauthorizedException();
			}
		}else if (handlerAccLevel.equals(AccessLevelEnum.PRIVATE)){//需登录
			if(!userAccLevel.equals(AccessLevelEnum.PRIVATE)){//用户未登录
				throw new AccessForbiddenException();
			}
		}
		
		return true;
	}

	/** 
     * Handler执行之后，ModelAndView返回之前调用这个方法 
     */ 
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception { }

	/** 
     * Handler执行完成之后调用这个方法 
     */  
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) { }
}
