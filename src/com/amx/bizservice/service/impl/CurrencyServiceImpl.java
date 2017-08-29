package com.amx.bizservice.service.impl;

import org.springframework.stereotype.Service;

import com.amx.bizservice.model.bo.CurrencyBo;
import com.amx.bizservice.service.CurrencyService;
import com.amx.bizservice.thrift.Response;
import com.amx.bizservice.util.JsonUtil;
import com.amx.bizservice.util.LogUtil;

@Service("currencyService")
public class CurrencyServiceImpl extends BaseService implements CurrencyService {

	public CurrencyServiceImpl() {
		this.ServiceName = "Currency";
	}

	
	/**
	 * 调用数据服务，获取货币对象
	 * @param id
	 * @return
	 */
	@Override
	public CurrencyBo findOne(Integer id) {
		Response response = thriftClient.serviceInvoke(this.ServiceName, "findOne", id);
		CurrencyBo bo = null;
		try {
			if (response != null) {
				if (response.isState()) {
					bo = JsonUtil.mapper.readValue(response.getData(),CurrencyBo.class);
				} else {
					LogUtil.recordWarnLog(response.getMsg());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return bo;
	}
}
