package com.amx.bizservice.service;

import java.util.List;

import com.amx.bizservice.model.bo.RegionBo;


public interface RegionService{

	RegionBo getRegion(Integer id);
	
	/**
	 * 
	 * @param hierarchy 查找的深度（最大为3），为null表示所有
	 * @return
	 */
	List<RegionBo> findAll(Integer hierarchy);

	/**
	 * 获取国家下的城市城市名称
	 * @param startCityIdList
	 * @return
	 */
	String getCityName(List<Integer> startCityIdList);

}
