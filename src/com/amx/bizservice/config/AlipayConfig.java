package com.amx.bizservice.config;


/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	//pid - deprected
	//public static String seller_id = "2088121744443589";
	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016071901638317";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCEpEPo3YJ8R7Yxqdkt+rk3G2ImXEYhQmgfBtFb6ch59f5+GJTIEla29lQ93nlaIFU0cnqcI1h/97PqcGF4iofIyedTxIufX5ehFKHoT6jZtAdBak0sOjLNB+KPd9efqgPhIRRHmqd1Mh0Gwe5tnXPuEYnsb5iMGbNLp4wzWlZyehZwgKaHP3mCPVPvBpdmhpotzOp0LSXFWEEDGVUWFa4VQ27y9sGV4GEmOlHOb8TxcTES9b3iLJ/d1X4RTIdvi7vZbWPzcLMHzDJbn0AZ/5P0vCbZSQnVh9zAV6J46YRT0TS3Js3qUc5Y5BlYL1+FsgEH+vZq555eBOPKtoqEafAZAgMBAAECggEAehIbFYJWhj4RJR+lajVmo5ql95vQRuewn21M3AQJpoSIFFBXDNIjz/pfiRjoIdPD1qLMyEmXNx4mDEokyiTj1hxKssMdpCz7hRlR8FTvlnuiecd1ApEFE8B/931Zie2T6GrjGQrxtl/Kat+rqGHRwXWOs3P4tmcopSWrV89545u17Qe/9bYpasKZhsB0a+ggpCA4ymJSbHjgjevh8iplC46zuKmHac71sSDAd+XtT05C7RLIMX4Qg6rHROK+VvmE8Risx018sV4OC6Fpsy+U35kRkJzO0VQWs0ehHIcLTF4CbQGdQhoSuSK9UhBtDOOV43f44HmOwHAoG9PMmhbcAQKBgQDT9t/Cq88zF5ZVFWEcaTYQvuUxp0FxJeyOxPUWiinvt5rRO+GcNnXqEwFCFb4vXrdPlCqagPNTE98OC6dEMz+x5ceIOCnU/f/vZ05CPB/YSn0mlI+MPm3P5VL49YqTan9xh3fFO4FMkft2kBKHgoadvewWE87Tslvt40nC+1SCQQKBgQCgMrAj82Kg6+MsEsbe7Yo1SkNsdQfENqdPO7QSjGC5X+RulmLyTmcL1ZdvL1mXc9QK7IoS5tI4GBbNRAFJIvMiiHOawe5WJxcGndVr5bqv7aghmOYlCIsuiKHIHXGiZmXcXljaeG/3syPtz84AqQGPSGdC6Vmzfu/DpCjBiMfH2QKBgQCAs4jAGjQwMKU+OkIBzzxdxJtDn+vTeRR+x8aNDg1SmaguXW6qvEKygyGjb7JP7vHdJvkLYTrdedZZH+hLN9b3/K4/3uI/io6JXa+d9gXb/bap41CZ9hoVPog31Fd1FRgW5gtqPaxPwt/t6KLnlvCH/RbJfPN7hPPeqMBT3Ok+QQKBgQCLVSrgSerpveD7T2WLNZ101h+ocG2N2l9QycPIMjJaaS38hW6QA2Ma5u5sdQl+ce7mhW9eG0aSJVmHtPNNUkmYKogp/Sy34A3l4ldjHg9uvszyU9MwOKsdkRbtP7a7IkbMEX9EnWX9UeEZERnCdzx1C+tMRjuPpAMT7W7JTZc9mQKBgQDG32BGhAuajwcwyTOtH7IEztMxHMEtRjUWOzlfv96ewSsb9WkiYVnJq6ot6jybKfC8HEBwwo/P+mXknttAF8Primx4GPD3iT3ySZ2cNioe6W03uPIOINsOjAbnM2f0kk+WWOIJ3hMeK7TZNK9XJ4T5wDp1zeNNR6iLQb+jkplQaQ==";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjrEVFMOSiNJXaRNKicQuQdsREraftDA9Tua3WNZwcpeXeh8Wrt+V9JilLqSa7N7sVqwpvv8zWChgXhX/A96hEg97Oxe6GKUmzaZRNh0cZZ88vpkn5tlgL4mH/dhSr3Ip00kvM4rHq9PwuT4k7z1DpZAf1eghK8Q5BgxL88d0X07m9X96Ijd0yMkXArzD7jg+noqfbztEKoH3kPMRJC2w4ByVdweWUT2PwrlATpZZtYLmtDvUKG/sOkNAIKEMg3Rut1oKWpjyYanzDgS7Cg3awr1KPTl9rHCazk15aNYowmYtVabKwbGVToCAGK+qQ1gT3ELhkGnf3+h53fukNqRH+wIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://api.aimanxing.com/alipay/notify";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 返回格式
	public static String format = "json";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipay.com/gateway.do";
	
//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
}

