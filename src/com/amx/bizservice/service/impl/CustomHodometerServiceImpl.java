
package com.amx.bizservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amx.bizservice.config.CommonConfig;
import com.amx.bizservice.enums.UpdateCodeEnum;
import com.amx.bizservice.model.bo.CustomHodometerBo;
import com.amx.bizservice.model.bo.ProductBo;
import com.amx.bizservice.model.bo.UpdateResponseBo;
import com.amx.bizservice.model.dto.UpdateResponseDto;
import com.amx.bizservice.service.CustomHodometerService;
import com.amx.bizservice.service.ProductService;
import com.amx.bizservice.thrift.Response;
import com.amx.bizservice.util.JsonUtil;
import com.amx.bizservice.util.LogUtil;
import com.fasterxml.jackson.core.type.TypeReference;

@Service("customHodometerService")
public class CustomHodometerServiceImpl extends BaseService implements CustomHodometerService {

	@Autowired
	private ProductService productService;	
	
	public CustomHodometerServiceImpl() {
		this.ServiceName = "CustomHodometer";
	}

	@Override
	public UpdateResponseBo insertCustomHodometer(CustomHodometerBo bo) {
		UpdateResponseBo response = null;
		UpdateResponseDto serverResult = null;
		
		setDefaultAttribute(bo);
		
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
	public UpdateResponseBo deleteCustomHodometer(long userId, List<Long> idList) {
		//TODO - 检验用户id
		
		Response response = thriftClient.serviceInvoke(this.ServiceName, "delete", idList);
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
	public UpdateResponseBo updateCustomHodometer(CustomHodometerBo bo) {
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

	@Override
	public List<CustomHodometerBo> getCustomHodometerListByUserId(long userId) {
		Response response = thriftClient.serviceInvoke(this.ServiceName, "findAllByUserId", userId);
		List<CustomHodometerBo> boList = null;
		try {
			if (response != null) {
				if (response.isState()) {
					boList = JsonUtil.mapper.readValue(response.getData(),new TypeReference<ArrayList<CustomHodometerBo>>() {
					});
				} else {
					LogUtil.recordWarnLog(response.getMsg());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		if(boList == null){
			boList = new ArrayList<CustomHodometerBo>();	
		}
		
		return boList;
	}

	@Override
	public List<CustomHodometerBo> getCustomHodometerListByTravelId(long travelId) {
		Response response = thriftClient.serviceInvoke(this.ServiceName, "findAllByTravelId", travelId);
		List<CustomHodometerBo> boList = null;
		try {
			if (response != null) {
				if (response.isState()) {
					boList = JsonUtil.mapper.readValue(response.getData(),new TypeReference<ArrayList<CustomHodometerBo>>() {
					});
				} else {
					LogUtil.recordWarnLog(response.getMsg());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return boList;
	}
	
	@Override
	public CustomHodometerBo getFirstCustomHodometer(Long travelId) {
		Response response = thriftClient.serviceInvoke(this.ServiceName, "findOneByTravelId", travelId);
		CustomHodometerBo bo = null;
		try {
			if (response != null) {
				if (response.isState()) {
					bo = JsonUtil.mapper.readValue(response.getData(),CustomHodometerBo.class) ;
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
	public UpdateResponseBo insertCustomHodometerInBatch(List<CustomHodometerBo> boList) {
		UpdateResponseBo response = null;
		UpdateResponseDto serverResult = null;
		
		for (int i = 0; boList != null && i < boList.size(); i++) {
			setDefaultAttribute(boList.get(i));
		}
		
		Response serverResponse = thriftClient.serviceInvoke(this.ServiceName, "saveInBatch", boList);
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
	public UpdateResponseBo deleteCustomHodometerByTravelId(Long id) {
		Response response = thriftClient.serviceInvoke(this.ServiceName, "deleteByTravelId", id);
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
	public void saveTempCustomHodometer(Long tempUserId,Long userId){
		List<CustomHodometerBo> updateHodometerList = null;
		
		List<CustomHodometerBo> hodometerList = this.getCustomHodometerListByUserId(tempUserId);
		if(hodometerList != null && hodometerList.size() != 0){
			updateHodometerList = new ArrayList<CustomHodometerBo>();
			for (CustomHodometerBo chBo : hodometerList) {
				if(chBo != null){
					CustomHodometerBo upateCartBo = new CustomHodometerBo();
					upateCartBo.setId(chBo.getId());
					upateCartBo.setUserId(userId);
					updateHodometerList.add(upateCartBo);
				}
			}
		}
		this.updateCustomHodometer(updateHodometerList);
	}
	
	private UpdateResponseBo updateCustomHodometer(List<CustomHodometerBo> updateHodometerList) {
		UpdateResponseBo response = null;
		UpdateResponseDto serverResult = null;
		//TODO - bo合法性检验
		
		Response serverResponse = thriftClient.serviceInvoke(this.ServiceName, "updateInBatch", updateHodometerList);
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

	/**
	 * 获取行程占位产品
	 * @return
	 */
	@Override
	public CustomHodometerBo getPlaceholderHodometer() {
		CustomHodometerBo placeHolder = null;
		ProductBo productSnap = productService.getProductSnap(CommonConfig.TRAVEL_PLAN_PLACE_HOLDER_ID);
		if(productSnap != null){
			placeHolder = new CustomHodometerBo();
			//占位行程id为0
			placeHolder.setId(0L);
			placeHolder.setProductId(productSnap.getId());
			placeHolder.setAdultNum(0);
			placeHolder.setChildNum(0);
		}
		
		return placeHolder;
	}
	
	/**
	 * 设置默认值
	 * @param bo
	 */
	private void setDefaultAttribute(CustomHodometerBo bo) {
		if(bo == null) {return ;}
		
		/* bo合法性检验*/
		if(bo.getLanguageId() == null || bo.getPackageId() == null){
			ProductBo productSnap = productService.getProductSnap(bo.getProductId());
			if(bo.getLanguageId() == null){
				bo.setLanguageId(productSnap.getLanguageIdList().get(0));
			}
			if(bo.getPackageId() == null){
				bo.setPackageId(Long.valueOf(productSnap.getPackageIdList().get(0)));
			}
		}
		if( bo.getAdultNum() == null){
			bo.setAdultNum(1);
		}
		if( bo.getChildNum() == null){
			bo.setChildNum(0);
		}
	}
}
