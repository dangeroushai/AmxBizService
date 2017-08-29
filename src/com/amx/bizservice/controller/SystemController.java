package com.amx.bizservice.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amx.bizservice.enums.ResponseTypeEnum;
import com.amx.bizservice.model.dto.JsonResponseDto;
import com.amx.bizservice.service.CacheService;
import com.amx.bizservice.service.ConfigService;
import com.amx.bizservice.util.ConfigUtil;

/**
 * 系统控制器
 * @author DangerousHai
 *
 */
@Controller
@RequestMapping(value = "/sys")
public class SystemController extends BaseController{
	
	@Autowired
	private CacheService cacheService;
	@Autowired
	private ConfigService configService;
	
	/**
	 * 清理一级缓存
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/cache/l1/clear")
	public Map<String, Object> l1CacheClear(){
		cacheService.clear();
		
		return JsonResponseDto.getResult(ResponseTypeEnum.OK);
	}
	
	/**
	 * 刷新全局配置
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/config/refresh")
	public Map<String, Object> refreshConfig(){
		ConfigUtil.loadConfig(configService.getAllConfigs());
		
		return JsonResponseDto.getResult(ResponseTypeEnum.OK);
	}
}
