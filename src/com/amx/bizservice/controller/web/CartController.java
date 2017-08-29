package com.amx.bizservice.controller.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.amx.bizservice.enums.AccessLevelEnum;
import com.amx.bizservice.enums.ResponseTypeEnum;
import com.amx.bizservice.enums.ResponseTypeEnum.Response;
import com.amx.bizservice.model.bo.CartBo;
import com.amx.bizservice.model.bo.LanguageBo;
import com.amx.bizservice.model.bo.PackageBo;
import com.amx.bizservice.model.bo.PriceBo;
import com.amx.bizservice.model.bo.ProductBo;
import com.amx.bizservice.model.dto.JsonResponseDto;
import com.amx.bizservice.model.dto.PageResponseDto;
import com.amx.bizservice.model.qo.PageQuery;
import com.amx.bizservice.model.vo.CartUpdateResponseVo;
import com.amx.bizservice.model.vo.web.CartListVo;
import com.amx.bizservice.service.CartService;
import com.amx.bizservice.service.LanguageService;
import com.amx.bizservice.service.PackageService;
import com.amx.bizservice.service.ProductService;
import com.amx.bizservice.util.AccessLevelUtil;
import com.amx.bizservice.util.SessionUtil;

//允许未登录用户使用购物车
@Authentication(accessLevel = AccessLevelEnum.PUBLIC)
@Controller
@RequestMapping("/cart")
public class CartController extends BaseController{
	
	@Autowired
	private CartService cartService;
	@Autowired
	private ProductService productService;
	@Autowired
	private PackageService packageService;
	@Autowired
	private LanguageService languageService;
	
	/**
	 * 获取购物车数
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public Map<String, Object> getCartNum(HttpServletRequest request){
		Object responseBody = ResponseTypeEnum.PARAMETER_ERROR; 
		CartUpdateResponseVo vo = new CartUpdateResponseVo();
		
		AccessLevelEnum userAccessLevel = AccessLevelUtil.getUserAccessLevel(request);
		//未认证用户
		if(userAccessLevel.equals(AccessLevelEnum.PUBLIC)){
			responseBody = ResponseTypeEnum.getNewOkResponse();
			vo.setIsSucceeded(true); 
			vo.setCartAmount(0);
		}else{
			HttpSession session = request.getSession(false);
			Long userId = (Long) session.getAttribute(CommonConstants.SESSION_USER_ID);
			Integer cartNum = cartService.countCart(userId);
			if(cartNum != null){
				responseBody = ResponseTypeEnum.getNewOkResponse();
				vo.setIsSucceeded(true); 
				vo.setCartAmount(cartNum); 
			}
		}
		
		//设置响应数据
		((Response)responseBody).setData(vo);
		
		return JsonResponseDto.getResult(responseBody);
	}
	
	/**
	 * 获取收藏列表
	 * @param pageIndex
	 * @return
	 */
//	@ResponseBody
	@RequestMapping(value = "/{pageIndex}", method = RequestMethod.GET)
	public Map<String, Object> getCartList(@PathVariable("pageIndex") int pageIndex, HttpServletRequest request){
		Object response = ResponseTypeEnum.PARAMETER_ERROR; 
		CartListVo vo = null;
		int pageSize = CommonConfig.WEB_PAGE_SIZE;
		
		//获取用户访问级别
		AccessLevelEnum userAccessLevel = AccessLevelUtil.getUserAccessLevel(request);
		//未认证用户
		if(userAccessLevel.equals(AccessLevelEnum.PUBLIC)){
			response = ResponseTypeEnum.getNewOkResponse();
			vo =  new CartListVo();
			vo.setPageIndex(0);
			vo.setPageAmount(0);
			vo.setCartList(null);
			vo.setCartAmount(0);
		} else {
			HttpSession session = request.getSession(false);
			Long userId = (Long) session.getAttribute(CommonConstants.SESSION_USER_ID);
			PageQuery query = new PageQuery(userId, pageIndex, pageSize );
			PageResponseDto<CartBo> page = cartService.getCartList(query );
			if(page != null){
				response = ResponseTypeEnum.getNewOkResponse();
				//将Bo转为VO
				vo =  new CartListVo(page);
				if(page.getContent() != null){
					List<CartListVo.CartVo> cartList = new ArrayList<CartListVo.CartVo>();
					for (CartBo bo : page.getContent() ) {
						//将收藏信息转化为产品快照信息
						ProductBo prodbo = productService.getProductSnap(bo.getProductId(), userId);
						PackageBo packbo = packageService.findOne(bo.getPackageId());
						LanguageBo lbo = languageService.findOne(bo.getLanguageId());
						PriceBo pricebo = packageService.getPrice(bo.getProductId(), bo.getPackageId(), bo.getLanguageId(), bo.getAdultNum(), bo.getChildNum(), null);
						cartList.add(vo.new CartVo(bo, prodbo, packbo, lbo, pricebo));
					}
					vo.setCartList(cartList);
				}
			}
		}
		//设置响应数据
		((Response)response).setData(vo);
		
		return JsonResponseDto.getResult(response);
	}
	
