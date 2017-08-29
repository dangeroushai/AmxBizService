package com.amx.bizservice.config;

public class CommonConstants {
	
	public static final String COOKIE_SESSION_NAME = "JSESSIONID";
	public static final String MSG_CAPTCHA = "msgCaptcha";
	public static final String COOKIE_TOKEN_NAME = "jwt";
	public static final String SESSION_USER_ID = "userId";
	public static final String SESSION_CART_NUM = "cartNum";
	public static final String SESSION_CART_LIST = "cartList";
	public static final String PIC_CAPTCHA = "picCaptcha";
	public static final String REQUEST_HEADER_JWT = "Authorization";
	public static final String SESSION_PAY_REQUEST_ORDER_IDS = "payRequestOrderIds";
	public static final String FLAG_L1_CACCHE = "FlagL1Cache";
	public static final String FLAG_L2_CACCHE = "FlagL2Cache";
	public static final String SESSION_LOGIN_FAIL_TIME = "FailTimes";
	
	/**
	 * 緩存名字
	 * @author DangerousHai
	 *
	 */
	public static final class CACHE_NAME{
		public static final String CART = "Cart";
		public static final String USER = "User";
		public static final String FIRST_LEVEL = "FirstLevel";
		public static final String CONTACT = "Contact";
		public static final String FAVORITE = "Favorite";
		public static final String TRAVEL = "Travel";
	}
}
