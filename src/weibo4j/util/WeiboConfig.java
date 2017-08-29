package weibo4j.util;

import java.util.Properties;

public class WeiboConfig {
	public WeiboConfig(){}
	private static Properties props = new Properties(); 
	
	static{
		//Common Config
		props.put("baseURL", "https://api.weibo.com/2/");
		props.put("accessTokenURL", "https://api.weibo.com/oauth2/access_token");
		props.put("authorizeURL", "https://api.weibo.com/oauth2/authorize");
		props.put("rmURL", "https://rm.api.weibo.com/2/");
		//User Config
		props.put("client_ID", "755286474");
		props.put("client_SERCRET", "22f747cbf351066f4eae83c1ff436581");
		props.put("redirect_URI", "http://www.aimanxing.com/Server/member/sina.php");
	}
	
/*	static{
		try {
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}*/
	public static String getValue(String key){
		return props.getProperty(key);
	}

    public static void updateProperties(String key,String value) {    
            props.setProperty(key, value); 
    } 
}