	/**
	 * 添加收藏
	 * @param pageIndex
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
//	public Map<String, Object> insertCart(HttpServletRequest request){//使用request接收参数
//	public Map<String, Object> insertCart(@RequestBody MultiValueMap<String	, String> map){//使用Map接收参数
	public Map<String, Object> insertCart( com.amx.bizservice.model.vo.input.CartVo inVo , HttpServletRequest request, HttpServletResponse response){// 使用Bean接收参数
		Object responseBody = ResponseTypeEnum.PARAMETER_ERROR;
		CartUpdateResponseVo result = null;
		/*封装参数*/
		CartBo bo = new CartBo();
		bo.setProductId(inVo.getProductId());
		bo.setPackageId(inVo.getPackageId());
		bo.setLanguageId(inVo.getLanguageId());
		bo.setAdultNum(inVo.getAdultNum());
		bo.setChildNum(inVo.getChildNum());
		bo.setGoOffDate(inVo.getGoOffDate());
		bo.setGoOffTime(inVo.getGoOffTime());
		
		HttpSession session = SessionUtil.createUserSession(request, response); 
		Long userId = (Long) session.getAttribute(CommonConstants.SESSION_USER_ID);
		
		bo.setUserId(userId);
		
		result = new CartUpdateResponseVo(cartService.insertCart(bo));
		if(result != null){
			result.setCartAmount(cartService.countCart(userId));
			responseBody = ResponseTypeEnum.getNewOkResponse();
		}
		//设置响应数据
		((Response)responseBody).setData(result);
		
		return JsonResponseDto.getResult(responseBody);
	}
	
	@Authentication(accessLevel = AccessLevelEnum.PROTECTED)
	@ResponseBody
	@RequestMapping(method = RequestMethod.PUT)
	public Map<String, Object> updateCart( com.amx.bizservice.model.vo.input.CartVo inVo , HttpServletRequest request){// 使用Bean接收参数
		Object response = ResponseTypeEnum.PARAMETER_ERROR;
		CartUpdateResponseVo result = null;
		/*封装参数*/
		CartBo bo = new CartBo();
		bo.setId(inVo.getId());
		bo.setProductId(inVo.getProductId());
		bo.setPackageId(inVo.getPackageId());
		bo.setLanguageId(inVo.getLanguageId());
		bo.setAdultNum(inVo.getAdultNum());
		bo.setChildNum(inVo.getChildNum());
		bo.setGoOffDate(inVo.getGoOffDate());
		bo.setGoOffTime(inVo.getGoOffTime());
		
		HttpSession session = request.getSession(false);
		Long userId = (Long) session.getAttribute(CommonConstants.SESSION_USER_ID);
		
		bo.setUserId(userId);
		
		result = new CartUpdateResponseVo(cartService.updateCart(bo));
		if(result != null){
			response = ResponseTypeEnum.getNewOkResponse();
		}
		//设置响应数据
		((Response)response).setData(result);
		
		return JsonResponseDto.getResult(response);
	}
	
	/**
	 * 删除收藏产品
	 * @param favorote_id
	 * @return
	 */
	@Authentication(accessLevel = AccessLevelEnum.PROTECTED)
	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Map<String, Object> deleteCart(@PathVariable("id") long cartId, HttpServletRequest request){
		Object response = ResponseTypeEnum.PARAMETER_ERROR;
		CartUpdateResponseVo result = null;
		HttpSession session = request.getSession(false);
		Long userId = (Long) session.getAttribute(CommonConstants.SESSION_USER_ID);
		result = new CartUpdateResponseVo(cartService.deleteCart(userId,cartId));
		if(result != null){
			response = ResponseTypeEnum.getNewOkResponse();
		}
		//设置响应数据
		((Response)response).setData(result);
		
		return JsonResponseDto.getResult(response);
	}
	
	/**
	 * 批量删除收藏产品
	 * @return
	 */
	@Authentication(accessLevel = AccessLevelEnum.PROTECTED)
	@ResponseBody
	@RequestMapping(method = RequestMethod.DELETE)
	public Map<String, Object> deleteCart(HttpServletRequest request){
		Object responseBody = ResponseTypeEnum.PARAMETER_ERROR;
		CartUpdateResponseVo result = null;
		List<Long> idList = new ArrayList<Long>();
		try{
			for(String idStr : request.getParameterValues("cartIdList[]")){
				idList.add(Long.parseLong(idStr));
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		HttpSession session = request.getSession(false);
		Long userId = (Long) session.getAttribute(CommonConstants.SESSION_USER_ID);
		result = new CartUpdateResponseVo(cartService.deleteCart(userId, idList));
		if(result != null){
			responseBody = ResponseTypeEnum.getNewOkResponse();
		}
		//设置响应数据
		((Response)responseBody).setData(result);
		
		return JsonResponseDto.getResult(responseBody);
	}

}
