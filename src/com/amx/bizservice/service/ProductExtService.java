package com.amx.bizservice.service;

import com.amx.bizservice.model.bo.ProductExtBo;

public interface ProductExtService{

	/**
	 * 获取产品的扩展信息
	 * @param pid
	 * @return
	 */
	ProductExtBo getProductExtByProductId(long productId);
}
