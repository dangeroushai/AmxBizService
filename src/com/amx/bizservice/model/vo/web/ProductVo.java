package com.amx.bizservice.model.vo.web;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import com.amx.bizservice.enums.CommonTypeEnum;
import com.amx.bizservice.model.bo.AttributeBo;
import com.amx.bizservice.model.bo.LanguageBo;
import com.amx.bizservice.model.bo.PackageBo;
import com.amx.bizservice.model.bo.ProductBo;
import com.amx.bizservice.model.vo.LanguageVo;
import com.amx.bizservice.model.vo.PackageVo;
import com.amx.bizservice.model.vo.PictureVo;
import com.amx.bizservice.util.StringUtil;

/**
 * 产品业务对象，所有关联对象都只指明ID
 * @author DangerousHai
 *
 */
public class ProductVo{
	
	public ProductVo(ProductBo bo){
		if(bo != null){
			this.id = bo.getId();
			this.name = bo.getName();
			this.no = bo.getNo();
			this.introduce = bo.getIntroduceHtml();
			this.pictures = new ArrayList<PictureVo>();
			if(bo.getPictureMap() != null){
				for(String picUrl : bo.getPictureMap().keySet()){
					this.pictures.add(new PictureVo(picUrl));
				}
			}
			if(bo.getGatherWay() == 1){
				this.supGatherWay = "10";
			}else if(bo.getGatherWay() == 2){
				this.supGatherWay = "01";
			}else{
				this.supGatherWay = "11";
			}
			
			this.enName = bo.getSubTitle();
			this.baseDate = bo.getBaseDate();
			this.exceptionDate = bo.getExceptionDateList();
			this.saleRule = bo.getSaleRuleList();
			this.description = bo.getDescription();
			this.childRule = bo.getChildRule();
			this.recommend = new ArrayList<ProductVo>();
			if(bo.getRecommendList() != null){
				for(ProductBo pbo : bo.getRecommendList()){
					this.recommend.add(new ProductVo(pbo));
				}
			}
			this.languages = new ArrayList<LanguageVo>();
			if(bo.getLanguageList()!= null){
				for(LanguageBo lbo : bo.getLanguageList()){
					this.languages.add(new LanguageVo(lbo));
				}
			}
			this.refundRule = bo.getRefundRule();
			this.timeRule = bo.getTimeRuleList();
			this.bookRule = bo.getBookRule();
			this.coverPicUrl = bo.getCoverPic();
			this.feeDes = bo.getFeeDes();
			this.startCity = bo.getStartCity();
//			this.startTime = bo.get
			this.typeId = bo.getTypeId();
			this.themes = new ArrayList<AttributeVo>(); 
			if(bo.getAttrList() != null){
				for(AttributeBo abo :  bo.getAttrList()){
					this.themes.add(new AttributeVo(abo));
				}
			}
			this.packages = new ArrayList<PackageVo>();
			if(bo.getPackageList() != null){
				for(PackageBo pbo :  bo.getPackageList()){
					//套餐无介绍，则取产品介绍填充
					if(StringUtil.isEmpty(pbo.getDescription())){
						pbo.setDescription(this.description);
					}
					
					this.packages.add(new PackageVo(pbo));
				}
			}
			if(bo.getIsCollect() != null && bo.getIsCollect() == true){
				this.setIsCollect(CommonTypeEnum.TRUE.getId());
			}else{
				this.setIsCollect(CommonTypeEnum.FALSE.getId());
			}
			this.address = bo.getAddress();
			this.longitude = bo.getLongitude();
			this.latitude = bo.getLatitude();
			/*推荐套餐的信息*/
			this.price = bo.getPrice();
			this.duration = bo.getDuration();
			this.setMaxAdultNum(bo.getMaxAdultNum());
			this.setMaxChildNum(bo.getMaxChildNum());
			this.setMaxPersonNum(bo.getMaxPersonNum());
			this.setMinPersonNum(bo.getMinPersonNum());
		}
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductVo other = (ProductVo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}



	/**
     * @主键.
     */
	private Long id;
	
    /**
     * @名称.
     */
	private String name;
	/**
	 * @名称.
	 */
	private String enName;
	
    /**
     * @成人上限.
     */
    private Integer maxAdultNum; 
    
    /**
     * @人数上限.
     */
    private Integer maxPersonNum; 
    /**
     * @人数下限.
     */
    private Integer minPersonNum; 

    /**
     * @儿童上限.
     */
    private Integer maxChildNum;

	
    /**
     * @编号.
     */
    private String no;
    /**
     * @出发时间.
     */
    private Time startTime;
    
    /**
     * @单人起售价.
     */
    private Double price; 

    /**
     * @产品简介.
     */
    private String introduce; 

    /**
     * @附图<路径,描述>.
     */
    private List<PictureVo> pictures; 

    /**
     * @集合方式.
     */
    private String supGatherWay; 
    
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
     * @时长.
     */
    private Float duration; 

    /**
     * @预售时间.
     */
    private String baseDate; 

    /**
     * @例外日期.
     */
    private List<String> exceptionDate; 

    /**
     * @售卖规则.
     */
    private List<Integer> saleRule; 

    /**
     * @产品介绍.
     */
    private String description; 

    /**
     * @目的地.
     */
    private String destination;

    /**
     * @儿童政策.
     */
    private String childRule; 


    /**
     * @推荐产品.
     */
    private List<ProductVo> recommend; 

    /**
     * @服务语言.
     */
    private List<LanguageVo> languages; 
    
    /**
     * 是否收藏
     */
    private Integer isCollect;
    

    /**
     * @退改政策.
     */
    private String refundRule; 

    /**
     * @出发时间.
     */
    private List<String> timeRule; 

    /**
     * @预定须知.
     */
    private String bookRule; 

    /**
     * @封面图.
     */
    private String coverPicUrl; 

    /**
     * @费用说明.
     */
    private String feeDes; 


    /**
     * @出发地.
     */
    private String startCity; 

    /**
     * @产品类型.
     */
    private Integer typeId; 

    /**
     * @属性.
     */
    private List<AttributeVo> themes; 
    
	/**
	 * 套餐
	 */
	private List<PackageVo> packages;
	/**
	 * 行程
	 */
	private List<HodometerVo> hodometer;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}
	

