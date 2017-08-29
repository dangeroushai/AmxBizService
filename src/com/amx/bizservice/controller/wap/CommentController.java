package com.amx.bizservice.controller.wap;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amx.bizservice.annotation.Authentication;
import com.amx.bizservice.config.CommonConfig;
import com.amx.bizservice.config.CommonConstants;
import com.amx.bizservice.controller.BaseController;
import com.amx.bizservice.enums.ResponseTypeEnum;
import com.amx.bizservice.enums.ResponseTypeEnum.Response;
import com.amx.bizservice.model.bo.CommentBo;
import com.amx.bizservice.model.bo.UpdateResponseBo;
import com.amx.bizservice.model.dto.JsonResponseDto;
import com.amx.bizservice.model.dto.PageResponseDto;
import com.amx.bizservice.model.qo.PageQuery;
import com.amx.bizservice.model.vo.input.CommentVo;
import com.amx.bizservice.model.vo.wap.CommentListVo;
import com.amx.bizservice.service.CommentService;
import com.amx.bizservice.service.OrderService;

@Controller("mCommentController")
@RequestMapping("/comments")
public class CommentController extends BaseController{
	
	@Autowired
	private CommentService commentService;
	@Autowired
	private OrderService orderService;
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public Map<String, Object> getCommentList(
			@RequestParam(name = "pageindex", defaultValue = "0") int pageIndex,
			@RequestParam(name = "pagesize", defaultValue = "" + CommonConfig.WAP_PAGE_SIZE) int pageSize,
			@RequestParam("typeid") int typeId,
			@RequestParam("productid")long productId
			){
		Object  response  = ResponseTypeEnum.PARAMETER_ERROR; 
		if(pageSize <= 0){
			pageSize = CommonConfig.WAP_PAGE_SIZE;
		}
		CommentListVo vo = null;
		PageQuery query = new PageQuery(null, pageIndex, pageSize );
		query.setProductId(productId);
		PageResponseDto<CommentBo> page = commentService.getCommentList(query);
		if(page != null){
			response = ResponseTypeEnum.getNewOkResponse();
			//将Bo转为VO
			vo =  new CommentListVo(page);
			//设置响应数据
			((Response)response).setData(vo);
		}
		
		return JsonResponseDto.getResult(response);
	}
	
	/**
	 * 添加评论
	 * @return
	 */
	@Authentication
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> insertComment(@RequestBody CommentVo invo, HttpServletRequest request){
		Object response = ResponseTypeEnum.SERVER_ERROR;
		
		Long userId = (Long) request.getAttribute(CommonConstants.SESSION_USER_ID);
		
		CommentBo bo = new CommentBo();
		bo.setUserId(userId);
		bo.setContent(invo.getContent());
		bo.setOrderId(invo.getOrderId());
		bo.setPictureList(null);
		//获取订单关联的产品Id
		bo.setProductId(orderService.getProductIdByOrderId(invo.getOrderId()));
		
		UpdateResponseBo result = commentService.insertComment(bo);
		if(result != null){
			response = ResponseTypeEnum.getNewOkResponse();
			//设置响应数据
			((Response)response).setData(result);
		}
		
		return JsonResponseDto.getResult(response);
	}
}
