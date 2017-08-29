package com.amx.bizservice.config;

/**
 * 注意：
 * 1.未保证配置正常加载，名称需全为大写和下划线组成
 * 2.除String外的所有数据类型需为基本类型
 * @author DangerousHai
 *
 */
public class CommonConfig {
	
/****************************以上配置在启动时由配置文件加载*****************************/
	/**
	 * 单个用户最大联系人数
	 */
	public static  int MAX_CONTACT_NUM = 20;
	/**
	 * WEB端默认分页大小
	 */
	public final static int WEB_PAGE_SIZE = 21;
	/**
	 * WAP端默认分页大小
	 */
	public final static int WAP_PAGE_SIZE = 20;	
	/**
	 * 验证码过期时间（ms）
	 */
	public static  long CAPTCHE_TIME_OUT = 5 * 60 * 1000;
	/**
	 * token 过期时间 (s)
	 */
	public static  int TOKEN_EXPIRE = 7 * 24 * 60 * 60;
	/**
	 * 用户在线状态过期时间 (ms)
	 */
	public static  int USER_ONLINE_EXPIRE = 30 * 60 * 1000;
	/**
	 * 图片站点根目录(绝对路径)
	 */
	public static String IMG_SITE_BASE_PATH = "D:/Program Files/IIS/wwwroot/image";
	/**
	 * 上传文件根目录
	 */
	public static String UPLOAD_DIRECTORY = "/uploads";
	/**
	 * 图片服务器
	 */
	public static String IMAGE_SERVER_URL = "http://image.aimanxing.com";
	/**
	 * 允许访问的域
	 */
	public static String WEB_URL = "http://www.aimanxing.com";
	/**
	 * 微站客户机IP
	 */
	public static String WAP_HOST_IP = "192.168.163.128";
	/**
	 * 是否对参数HTML编码
	 */
	public static boolean ESCAPE_HTML = true;
	/**
	 * 是否对参数JS编码
	 */
	public static boolean ESCAPE_JAVASCRIPT = true;
	/**
	 * 是否对参数SQL编码
	 */
	public static boolean ESCAPE_SQL = true;
/****************************以上配置在启动时由配置文件加载*****************************/	

	/**
	 * 接受通知的URL
	 */
	private static  String BASE_NOTICE_URL = WEB_URL + "/admin/index.php/notice/notice_send?novalidate=1&id={orderId}&type=";
	
	/**
	 * 接收到支付宝异步通知时，转发支付宝请求到后台管理服务器地址，由后台管理服务器处理通知请求
	 */
	public static  String ALIPAY_DONE_NOTICE_URL = BASE_NOTICE_URL + "CV";
	/**
	 * 新订单通知地址
	 */
	public static  String NEW_ORDER_NOTICE_URL = BASE_NOTICE_URL + "CR";	
	
	
/****************************以下配置在启动时由数据库加载*****************************/
	
	/**
	 * 文章-关于我们-数据库中的id
	 */
	public static  int ABOUT_US_ARTICLE_ID = 2;
	/**
	 * 行程规划占位产品ID
	 */
	public static  long TRAVEL_PLAN_PLACE_HOLDER_ID = 0;
	
	/**
	 * 接收新订单通知的手机号
	 */
	public static  String RECEIVE_NOTICE_PHONE = "15708457468";
	/**
	 * 接收新订单通知的邮箱
	 */
	public static  String RECEIVE_NOTICE_EMAIL = "info@aimanxing.com";
	
	/**
	 * 可容忍的支付价格差
	 */
	public static double TOLERANCE_PRICE = 0;
	
	/**
	 * 限制人数
	 */
	public static  int DEFAULT_MAX_ADULT = 15;
	public static  int DEFAULT_MAX_CHILD = 10;
	public static  int DEFAULT_MAX_PERSON = 20;
	public static  int DEFAULT_MIN_PERSON = 1;
	/**
	 * 预售延迟天数
	 */
	public static  int DEFAULT_PRESALE_DELAY = 7;
	
	/**
	 * 儿童规则
	 */
	public static  String DEFAULT_CHILDRULE = "6-12岁";
	
	/**
	 * 默认图像路径
	 */
	public static  String DEFAULT_PIC_PATH = "";
	
/****************************以上配置在启动时由数据库加载*****************************/
}
