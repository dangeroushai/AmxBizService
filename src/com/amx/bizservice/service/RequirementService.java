package com.amx.bizservice.service;

import com.amx.bizservice.model.bo.RequirementBo;
import com.amx.bizservice.model.bo.UpdateResponseBo;


public interface RequirementService{
	
	UpdateResponseBo insertRequirement(RequirementBo bo);
}
