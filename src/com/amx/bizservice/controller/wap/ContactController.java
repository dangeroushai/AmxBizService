package com.amx.bizservice.controller.wap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
import com.amx.bizservice.model.vo.wap.ContactVo;
import com.amx.bizservice.model.vo.wap.input.DeleteContactVo;
import com.amx.bizservice.service.ContactService;

@Authentication
@Controller("mContactController")
@RequestMapping("/contacts")
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
		
		Long userId = (Long) request.getAttribute(CommonConstants.SESSION_USER_ID);
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
	 * 获取联系人
	 */
	@ResponseBody
	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
	public Map<String, Object> getContact(@PathVariable("id")long id){
		Object  response  = ResponseTypeEnum.PARAMETER_ERROR; 
		ContactVo vo = null;
		
		ContactBo bo = contactService.getContact(id );
		if(bo != null){
			response = ResponseTypeEnum.getNewOkResponse();
			vo = new ContactVo(bo);
			((Response)response).setData(vo);
		}
		
		return JsonResponseDto.getResult(response);
	}
	
	/**
	 * 添加联系人(有ID为编辑，无ID为新增)
	 */
	@ResponseBody
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.PUT})
	public Map<String, Object> insertContact(@RequestBody ContactVo inVo,HttpServletRequest request){// 使用Bean接收参数
		Object response = ResponseTypeEnum.PARAMETER_ERROR;

		Long userId = (Long) request.getAttribute(CommonConstants.SESSION_USER_ID);

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
	 * 批量删除收藏产品
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.DELETE)
	public Map<String, Object> deleteContact(@RequestBody DeleteContactVo invo, HttpServletRequest request){
		Object  response = ResponseTypeEnum.PARAMETER_ERROR;
		Long userId = (Long) request.getAttribute(CommonConstants.SESSION_USER_ID);
			
		UpdateResponseBo result = contactService.deleteContact(userId, invo.getContactIdList());
		if(result != null){
			response = ResponseTypeEnum.getNewOkResponse();
			//设置响应数据
			((Response)response).setData(result);
		}
		
		return JsonResponseDto.getResult(response);
	}
	
}
