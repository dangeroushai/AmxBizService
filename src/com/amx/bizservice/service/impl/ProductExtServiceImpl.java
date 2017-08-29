package com.amx.bizservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amx.bizservice.model.bo.ProductExtBo;
import com.amx.bizservice.service.ProductExtService;
import com.amx.bizservice.service.ProductService;
import com.amx.bizservice.thrift.Response;
import com.amx.bizservice.util.JsonUtil;
import com.amx.bizservice.util.LogUtil;

@Service("productExtService")
public class ProductExtServiceImpl extends BaseService implements ProductExtService {

	@Autowired
	private ProductService productService;

	public ProductExtServiceImpl() {
		this.ServiceName = "ProductExt";
	}
	
	@Override
	public ProductExtBo getProductExtByProductId(long productId) {
		Response response = thriftClient.serviceInvoke(this.ServiceName, "findOneByProductId", (Long)productId);
		ProductExtBo productExtBo = null;
		
		try {
			if (response != null) {
				if (response.isState()) {
					productExtBo = JsonUtil.mapper.readValue(response.getData(), ProductExtBo.class);
					inflation(productExtBo);
				} else {
					LogUtil.recordWarnLog(response.getMsg());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return productExtBo;
	}
	
	
	/**
	 * 填充非数据库属性
	 */
	private void inflation(ProductExtBo productExtBo) {
		
		productExtBo.setAdditionProductList(productService.getProductList(productExtBo.getAdditionProductIdList()));
		productExtBo.setCatProductList(productService.getProductList(productExtBo.getCatProductIdList()));
		productExtBo.setHotelProductList(productService.getProductList(productExtBo.getHotelProductIdList()));
		if(productExtBo.getIsTicket()){
			productExtBo.setTicketProductList(productService.getProductList(productExtBo.getTicketProductIdList()));
		}
	}
}
