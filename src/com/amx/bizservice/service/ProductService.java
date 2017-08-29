package com.amx.bizservice.service;

import java.util.List;

import com.amx.bizservice.model.bo.ProductBo;
import com.amx.bizservice.model.qo.ProductQuery;


public interface ProductService{

	/**
	 * 获取详细的产品信息
	 * @param pid
	 * @return
	 */
	ProductBo getProductDetail(long pid, Long userId);
	
	/**
	 * 获取产品快照信息
	 * @param pid
	 * @return
	 */
	ProductBo getProductSnap(long pid, Long userId);

	/**
	 * 根据查询条件获取产品
	 * @param query
	 * @return
	 */
	List<ProductBo> getProductList(ProductQuery query, Long userId);

	/**
	 * 获取指定目的地下所有产品包含的属性
	 * @param destId
	 * @return
	 */
	List<Integer> getAllAttrIdsByDestId(int destId);

	ProductBo getProductSnap(long id);

	ProductBo getProductDetail(long id);

	List<ProductBo> getProductList(ProductQuery query);

	List<ProductBo> getProductList(List<Long> idList);
	
}
