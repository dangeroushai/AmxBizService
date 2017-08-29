package com.amx.bizservice.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.amx.bizservice.model.bo.ConfigBo;
import com.amx.bizservice.service.ConfigService;
import com.amx.bizservice.thrift.Response;
import com.amx.bizservice.util.JsonUtil;
import com.amx.bizservice.util.LogUtil;
import com.fasterxml.jackson.core.type.TypeReference;

@Service
public class ConfigServiceImpl extends BaseService implements ConfigService {
	
	public ConfigServiceImpl() {
		this.ServiceName = "Config";
	}
	
	@Override
	public List<ConfigBo> getAllConfigs() {
		Response response = thriftClient.serviceInvoke(this.ServiceName, "findAll",null);
		List<ConfigBo> boList = null;
		try {
			if (response != null) {
				if (response.isState()) {
					boList = JsonUtil.mapper.readValue(response.getData(),new TypeReference<List<ConfigBo>>() {
					});
				} else {
					LogUtil.recordWarnLog(response.getMsg());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return boList;
	}
}
