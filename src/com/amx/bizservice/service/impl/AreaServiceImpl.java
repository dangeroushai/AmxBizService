package com.amx.bizservice.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amx.bizservice.model.bo.AreaBo;
import com.amx.bizservice.service.AreaService;
import com.fasterxml.jackson.core.type.TypeReference;

@SuppressWarnings("unchecked")
@Service
public class AreaServiceImpl extends BaseService implements AreaService {
	
	public AreaServiceImpl() {
		this.ServiceName = "Area";
	}
	
	@Override
	public List<AreaBo> getAllByParentId(Integer pid) throws Exception {
		List<AreaBo> list = getResultFromRemote("findAllByParentId", pid, new TypeReference<List<AreaBo>>() {});
		if (list != null){
			return list;
		}
		return Collections.EMPTY_LIST;
	}
}
