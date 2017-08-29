package com.amx.bizservice.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amx.bizservice.config.CommonConfig;
import com.amx.bizservice.enums.OrderStateEnum;
import com.amx.bizservice.enums.PayWayEnum;
import com.amx.bizservice.enums.UpdateCodeEnum;
import com.amx.bizservice.model.bo.OrderBo;
import com.amx.bizservice.model.bo.PriceBo;
import com.amx.bizservice.model.bo.ProductBo;
import com.amx.bizservice.model.bo.UpdateResponseBo;
import com.amx.bizservice.model.dto.PageResponseDto;
import com.amx.bizservice.model.dto.UpdateResponseDto;
import com.amx.bizservice.model.qo.OrderQuery;
import com.amx.bizservice.service.OrderService;
import com.amx.bizservice.service.PackageService;
import com.amx.bizservice.service.PayService;
import com.amx.bizservice.service.ProductService;
import com.amx.bizservice.thread.NewOrderNoticeThread;
import com.amx.bizservice.thrift.Response;
import com.amx.bizservice.util.JsonUtil;
import com.amx.bizservice.util.LogUtil;
import com.amx.bizservice.util.StringUtil;
import com.fasterxml.jackson.core.type.TypeReference;

@Service("orderService")
public class OrderServiceImpl extends BaseService implements OrderService{

	public OrderServiceImpl(){
		this.ServiceName = "Order";		
	}
	
	@Autowired
	private PayService payService;
	@Autowired
	private ProductService productService;
	@Autowired
	private PackageService packageService;
	
