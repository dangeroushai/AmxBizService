package com.amx.bizservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amx.bizservice.config.CommonConfig;
import com.amx.bizservice.enums.UpdateCodeEnum;
import com.amx.bizservice.model.bo.ContactBo;
import com.amx.bizservice.model.bo.UpdateResponseBo;
import com.amx.bizservice.model.dto.PageResponseDto;
import com.amx.bizservice.model.dto.UpdateResponseDto;
import com.amx.bizservice.model.qo.PageQuery;
import com.amx.bizservice.service.ContactService;
import com.amx.bizservice.thrift.Response;
import com.amx.bizservice.util.JsonUtil;
import com.amx.bizservice.util.LogUtil;
import com.fasterxml.jackson.core.type.TypeReference;

@Service("contactService")
public class ContactServiceImpl extends BaseService implements ContactService {


	public ContactServiceImpl() {
		this.ServiceName = "Contact";
	}
	
	@Override
	public ContactBo getContact(long id) {
		Response response = thriftClient.serviceInvoke(this.ServiceName, "findOne", id);
		ContactBo bo = null;
		try {
			if (response != null) {
				if (response.isState()) {
					bo = JsonUtil.mapper.readValue(response.getData(),ContactBo.class);
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
	public PageResponseDto<ContactBo> getContactList(PageQuery query) {
		Response response = thriftClient.serviceInvoke(this.ServiceName, "findAllByQuery", query);
		PageResponseDto<ContactBo> page = null;
		try {
			if (response != null) {
				if (response.isState()) {
					//XXX - 获取泛型的类类型 ： TypeReference
					page = JsonUtil.mapper.readValue(response.getData(),new TypeReference<PageResponseDto<ContactBo>>() {
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
	public UpdateResponseBo insertContact(ContactBo bo) {
		UpdateResponseBo response = null;
		UpdateResponseDto serverResult = null;
		if(this.countContact(bo.getUserId()) > CommonConfig.MAX_CONTACT_NUM){
			response = new UpdateResponseBo();
			response.setIsSucceeded(false);
			response.setMsg("联系人个数已超过最大数限制");
		}else{
			//TODO - bo合法性检验
			
			Response serverResponse = thriftClient.serviceInvoke(this.ServiceName, "save", bo);
			try {
				if (serverResponse != null) {
					if (serverResponse.isState()) {
						response = new UpdateResponseBo();
						serverResult = JsonUtil.mapper.readValue(serverResponse.getData(),UpdateResponseDto.class);
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
		}
		return response;
	}
	
	@Override
	public UpdateResponseBo deleteContact(long userId, long id) {
		List<Long> idList = new ArrayList<Long>();
		idList.add(id);
		
		return deleteContact(userId,idList);
	}
	
	@Override
	public UpdateResponseBo deleteContact(long userId, List<Long> idList) {
		List<ContactBo> boList = null;
		if(idList != null && idList.size() > 0){
			boList = new ArrayList<ContactBo>();
			for (long id : idList){
				ContactBo bo = new ContactBo();
				bo.setUserId(userId);
				bo.setId(id);
				boList.add(bo);
			}
		}
		
		return delete(boList);
	}

	private Integer countContact(long userId) {
		ContactBo bo = new ContactBo();
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
	
	private UpdateResponseBo delete(List<ContactBo> boList){
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
