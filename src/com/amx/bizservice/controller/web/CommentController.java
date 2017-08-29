package com.amx.bizservice.controller.web;

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
import com.amx.bizservice.enums.ResponseTypeEnum;
import com.amx.bizservice.enums.ResponseTypeEnum.Response;
import com.amx.bizservice.model.bo.CommentBo;
import com.amx.bizservice.model.bo.UpdateResponseBo;
import com.amx.bizservice.model.dto.JsonResponseDto;
import com.amx.bizservice.model.dto.PageResponseDto;
import com.amx.bizservice.model.qo.PageQuery;
import com.amx.bizservice.model.vo.input.CommentVo;
import com.amx.bizservice.model.vo.web.CommentListVo;
import com.amx.bizservice.service.CommentService;
import com.amx.bizservice.service.OrderService;
import com.amx.bizservice.util.PictureUtil;

@Controller
@RequestMapping("/comment")
public class CommentController extends BaseController{
	
	@Autowired
	private CommentService commentService;
	@Autowired
	private OrderService orderService;
	
	@ResponseBody
	@RequestMapping(value = "/{typeId}/{productId}/{pageIndex}", method = RequestMethod.GET)
	public Map<String, Object> getCommentList(@PathVariable("typeId") int typeId, @PathVariable("productId")long productId, @PathVariable("pageIndex") int pageIndex){
		Object response = ResponseTypeEnum.PARAMETER_ERROR; 
		CommentListVo vo = null;
		int pageSize = CommonConfig.WEB_PAGE_SIZE;
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
	public Map<String, Object> insertComment(HttpServletRequest request,CommentVo invo){
		Object response = ResponseTypeEnum.SERVER_ERROR;
		
		Long userId = (Long) request.getSession(false).getAttribute(CommonConstants.SESSION_USER_ID);
		//保存图片
		List<String> pictureList = PictureUtil.saveCommnetPictures(request, invo.getOrderId(), userId);
		
		CommentBo bo = new CommentBo();
		bo.setUserId(userId);
		bo.setContent(invo.getContent());
		bo.setOrderId(invo.getOrderId());
		bo.setPictureList(pictureList);
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
