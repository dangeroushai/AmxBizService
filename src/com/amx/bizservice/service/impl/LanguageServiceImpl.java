package com.amx.bizservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amx.bizservice.model.bo.LanguageBo;
import com.amx.bizservice.service.LanguageService;
import com.amx.bizservice.thrift.Response;
import com.amx.bizservice.util.JsonUtil;
import com.amx.bizservice.util.LogUtil;
import com.fasterxml.jackson.databind.JavaType;

@Service("languageService")
public class LanguageServiceImpl extends BaseService implements LanguageService{

	public LanguageServiceImpl(){
		this.ServiceName = "Language";		
	}
	
	@Override
	public List<LanguageBo> findAllByIdList(List<Integer> idList) {
		Response response = thriftClient.serviceInvoke(this.ServiceName, "findAllByIdList", idList);
		List<LanguageBo> boList = null; 
		try {
			if(response != null){
				if(response.isState()){
					JavaType javaType = JsonUtil.getCollectionType(ArrayList.class, LanguageBo.class);		
					boList = JsonUtil.mapper.readValue(response.getData(),javaType);
				}else{
					LogUtil.recordWarnLog(response.getMsg());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return boList;
	}

	@Override
	public LanguageBo findOne(Integer id) {
		if(id == null) return null;
		
		Response response = thriftClient.serviceInvoke(this.ServiceName, "findOne", id);
		LanguageBo bo = null;
		try {
			if (response != null) {
				if (response.isState()) {
					bo = JsonUtil.mapper.readValue(response.getData(), LanguageBo.class);
				} else {
					LogUtil.recordWarnLog(response.getMsg());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return bo;
	}
}
