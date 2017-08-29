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
import com.amx.bizservice.model.vo.PayUrlVo;
import com.amx.bizservice.model.vo.wap.InsertOrderResponseVo;
import com.amx.bizservice.model.vo.wap.OrderListVo;
import com.amx.bizservice.model.vo.wap.OrderVo;
import com.amx.bizservice.model.vo.wap.input.OrderUpdateVo;
import com.amx.bizservice.service.CurrencyService;
import com.amx.bizservice.service.LanguageService;
import com.amx.bizservice.service.OrderService;
import com.amx.bizservice.service.PackageService;
import com.amx.bizservice.service.ProductService;
import com.amx.bizservice.util.StringUtil;

@Authentication
@Controller("mOrderController")
@RequestMapping(value = "/orders")
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
	@RequestMapping(method = RequestMethod.GET)
	public Map<String, Object> getOrderList(@RequestParam(name = "pageindex",defaultValue = "0") int pageIndex,@RequestParam(name = "pagesize",defaultValue = "" + CommonConfig.WAP_PAGE_SIZE) int pageSize, @RequestParam(name = "status",defaultValue = "0") int orderStatusId, HttpServletRequest request){
		Object  response  = ResponseTypeEnum.PARAMETER_ERROR; 
		if(pageSize <= 0){
			pageSize = CommonConfig.WAP_PAGE_SIZE;
		}
		OrderListVo vo = null;
		
		Long userId =  (Long) request.getAttribute(CommonConstants.SESSION_USER_ID);
		
		OrderQuery query = new OrderQuery(userId,orderStatusId,pageIndex, pageSize );
		PageResponseDto<OrderBo> page = orderService.getOrderList(query );
		if(page != null){
			response = ResponseTypeEnum.getNewOkResponse();
			//将Bo转为VO
			vo =  new OrderListVo(page);
			if(page.getContent() != null){
				List<OrderListVo.OrderVo> orderList = new ArrayList<OrderListVo.OrderVo>();
				for (OrderBo bo : page.getContent() ) {
					ProductBo prodbo = productService.getProductSnap(bo.getProductId(), userId);
					PackageBo packbo = packageService.findOne(bo.getPackageId());
					LanguageBo langbo = languageService.findOne(bo.getLanguageId());
					CurrencyBo currbo = currencyService.findOne(bo.getCurrencyId());
					
					orderList.add(vo.new OrderVo(bo, prodbo, packbo, langbo, currbo));
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
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Map<String, Object> getOrder(@PathVariable("id") long ordreId, HttpServletRequest request){
		Object  response  = ResponseTypeEnum.PARAMETER_ERROR; 
		OrderVo vo = null;
		
		Long userId = (Long) request.getAttribute(CommonConstants.SESSION_USER_ID);
		
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
	@RequestMapping( method = RequestMethod.POST)
	public Map<String, Object> insertOrder(@RequestBody com.amx.bizservice.model.vo.wap.input.OrderVo inVo, HttpServletRequest request){// 使用Bean接收参数
		Object  response  = ResponseTypeEnum.PARAMETER_ERROR;
		
		Long userId =  (Long) request.getAttribute(CommonConstants.SESSION_USER_ID);

		InsertOrderResponseVo result = new InsertOrderResponseVo();
		ArrayList<OrderBo> inBoList = null;
		if(inVo.getOrderList() != null){
			inBoList = new ArrayList<OrderBo>();
			for (com.amx.bizservice.model.vo.wap.input.TravelInfoVo travelInfo: inVo.getOrderList()) {
				/*封装参数*/
				OrderBo bo = new OrderBo();
				bo.setUserId(userId);
				
				bo.setProductId(travelInfo.getProductId());
				bo.setPackageId(travelInfo.getPackageId());
				bo.setProductTypeId(travelInfo.getProductTypeId());
				bo.setLanguageId(travelInfo.getLanguageId());
				bo.setAdultNum(travelInfo.getAdultNum());
				bo.setChildNum(travelInfo.getChildNum());
				bo.setGoOffDate(travelInfo.getGoOffDate());
				bo.setGoOffTime(StringUtil.getHMTime(travelInfo.getGoOffTime()));
				
				bo.setGatherWay(travelInfo.getGatherWay());
				bo.setHotelAddr(travelInfo.getHotelAddr());
				bo.setHotelName(travelInfo.getHotelName());
				bo.setRemark(travelInfo.getRemark());
				
				bo.setUdid(inVo.getUdid());
				/* 出行人 */
				List<TravellerBo> travellerList = new ArrayList<TravellerBo>();
				TravellerBo tbo = new TravellerBo();
				tbo.setIsPrincipal(true);
				tbo.setEmail(inVo.getEmail());
				tbo.setFirstName(inVo.getFirstName());
				tbo.setGender(GenderTypeEnum.UNKONW.getId());
				tbo.setLastName(inVo.getLastName());
				tbo.setPassport(inVo.getPassport());
				tbo.setPhone(inVo.getPhone());
				
				travellerList.add(tbo);
				bo.setTravellerList(travellerList);
				
				inBoList.add(bo);
			}
		}
		UpdateResponseBo insertOrderResult = orderService.insertOrders(inBoList);
		if(insertOrderResult != null){
			response = ResponseTypeEnum.getNewOkResponse();
			result.setIsSucceeded(insertOrderResult.getIsSucceeded());
			List<Long> orderIdList = new ArrayList<Long>();
			for (OrderBo orderBo : inBoList) {
				orderIdList.add(orderBo.getId());
			}
			result.setOrderId(orderIdList);
		}
		((Response)response).setData(result);
		
		return JsonResponseDto.getResult(response);
	}
	
	@ResponseBody
	@RequestMapping( method = RequestMethod.PUT)
	public Map<String, Object> cancelOrder(@RequestBody OrderUpdateVo inVo, HttpServletRequest request){
		Object  response  = ResponseTypeEnum.PARAMETER_ERROR;

		Long userId =  (Long) request.getAttribute(CommonConstants.SESSION_USER_ID);
		
		UpdateResponseBo result = orderService.cancelOrders(userId,inVo.getOrderIdList());
		if(result != null){
			response = ResponseTypeEnum.getNewOkResponse();
			//设置响应数据
			((Response)response).setData(result);
		}
		
		return JsonResponseDto.getResult(response);
	}
	
	@ResponseBody
	@RequestMapping( method = RequestMethod.DELETE)
	public Map<String, Object> deleteOrder(@RequestBody OrderUpdateVo inVo,HttpServletRequest request){
		Object  response  = ResponseTypeEnum.PARAMETER_ERROR;
		Long userId =  (Long) request.getAttribute(CommonConstants.SESSION_USER_ID);
		
		UpdateResponseBo result = orderService.deleteOrders(userId,inVo.getOrderIdList());
		if(result != null){
			response = ResponseTypeEnum.getNewOkResponse();
			//设置响应数据
			((Response)response).setData(result);
		}
		
		return JsonResponseDto.getResult(response);
	}
	
	@ResponseBody
	@RequestMapping(value = "/payurl", method = RequestMethod.GET)
	public Map<String, Object> getPayUrl(@RequestParam("orderids") String orderIds, HttpServletRequest request){
		Object response = ResponseTypeEnum.getNewOkResponse();
		PayUrlVo vo = new PayUrlVo();
		Long userId = (Long) request.getAttribute(CommonConstants.SESSION_USER_ID);
		
		List<Long> orderIdList = new ArrayList<Long>();
		String[] idArr = orderIds.split(",");
		for (int i = 0; i < idArr.length; i++) {
			orderIdList.add(Long.parseLong(idArr[i]));
		}
		
		List<OrderBo> orderBoList = orderService.getOrders(userId, orderIdList);
		String payUrl = orderService.getPayUrl(orderBoList, true);
		if(payUrl != null){
			vo.setIsSucceeded(true);
			vo.setPayUrl(payUrl);
			//在生成PayUrl时为第一个订单生成了交易号
			OrderBo richOrder = orderBoList.get(0);
			vo.setTradeNo(richOrder.getTradeNo());
		}else{
			vo.setIsSucceeded(false);
			vo.setMsg("获取支付链接失败");
		}
		((Response)response).setData(vo);
		
		return JsonResponseDto.getResult(response);
	}
	
	@ResponseBody
	@RequestMapping(value = "/paystate", method = RequestMethod.GET)
	public Map<String, Object> getPayState(@RequestParam("tradeno") String tradeNo, HttpServletRequest request){
		Object  response  = ResponseTypeEnum.getNewOkResponse();
		UpdateResponseBo bo = new UpdateResponseBo();
		
		Long userId = (Long) request.getAttribute(CommonConstants.SESSION_USER_ID);
		List<OrderBo> orderBoList = orderService.getOrdersByTradeNo(userId, tradeNo);
		boolean payState = false;
		if(orderBoList != null){
			payState = true;
			for (OrderBo ordBo : orderBoList) {
				payState = payState && orderService.getPayState(ordBo);
			}
		}
		bo.setIsSucceeded(payState);
		
		((Response)response).setData(bo);

		return JsonResponseDto.getResult(response);
	}
}
