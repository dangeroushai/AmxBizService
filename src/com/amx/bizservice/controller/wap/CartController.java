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
import org.springframework.web.bind.annotation.RequestParam;
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
import com.amx.bizservice.model.bo.UpdateResponseBo;
import com.amx.bizservice.model.dto.JsonResponseDto;
import com.amx.bizservice.model.dto.PageResponseDto;
import com.amx.bizservice.model.qo.PageQuery;
import com.amx.bizservice.model.vo.CartUpdateResponseVo;
import com.amx.bizservice.model.vo.wap.CartListVo;
import com.amx.bizservice.model.vo.wap.CartVo;
import com.amx.bizservice.model.vo.wap.input.DeleteCartVo;
import com.amx.bizservice.service.CartService;
import com.amx.bizservice.service.LanguageService;
import com.amx.bizservice.service.PackageService;
import com.amx.bizservice.service.ProductService;

@Authentication(accessLevel = AccessLevelEnum.PROTECTED)
@Controller("mCartController")
@RequestMapping("/carts")
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
	 * 获取购物车明细
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/{cartId}", method = RequestMethod.GET)
	public Map<String, Object> getCart(@PathVariable("cartId") long cartId, HttpServletRequest request){
		Object response = ResponseTypeEnum.PARAMETER_ERROR; 
		CartVo vo = null;
		
		Long userId = (Long) request.getAttribute(CommonConstants.SESSION_USER_ID);
		
		CartBo bo = cartService.getCart(userId,cartId);
		if(bo != null){
			response = ResponseTypeEnum.getNewOkResponse();
			//将Bo转为VO
			ProductBo prodbo = productService.getProductSnap(bo.getProductId(),userId);
			PackageBo packbo = packageService.findOne(bo.getPackageId());
			LanguageBo langbo = languageService.findOne(bo.getLanguageId());
			PriceBo pricebo = packageService.getPrice(bo.getProductId(), bo.getPackageId(), bo.getLanguageId(), bo.getAdultNum(), bo.getChildNum(), null);
				
			vo = new CartVo(bo, prodbo, packbo, langbo, pricebo);
			
			//设置响应数据
			((Response)response).setData(vo);
		}
		
		return JsonResponseDto.getResult(response);
	}
	
	
	
	/**
	 * 获取购物车列表
	 * @param pageIndex
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public Map<String, Object> getCartList(@RequestParam(name = "pageindex", defaultValue = "0") int pageIndex,@RequestParam(name = "pagesize", defaultValue = "" + CommonConfig.WAP_PAGE_SIZE) int pageSize, HttpServletRequest request){
		Object response = ResponseTypeEnum.PARAMETER_ERROR; 
		if(pageSize <= 0){
			pageSize = CommonConfig.WAP_PAGE_SIZE;
		}
		CartListVo vo = null;
		
		Long userId = (Long) request.getAttribute(CommonConstants.SESSION_USER_ID);
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
		//设置响应数据
		((Response)response).setData(vo);
		
		return JsonResponseDto.getResult(response);
	}
	
	/**
	 * @param pageIndex
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> insertCart(@RequestBody com.amx.bizservice.model.vo.input.CartVo inVo , HttpServletRequest request){// 使用Bean接收参数
		Object response = ResponseTypeEnum.PARAMETER_ERROR;
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
		
		Long userId = (Long) request.getAttribute(CommonConstants.SESSION_USER_ID);
		
		bo.setUserId(userId);
		
		result = new CartUpdateResponseVo(cartService.insertCart(bo));
		if(result != null){
			response = ResponseTypeEnum.getNewOkResponse();
		}
		//设置响应数据
		((Response)response).setData(result);
		
		return JsonResponseDto.getResult(response);
	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.PUT)
	public Map<String, Object> updateCart(@RequestBody com.amx.bizservice.model.vo.input.CartVo inVo , HttpServletRequest request){// 使用Bean接收参数
		Object response = ResponseTypeEnum.PARAMETER_ERROR;
		UpdateResponseBo result = null;
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
		
		Long userId = (Long) request.getAttribute(CommonConstants.SESSION_USER_ID);
		bo.setUserId(userId);
		
		result = cartService.updateCart(bo);
		if(result != null){
			response = ResponseTypeEnum.getNewOkResponse();
		}
		//设置响应数据
		((Response)response).setData(result);
		
		return JsonResponseDto.getResult(response);
	}
	
	/**
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.DELETE)
	public Map<String, Object> deleteCart(@RequestBody DeleteCartVo invo, HttpServletRequest request){
		Object response = ResponseTypeEnum.PARAMETER_ERROR;
		CartUpdateResponseVo result = null;
		Long userId = (Long) request.getAttribute(CommonConstants.SESSION_USER_ID);
		result = new CartUpdateResponseVo(cartService.deleteCart(userId, invo.getCartIdList()));
		if(result != null){
			response = ResponseTypeEnum.getNewOkResponse();
		}
		//设置响应数据
		((Response)response).setData(result);
		
		return JsonResponseDto.getResult(response);
	}
}
