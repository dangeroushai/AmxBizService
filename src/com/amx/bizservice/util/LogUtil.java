package com.amx.bizservice.util;

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alipay.api.AlipayApiException;
import com.amx.bizservice.config.CommonConfig;
import com.amx.bizservice.config.CommonConstants;
import com.amx.bizservice.filter.base.AmxRequest;
import com.amx.bizservice.filter.base.AmxResponse;

/**
 * 日志工具类
 * @author DangerousHai
 *
 */
public class LogUtil {
	/**
	 * 访问日志
	 */
	private static Logger accessLogger = LoggerFactory.getLogger("access");
	/**
	 * 支付日志
	 */
	private static Logger payLogger = LoggerFactory.getLogger("pay");
	/**
	 * 警告日志
	 */
	private static Logger warnLogger = LoggerFactory.getLogger("warn");
	/**
	 * 异常日志
	 */
	private static Logger exceptionLogger = LoggerFactory.getLogger("exception");
	
	
	/**
	 * 记录访问日志
	 * @暂不能记录到请求头为 application/json 提交的数据
	 * @param request
	 * @param response
	 * @param handleTimeMillis 处理请求耗时
	 */
	public static void recordAccessLog(AmxRequest request, AmxResponse response, long handleTimeMillis){
		if(!accessLogger.isInfoEnabled()){return ;}
		//不记录WAP客户机访问日志（由WAP客户机记录）
		if(CommonConfig.WAP_HOST_IP.equals(request.getRemoteAddr())){return ;}
		
		HttpSession session = request.getSession(false);
		//获取用户ID
		Long userId = null;
		if(session != null){
			userId = (Long) session.getAttribute(CommonConstants.SESSION_USER_ID);
		}else{
			userId = (Long)request.getAttribute(CommonConstants.SESSION_USER_ID);
		}
		//请求内容
		String remoteAddr = request.getRemoteAddr();
		String method = request.getMethod();
		String pathInfo = request.getPathInfo();
		String queryString = request.getQueryString();
		StringBuilder sbParameter = new StringBuilder();
		Map<String, String[]> parameterMap = request.getParameterMap();
		if(parameterMap != null){
			for (Entry<String, String[]> parameter : parameterMap.entrySet()) {
				sbParameter.append(parameter.getKey()).append("=");
				String[] values = parameter.getValue();
				if(values != null){
					if(values.length == 1){
						sbParameter.append(values[0]);
					}else{
						sbParameter.append("[");
						for (String value : values) {
							sbParameter.append(value).append(",");
						}
						sbParameter.deleteCharAt(sbParameter.length()-1);
						sbParameter.append("]");
					}
				}
				sbParameter.append(";");
			}
		}
		StringBuilder sbCookie = new StringBuilder();
		Cookie[] cookies = request.getCookies();
		for (int i = 0; cookies != null && i < cookies.length; i++) {
			sbCookie.append(cookies[i].getName()).append("=").append(cookies[i].getValue()).append(";");
		}
		
		//响应内容(若是从缓存中获取的数据则不不记录详细的响应内容，以减小日志大小)
		String responseBody = null;
		if(request.getAttribute(CommonConstants.FLAG_L1_CACCHE) != null ){
			responseBody = "[FROM L1 CACHE]";
		}else if(request.getAttribute(CommonConstants.FLAG_L2_CACCHE) != null ){
			responseBody = "[FROM L2 CACHE]";
		}else{
			responseBody = response.getResponseBody();
		}
		
		StringBuilder sbLog = new StringBuilder();
		sbLog.append(remoteAddr).append(" ").append(userId).append(" ").append(method).append(" ").append(pathInfo).append("?").append(queryString);
		sbLog.append("\n");
		sbLog.append("Cookies:").append(sbCookie.toString());
		sbLog.append("\n");
		sbLog.append("RequestParams:").append(sbParameter.toString());
		sbLog.append("\n");
		sbLog.append("ResponseBody:").append(responseBody);
		sbLog.append("\n");
		sbLog.append("ElapsedTime:").append(handleTimeMillis);
		sbLog.append("\n");
		
		accessLogger.info(sbLog.toString());
	}
	
	
	/**
	 * 记录异常日志
	 * @param e
	 * @param request
	 */
	public static void recordExceptionLog(Exception e,HttpServletRequest request){
		if(!exceptionLogger.isErrorEnabled()){return ;}
		
		HttpSession session = request.getSession(false);
		//获取用户ID
		Long userId = null;
		if(session != null){
			userId = (Long) session.getAttribute(CommonConstants.SESSION_USER_ID);
		}else{
			userId = (Long)request.getAttribute(CommonConstants.SESSION_USER_ID);
		}
		String remoteAddr = request.getRemoteAddr();
		String method = request.getMethod();
		String pathInfo = request.getPathInfo();
		String queryString = request.getQueryString();
		
		String log = remoteAddr +" "+ userId +" "+ method +" "+ pathInfo +"?"+ queryString;
		
		exceptionLogger.error(log, e);
	}
	public static void recordExceptionLog(Exception e){
		exceptionLogger.error(e.getMessage());
	}
	
	/**
	 * 记录警告信息
	 * @param msg
	 */
	public static void recordWarnLog(String msg){
		warnLogger.warn(msg);
	}


	/**
	 * 记录支付日志
	 * @param log
	 */
	public static void recordPayLog(String log) {
		payLogger.info(log);
	}


	/**
	 * 记录支付日志
	 * @param log
	 */
	public static void recordPayLog(String log, AlipayApiException e) {
		payLogger.error(log, e);
	}
}
