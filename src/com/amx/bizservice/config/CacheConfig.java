package com.amx.bizservice.config;

import java.util.ArrayList;
import java.util.List;

/**
 * 一级缓存配置
 * @author DangerousHai
 *
 */
public class CacheConfig {
	
	/**
	 * 是否允许客户端(浏览器)缓存响应结果
	 */
	public static boolean ALLOW_BROWSER_CACHE = true;
	/**
	 * 浏览器缓存过期时长（分）
	 */
	public static int CACHE_EXPIRE = 1;
	/**
	 * 不允许被缓存的请求列表（只需配置无需身份验证的GET请求）
	 */
	public static List<String> NoCacheableRequestList = new ArrayList<String>();
	/**
	 * 不允许缓存的主机IP列表
	 */
	public static List<String> NoCacheableHostList = new ArrayList<String>(11);
	
	static {
		//系统命令都不缓存
		NoCacheableRequestList.add("/sys");
		/*WAP - begin*/
		NoCacheableRequestList.add("/tokens");
		NoCacheableRequestList.add("/captchas/sms/");
		/*WAP - end*/
		
		/*WEB - begin*/
		NoCacheableRequestList.add("/authCode");
		NoCacheableRequestList.add("/isLogin");
		NoCacheableRequestList.add("/cart");
		NoCacheableRequestList.add("/travelPlan");
		NoCacheableRequestList.add("/travelList");
		NoCacheableRequestList.add("/third/weibo/onlogin");
		NoCacheableRequestList.add("/third/qq/onlogin");
		
		/*WEB - end*/
		
		//微站客户机的IP
		NoCacheableHostList.add(CommonConfig.WAP_HOST_IP);
	}
}

