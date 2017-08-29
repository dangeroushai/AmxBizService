package com.amx.bizservice.model.vo.wap;

import java.util.Date;
import java.util.List;

import com.amx.bizservice.model.bo.CustomHodometerBo;
import com.amx.bizservice.model.bo.HodometerBo;
import com.amx.bizservice.model.bo.LanguageBo;
import com.amx.bizservice.model.bo.PackageBo;
import com.amx.bizservice.model.bo.ProductBo;
import com.amx.bizservice.util.StringUtil;

/**
 * 单天行程安排
 * @author DangerousHai
 *
 */
public class HodometerVo{
	
    /**
     * @天序号.
     */
    private Integer dayOrder; 

    /**
     * @出发日期.
     */
    private Date date; 

    /**
     * @活动项.
     */
    private List<ItemVo> items;
    
    public class ItemVo{
    	private Integer itemOrder; 
    	
    	private ProductSnapVo product;
    	
    	private TravelInfoVo travelInfo;
    	
		public ItemVo(HodometerBo hbo, ProductBo itemProductBo) {
			if(hbo != null && itemProductBo != null){
				this.itemOrder = hbo.getItemOrder() + 1;
				this.product = new ProductSnapVo(itemProductBo);
				this.travelInfo = new TravelInfoVo(hbo); 
			}
		}

		public ItemVo(CustomHodometerBo chBo, ProductBo prodbo, PackageBo packBo, LanguageBo langBo) {
			if(prodbo != null){
				this.product  = new ProductSnapVo(prodbo);
			}
			if(chBo != null){
				this.travelInfo  = new TravelInfoVo(chBo,packBo,langBo);
			}
		}
    	
    	class TravelInfoVo{
    		private String goOffTime; 
    		private String goOffDate; 
    		private Integer adultNum; 
    		private Integer childNum; 
    		private Integer languageId ; 
    		private String languageName ; 
    		private Long packageId ; 
    		private String packageName ; 
    		private String remark ;
    		
			public TravelInfoVo(CustomHodometerBo chBo, PackageBo packBo, LanguageBo langBo) {
				this.goOffDate = StringUtil.getSdfDate(chBo.getGoOffDate());
				this.goOffTime = StringUtil.getHMTime(chBo.getGoOffTime());
				this.adultNum = chBo.getAdultNum();
				this.languageId = chBo.getLanguageId();
				this.packageId = chBo.getPackageId();
				this.remark = chBo.getRemark();
				if(packBo != null){
					this.packageName = packBo.getName();
				}
				if(langBo != null){
					this.languageName = langBo.getName();
				}
			}
			public TravelInfoVo(HodometerBo hbo) {
				if(hbo != null){
					this.goOffTime = StringUtil.getHMTime(hbo.getGoOffTime());
				}
			}
			public String getGoOffTime() {
				return goOffTime;
			}
			public void setGoOffTime(String goOffTime) {
				this.goOffTime = goOffTime;
			}
			public String getGoOffDate() {
				return goOffDate;
			}
			public void setGoOffDate(String goOffDate) {
				this.goOffDate = goOffDate;
			}
			public Integer getAdultNum() {
				return adultNum;
			}
			public void setAdultNum(Integer adultNum) {
				this.adultNum = adultNum;
			}
			public Integer getChildNum() {
				return childNum;
			}
			public void setChildNum(Integer childNum) {
				this.childNum = childNum;
			}
			public Integer getLanguageId() {
				return languageId;
			}
			public void setLanguageId(Integer languageId) {
				this.languageId = languageId;
			}
			public String getLanguageName() {
				return languageName;
			}
			public void setLanguageName(String languageName) {
				this.languageName = languageName;
			}
			public Long getPackageId() {
				return packageId;
			}
			public void setPackageId(Long packageId) {
				this.packageId = packageId;
			}
			public String getPackageName() {
				return packageName;
			}
			public void setPackageName(String packageName) {
				this.packageName = packageName;
			}
			public String getRemark() {
				return remark;
			}
			public void setRemark(String remark) {
				this.remark = remark;
			} 
    	}


		public Integer getItemOrder() {
			return itemOrder;
		}

		public void setItemOrder(Integer itemOrder) {
			this.itemOrder = itemOrder;
		}

		public ProductSnapVo getProduct() {
			return product;
		}

		public void setProduct(ProductSnapVo product) {
			this.product = product;
		}

		public TravelInfoVo getTravelInfo() {
			return travelInfo;
		}

		public void setTravelInfo(TravelInfoVo travelInfo) {
			this.travelInfo = travelInfo;
		}
    }

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<ItemVo> getItems() {
		return items;
	}

	public void setItems(List<ItemVo> items) {
		this.items = items;
	}

	public Integer getDayOrder() {
		return dayOrder;
	}

	public void setDayOrder(Integer dayOrder) {
		this.dayOrder = dayOrder;
	}

}