package com.amx.bizservice.service.impl;

import org.springframework.stereotype.Service;

import com.amx.bizservice.enums.GenderTypeEnum;
import com.amx.bizservice.enums.OrderStateEnum;
import com.amx.bizservice.enums.UpdateCodeEnum;
import com.amx.bizservice.model.bo.RequirementBo;
import com.amx.bizservice.model.bo.UpdateResponseBo;
import com.amx.bizservice.model.dto.UpdateResponseDto;
import com.amx.bizservice.service.RequirementService;
import com.amx.bizservice.thrift.Response;
import com.amx.bizservice.util.JsonUtil;
import com.amx.bizservice.util.LogUtil;

@Service("requirementService")
public class RequirementServiceImpl extends BaseService implements RequirementService {


	public RequirementServiceImpl() {
		this.ServiceName = "Requirement";
	}

	@Override
	public UpdateResponseBo insertRequirement(RequirementBo bo) {
		UpdateResponseBo response = null;
		UpdateResponseDto serverResult = null;
		//默认值
		bo.setGender(GenderTypeEnum.MAN.getId());
		bo.setOrderStateId(OrderStateEnum.WAIT_COMFIRMATION.getId());
		bo.setSubscription(0D);
		
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
}
