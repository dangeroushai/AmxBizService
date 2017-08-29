package com.amx.bizservice.service;

import java.util.List;

import com.amx.bizservice.model.bo.CustomHodometerBo;
import com.amx.bizservice.model.bo.UpdateResponseBo;


public interface CustomHodometerService{

	UpdateResponseBo insertCustomHodometer(CustomHodometerBo bo);
	
	UpdateResponseBo insertCustomHodometerInBatch(List<CustomHodometerBo> travel_items);

	/**
	 * 获取指定行程第一项活动
	 * @param travelId
	 * @return
	 */
	CustomHodometerBo getFirstCustomHodometer(Long travelId);

	List<CustomHodometerBo> getCustomHodometerListByUserId(long userId);
	
	/**
	 * 已按出发日期+时间升序排列
	 * @param travelId
	 * @return
	 */
	List<CustomHodometerBo> getCustomHodometerListByTravelId(long travelId);

	UpdateResponseBo updateCustomHodometer(CustomHodometerBo bo);

	UpdateResponseBo deleteCustomHodometer(long userId, List<Long> idList);

	UpdateResponseBo deleteCustomHodometerByTravelId(Long id);

	void saveTempCustomHodometer(Long tempUserId, Long userId);

	CustomHodometerBo getPlaceholderHodometer();


	
}
