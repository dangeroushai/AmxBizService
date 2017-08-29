package com.amx.bizservice.service;

import java.util.List;

import com.amx.bizservice.model.bo.ContactBo;
import com.amx.bizservice.model.bo.UpdateResponseBo;
import com.amx.bizservice.model.dto.PageResponseDto;
import com.amx.bizservice.model.qo.PageQuery;


public interface ContactService{
	
	PageResponseDto<ContactBo> getContactList(PageQuery query);
	
	UpdateResponseBo insertContact(ContactBo bo);
	
	//UpdateResponseBo updateContact(ContactBo bo);
	
	UpdateResponseBo deleteContact(long userId, long cartId);

	UpdateResponseBo deleteContact(long userId, List<Long> idList);

	ContactBo getContact(long id);


}
