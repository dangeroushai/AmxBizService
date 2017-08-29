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
import com.amx.bizservice.enums.ResponseTypeEnum;
import com.amx.bizservice.enums.ResponseTypeEnum.Response;
import com.amx.bizservice.model.bo.FavoriteBo;
import com.amx.bizservice.model.bo.ProductBo;
import com.amx.bizservice.model.bo.UpdateResponseBo;
import com.amx.bizservice.model.dto.JsonResponseDto;
import com.amx.bizservice.model.dto.PageResponseDto;
import com.amx.bizservice.model.qo.PageQuery;
import com.amx.bizservice.model.vo.input.FavoriteVo;
import com.amx.bizservice.model.vo.web.FavoriteListVo;
import com.amx.bizservice.service.FavoriteService;
import com.amx.bizservice.service.ProductService;

@Authentication
@Controller
@RequestMapping("/collect")
public class FavoriteController extends BaseController{
	
	@Autowired
	private FavoriteService favoriteService;
	@Autowired
	private ProductService productService;
	
	/**
	 * 获取收藏列表
	 * @param pageIndex
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/{pageIndex}", method = RequestMethod.GET)
	public Map<String, Object> getFavoriteList(@PathVariable("pageIndex") int pageIndex,HttpServletRequest request){
		Object response = ResponseTypeEnum.PARAMETER_ERROR; 
		FavoriteListVo vo = null;
		int pageSize = CommonConfig.WEB_PAGE_SIZE;
		
		long userId = (long) request.getSession(false).getAttribute(CommonConstants.SESSION_USER_ID);
		PageQuery query = new PageQuery(userId, pageIndex, pageSize );
		PageResponseDto<FavoriteBo> page = favoriteService.getFavoriteList(query );
		if(page != null){
			List<ProductBo> content = new ArrayList<ProductBo>();
			response = ResponseTypeEnum.getNewOkResponse();
			if(page.getContent() != null){
				for (FavoriteBo bo : page.getContent() ) {
					//将收藏信息转化为产品快照信息
					content.add(productService.getProductSnap(bo.getProductId(),userId));
				}
			}
			
			//将Bo转为VO
			vo =  new FavoriteListVo(page, content);
			//设置响应数据
			((Response)response).setData(vo);
		}
		
		return JsonResponseDto.getResult(response);
	}
	
	/**
	 * 添加收藏
	 * @param pageIndex
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> insertFavorite(HttpServletRequest request ,FavoriteVo invo){
		Object response = ResponseTypeEnum.SERVER_ERROR;
		Long userId = (Long) request.getSession(false).getAttribute(CommonConstants.SESSION_USER_ID);
		UpdateResponseBo result = favoriteService.insertFavorite(userId , invo.getProductId());
		if(result != null){
			response = ResponseTypeEnum.getNewOkResponse();
			//设置响应数据
			((Response)response).setData(result);
		}
		
		return JsonResponseDto.getResult(response);
	}
	
	/**
	 * 删除收藏产品
	 * @param favorote_id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Map<String, Object> deleteFavorite(@PathVariable("id") int favoriteId, HttpServletRequest request){
		Object response = ResponseTypeEnum.PARAMETER_ERROR;
		
		long userId = (long) request.getSession(false).getAttribute(CommonConstants.SESSION_USER_ID);
		UpdateResponseBo result = favoriteService.deleteFavoriteById(userId, favoriteId);
		if(result != null){
			response = ResponseTypeEnum.getNewOkResponse();
			//设置响应数据
			((Response)response).setData(result);
		}
		
		return JsonResponseDto.getResult(response);
	}
	
	/**
	 * 删除收藏产品
	 * @param product_id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/{productTypeId}/{productId}", method = RequestMethod.DELETE)
	public Map<String, Object> deleteFavorite(@PathVariable("productTypeId") int typeId , @PathVariable("productId") int productId , HttpServletRequest request){
		Object response = ResponseTypeEnum.PARAMETER_ERROR;
		
		long userId = (long) request.getSession(false).getAttribute(CommonConstants.SESSION_USER_ID);
		UpdateResponseBo result = favoriteService.deleteFavoriteByProductId(userId, productId);
		if(result != null){
			response = ResponseTypeEnum.getNewOkResponse();
			//设置响应数据
			((Response)response).setData(result);
		}
		
		return JsonResponseDto.getResult(response);
	}
}
