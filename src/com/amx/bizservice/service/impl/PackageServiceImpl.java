package com.amx.bizservice.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amx.bizservice.enums.PayTypeEnum;
import com.amx.bizservice.model.bo.CurrencyBo;
import com.amx.bizservice.model.bo.PackageBo;
import com.amx.bizservice.model.bo.PriceBo;
import com.amx.bizservice.model.bo.ProductBo;
import com.amx.bizservice.service.CurrencyService;
import com.amx.bizservice.service.PackageService;
import com.amx.bizservice.service.ProductService;
import com.amx.bizservice.thrift.Response;
import com.amx.bizservice.util.JsonUtil;
import com.amx.bizservice.util.LogUtil;
import com.fasterxml.jackson.databind.JavaType;

@Service("packageService")
public class PackageServiceImpl extends BaseService implements PackageService {

	public PackageServiceImpl() {
		this.ServiceName = "Package";
	}

	@Autowired
	private ProductService productService;
	@Autowired
	private CurrencyService currencyService;

	@Override
	public List<PackageBo> findAllByProductId(long id) {
		Response response = thriftClient.serviceInvoke(this.ServiceName,
				"findAllByProductId", id);
		List<PackageBo> boList = null;
		try {
			if (response != null) {
				if (response.isState()) {
					JavaType javaType = JsonUtil.getCollectionType(
							ArrayList.class, PackageBo.class);
					boList = JsonUtil.mapper.readValue(response.getData(),
							javaType);
				} else {
					LogUtil.recordWarnLog(response.getMsg());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return boList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PriceBo getPrice(Long productId, Long packageId, Integer languageId, Integer adultNum, Integer childNum, String udid) {
		if(productId == null || packageId == null || languageId == null || adultNum == null || adultNum == 0){
			return null;
		}
		if(childNum == null){
			childNum = 0;
		}
		
		PackageBo bo = findOne(packageId);
		StringBuffer msg = new StringBuffer();
		PriceBo priceBo = new PriceBo();
		if (bo != null) {
			// 套餐所属产品
			ProductBo productSnap = productService.getProductSnap(productId);
			// 检查套餐是否从属与产品
			if (productSnap.getPackageIdList().contains(Long.valueOf(packageId).intValue())) {
				if (bo.getMaxAdultNum() >= adultNum
						&& bo.getMaxChildNum() >= childNum
						&& bo.getMaxPersonNum() >= (adultNum + childNum)) {
					List<Integer> langList = productSnap.getLanguageIdList();
					if (langList.contains(languageId)) {
						//是否待付产品
						boolean isObligation = false;
						if(productSnap.getPayType() == PayTypeEnum.PAY_PART.getId()){
							isObligation = true;
						}
						/* 计算价格 */
						int index = langList.indexOf(languageId);
						//实际参与计价的成人数
						int valuationAdultNum = adultNum;
						//儿童按成人处理
						boolean isChildAsAdult = false;
						
						/* 儿童 */
						Double childTotalPrice = 0d;
						Double childPerPrice = 0d;
						Double childObligationTotalPrice = 0d;
						if(bo.getChildPricePerPersonList() == null && bo.getChildPriceLevelList() == null){//儿童按成人计价
							isChildAsAdult = true;
							//把儿童并入成人计价
							valuationAdultNum += childNum;
						}else{//儿童单独计价
							if (bo.getChildPricePerPersonList() != null) {// 按人数计价
								try{//由于数据库中格式问题，可能出现越界的情况（即找不到对应语言的价格）
									childPerPrice = bo.getChildPricePerPersonList().get(index);
								}catch(Exception e){
									//使用第一种个语言的价格
									childPerPrice = bo.getChildPricePerPersonList().get(0);
								}
								childTotalPrice = childPerPrice * childNum;
								if(isObligation){
									childObligationTotalPrice = bo.getChildObligationPricePerPersonList().get(index) * childNum;
								}
							} else if (bo.getChildPriceLevelList() != null) {// 按梯度计
								for(Object entry : bo.getChildPriceLevelList().get(index).entrySet()){
									Map.Entry<Integer,Double> personNum_totalPrice = (Entry<Integer,Double>) entry;
									//检查人数是否符合当前梯度
									if(childNum > 0 && childNum <= personNum_totalPrice.getKey()){
										childTotalPrice = (Double) personNum_totalPrice.getValue();
										childPerPrice = childTotalPrice / childNum;
										/* 处理待付信息 */
										if(isObligation){
											childObligationTotalPrice = (Double) bo.getChildObligatioPriceLevelList().get(index).get(personNum_totalPrice.getKey());
										}
										break;
									}else{
										childPerPrice = 0d;
									}
								}
							}
						}
						
						/* 成人 */
						Double adultTotalPrice = 0d;
						Double adultPerPrice = 0d;
						Double adultObligationTotalPrice = 0d;
						if (bo.getAdultPricePerPersonList() != null) {// 按人数计价
							try{//由于数据库中格式问题，可能出现越界的情况（即找不到对应语言的价格）
								adultPerPrice = bo.getAdultPricePerPersonList().get(index);
							}catch(Exception e){
								//使用第一种个语言的价格
								adultPerPrice = bo.getAdultPricePerPersonList().get(0);
							}
							adultTotalPrice = adultPerPrice * valuationAdultNum;
							
							if(isObligation){
								adultObligationTotalPrice = bo.getAdultObligationPricePerPersonList().get(index) * valuationAdultNum;
							}
						} else if (bo.getAdultPriceLevelList() != null) {// 按梯度计
							for(Object entry : bo.getAdultPriceLevelList().get(index).entrySet()){
								Map.Entry<Integer,Double> personNum_totalPrice = (Entry<Integer,Double>) entry;
								//检查人数是否符合当前梯度
								if(valuationAdultNum <= personNum_totalPrice.getKey()){
									adultTotalPrice = (Double) personNum_totalPrice.getValue();
									adultPerPrice = adultTotalPrice / valuationAdultNum;
									/* 处理待付信息 */
									if(isObligation && bo.getAdultObligatioPriceLevelList() != null){
										adultObligationTotalPrice = (Double) bo.getAdultObligatioPriceLevelList().get(index).get(personNum_totalPrice.getKey());
									}
									break;
								}
							}
						}
						//儿童按成人计价后，需把实际参与成人计价的价格信息拆分为儿童和成人分别的价格信息
						if(isChildAsAdult){
							childPerPrice = adultPerPrice;
							childTotalPrice = adultTotalPrice - (childNum * childPerPrice);
							adultTotalPrice = adultTotalPrice - childTotalPrice;
						}
						
						/*填充全款价格*/
						priceBo.setPayType(productSnap.getPayType()); 
						priceBo.setPrice(new BigDecimal(adultTotalPrice + childTotalPrice));
						priceBo.setChildPrice(new BigDecimal(childPerPrice)); 
						priceBo.setAdultPrice(new BigDecimal(adultPerPrice)); 
						//默认预付价即为订单总价
						priceBo.setPrePayPrice(priceBo.getPrice()); 
						/* 处理待付信息 */
						if(isObligation){
							//获取货币信息
							CurrencyBo currencyBo = currencyService.findOne(productSnap.getCurrencyId());
							/*填充待付价格*/
							priceBo.setObligation(new BigDecimal(childObligationTotalPrice + adultObligationTotalPrice));
							priceBo.setPrePayPrice(getPrePayPrice(priceBo.getPrice().doubleValue(),priceBo.getObligation().doubleValue(),currencyBo)); 
							priceBo.setCurrencyType(getCurrencyType(currencyBo));
							
						}
						/* 价格有效性检查 */
						if(isPriceValid(priceBo)){
							priceBo.setCanBook(true);
							priceBo.setDiscount(this.getDiscount(priceBo));
							//修改 预付价 = 订单原价 - 折扣价
							priceBo.setPrePayPrice(priceBo.getPrePayPrice().subtract(priceBo.getDiscount()));
							if(priceBo.getObligation() == null){
								priceBo.setObligation(new BigDecimal(0));
							}
						}else{
							msg.append("价格计算失败");
						}
							
					} else {
						msg.append("非法语言");
					}

				} else {
					msg.append("非法出行人数");
				}
			} else {
				msg.append("非法产品");
			}
		} else {
			msg.append("非法套餐");
		}
		if(msg != null){
			priceBo.setMsg(msg.toString());
		}

		return priceBo;
	}
	
	@Override
	public PackageBo getCheapestPackage(long productId){
		PackageBo bo = null;
		Double startPrice = null;
		
		List<PackageBo> packages = this.findAllByProductId(productId);
		if(packages != null){
			for (PackageBo packageBo : packages) {
				if(startPrice == null || packageBo.getStartPrice().doubleValue() < startPrice){
					startPrice = packageBo.getStartPrice().doubleValue();
					bo = packageBo;
				}
			}
		}
		
		return bo;
	}
	
	
	/**
	 * 获取订单折扣（元）
	 * @param priceBo
	 * @return
	 */
	private BigDecimal getDiscount(PriceBo priceBo) {
		// TODO Auto-generated method stub
		return new BigDecimal(0);
	}

	/**
	 * 检查是否有效
	 * @param priceBo
	 * @return
	 */
	private boolean isPriceValid(PriceBo priceBo) {
		boolean isValid = false;
		if(priceBo.getPayType()!= null && priceBo.getAdultPrice() != null && priceBo.getChildPrice() != null &&priceBo.getPrice() != null ){
			isValid = true;
			if(priceBo.getPayType() == PayTypeEnum.PAY_PART.getId()){
				isValid = isValid && (priceBo.getPrePayPrice() != null && priceBo.getObligation() != null && priceBo.getCurrencyType() != null);
			}
		}
		return isValid;
	}

	/**
	 * 获取预付价格
	 * @param price 产品总价
	 * @param obligation 待付价格
	 * @param currencyId 待付货币类型
	 * @return
	 */
	private BigDecimal getPrePayPrice(Double price, Double obligation,CurrencyBo cbo) {
		Double prePayPrice = null;
		if(cbo != null){
			prePayPrice = price.doubleValue() - (obligation / cbo.getParities().doubleValue());
		}
		return new BigDecimal(prePayPrice).setScale(2, BigDecimal.ROUND_UP);
	}

	/**
	 * 获取待付货币名称
	 * @param currencyId 货币ID
	 * @return
	 */
	private String getCurrencyType(CurrencyBo bo) {
		String name = null;
		if(bo != null){
			name =  "".equals(bo.getName().trim()) ? bo.getAbbreviation() : bo.getName();
		}
		return name;
	}

	@Override
	public PackageBo findOne(Long id) {
		if(id == null) return null;
		Response response = thriftClient.serviceInvoke(this.ServiceName, "findOne", id);
		PackageBo bo = null;
		try {
			if (response != null) {
				if (response.isState()) {
					bo = JsonUtil.mapper.readValue(response.getData(),
							PackageBo.class);
				} else {
					LogUtil.recordWarnLog(response.getMsg());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return bo;
	}


}
