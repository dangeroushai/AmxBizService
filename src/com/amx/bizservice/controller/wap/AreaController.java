package com.amx.bizservice.controller.wap;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amx.bizservice.annotation.Authentication;
import com.amx.bizservice.controller.BaseController;
import com.amx.bizservice.enums.AccessLevelEnum;
import com.amx.bizservice.enums.ResponseTypeEnum;
import com.amx.bizservice.enums.ResponseTypeEnum.Response;
import com.amx.bizservice.model.bo.AreaBo;
import com.amx.bizservice.model.dto.JsonResponseDto;
import com.amx.bizservice.service.AreaService;

@Authentication(accessLevel = AccessLevelEnum.PUBLIC)
@Controller("mAreaController")
@RequestMapping("/areas")
public class AreaController extends BaseController{
	
	@Autowired
	private AreaService areaService;
	
	/**
	 * 获取联系人列表
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public Map<String, Object> getContactList(@RequestParam(name = "parentid",defaultValue = "-1") int parentId, HttpServletRequest request) throws Exception{
		Object response = ResponseTypeEnum.PARAMETER_ERROR; 
		
		List<AreaBo> list = areaService.getAllByParentId(parentId);
		if(list != null){
			response = ResponseTypeEnum.getNewOkResponse();
			//设置响应数据
			((Response)response).setData(list);
		}
		
		return JsonResponseDto.getResult(response);
	}
}
