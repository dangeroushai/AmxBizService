package com.amx.bizservice.util;

import java.lang.reflect.Field;
import java.util.List;

import com.amx.bizservice.config.CommonConfig;
import com.amx.bizservice.model.bo.ConfigBo;

public class ConfigUtil {
	/**
	 * 将外部持久保存的配置映射到内存
	 * @param configs
	 */
	public static void loadConfig(List<ConfigBo> configs) {
		if(configs != null){
			Class<CommonConfig> clazz =  CommonConfig.class;
			for (ConfigBo configBo : configs) {
				String key = configBo.getKey();
				//只加载名称全为大写和下划线组成的配置项（自定义配置）
				if(!key.toUpperCase().equals(key)){
					continue;
				}
				try {
					Field field = clazz.getField(key);
					String fieldTypeName = field.getType().getName();
					
					Object value = null;
					String strValue = configBo.getValue();
					if(fieldTypeName.indexOf("int") != -1){
						value = Integer.parseInt(strValue);
					} else if(fieldTypeName.indexOf("long") != -1){
						value = Long.parseLong(strValue);
					} else if(fieldTypeName.indexOf("double") != -1){
						value = Double.parseDouble(strValue);
					} else if(fieldTypeName.indexOf("boolean") != -1){
						value = Boolean.parseBoolean(strValue);
					} else if (field.get(null) instanceof String) {
						value = strValue;
					}else{
						LogUtil.recordWarnLog("配置项["+ configBo.getKey() +"]数据类型不支持");
					}
					field.set(null, value);
				} catch (Exception e) {
					LogUtil.recordWarnLog("配置项["+ configBo.getKey() +"]加载失败");
				}
			}
		}
	}
}
