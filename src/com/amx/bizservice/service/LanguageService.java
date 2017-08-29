package com.amx.bizservice.service;

import java.util.List;

import com.amx.bizservice.model.bo.LanguageBo;


public interface LanguageService{

	/**
	 * 根据ID列表获取语言列表
	 * @param idList
	 * @return
	 */
	List<LanguageBo> findAllByIdList(List<Integer> idList);

	LanguageBo findOne(Integer languageId);
	
}
