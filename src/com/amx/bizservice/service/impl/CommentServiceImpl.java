package com.amx.bizservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amx.bizservice.enums.UpdateCodeEnum;
import com.amx.bizservice.model.bo.CommentBo;
import com.amx.bizservice.model.bo.UpdateResponseBo;
import com.amx.bizservice.model.dto.PageResponseDto;
import com.amx.bizservice.model.dto.UpdateResponseDto;
import com.amx.bizservice.model.qo.PageQuery;
import com.amx.bizservice.service.CommentService;
import com.amx.bizservice.service.OrderService;
import com.amx.bizservice.thrift.Response;
import com.amx.bizservice.util.JsonUtil;
import com.amx.bizservice.util.LogUtil;
import com.amx.bizservice.util.StringUtil;
import com.fasterxml.jackson.core.type.TypeReference;

@Service("commentService")
public class CommentServiceImpl extends BaseService implements CommentService {

	@Autowired
	private OrderService orderService;
	
	public CommentServiceImpl() {
		this.ServiceName = "Comment";
	}

	@Override
	public PageResponseDto<CommentBo> getCommentList(PageQuery query) {
		Response response = thriftClient.serviceInvoke(this.ServiceName, "findAllByQuery", query);
		PageResponseDto<CommentBo> page = null;
		try {
			if (response != null) {
				if (response.isState()) {
					//XXX - 获取泛型的类类型 ： TypeReference
					page = JsonUtil.mapper.readValue(response.getData(),new TypeReference<PageResponseDto<CommentBo>>() {});
					if(page != null && page.getContent() != null){
						for (CommentBo bo : page.getContent()) {
							bo.setUserName(StringUtil.fuzzy(bo.getUserName()));
						}
					}
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
	public UpdateResponseBo insertComment(CommentBo bo) {
		UpdateResponseBo response = null;
		UpdateResponseDto serverResult = null;
		//TODO - bo合法性检验
		
		Response serverResponse = thriftClient.serviceInvoke(this.ServiceName, "save", bo);
		try {
			if (serverResponse != null) {
				if (serverResponse.isState()) {
					serverResult = JsonUtil.mapper.readValue(serverResponse.getData(),UpdateResponseDto.class);
					response = new UpdateResponseBo();
					//执行成功
					if(serverResult.getCode().equals(UpdateCodeEnum.SUCCESS.getCode())){
						//更新订单状态:完成
						orderService.doneOrder(bo.getUserId(),bo.getOrderId());
						
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
