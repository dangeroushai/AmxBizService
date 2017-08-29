package com.amx.bizservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amx.bizservice.enums.UpdateCodeEnum;
import com.amx.bizservice.model.bo.FavoriteBo;
import com.amx.bizservice.model.bo.UpdateResponseBo;
import com.amx.bizservice.model.dto.PageResponseDto;
import com.amx.bizservice.model.dto.UpdateResponseDto;
import com.amx.bizservice.model.qo.PageQuery;
import com.amx.bizservice.service.FavoriteService;
import com.amx.bizservice.thrift.Response;
import com.amx.bizservice.util.JsonUtil;
import com.amx.bizservice.util.LogUtil;
import com.fasterxml.jackson.core.type.TypeReference;

@Service("favoriteService")
public class FavoriteServiceImpl extends BaseService implements FavoriteService {

	public FavoriteServiceImpl() {
		this.ServiceName = "Favorite";
	}
	
	@Override
	public List<Long> getFavoriteProductIdsByUserId(Long userId) {
		Response response = thriftClient.serviceInvoke(this.ServiceName, "getProductIdListByUserId", userId);
		List<Long> idList = null;
		try {
			if (response != null) {
				if (response.isState()) {
					//XXX - 获取泛型的类类型 ： TypeReference
					idList = JsonUtil.mapper.readValue(response.getData(),new TypeReference<ArrayList<Long>>() {
					});
				} else {
					LogUtil.recordWarnLog(response.getMsg());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return idList;
	}

	@Override
	public PageResponseDto<FavoriteBo> getFavoriteList(PageQuery query) {
		Response response = thriftClient.serviceInvoke(this.ServiceName, "findAllByQuery", query);
		PageResponseDto<FavoriteBo> page = null;
		try {
			if (response != null) {
				if (response.isState()) {
					//XXX - 获取泛型的类类型 ： TypeReference
					page = JsonUtil.mapper.readValue(response.getData(),new TypeReference<PageResponseDto<FavoriteBo>>() {
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
	public UpdateResponseBo insertFavorite(long userId, long productId) {
		FavoriteBo bo = new FavoriteBo();
		bo.setProductId(productId);
		bo.setUserId(userId);
		Response response = thriftClient.serviceInvoke(this.ServiceName, "save", bo);
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
						if(serverResult.getCode().equals(UpdateCodeEnum.EXIST.getCode())){
							reaponse.setMsg("您已经收藏过该商品");
						}else{
							reaponse.setMsg("收藏失败");
						}
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

	@Override
	public UpdateResponseBo deleteFavoriteById(long userId, long favoriteId) {
		FavoriteBo bo = new FavoriteBo();
		bo.setId(favoriteId);
		bo.setUserId(userId);
		
		return delete(bo);
	}

	@Override
	public UpdateResponseBo deleteFavoriteByProductId(long userId, long productId) {
		FavoriteBo bo = new FavoriteBo();
		bo.setProductId(productId);
		bo.setUserId(userId);
		
		return delete(bo);
	}
	
	private UpdateResponseBo delete(FavoriteBo bo) {
		
		Response response = thriftClient.serviceInvoke(this.ServiceName, "delete", bo);
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
