package com.amx.bizservice.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.amx.bizservice.config.CacheConfig;
import com.amx.bizservice.config.CommonConfig;
import com.amx.bizservice.config.CommonConstants;
import com.amx.bizservice.enums.AccessLevelEnum;
import com.amx.bizservice.filter.base.AmxResponse;
import com.amx.bizservice.holder.SpringContextHolder;
import com.amx.bizservice.service.CacheService;
import com.amx.bizservice.util.AccessLevelUtil;
import com.amx.bizservice.util.StringUtil;

/**
 * 一级缓存拦截器
 * @author DangerousHai
 *
 */
public class CacheInterceptor implements HandlerInterceptor {
	private CacheService cacheService = SpringContextHolder.getBean(CacheService.class);
	
    /** 
     * Handler执行之前调用这个方法 
     */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		//GET请求才需要查一级缓存
		if(request.getMethod().equalsIgnoreCase(RequestMethod.GET.name())){
			String cacheKey = getCacheKey(request);
			String responseJson = (String) cacheService.get(cacheKey);
			if(responseJson != null){
				//从缓存获取数据时SpringMVC全局配置的跨域策略失效
				crossOriginControl(response);
				//缓存控制
				if(isRequestCacheable(request, handler)){
					browesrCacheControl(response);
				}
				//标记该请求的响应内容来自一级缓存
				request.setAttribute(CommonConstants.FLAG_L1_CACCHE, true);
				
				response.getWriter().write(responseJson);
				
				return false;
			}
		}
		return true;
	}

	/** 
     * Handler执行之后，ModelAndView返回之前调用这个方法 
     */ 
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	/** 
     * Handler执行完成之后调用这个方法 
     */  
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		if(isRequestCacheable(request,handler)){
			//写缓存
			String responseJson = getResponseJson(response);
			if(isJsonCacheable(responseJson)){
				cacheService.put(getCacheKey(request),responseJson);
				browesrCacheControl(response);
			}
		}
	}
	

	/**
	 * 跨域控制
	 * @param response
	 */
	private void crossOriginControl(HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Credentials", "true");
		response.addHeader("Access-Control-Allow-Origin", CommonConfig.WEB_URL);
	}

	/**
	 * 控制浏览器缓存
	 * @param response
	 */
	private void browesrCacheControl(HttpServletResponse response) {
		if(CacheConfig.ALLOW_BROWSER_CACHE){
			//设置头信息，控制浏览器缓存
			//时间值一定得是当前时间值加上要缓存的时间（1 hour）
			long currentTimeMillis = System.currentTimeMillis();
			response.setDateHeader("Last-Modified",currentTimeMillis);
			response.setDateHeader("Expires", currentTimeMillis + 1000 * 60 * CacheConfig.CACHE_EXPIRE);
			response.setHeader("Cache-Control", "public");  
			response.setHeader("Pragma", "Pragma");
		}
	}

	/**
	 * 获取缓存请求的键
	 * @param request
	 * @return
	 */
	private String getCacheKey(HttpServletRequest request){
		if(StringUtil.isEmpty(request.getQueryString())){
			return request.getPathInfo(); 
		}
		return request.getPathInfo() + "?" + request.getQueryString();
	}
	
	/**
	 * @return 最终返回的Json字符串
	 * @throws IOException 
	 */
	private String getResponseJson(HttpServletResponse response) throws Exception{
		if(response instanceof AmxResponse){
			AmxResponse amxResponse = (AmxResponse)response;
			return amxResponse.getResponseBody();
		}
		
		return null;
	}
	
	/**
	 * 检查请求是否需要缓存
	 * 检查处理请求的方法是否可以缓存
	 * @param request
	 * @return
	 */
	private boolean isRequestCacheable(HttpServletRequest request, Object handler) {
		//不缓存非GTE请求
		if(!request.getMethod().equalsIgnoreCase(RequestMethod.GET.name())){
			return false;
		}
		//不缓存配置的请求
		if(CacheConfig.NoCacheableRequestList.contains(request.getPathInfo())){
			return false;
		}else{
			//路径匹配的请求也不缓存
			for(String requestPath : CacheConfig.NoCacheableRequestList){
				if(request.getPathInfo().startsWith(requestPath)){
					return false;
				}
			}
		}
		//不缓存需要权限验证的请求
		if(!AccessLevelUtil.getHandlerAccessLevel(handler).equals(AccessLevelEnum.PUBLIC)){
			return false;
		}
		
		return true;
	}
	
	/**
	 * 检查是否需要缓存Json
	 * @param json
	 * @return
	 */
	private boolean isJsonCacheable(String json){
		//（无效字符串 || 处理出错 || 无实质返回数据 ） 都不缓存
		if(StringUtil.isEmpty(json) || json.contains("\"status\":false") || json.contains("\"data\":200")){
			return false;
		}
		
		return true;
	}
}
