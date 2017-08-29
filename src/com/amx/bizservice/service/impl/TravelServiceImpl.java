package com.amx.bizservice.service.impl;

import org.springframework.stereotype.Service;

import com.amx.bizservice.enums.UpdateCodeEnum;
import com.amx.bizservice.model.bo.TravelBo;
import com.amx.bizservice.model.bo.UpdateResponseBo;
import com.amx.bizservice.model.dto.PageResponseDto;
import com.amx.bizservice.model.dto.UpdateResponseDto;
import com.amx.bizservice.model.qo.PageQuery;
import com.amx.bizservice.service.TravelService;
import com.amx.bizservice.thrift.Response;
import com.amx.bizservice.util.JsonUtil;
import com.amx.bizservice.util.LogUtil;
import com.amx.bizservice.util.StringUtil;
import com.fasterxml.jackson.core.type.TypeReference;

@Service("travelService")
public class TravelServiceImpl extends BaseService implements TravelService {


	public TravelServiceImpl() {
		this.ServiceName = "Travel";
	}

	@Override
	public PageResponseDto<TravelBo> getTravelList(PageQuery query) {
		Response response = thriftClient.serviceInvoke(this.ServiceName, "findAllByQuery", query);
		PageResponseDto<TravelBo> page = null;
		try {
			if (response != null) {
				if (response.isState()) {
					page = JsonUtil.mapper.readValue(response.getData(),new TypeReference<PageResponseDto<TravelBo>>() {
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
	public UpdateResponseBo deleteTravel(long userId, long travelId) {
		
		//TODO userId check
		
		Response response = thriftClient.serviceInvoke(this.ServiceName, "delete", travelId);
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

	@Override
	public TravelBo getTravel(long userId, long travelId) {
		TravelBo bo = null;
		Response serverResponse = thriftClient.serviceInvoke(this.ServiceName, "findOne", travelId);
		try {
			if (serverResponse != null) {
				if (serverResponse.isState()) {
					bo = JsonUtil.mapper.readValue(serverResponse.getData(),TravelBo.class);
					if(!(bo != null && bo.getUserId().equals(userId))){
						bo = null;
					}
				} else {
					LogUtil.recordWarnLog(serverResponse.getMsg());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return bo;
	}

	@Override
	public Long insertTravel(TravelBo travelBo) {
		Long id = null;
		UpdateResponseDto serverResult = null;
		//bo合法性检验
		if(travelBo == null || StringUtil.isEmpty(travelBo.getName())){
			return null;
		}
		
		Response serverResponse = thriftClient.serviceInvoke(this.ServiceName, "save", travelBo);
		try {
			if (serverResponse != null) {
				if (serverResponse.isState()) {
					serverResult = JsonUtil.mapper.readValue(serverResponse.getData(),UpdateResponseDto.class);
					//执行成功
					if(serverResult.getCode().equals(UpdateCodeEnum.SUCCESS.getCode())){
						//解析ID
						id = Long.parseLong(serverResult.getMsg());
					}else if(serverResult.getCode().equals(UpdateCodeEnum.EXIST.getCode())){
						id = -1L;
					}
				} else {
					LogUtil.recordWarnLog(serverResponse.getMsg());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return id;
	}

	@Override
	public UpdateResponseBo updateTravel(TravelBo travelBo) {
		UpdateResponseBo response = null;
		UpdateResponseDto serverResult = null;
		//TODO - bo合法性检验
		
		Response serverResponse = thriftClient.serviceInvoke(this.ServiceName, "update", travelBo);
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


}
