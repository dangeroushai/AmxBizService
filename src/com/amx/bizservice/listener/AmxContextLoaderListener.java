package com.amx.bizservice.listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

import com.amx.bizservice.holder.SpringContextHolder;
import com.amx.bizservice.model.bo.ConfigBo;
import com.amx.bizservice.service.ConfigService;
import com.amx.bizservice.util.ConfigUtil;
import com.amx.bizservice.util.LogUtil;

/**
 * 
 *  继承了Spring的监听器ContextLoaderListener ， 无需在web.xml中再配置
 * @author DangerousHai
 *
 */
public class AmxContextLoaderListener extends ContextLoaderListener {

	private static final String PROPERTIES_LOCATION_PARAM = "propertiesLocation";

	@Override
	public void contextInitialized(ServletContextEvent event) {
		//顺序不能换
		this.initProperties(event);
		
		super.contextInitialized(event);
		
		this.initCommonConfigFromProp();
		this.initCommonConfigFromDB();
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		super.contextDestroyed(event);
	}

	/**
	 * 初始化属性配置文件配置
	 */
	private void initProperties(ServletContextEvent event) {
		ServletContext sc = event.getServletContext();
		String configLocationParam = sc.getInitParameter(PROPERTIES_LOCATION_PARAM);
		List<String> configFiles = Arrays
				.asList(configLocationParam.split(","));
		for (String configFile : configFiles) {
			Properties properties = new Properties();
			try {
				properties.load(getClass().getResourceAsStream(configFile.trim()));
				for (String prop : properties.stringPropertyNames()) {
					if (System.getProperty(prop) == null) {
						System.setProperty(prop, properties.getProperty(prop));
					}
				}
			} catch (Exception e) {
				LogUtil.recordExceptionLog(e);
				throw new RuntimeException(e);
			}
		}
	}
	
	/**
	 * 初始化公共配置（从数据库）
	 */
	private void initCommonConfigFromDB(){
		ConfigService configService = SpringContextHolder.getBean(ConfigService.class);
		if(configService != null){
			ConfigUtil.loadConfig(configService.getAllConfigs());
		}
	}
	
	/**
	 * 初始化公共配置（从配置文件）
	 */
	private void initCommonConfigFromProp(){
		Properties properties = System.getProperties();
		Enumeration<?> propertyNames = properties.propertyNames();
		List<ConfigBo> configs = new ArrayList<ConfigBo>();
		while (propertyNames.hasMoreElements()) {
			String key = (String) propertyNames.nextElement();
			String value = (String) properties.get(key);
			
			ConfigBo config  = new ConfigBo();
			config.setKey(key);
			config.setValue(value);
			
			configs.add(config);
		}
		ConfigUtil.loadConfig(configs);
	}
	
}