	public Integer getMaxAdultNum() {
		return maxAdultNum;
	}

	public void setMaxAdultNum(Integer maxAdultNum) {
		this.maxAdultNum = maxAdultNum;
	}

	public Integer getMaxPersonNum() {
		return maxPersonNum;
	}
	

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public void setMaxPersonNum(Integer maxPersonNum) {
		this.maxPersonNum = maxPersonNum;
	}

	public Integer getMinPersonNum() {
		return minPersonNum;
	}

	public void setMinPersonNum(Integer minPersonNum) {
		this.minPersonNum = minPersonNum;
	}

	public Integer getMaxChildNum() {
		return maxChildNum;
	}

	public void setMaxChildNum(Integer maxChildNum) {
		this.maxChildNum = maxChildNum;
	}


	public String getEnName() {
		return enName;
	}

	public List<HodometerVo> getHodometer() {
		return hodometer;
	}

	public void setHodometer(List<HodometerVo> hodometer) {
		this.hodometer = hodometer;
	}

	public void setEnName(String enName) {
		this.enName = enName;
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

	public Float getDuration() {
		return duration;
	}

	public void setDuration(Float duration) {
		this.duration = duration;
	}

	public List<AttributeVo> getThemes() {
		return themes;
	}

	public void setThemes(List<AttributeVo> themes) {
		this.themes = themes;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public List<PictureVo> getPictures() {
		return pictures;
	}

	public void setPictures(List<PictureVo> pictures) {
		this.pictures = pictures;
	}

	public String getSupGatherWay() {
		return supGatherWay;
	}

	public void setSupGatherWay(String supGatherWay) {
		this.supGatherWay = supGatherWay;
	}

	public String getBaseDate() {
		return baseDate;
	}

	public void setBaseDate(String baseDate) {
		this.baseDate = baseDate;
	}

	public List<String> getExceptionDate() {
		return exceptionDate;
	}

	public void setExceptionDate(List<String> exceptionDate) {
		this.exceptionDate = exceptionDate;
	}

	public List<Integer> getSaleRule() {
		return saleRule;
	}

	public void setSaleRule(List<Integer> saleRule) {
		this.saleRule = saleRule;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getChildRule() {
		return childRule;
	}

	public void setChildRule(String childRule) {
		this.childRule = childRule;
	}

	public List<ProductVo> getRecommend() {
		return recommend;
	}

	public void setRecommend(List<ProductVo> recommend) {
		this.recommend = recommend;
	}

	public List<LanguageVo> getLanguages() {
		return languages;
	}

	public void setLanguages(List<LanguageVo> languages) {
		this.languages = languages;
	}

	public Integer getIsCollect() {
		return isCollect;
	}

	public void setIsCollect(Integer isCollect) {
		this.isCollect = isCollect;
	}

	public String getRefundRule() {
		return refundRule;
	}

	public void setRefundRule(String refundRule) {
		this.refundRule = refundRule;
	}

	public List<String> getTimeRule() {
		return timeRule;
	}

	public void setTimeRule(List<String> timeRule) {
		this.timeRule = timeRule;
	}

	public String getBookRule() {
		return bookRule;
	}

	public void setBookRule(String bookRule) {
		this.bookRule = bookRule;
	}

	public String getCoverPicUrl() {
		return coverPicUrl;
	}

	public void setCoverPicUrl(String coverPicUrl) {
		this.coverPicUrl = coverPicUrl;
	}

	public String getFeeDes() {
		return feeDes;
	}

	public void setFeeDes(String feeDes) {
		this.feeDes = feeDes;
	}

	public String getStartCity() {
		return startCity;
	}

	public void setStartCity(String startCity) {
		this.startCity = startCity;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public List<PackageVo> getPackages() {
		return packages;
	}

	public void setPackages(List<PackageVo> packages) {
		this.packages = packages;
	}
}