	@Override
	public long getProductIdByOrderId(long orderId) {
		Response response = thriftClient.serviceInvoke(this.ServiceName, "getProductIdById", orderId);
		Long productId = null;
		try {
			if (response != null) {
				if (response.isState()) {
					productId = JsonUtil.mapper.readValue(response.getData(), Long.class);
				} else {
					LogUtil.recordWarnLog(response.getMsg());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return productId;
	}

	@Override
	public PageResponseDto<OrderBo> getOrderList(OrderQuery query) {
		Response response = thriftClient.serviceInvoke(this.ServiceName, "findAllByQuery", query);
		PageResponseDto<OrderBo> page = null;
		try {
			if (response != null) {
				if (response.isState()) {
					//XXX - 获取泛型的类类型 ��? TypeReference
					page = JsonUtil.mapper.readValue(response.getData(),new TypeReference<PageResponseDto<OrderBo>>() {});
					if(page != null && page.getContent() != null){
						for (OrderBo bo : page.getContent()) {
							bo.setCancelMsg(this.getCancelMsg(bo));
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
	public List<OrderBo> getOrderList(List<Long> orderIdList) {
		Response response = thriftClient.serviceInvoke(this.ServiceName, "findAllByIdList", orderIdList);
		List<OrderBo> page = null;
		try {
			if (response != null) {
				if (response.isState()) {
					//XXX - 获取泛型的类类型 ��? TypeReference
					page = JsonUtil.mapper.readValue(response.getData(),JsonUtil.getCollectionType(ArrayList.class, OrderBo.class));
						/*for (OrderBo bo : page.getContent()) {
							//bo.setCancelMsg(this.getCancelMsg(bo));
						}*/
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
	public OrderBo getOrder(long userId, long ordreId) {
		Response response = thriftClient.serviceInvoke(this.ServiceName, "findOne", ordreId);
		OrderBo bo = null;
		try {
			if (response != null) {
				if (response.isState()) {
					bo = JsonUtil.mapper.readValue(response.getData(),OrderBo.class);
					//校验当前订单是否属于当前用户
					if(!bo.getUserId().equals(userId)){
						bo = null;
					}else{
						bo.setCancelMsg(this.getCancelMsg(bo));
					}
				} else {
					LogUtil.recordWarnLog(response.getMsg());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return bo;
	}

	@Override
	@Transactional
	public UpdateResponseBo insertOrder(OrderBo bo) {
		UpdateResponseBo response = null;
		UpdateResponseDto serverResult = null;
		
		//TODO - bo合法性检��?
		//获取价格信息
		PriceBo price = packageService.getPrice(bo.getProductId(), bo.getPackageId(), bo.getLanguageId(), bo.getAdultNum(), bo.getChildNum(), bo.getUdid());
		if(price.getCanBook()){
			bo = inflationPrice(bo,price);
			
			Response serverResponse = thriftClient.serviceInvoke(this.ServiceName, "save", bo);
			try {
				if (serverResponse != null) {
					if (serverResponse.isState()) {
						serverResult = JsonUtil.mapper.readValue(serverResponse.getData(),UpdateResponseDto.class);
						response = new UpdateResponseBo();
						//执行成功
						if(serverResult.getCode().equals(UpdateCodeEnum.SUCCESS.getCode())){
							response.setIsSucceeded(true);
							//解析ID
							bo.setId( Long.parseLong(serverResult.getMsg()));
							//通知
							this.sendNewOrderNotice(bo);
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
		}else{
			response = new UpdateResponseBo();
			response.setIsSucceeded(false);
			response.setMsg("订单不合��?:" + price.getMsg());
		}
		
		return response;
	}

	@Override
	public UpdateResponseBo cancelOrder(long userId, long orderId) {
		UpdateResponseBo reaponse = new UpdateResponseBo();
		reaponse.setIsSucceeded(false);
		
		OrderBo ordreBo = this.getOrder(userId, orderId);
		if(ordreBo != null){//查不到订单则说明是非法请��?
			//��?查订单状��?
			if (ordreBo.getOrderStateId() <= OrderStateEnum.WAIT_USE.getId()){
				OrderBo bo = new OrderBo();
				bo.setId(orderId);
				//计算服务��?
				bo.setServiceFee(this.getServiceFee(ordreBo));
				//更新订单状�??
				bo.setOrderStateId( OrderStateEnum.CANCELED.getId());
				/*调用数据服务*/
				Response response = thriftClient.serviceInvoke(this.ServiceName, "update", bo);
				UpdateResponseDto serverResult = null;
				try {
					if (response != null) {
						if (response.isState()) {
							serverResult = JsonUtil.mapper.readValue(response.getData(),UpdateResponseDto.class);
							//执行成功
							if(serverResult.getCode().equals(UpdateCodeEnum.SUCCESS.getCode())){
								reaponse.setIsSucceeded(true);
							}else {//执行失败
								reaponse.setIsSucceeded(false);
								/*失败原因分析*/
								reaponse.setMsg("系统繁忙，请稍后再试");
							}
						} else {
							reaponse.setMsg(response.getMsg());
						}
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}else{
				reaponse.setMsg("当前订单不能取消");
			}
		}else{
			reaponse.setMsg("非法操作");
		}
		
		return reaponse;
	}

	@Override
	public UpdateResponseBo deleteOrder(long userId, long orderId) {
		UpdateResponseBo reaponse = null;
		
		OrderBo bo = this.getOrder(userId, orderId);
		if(bo != null){//查不到订单则说明是非法请��?
			Response response = thriftClient.serviceInvoke(this.ServiceName, "delete", orderId);
			UpdateResponseDto serverResult = null;
			try {
				if (response != null) {
					if (response.isState()) {
						serverResult = JsonUtil.mapper.readValue(response.getData(),UpdateResponseDto.class);
						reaponse = new UpdateResponseBo();
						//执行成功
						if(serverResult.getCode().equals(UpdateCodeEnum.SUCCESS.getCode())){
							reaponse.setIsSucceeded(true);
						}else {//执行失败
							reaponse.setIsSucceeded(false);
							/*失败原因分析*/
							reaponse.setMsg("系统繁忙，请稍后再试");
						}
					} else {
						LogUtil.recordWarnLog(response.getMsg());
					}
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}else{
			reaponse = new UpdateResponseBo();
			reaponse.setIsSucceeded(false);
			/*失败原因分析*/
			reaponse.setMsg("非法操作");
		}
		
		return reaponse;
	}
	
	@Override
	public Double getServiceFee(long userId, long id) {
		OrderBo bo = this.getOrder(userId, id);
		
		return this.getServiceFee(bo ).doubleValue();
	}
	
	@Override
	public Boolean isOrderExpire(OrderBo obo) {
		Boolean isOrderExpire = true;
		
		PriceBo price = packageService.getPrice(obo.getProductId(), obo.getPackageId(), obo.getLanguageId(), obo.getAdultNum(), obo.getChildNum(), obo.getUdid());
		if(price.getCanBook()){
			//比较订单总价是否发生变化
			if(price.getPrice().equals(obo.getPrice())){
				isOrderExpire = false;
			}
		}
		
		return isOrderExpire;
	}
	
	@Override
	public Boolean getPayState(OrderBo bo) {
		return bo.getRealPayment().equals(bo.getPrePayPrice()) && bo.getPayWay() != null && bo.getPayTime() != null;
	}
	
	@Override
	public UpdateResponseBo insertOrders(ArrayList<OrderBo> inBoList) {
		UpdateResponseBo response = null;
		UpdateResponseDto serverResult = null;
		
		for (OrderBo orderBo : inBoList) {
			//TODO - bo合法性检��?
			
			//获取价格信息
			PriceBo price = packageService.getPrice(orderBo.getProductId(), orderBo.getPackageId(), orderBo.getLanguageId(), orderBo.getAdultNum(), orderBo.getChildNum(), orderBo.getUdid());
			if(price.getCanBook()){
				inflationPrice(orderBo, price);
			}
		}
			
		Response serverResponse = thriftClient.serviceInvoke(this.ServiceName, "saveInBatch", inBoList);
		try {
			if (serverResponse != null) {
				if (serverResponse.isState()) {
					serverResult = JsonUtil.mapper.readValue(serverResponse.getData(),UpdateResponseDto.class);
					response = new UpdateResponseBo();
					//执行成功
					if(serverResult.getCode().equals(UpdateCodeEnum.SUCCESS.getCode())){
						response.setIsSucceeded(true);
						String[] ids = serverResult.getMsg().split(",");
						int i = 0;
						for(OrderBo ordreBo : inBoList){
							ordreBo.setId(Long.parseLong(ids[i++]));
							this.sendNewOrderNotice(ordreBo);
						}
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

	@Override
	public UpdateResponseBo cancelOrders(Long userId, List<Long> orderIdList) {
		UpdateResponseBo reaponse = new UpdateResponseBo();

		boolean isSuccess = true;
		for (Long orderId : orderIdList) {
			UpdateResponseBo cancelOrder = this.cancelOrder(userId, orderId);
			if(cancelOrder != null){
				isSuccess = cancelOrder.getIsSucceeded() && isSuccess;
			}else{
				isSuccess = false;
			}
		}
		reaponse.setIsSucceeded(isSuccess);
		
		return reaponse;
	}

	@Override
	public UpdateResponseBo deleteOrders(Long userId, List<Long> orderIdList) {
		UpdateResponseBo reaponse = null;
	
		Response response = thriftClient.serviceInvoke(this.ServiceName, "deleteInBatch", orderIdList);
		UpdateResponseDto serverResult = null;
		try {
			if (response != null) {
				if (response.isState()) {
					serverResult = JsonUtil.mapper.readValue(response.getData(),UpdateResponseDto.class);
					reaponse = new UpdateResponseBo();
					//执行成功
					if(serverResult.getCode().equals(UpdateCodeEnum.SUCCESS.getCode())){
						reaponse.setIsSucceeded(true);
					}else {//执行失败
						reaponse.setIsSucceeded(false);
						/*失败原因分析*/
						reaponse.setMsg("系统繁忙，请稍后再试");
					}
				} else {
					LogUtil.recordWarnLog(response.getMsg());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	
		return reaponse;
	}
	

	@Override
	public List<OrderBo> getOrdersByTradeNo(Long userId,String tradeNo) {
		return this.getOrdersByTradeNo(tradeNo);
	}

	@Override
	public List<OrderBo> getOrdersByTradeNo(String tradeNo) {
		Response response = thriftClient.serviceInvoke(this.ServiceName, "findAllByTradeNo", tradeNo);
		List<OrderBo> boList = null;
		try {
			if (response != null) {
				if (response.isState()) {
					boList = JsonUtil.mapper.readValue(response.getData(),JsonUtil.getCollectionType(ArrayList.class, OrderBo.class));
				} else {
					LogUtil.recordWarnLog(response.getMsg());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return boList;
	}
	
	@Override
	public void doneOrder(Long userId, Long orderId) {
		OrderBo orderBo = this.getOrder(userId, orderId);
		if(orderBo != null){//查不到订单则说明是非法请��?
			OrderBo bo = new OrderBo();
			bo.setId(orderId);	
			bo.setOrderStateId(OrderStateEnum.OK.getId());
			bo.setUserId(userId);
			
			thriftClient.serviceInvoke(this.ServiceName, "update", bo);
		}
	}
	
	@Override
	public List<OrderBo> getOrders(Long userId, List<Long> orderIds) {
		List<OrderBo> orderPage = this.getOrderList(orderIds);
		if(orderPage != null ){
			Iterator<OrderBo> iterator = orderPage.iterator();
			while (iterator.hasNext()) {
				OrderBo orderBo = iterator.next();
				//移除不是当前用户的订��?
				if(!userId.equals(orderBo.getUserId())){
					iterator.remove();
				}
			}
			
			return orderPage;
		}
		return null;
	}

	@Override
	public Boolean getPayState(List<OrderBo> orderBoList) {
		Boolean state = null;
		if(orderBoList == null){
			state = false;
		}else{
			for (OrderBo orderBo : orderBoList) {
				if(state == null){
					state = this.getPayState(orderBo);
				}else{
					state = state && this.getPayState(orderBo);
				}
			}
		}
		
		return state;
	}

	@Override
	public String getPayUrl(List<OrderBo> orderBoList) {
		return this.getPayUrl(orderBoList,false);
	}
	
	@Override
	public String getPayUrl(List<OrderBo> orderBoList ,boolean isWap) {
		//将多个订单信息整合位��?个订��?
		OrderBo richOrder = null;
		String tradeNo = this.generateOrderTradeNo(orderBoList);
		if(orderBoList != null && orderBoList.size() != 0){
			//取第��?个的主要信息
			richOrder = orderBoList.get(0);
			ProductBo productSnap = productService.getProductSnap(richOrder.getProductId());
			String itemName = productSnap != null ? productSnap.getName() : "此商品由爱漫行提��?";  
			richOrder.setProductName(itemName);
			richOrder.setPackageName("��?" + orderBoList.size() + "件商��?");
			richOrder.setPrePayPrice(this.getTotalPrice(orderBoList));
			richOrder.setTradeNo(tradeNo);
		}
		
		//记录订单交易��?
		if(this.setTradeNo(orderBoList, tradeNo)){
			if(isWap){
				return payService.getWapPayUrl(richOrder);
			}
			return payService.getPagePayUrl(richOrder);
		}
		
		return null;
	}
	

	@Override
	public boolean payDone(String trade_no, boolean payStatus, String out_trade_no, double total_amount) {
		if(payStatus){
			//查出同批交易的订��?
			List<OrderBo> orderBoList = this.getOrdersByTradeNo(out_trade_no);
			if(orderBoList == null){
				return false;
			}
			Timestamp payTime = new Timestamp(new Date().getTime());
			for (OrderBo orderBo : orderBoList) {
				if(total_amount <= 0){
					break;
				}
				boolean isPayFull = true;
				//实际需付  = 预付-折扣
				BigDecimal needPay = orderBo.getPrePayPrice().subtract(orderBo.getDiscount());
				//校验交易金额
				if(total_amount >= (needPay.doubleValue() - CommonConfig.TOLERANCE_PRICE)){
					//支付金额=实际��?付金��?
					orderBo.setRealPayment(needPay);
				}else{
					//支付金额<实际��?付金��?
					orderBo.setRealPayment(new BigDecimal(total_amount));
					isPayFull = false;
				}
				//更订单支付信��?
				orderBo.setPayTime(payTime );
				orderBo.setPayWay(PayWayEnum.ALIPAY.getId());
				orderBo.setTradeNo(out_trade_no);
				//扣除当前订单的支付价��?
				total_amount -=  orderBo.getPrePayPrice().doubleValue();
				
				//发�?�支付完成�?�知
				this.sendPayNotice(orderBo, isPayFull);
			}
			
			return total_amount >= (0 - CommonConfig.TOLERANCE_PRICE) ? true : false;
		}
		
		return false;
	}

	
	/**
	 * 更新待付款订单的临时交易��?
	 * @param orderBoList
	 * @param tradeNo
	 * @return
	 */
	private boolean setTradeNo(List<OrderBo> orderBoList,String tradeNo){
		List<OrderBo> updateOrderBoList = new ArrayList<OrderBo>(orderBoList.size());
		for (OrderBo orderBo : orderBoList) {
			OrderBo updateBo = new OrderBo();
			updateBo.setId(orderBo.getId());
			updateBo.setTradeNo(tradeNo);
			
			updateOrderBoList.add(updateBo);
		}
		
		UpdateResponseBo responseDto = this.updateOrdersInBatch(updateOrderBoList);
		
		if(responseDto != null){
			return responseDto.getIsSucceeded();
		}
		
		return false;
	}
	
	private UpdateResponseBo updateOrdersInBatch(List<OrderBo> orderBoList){
		UpdateResponseBo response = new UpdateResponseBo();
		Response serverResponse = thriftClient.serviceInvoke(this.ServiceName, "updateInBatch", orderBoList);
		try {
			if (serverResponse != null) {
				if (serverResponse.isState()) {
					UpdateResponseDto serverResult = JsonUtil.mapper.readValue(serverResponse.getData(),UpdateResponseDto.class);
					//执行成功
					if(serverResult.getCode().equals(UpdateCodeEnum.SUCCESS.getCode())){
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
	
	/**
	 * 生成订单��?
	 * @param bo
	 * @return 年（2位）月（2位）日（2位）时（2位）分（2位）秒（2位）  产品ID��?1位） 套餐ID��?1位） 语言ID��?1位） 用户ID��?1位）
	 */
	private String generateOrderNo(OrderBo bo) {
		StringBuffer noSb = new StringBuffer();
		noSb.append(StringUtil.getYMDHMSDate(new Date()));
		noSb.append(bo.getProductId() % 10);
		noSb.append(bo.getPackageId() % 10);
		noSb.append(bo.getLanguageId() % 10);
		noSb.append(bo.getUserId() % 10);
		
		return noSb.substring(2).toString();
	}
	
	/**
	 * 生成交易��?(16��?)，用于支付宝异步通知时识别交易包含的订单
	 * //时分秒之和（6位） 订单号后4位之和取后四个数字（4位）订单号ID之和取后四位��?4位）用户id取后两位��?2位）
	 * //单一订单的交易号同订单号
	 * @param orderList
	 * @return
	 */
	private String generateOrderTradeNo(List<OrderBo> orderList){
		if(orderList.size() == 1){
			//return orderList.get(0).getNo();
		}
		
		long timeSum = 0;
		int suffixSum = 0;
		long idSum = 0;
		for (OrderBo orderBo : orderList) {
			timeSum = Integer.parseInt(orderBo.getNo().substring(6,12));
			suffixSum += Integer.parseInt(orderBo.getNo().substring(orderBo.getNo().length() - 4));
			idSum += orderBo.getId();
		}
		
		return "" + timeSum + suffixSum % 10000 +  String.format("%04d",idSum % 10000) +  String.format("%02d",orderList.get(0).getUserId() % 100);
	}
	
	private OrderBo inflationPrice(OrderBo bo, PriceBo price) {
		bo.setNo(this.generateOrderNo(bo));
		
		/* 价格 */
		bo.setDiscount(price.getDiscount());
		bo.setChildPrice(price.getChildPrice());
		bo.setAdultPrice(price.getAdultPrice());
		bo.setObligation(price.getObligation());
		bo.setPrePayPrice(price.getPrePayPrice());
		bo.setPayType(price.getPayType());
		bo.setPrice(price.getPrice());
		
		ProductBo productDetail = productService .getProductDetail(bo.getProductId());
		bo.setCurrencyId(productDetail.getCurrencyId());

		BigDecimal zero = new BigDecimal(0);
		bo.setRealPayment(zero);
		bo.setServiceFee(zero);
		bo.setOrderStateId(OrderStateEnum.WAIT_COMFIRMATION.getId());
		bo.setOrderTime(new Timestamp(new Date().getTime()));
		
		return bo;
	}

	
	/**
	 * 生成取消订单的提示消��?
	 * @param bo
	 * @return
	 */
	private String getCancelMsg(OrderBo bo) {
		// TODO Auto-generated method stub
		//bo.getOrderTime().getDate()
		return "我们不收取您服务��?";
	}
	
	/**
	 * 获取取消订单��?收取的服务费
	 * @param bo
	 * @return
	 */
	private BigDecimal getServiceFee(OrderBo bo) {
		// TODO Auto-generated method stub
		return new BigDecimal(0);
	}
	
	/**
	 * 获取批量订单的�?�价
	 * @param orderBoList
	 * @return
	 */
	private BigDecimal getTotalPrice(List<OrderBo> orderBoList){
		BigDecimal totalPrice = orderBoList.get(0).getPrePayPrice();
		for (int i = 1;i< orderBoList.size();i++) {
			totalPrice.add(orderBoList.get(i).getPrePayPrice());
		}
		return totalPrice;
	}
	
	/**
	 * 发�?�新订单通知 
	 * @param bo
	 */
	private void sendNewOrderNotice(OrderBo bo) {
		NewOrderNoticeThread.getInstance().sendNewOrderNotice(bo);
	}
	
	/**
	 * 通知后台管理程序更新订单支付状�?�信��?
	 * @param bo
	 * @param isPayFull 付款是否与实际付款相��?
	 */
	@SuppressWarnings("deprecation")
	private void sendPayNotice(OrderBo bo,boolean isPayFull){
		try {
			HttpClient client = new DefaultHttpClient();
			String url = CommonConfig.ALIPAY_DONE_NOTICE_URL.replaceAll("{orderId}", bo.getId().toString());
			if(!isPayFull){
				url += "&ispayfull=0";
			}
			HttpPost method = new HttpPost(url);
			
			List<NameValuePair> postParams = new ArrayList<NameValuePair>();
			postParams.add(new BasicNameValuePair("fee",bo.getRealPayment().toPlainString())); 
			postParams.add(new BasicNameValuePair("payWay",bo.getPayWay().toString())); 
			postParams.add(new BasicNameValuePair("payTime",new Date(bo.getPayTime().getTime()).toLocaleString())); 
			postParams.add(new BasicNameValuePair("tradeNo",bo.getTradeNo())); 
			postParams.add(new BasicNameValuePair("detail",null)); 
			
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postParams,"utf-8"); 
			entity.setContentEncoding("UTF-8");
			entity.setContentType("application/x-www-form-urlencoded");
			method.setEntity(entity);
			
			client.execute(method);
		} catch (Exception e) {
			//TODO - 通知后台失败
			throw new RuntimeException(e);
		}
	}
}

