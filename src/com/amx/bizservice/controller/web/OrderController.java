package com.amx.bizservice.controller.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amx.bizservice.annotation.Authentication;
import com.amx.bizservice.config.CommonConfig;
import com.amx.bizservice.config.CommonConstants;
import com.amx.bizservice.controller.BaseController;
import com.amx.bizservice.enums.CommonTypeEnum;
import com.amx.bizservice.enums.GenderTypeEnum;
import com.amx.bizservice.enums.ResponseTypeEnum;
import com.amx.bizservice.enums.ResponseTypeEnum.Response;
import com.amx.bizservice.model.bo.CurrencyBo;
import com.amx.bizservice.model.bo.LanguageBo;
import com.amx.bizservice.model.bo.OrderBo;
import com.amx.bizservice.model.bo.PackageBo;
import com.amx.bizservice.model.bo.ProductBo;
import com.amx.bizservice.model.bo.TravellerBo;
import com.amx.bizservice.model.bo.UpdateResponseBo;
import com.amx.bizservice.model.dto.JsonResponseDto;
import com.amx.bizservice.model.dto.PageResponseDto;
import com.amx.bizservice.model.qo.OrderQuery;
import com.amx.bizservice.model.vo.ContactVo;
import com.amx.bizservice.model.vo.PayUrlVo;
import com.amx.bizservice.model.vo.web.OrderListVo;
import com.amx.bizservice.model.vo.web.OrderVo;
import com.amx.bizservice.model.vo.web.ServiceFeeResponseVo;
import com.amx.bizservice.model.vo.web.VoucherVo;
import com.amx.bizservice.service.CurrencyService;
import com.amx.bizservice.service.LanguageService;
import com.amx.bizservice.service.OrderService;
import com.amx.bizservice.service.PackageService;
import com.amx.bizservice.service.ProductService;
import com.amx.bizservice.util.JsonUtil;
import com.amx.bizservice.util.SessionUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@SuppressWarnings("unchecked")
@Authentication
@Controller
public class OrderController extends BaseController{
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private ProductService productService;
	@Autowired
	private PackageService packageService;
	@Autowired
	private LanguageService languageService;
	@Autowired
	private CurrencyService currencyService;
	
	
	/**
	 * 获取订单列表
	 * @return
	 */
//	@ResponseBody
	@RequestMapping(value = "/orderList/{pageIndex}/{orderStatusId}", method = RequestMethod.GET)
	public Map<String, Object> getOrderList(@PathVariable("pageIndex") int pageIndex, @PathVariable("orderStatusId") int orderStatusId, HttpServletRequest request){
		Object response = ResponseTypeEnum.PARAMETER_ERROR; 
		OrderListVo vo = null;
		int pageSize = CommonConfig.WEB_PAGE_SIZE;
		
		HttpSession session = request.getSession(false);
		Long userId =  (Long) session.getAttribute(CommonConstants.SESSION_USER_ID);
		
		OrderQuery query = new OrderQuery(userId,orderStatusId,pageIndex, pageSize );
		PageResponseDto<OrderBo> page = orderService.getOrderList(query );
		if(page != null){
			response = ResponseTypeEnum.getNewOkResponse();
			//将Bo转为VO
			vo =  new OrderListVo(page);
			if(page.getContent() != null){
				List<OrderVo> orderList = new ArrayList<OrderVo>();
				for (OrderBo bo : page.getContent() ) {
					ProductBo prodbo = productService.getProductSnap(bo.getProductId(), userId);
					PackageBo packbo = packageService.findOne(bo.getPackageId());
					LanguageBo langbo = languageService.findOne(bo.getLanguageId());
					CurrencyBo currbo = currencyService.findOne(bo.getCurrencyId());
					
					orderList.add(new OrderVo(bo, prodbo, packbo, langbo, currbo));
				}
				vo.setOrderList(orderList);
			}
			
			//设置响应数据
			((Response)response).setData(vo);
		}
		
		return JsonResponseDto.getResult(response);
	}
	
