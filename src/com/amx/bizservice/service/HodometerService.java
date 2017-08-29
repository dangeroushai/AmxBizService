package com.amx.bizservice.service;

import java.util.List;
import java.util.Map;

import com.amx.bizservice.model.bo.HodometerBo;


public interface HodometerService{
	
	Map<Integer, List<HodometerBo>> getHodometerByProductId(long id);
}
