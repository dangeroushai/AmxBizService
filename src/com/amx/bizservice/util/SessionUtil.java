package com.amx.bizservice.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.amx.bizservice.config.CommonConfig;
import com.amx.bizservice.config.CommonConstants;
import com.amx.bizservice.enums.AccessLevelEnum;
import com.amx.bizservice.model.bo.UserBo;



public class SessionUtil {
	
	/**
	 * 本应用中所有创建Session均使用该方法
	 * 默认request.getSession(true)会设置cookie的其它属性（path，httponly等）
	 * @param request
	 * @param response
	 * @return
	 */
	public static HttpSession getSession(HttpServletRequest request, HttpServletResponse response){
		//为当前用户创建SESSION
		HttpSession session = request.getSession(true);
		
		//覆盖Session创建的默认行为
		/*Cookie sessionId = new Cookie(CommonConstants.COOKIE_SESSION_NAME,session.getId());
		sessionId.setPath(request.getContextPath());
		sessionId.setMaxAge(3 * CommonConfig.TOKEN_EXPIRE);
		response.addCookie(sessionId);*/
		
		return session;
	}
	
	/**
	 * 为临时用户创建Session 
	 */
	public static HttpSession createUserSession(HttpServletRequest request,HttpServletResponse response) {
		return createUserSession(request, response, null, false);
	}
	
	public static HttpSession createUserSession(HttpServletRequest request,HttpServletResponse response, UserBo user) {
		return createUserSession(request, response, user, false);
	}
	
	/**
	 * 为用户创建Seesion，并发送Cookie
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 */
	public static HttpSession createUserSession(HttpServletRequest request,HttpServletResponse response, UserBo user,boolean isRemember) {
		//为当前用户创建SESSION
		HttpSession session = SessionUtil.getSession(request, response);
		
		UserBo userRef = user == null ? new UserBo() : user;
		
		//防止已经获取过令牌的用户重复获取
		AccessLevelEnum userAccessLevel = AccessLevelUtil.getUserAccessLevel(request);
		//临时用户 和 未登录用户都需要重写cookie
		if (! userAccessLevel.equals(AccessLevelEnum.PRIVATE)) {
			/*设置Cookie*/
			Cookie token = new Cookie(CommonConstants.COOKIE_TOKEN_NAME, JWTUtil.createToken(userRef));
			token.setPath(request.getContextPath());
			if(isRemember){
				token.setMaxAge(CommonConfig.TOKEN_EXPIRE);
			}
			//将用户令牌存放在Cookie中
			response.addCookie(token );
			
			//将USERID存放在Session中（临时用户USerID＜0）
			session.setAttribute(CommonConstants.SESSION_USER_ID, userRef.getId());
		}
		
		return session;
	}
}