	/**
	 * 获取订单明细
	 * @return
	 */
//	@ResponseBody
	@RequestMapping(value = "/order/{id}", method = RequestMethod.GET)
	public Map<String, Object> getOrder(@PathVariable("id") long ordreId, HttpServletRequest request){
		Object response = ResponseTypeEnum.PARAMETER_ERROR; 
		OrderVo vo = null;
		
		HttpSession session = request.getSession(false);
		Long userId = (Long) session.getAttribute(CommonConstants.SESSION_USER_ID);
		
		OrderBo bo = orderService.getOrder(userId, ordreId);
		if(bo != null){
			response = ResponseTypeEnum.getNewOkResponse();
			//将Bo转为VO
			ProductBo prodbo = productService.getProductSnap(bo.getProductId(),userId);
			PackageBo packbo = packageService.findOne(bo.getPackageId());
			LanguageBo langbo = languageService.findOne(bo.getLanguageId());
			CurrencyBo currbo = currencyService.findOne(bo.getCurrencyId());
				
			vo = new OrderVo(bo, prodbo, packbo, langbo, currbo);
			
			//设置响应数据
			((Response)response).setData(vo);
		}
		
		return JsonResponseDto.getResult(response);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/order", method = RequestMethod.POST)
	public Map<String, Object> insertOrder(@RequestBody MultiValueMap<String, String> map ,HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException{// 使用Bean接收参数
		Object response = ResponseTypeEnum.PARAMETER_ERROR;
		//由于SpringMvc无法直接接收接收数组，故在前端被处理为json
		com.amx.bizservice.model.vo.web.input.OrderVo inVo = JsonUtil.customMapper.readValue(map.getFirst("json"), com.amx.bizservice.model.vo.web.input.OrderVo.class);
		
		HttpSession session = request.getSession(false);
		Long userId =  (Long) session.getAttribute(CommonConstants.SESSION_USER_ID);

		/*封装参数*/
		OrderBo bo = new OrderBo();
		bo.setUserId(userId);
		
		bo.setProductId(inVo.getProductId());
		bo.setPackageId(inVo.getPackageId());
		bo.setProductTypeId(inVo.getProductTypeId());
		bo.setLanguageId(inVo.getLanguageId());
		bo.setAdultNum(inVo.getAdultNum());
		bo.setChildNum(inVo.getChildNum());
		bo.setGoOffDate(inVo.getGoOffDate());
		bo.setGoOffTime(inVo.getGoOffTime());
		
		bo.setGatherWay(inVo.getGatherWay());
		bo.setHotelAddr(inVo.getHotelAddr());
		bo.setHotelName(inVo.getHotelName());
		
		bo.setUdid(inVo.getUdid());
		bo.setRemark(inVo.getRemark());
		
		/* 出行人 */
		List<TravellerBo> travellerList = new ArrayList<TravellerBo>();
		for (ContactVo cvo : inVo.getTravellers()) {
			TravellerBo tbo = new TravellerBo();
			tbo.setEmail(cvo.getEmail());
			tbo.setFirstName(cvo.getFirstName());
			tbo.setGender(GenderTypeEnum.UNKONW.getId());
			tbo.setIsPrincipal(cvo.getIsContact() == CommonTypeEnum.TRUE.getId());
			tbo.setLastName(cvo.getLastName());
			tbo.setPassport(cvo.getPassport());
			tbo.setPhone(cvo.getPhone());
			
			travellerList.add(tbo);
		}
		bo.setTravellerList(travellerList);
		
		UpdateResponseBo result = orderService.insertOrder(bo);
		
		if(result != null){
			response = ResponseTypeEnum.getNewOkResponse();
			//设置响应数据
			((Response)response).setData(result);
		}
		
		return JsonResponseDto.getResult(response);
	}
	
	@ResponseBody
	@RequestMapping(value = "/order/{id}", method = RequestMethod.PUT)
	public Map<String, Object> cancelOrder(@PathVariable("id") long id, HttpServletRequest request){
		Object response = ResponseTypeEnum.PARAMETER_ERROR;

		HttpSession session = request.getSession(false);
		Long userId =  (Long) session.getAttribute(CommonConstants.SESSION_USER_ID);
		
		UpdateResponseBo result = orderService.cancelOrder(userId,id);
		if(result != null){
			response = ResponseTypeEnum.getNewOkResponse();
			//设置响应数据
			((Response)response).setData(result);
		}
		
		return JsonResponseDto.getResult(response);
	}
	
	@ResponseBody
	@RequestMapping(value = "/order/{id}", method = RequestMethod.DELETE)
	public Map<String, Object> deleteOrder(@PathVariable("id") long id, HttpServletRequest request){
		Object response = ResponseTypeEnum.PARAMETER_ERROR;
		
		HttpSession session = request.getSession(false);
		Long userId = (Long) session.getAttribute(CommonConstants.SESSION_USER_ID);
		
		UpdateResponseBo result = orderService.deleteOrder(userId,id);
		if(result != null){
			response = ResponseTypeEnum.getNewOkResponse();
			//设置响应数据
			((Response)response).setData(result);
		}
		
		return JsonResponseDto.getResult(response);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/serviceFee/{orderId}", method = RequestMethod.GET)
	public Map<String, Object> getServiceFee(@PathVariable("orderId") long id, HttpServletRequest request){
		Object response = ResponseTypeEnum.PARAMETER_ERROR;

		HttpSession session = request.getSession(false);
		Long userId = (Long) session.getAttribute(CommonConstants.SESSION_USER_ID);
		
		ServiceFeeResponseVo result = new ServiceFeeResponseVo();
		
		Double serviceFee = orderService.getServiceFee(userId,id);
		response = ResponseTypeEnum.getNewOkResponse();
		if(serviceFee != null){
			result.setIsSucceeded(true);
			result.setServiceFee(serviceFee);
		}else{
			result.setIsSucceeded(false);
			result.setMsg("拉取服务费失败，请稍后重试");
		}
		//设置响应数据
		((Response)response).setData(serviceFee);
		
		return JsonResponseDto.getResult(response);
	}
	
	@ResponseBody
	@RequestMapping(value = "/voucher", method = RequestMethod.POST)
	public Map<String, Object> payRequest(HttpServletRequest request,HttpServletResponse servletResponse){
		Object response = ResponseTypeEnum.getNewOkResponse();
		UpdateResponseBo result = new UpdateResponseBo();
		
		List<Long> orderIds = null;
		String[] orderIdsStr = request.getParameterValues("orderIdList[]");
		if(orderIdsStr != null){
			orderIds = new ArrayList<Long>();
			for (String orderIdStr : orderIdsStr) {
				orderIds.add(Long.parseLong(orderIdStr));
			}
		}
		if(orderIds != null){
			//放入SESSION
			SessionUtil.getSession(request, servletResponse).setAttribute(CommonConstants.SESSION_PAY_REQUEST_ORDER_IDS, orderIds);
			result.setIsSucceeded(true);
		}else{
			result.setIsSucceeded(false);
		}
		((Response)response).setData(result);
		
		return JsonResponseDto.getResult(response);
	}
	
	@ResponseBody
	@RequestMapping(value = "/voucher", method = RequestMethod.GET)
	public Map<String, Object> getVoucher(HttpServletRequest request){
		Object response = ResponseTypeEnum.PARAMETER_ERROR;
		
		VoucherVo vo = new VoucherVo();
		
		Long userId = null;
		HttpSession session = request.getSession(false);
		if(session != null){
			userId = (Long) session.getAttribute(CommonConstants.SESSION_USER_ID);
		}
		
		//从SESSION中取出请求付款的订单ID
		List<Long> orderIds = (List<Long>) session.getAttribute(CommonConstants.SESSION_PAY_REQUEST_ORDER_IDS);
		if(orderIds != null){
			response = ResponseTypeEnum.getNewOkResponse();
			
			List<OrderBo> orderPage = orderService.getOrderList(orderIds);
			double totalPrice = 0;
			for (OrderBo obo : orderPage) {
				if(vo.getOrderList() == null){
					vo.setOrderList(new ArrayList<OrderVo>());
				}
				if(orderService.isOrderExpire(obo)){
					if(vo.getInvalidOrderList() == null){
						vo.setInvalidOrderList(new ArrayList<VoucherVo.InvalidOrderVo>());
					}
					vo.getInvalidOrderList().add(vo.new InvalidOrderVo(obo.getId(), obo.getNo(), "无效订单"));
				}
				//将Bo转为VO
				ProductBo prodbo = productService.getProductSnap(obo.getProductId(), userId);
				PackageBo packbo = packageService.findOne(obo.getPackageId());
				LanguageBo langbo = languageService.findOne(obo.getLanguageId());
				CurrencyBo currbo = currencyService.findOne(obo.getCurrencyId());
				
				vo.getOrderList().add(new OrderVo(obo, prodbo, packbo, langbo, currbo));
				
				totalPrice += obo.getPrice().doubleValue();
			}
			vo.setAmount(orderPage.size());
			vo.setTotalPrice(totalPrice);
			if(vo.getOrderList() != null && vo.getInvalidOrderList() == null){
				vo.setCanPay(true);
			}else{
				vo.setCanPay(false);
			}
			
			((Response)response).setData(vo);
		}
		
		return JsonResponseDto.getResult(response);
	}
	
	/**
	 * 获取支付链接
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/pay", method = RequestMethod.POST)
	public Map<String, Object> getPayUrl(HttpServletRequest request){
		Object response = ResponseTypeEnum.getNewOkResponse();
		PayUrlVo vo = new PayUrlVo();
		
		Long userId = null;
		HttpSession session = request.getSession(false);
		if(session != null){
			userId = (Long) session.getAttribute(CommonConstants.SESSION_USER_ID);
		}
		List<Long> orderIds = (List<Long>) session.getAttribute(CommonConstants.SESSION_PAY_REQUEST_ORDER_IDS);
		List<OrderBo> orderBoList = orderService.getOrders(userId, orderIds);
		String payUrl = orderService.getPayUrl(orderBoList);
		if(payUrl != null){
			vo.setIsSucceeded(true);
			vo.setPayUrl(payUrl);
		}else{
			vo.setIsSucceeded(false);
			vo.setMsg("获取支付链接失败");
		}
		((Response)response).setData(vo);
		
		return JsonResponseDto.getResult(response);
	}
	
	/**
	 * 获取支付状态
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/pay", method = RequestMethod.GET)
	public Map<String, Object> getPayState(HttpServletRequest request){
		Object response = ResponseTypeEnum.getNewOkResponse();
		UpdateResponseBo bo = new UpdateResponseBo();
		
		HttpSession session = request.getSession(false);
		Long userId = (Long) session.getAttribute(CommonConstants.SESSION_USER_ID);
		List<Long> orderIds = (List<Long>) session.getAttribute(CommonConstants.SESSION_PAY_REQUEST_ORDER_IDS);
		List<OrderBo> orderBoList = orderService.getOrders(userId, orderIds);
		bo.setIsSucceeded(orderService.getPayState(orderBoList));
		//清空SESSION
		session.removeAttribute(CommonConstants.SESSION_PAY_REQUEST_ORDER_IDS);
		
		((Response)response).setData(bo);

		return JsonResponseDto.getResult(response);
	}
	
}
