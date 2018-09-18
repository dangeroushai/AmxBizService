package com.amx.bizservice.service;

import java.util.List;

import com.amx.bizservice.model.bo.AreaBo;

public interface AreaService {

	List<AreaBo> getAllByParentId(Integer pid) throws Exception;

}
