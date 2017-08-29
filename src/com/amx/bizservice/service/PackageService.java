package com.amx.bizservice.service;

import java.util.List;

import com.amx.bizservice.model.bo.PackageBo;
import com.amx.bizservice.model.bo.PriceBo;


public interface PackageService{
	
	List<PackageBo> findAllByProductId(long pid);

	/**
	 * 获取产品的实时价格
	 * @param packageId
	 * @param languageId
	 * @param adultNum
	 * @param childNum
	 * @param udid 优惠码标识
	 * @return
	 */
	PriceBo getPrice(Long productId, Long packageId, Integer languageId,
			Integer adultNum, Integer childNum, String udid);

	PackageBo findOne(Long packageId);

	PackageBo getCheapestPackage(long productId);
}
