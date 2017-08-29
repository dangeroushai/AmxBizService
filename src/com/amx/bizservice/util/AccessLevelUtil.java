package com.amx.bizservice.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.method.HandlerMethod;

import com.amx.bizservice.annotation.Authentication;
import com.amx.bizservice.config.CommonConstants;
import com.amx.bizservice.enums.AccessLevelEnum;
import com.amx.bizservice.model.bo.UserBo;

/**
 * 访问权限工具类
 * @author DangerousHai
 *
 */
public class AccessLevelUtil {
	
	private static Map<Object, AccessLevelEnum> handlerAccLevelCacheMap = new HashMap<Object, AccessLevelEnum>();
	
	/**
	 * 获取用户所拥有的权限级别
	 * Session 中含UserId - PROTECTED
	 * Session 中含UserId && COOKIE中含USERID && SESSION.UserId == COOKIE.UserId - PRIVATE
	 * @param request
	 * @return
	 */
	public static AccessLevelEnum getUserAccessLevel(HttpServletRequest request) {
		AccessLevelEnum accLevel = AccessLevelEnum.PUBLIC;
			
		String jwtHeader = request.getHeader(CommonConstants.REQUEST_HEADER_JWT);
		if(jwtHeader != null){/*移动端*/ //移动端始终不创建Session
			String[] jwtHeader_value = jwtHeader.split(" ");
			if(jwtHeader_value.length == 2 && jwtHeader_value[0].equals("Bearer")){
				UserBo loginUser = JWTUtil.verify(jwtHeader_value[1]);
				if(loginUser != null){
					if(loginUser.getId() >= 0){//登录用户
						accLevel = AccessLevelEnum.PRIVATE;
					}else {//NOTE - userId < 0 => 临时用户
						accLevel = AccessLevelEnum.PROTECTED;
					}
					//将用户标识放入请求域
					request.setAttribute(CommonConstants.SESSION_USER_ID, loginUser.getId());
				}
			}
		}else{//WEB端
			//检查Cookie
			Cookie[] cookies = request.getCookies();
			if(cookies != null){
				for (Cookie cookie : cookies) {
					if(cookie.getName().equals(CommonConstants.COOKIE_TOKEN_NAME)){
						String cookie_token = cookie.getValue(); 
						if(cookie_token != null){
							UserBo loginUser = JWTUtil.verify(cookie_token);
							if(loginUser != null && loginUser.getId() != null){
								if(loginUser.getId() > 0){
									accLevel = AccessLevelEnum.PRIVATE;
								}else{
									accLevel = AccessLevelEnum.PROTECTED;
								}
								//将UserId 存放在Session中
								SessionUtil.getSession(request, null).setAttribute(CommonConstants.SESSION_USER_ID, loginUser.getId());
							}
						}
						break;
					}
				}
			}
		}
		
		return accLevel;
	}

	/**
	 * 获取访问Handler所需要的权限级别
	 * @param handler
	 * @return
	 */
	public static AccessLevelEnum getHandlerAccessLevel(Object handler) {
		AccessLevelEnum accLevel= handlerAccLevelCacheMap.get(handler);
		if(accLevel == null){
			accLevel = AccessLevelEnum.PUBLIC;
			
			if(handler instanceof HandlerMethod){
				HandlerMethod handlerMethod = (HandlerMethod)handler;
				//优先解析方法上的权限注解
				Authentication authentication = handlerMethod.getMethodAnnotation(Authentication.class);
				//解析类上的权限注解
				if(authentication == null){
					authentication = handlerMethod.getBean().getClass().getAnnotation(Authentication.class);
				}
				
				//获取 注解的访问等级
				if(authentication != null){
					accLevel = authentication.accessLevel();
				}
			}
			
			handlerAccLevelCacheMap.put(handler, accLevel);
		}
		
		return accLevel;
	}
}
