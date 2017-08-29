package com.amx.bizservice.controller.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amx.bizservice.annotation.Authentication;
import com.amx.bizservice.config.CommonConfig;
import com.amx.bizservice.config.CommonConstants;
import com.amx.bizservice.controller.BaseController;
import com.amx.bizservice.enums.GenderTypeEnum;
import com.amx.bizservice.enums.ResponseTypeEnum;
import com.amx.bizservice.enums.ResponseTypeEnum.Response;
import com.amx.bizservice.model.bo.ContactBo;
import com.amx.bizservice.model.bo.UpdateResponseBo;
import com.amx.bizservice.model.dto.JsonResponseDto;
import com.amx.bizservice.model.dto.PageResponseDto;
import com.amx.bizservice.model.qo.PageQuery;
import com.amx.bizservice.model.vo.ContactVo;
import com.amx.bizservice.service.ContactService;

@Authentication
@Controller
@RequestMapping("/contact")
public class ContactController extends BaseController{
	
	@Autowired
	private ContactService contactService;
	
	/**
	 * 获取联系人列表
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public Map<String, Object> getContactList(HttpServletRequest request){
		Object response = ResponseTypeEnum.PARAMETER_ERROR; 
		List<ContactVo> voList = null;
		
		Long userId = (Long) request.getSession(false).getAttribute(CommonConstants.SESSION_USER_ID);
		PageQuery query = new PageQuery(userId, 0, CommonConfig.MAX_CONTACT_NUM);
		PageResponseDto<ContactBo> page = contactService.getContactList(query );
		if(page != null){
			response = ResponseTypeEnum.getNewOkResponse();
			//将Bo转为VO
			if(page.getContent() != null){
				voList =  new ArrayList<ContactVo>();
				for (ContactBo bo : page.getContent() ) {
					voList.add(new ContactVo(bo));
				}
			}
			//设置响应数据
			((Response)response).setData(voList);
		}
		
		return JsonResponseDto.getResult(response);
	}
	
	/**
	 * 添加联系人(有ID为编辑，无ID为新增)
	 */
	@ResponseBody
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.PUT})
	public Map<String, Object> insertContact( ContactVo inVo,HttpServletRequest request){// 使用Bean接收参数
		Object response = ResponseTypeEnum.PARAMETER_ERROR;

		Long userId = (Long) request.getSession(false).getAttribute(CommonConstants.SESSION_USER_ID);

		/*封装参数*/
		ContactBo bo = new ContactBo();
		bo.setUserId(userId);
		bo.setId(inVo.getId());
		bo.setFirstName(inVo.getFirstName());
		bo.setLastName(inVo.getLastName());
		bo.setGender(GenderTypeEnum.UNKONW.getId());
		bo.setEmail(inVo.getEmail());
		bo.setPassport(inVo.getPassport());
		bo.setCountryCode(inVo.getCountryCode());
		bo.setPhone(inVo.getPhone());
		
		UpdateResponseBo result = contactService.insertContact(bo);
		if(result != null){
			response = ResponseTypeEnum.getNewOkResponse();
			//设置响应数据
			((Response)response).setData(result);
		}
		
		return JsonResponseDto.getResult(response);
	}
	
	
	/**
	 * 删除联系人
	 * @param favorote_id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Map<String, Object> deleteContact(@PathVariable("id") long contactId,HttpServletRequest request){
		Object response = ResponseTypeEnum.PARAMETER_ERROR;

		Long userId = (Long) request.getSession(false).getAttribute(CommonConstants.SESSION_USER_ID);
		UpdateResponseBo result = contactService.deleteContact(userId,contactId);
		if(result != null){
			response = ResponseTypeEnum.getNewOkResponse();
			//设置响应数据
			((Response)response).setData(result);
		}
		
		return JsonResponseDto.getResult(response);
	}
}
