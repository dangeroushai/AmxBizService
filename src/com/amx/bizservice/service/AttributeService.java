package com.amx.bizservice.service;

import java.util.List;

import com.amx.bizservice.model.bo.AttributeBo;


public interface AttributeService{

	/**
	 * 根据ID列表获取属性列表
	 * @param idList
	 * @return
	 */
	List<AttributeBo> findAllByIdList(List<Integer> idList);
	
	List<AttributeBo> findAllByTypeId(Integer typeId);
	
	/**
	 * 根据ID列表获取主题属性列表
	 * @param idList
	 * @return
	 */
	List<AttributeBo> findAllThemeByIdList(List<Integer> idList);

	AttributeBo findOne(Integer id);
	
}
