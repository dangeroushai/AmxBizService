package com.amx.bizservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.amx.bizservice.config.CommonConstants;
import com.amx.bizservice.enums.UpdateCodeEnum;
import com.amx.bizservice.model.bo.CartBo;
import com.amx.bizservice.model.bo.ProductBo;
import com.amx.bizservice.model.bo.UpdateResponseBo;
import com.amx.bizservice.model.dto.PageResponseDto;
import com.amx.bizservice.model.dto.UpdateResponseDto;
import com.amx.bizservice.model.qo.PageQuery;
import com.amx.bizservice.service.CartService;
import com.amx.bizservice.service.ProductService;
import com.amx.bizservice.thrift.Response;
import com.amx.bizservice.util.JsonUtil;
import com.amx.bizservice.util.LogUtil;
import com.fasterxml.jackson.core.type.TypeReference;

@Service("cartService")
@CacheConfig(cacheNames = CommonConstants.CACHE_NAME.CART)
public class CartServiceImpl extends BaseService implements CartService {

	@Autowired
	private ProductService productService;
	
	public CartServiceImpl() {
		this.ServiceName = "Cart";
	}

	@Override
	public PageResponseDto<CartBo> getCartList(PageQuery query) {
		Response response = thriftClient.serviceInvoke(this.ServiceName, "findAllByQuery", query);
		PageResponseDto<CartBo> page = null;
		try {
			if (response != null) {
				if (response.isState()) {
					//XXX - 获取泛型的类类型 ： TypeReference
					page = JsonUtil.mapper.readValue(response.getData(),new TypeReference<PageResponseDto<CartBo>>() {
					});
				} else {
					LogUtil.recordWarnLog(response.getMsg());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return page;
	}

	@Override
	@CacheEvict(key="#bo.getUserId()")
	public UpdateResponseBo insertCart(CartBo bo) {
		UpdateResponseBo response = null;
		UpdateResponseDto serverResult = null;
		
		/*bo合法性检验*/
		//设置默认值
		if(bo.getPackageId() == null || bo.getLanguageId() == null){
			ProductBo productSnap = productService.getProductSnap(bo.getProductId());
			if(bo.getPackageId() == null){
				bo.setPackageId(productSnap.getPackageIdList().get(0).longValue());
			}
			if(bo.getLanguageId() == null){
				bo.setLanguageId(productSnap.getLanguageIdList().get(0));
			}
		}
		if(bo.getAdultNum() == null){
			bo.setAdultNum(1);
		}
		if(bo.getChildNum() == null){
			bo.setChildNum(0);
		}
		
		Response serverResponse = thriftClient.serviceInvoke(this.ServiceName, "save", bo);
		try {
			if (serverResponse != null) {
				if (serverResponse.isState()) {
					serverResult = JsonUtil.mapper.readValue(serverResponse.getData(),UpdateResponseDto.class);
					response = new UpdateResponseBo();
					//执行成功
					if(serverResult.getCode().equals(UpdateCodeEnum.SUCCESS.getCode())){
						response.setIsSucceeded(true);
					}else {//执行失败
						response.setIsSucceeded(false);
						/*失败原因分析*/
						response.setMsg("系统繁忙，请稍后再试");
					}
				} else {
					LogUtil.recordWarnLog(serverResponse.getMsg());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return response;
	}
	
	@Override
	@CacheEvict(key="#userId")
	public UpdateResponseBo deleteCart(long userId, long id) {
		List<Long> idList = new ArrayList<Long>();
		idList.add(id);
		
		return deleteCart(userId,idList);
	}
	
	@Override
	@CacheEvict(key="#userId")
	public UpdateResponseBo deleteCart(long userId, List<Long> idList) {
		List<CartBo> boList = null;
		if(idList != null && idList.size() > 0){
			boList = new ArrayList<CartBo>();
			for (long id : idList){
				CartBo bo = new CartBo();
				bo.setUserId(userId);
				bo.setId(id);
				boList.add(bo);
			}
		}
		
		return delete(boList);
	}

	@Override
	@Cacheable( key="#userId")
	public Integer countCart(long userId) {
		CartBo bo = new CartBo();
		bo.setUserId(userId);
		Response response = thriftClient.serviceInvoke(this.ServiceName, "count", bo);
		Integer serverResult = null;
		try {
			if (response != null) {
				if (response.isState()) {
					serverResult = JsonUtil.mapper.readValue(response.getData(),Integer.class);
				} else {
					LogUtil.recordWarnLog(response.getMsg());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return serverResult;
	} 
	
	
	@Override
	public UpdateResponseBo updateCart(CartBo bo) {
		UpdateResponseBo response = null;
		UpdateResponseDto serverResult = null;
		//TODO - bo合法性检验
		
		Response serverResponse = thriftClient.serviceInvoke(this.ServiceName, "update", bo);
		try {
			if (serverResponse != null) {
				if (serverResponse.isState()) {
					serverResult = JsonUtil.mapper.readValue(serverResponse.getData(),UpdateResponseDto.class);
					response = new UpdateResponseBo();
					//执行成功
					if(serverResult.getCode().equals(UpdateCodeEnum.SUCCESS.getCode())){
						response.setIsSucceeded(true);
					}else {//执行失败
						response.setIsSucceeded(false);
						/*失败原因分析*/
						response.setMsg("系统繁忙，请稍后再试");
					}
				} else {
					LogUtil.recordWarnLog(serverResponse.getMsg());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return response;
	}
	
	private UpdateResponseBo updateCart(List<CartBo> boList) {
		if (boList == null || boList.size() == 0) { return null; }
		UpdateResponseBo response = null;
		UpdateResponseDto serverResult = null;
		//TODO - bo合法性检验
		
		Response serverResponse = thriftClient.serviceInvoke(this.ServiceName, "updateInBatch", boList);
		try {
			if (serverResponse != null) {
				if (serverResponse.isState()) {
					serverResult = JsonUtil.mapper.readValue(serverResponse.getData(),UpdateResponseDto.class);
					response = new UpdateResponseBo();
					//执行成功
					if(serverResult.getCode().equals(UpdateCodeEnum.SUCCESS.getCode())){
						response.setIsSucceeded(true);
					}else {//执行失败
						response.setIsSucceeded(false);
						/*失败原因分析*/
						response.setMsg("系统繁忙，请稍后再试");
					}
				} else {
					LogUtil.recordWarnLog(serverResponse.getMsg());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return response;
	}
	
	@Override
	public CartBo getCart(long userId, Long cartId) {
		Response response = thriftClient.serviceInvoke(this.ServiceName, "findOne", cartId);
		CartBo bo = null;
		try {
			if (response != null) {
				if (response.isState()) {
					bo = JsonUtil.mapper.readValue(response.getData(),CartBo.class);
				} else {
					LogUtil.recordWarnLog(response.getMsg());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return bo;
	}
	
	@Override
	public void saveTempCart(Long tempUserId,Long userId){
		List<CartBo> updateCartList = null;
		
		PageQuery query = new PageQuery(tempUserId, 0, 1000);
		PageResponseDto<CartBo> pageResponseDto = this.getCartList(query );
		if(pageResponseDto != null && pageResponseDto.getContent() != null && pageResponseDto.getContent().size() != 0){
			updateCartList = new ArrayList<CartBo>();
			for (CartBo cartBo : pageResponseDto.getContent()) {
				if(cartBo != null){
					CartBo upateCartBo = new CartBo();
					upateCartBo.setId(cartBo.getId());
					upateCartBo.setUserId(userId);
					updateCartList.add(upateCartBo);
				}
			}
		}
		this.updateCart(updateCartList);
	}
	
	private UpdateResponseBo delete(List<CartBo> boList){
		Response response = thriftClient.serviceInvoke(this.ServiceName, "deleteInBatch", boList);
		UpdateResponseBo reaponse = null;
		UpdateResponseDto serverResult = null;
		try {
			if (response != null) {
				if (response.isState()) {
					serverResult = JsonUtil.mapper.readValue(response.getData(),UpdateResponseDto.class);
					reaponse = new UpdateResponseBo();
					//执行成功
					if(serverResult.getCode().equals(UpdateCodeEnum.SUCCESS.getCode())){
						reaponse.setIsSucceeded(true);
					}else {//执行失败
						reaponse.setIsSucceeded(false);
						/*失败原因分析*/
						reaponse.setMsg("系统繁忙，请稍后再试");
					}
				} else {
					LogUtil.recordWarnLog(response.getMsg());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return reaponse;
	}

}
