package com.amx.bizservice.model.vo.web;

import java.util.List;

import com.amx.bizservice.model.bo.CustomHodometerBo;
import com.amx.bizservice.model.bo.LanguageBo;
import com.amx.bizservice.model.bo.PackageBo;
import com.amx.bizservice.model.bo.ProductBo;
import com.amx.bizservice.util.StringUtil;


/**
 * 
 * @author DangerousHai
 *
 */
public class TravelVo {

	/**
     * @主键.
     */
	private Long id;
	
	/**
	 * @名称.
	 */
	private String name;
	
	private Integer dayNum;
	
	private String updateTime;
	
    private List<HodometerVo> hodometer;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 单天行程安排
	 * @author DangerousHai
	 *
	 */
	public class HodometerVo{
		
		public HodometerVo(){}
		
	    /**
	     * @天序号.
	     */
	    private Integer dayOrder; 

	    /**
	     * @出发日期.
	     */
	    private String date; 

	    /**
	     * @活动项.
	     */
	    private List<ItemVo> items;
	    

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}
		public Integer getDayOrder() {
			return dayOrder;
		}

		public void setDayOrder(Integer dayOrder) {
			this.dayOrder = dayOrder;
		}

		public List<ItemVo> getItems() {
			return items;
		}

		public void setItems(List<ItemVo> items) {
			this.items = items;
		}
		
		/**
		 * 我的行程 - 单项
		 * @author DangerousHai
		 *
		 */
		public class ItemVo{
			
		    private Integer itemOrder; 
			/**
		     * @product.id
		     */
			private Long id;
			  /**
		     * @product.typeId
		     */
		    private Integer typeId; 
		    /**
		     * @名称.
		     */
			private String name;

		    /**
		     * @封面图.
		     */
		    private String coverPicUrl; 
		    
		    /**
		     * @时长.
		     */
		    private Float duration; 
		    
		    /**
		     * @集合地.
		     */
		    private String address; 
		    
		    /**
		     * @经度.
		     */
		    private String longitude; 
		    
		    /**
		     * @纬度.
		     */
		    private String latitude;
		    
		    /**
		     * @单人起售价.
		     */
		    private Double price; 

		    /**
		     * @出发地.
		     */
		    private String startCity; 
		    
			private Integer languageId;
			private String languageName;
			private Long packageId;
			private String packageName;
			
		    private String goOffDate; 
		    private String goOffTime; 
		    private Integer adultNum; 
		    private Integer childNum; 
		    
		    private String remark;
		    
		    public ItemVo(){}
			public ItemVo(CustomHodometerBo chBo, ProductBo prodBo, PackageBo packBo, LanguageBo langBo) {
				//this.itemOrder =
				if(chBo != null){
					this.adultNum = chBo.getAdultNum();
					this.childNum = chBo.getChildNum();
					this.goOffDate = StringUtil.getSdfDate(chBo.getGoOffDate());
					this.goOffTime = StringUtil.getHMTime(chBo.getGoOffTime());
					this.remark = chBo.getRemark();
				}
				if(prodBo != null){
					this.id = prodBo.getId();
					this.typeId = prodBo.getTypeId();
					this.name = prodBo.getName();
					this.duration = prodBo.getDuration();
					this.coverPicUrl = prodBo.getCoverPic();
					this.address = prodBo.getAddress();
					this.latitude = prodBo.getLatitude();
					this.longitude = prodBo.getLongitude();
					this.price = prodBo.getPrice();
					this.startCity = prodBo.getStartCity();
					
				}
				if(packBo != null){
					this.packageId = packBo.getId();
					this.packageName = packBo.getName();
				}
				if(langBo != null){
					this.languageId = langBo.getId();
					this.languageName = langBo.getName();
				}
			}

			public Integer getItemOrder() {
				return itemOrder;
			}

			public void setItemOrder(Integer itemOrder) {
				this.itemOrder = itemOrder;
			}

			public Long getId() {
				return id;
			}

			public void setId(Long id) {
				this.id = id;
			}

			public Integer getTypeId() {
				return typeId;
			}

			public void setTypeId(Integer typeId) {
				this.typeId = typeId;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public String getCoverPicUrl() {
				return coverPicUrl;
			}

			public void setCoverPicUrl(String coverPicUrl) {
				this.coverPicUrl = coverPicUrl;
			}

			public Float getDuration() {
				return duration;
			}

			public void setDuration(Float duration) {
				this.duration = duration;
			}

			public String getAddress() {
				return address;
			}

			public void setAddress(String address) {
				this.address = address;
			}

			public String getLongitude() {
				return longitude;
			}

			public void setLongitude(String longitude) {
				this.longitude = longitude;
			}

			public String getLatitude() {
				return latitude;
			}

			public void setLatitude(String latitude) {
				this.latitude = latitude;
			}

			public Double getPrice() {
				return price;
			}

			public void setPrice(Double price) {
				this.price = price;
			}

			public String getStartCity() {
				return startCity;
			}

			public void setStartCity(String startCity) {
				this.startCity = startCity;
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

			public String getGoOffDate() {
				return goOffDate;
			}

			public void setGoOffDate(String goOffDate) {
				this.goOffDate = goOffDate;
			}

			public String getGoOffTime() {
				return goOffTime;
			}

			public void setGoOffTime(String goOffTime) {
				this.goOffTime = goOffTime;
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

			public String getRemark() {
				return remark;
			}

			public void setRemark(String remark) {
				this.remark = remark;
			}
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDayNum() {
		return dayNum;
	}

	public void setDayNum(Integer dayNum) {
		this.dayNum = dayNum;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public List<HodometerVo> getHodometer() {
		return hodometer;
	}

	public void setHodometer(List<HodometerVo> hodometer) {
		this.hodometer = hodometer;
	}
}
