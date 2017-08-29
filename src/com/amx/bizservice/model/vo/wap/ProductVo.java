package com.amx.bizservice.model.vo.wap;

import java.util.ArrayList;
import java.util.List;

import com.amx.bizservice.enums.CommonTypeEnum;
import com.amx.bizservice.model.bo.LanguageBo;
import com.amx.bizservice.model.bo.PackageBo;
import com.amx.bizservice.model.bo.ProductBo;
import com.amx.bizservice.model.vo.LanguageVo;
import com.amx.bizservice.model.vo.PackageVo;
import com.amx.bizservice.model.vo.PictureVo;

/**
 * @author DangerousHai
 *
 */
public class ProductVo{
	
	public ProductVo(ProductBo bo){
		if(bo != null){
			this.id = bo.getId();
			this.name = bo.getName();
			this.pictures = new ArrayList<PictureVo>();
			if(bo.getPictureMap() != null){
				for(String picUrl : bo.getPictureMap().keySet()){
					this.pictures.add(new PictureVo(picUrl));
				}
			}
			this.supGatherWay = bo.getGatherWay();
			this.subTitle = bo.getSubTitle();
			this.baseDate = bo.getBaseDate();
			this.exceptionDate = bo.getExceptionDateList();
			this.saleRule = bo.getSaleRuleList();
			this.description = bo.getDescription();
			this.childRule = bo.getChildRule();
			this.recommend = new ArrayList<ProductSnapVo>();
			if(bo.getRecommendList() != null){
				for(ProductBo pbo : bo.getRecommendList()){
					this.recommend.add(new ProductSnapVo(pbo));
				}
			}
			this.languages = new ArrayList<LanguageVo>();
			if(bo.getLanguageList()!= null){
				for(LanguageBo lbo : bo.getLanguageList()){
					this.languages.add(new LanguageVo(lbo));
				}
			}
			this.price = bo.getPrice();
			this.refundRule = bo.getRefundRule();
			this.timeRule = bo.getTimeRuleList();
			this.bookRule = bo.getBookRule();
			this.coverPicUrl = bo.getCoverPic();
			this.feeDes = bo.getFeeDes();
			this.startCity = bo.getStartCity();
			this.typeId = bo.getTypeId();
			this.packages = new ArrayList<PackageVo>();
			if(bo.getPackageList() != null){
				for(PackageBo pbo :  bo.getPackageList()){
					this.packages.add(new PackageVo(pbo));
				}
			}
			if(bo.getIsCollect() != null && bo.getIsCollect() == true){
				this.setIsCollect(CommonTypeEnum.TRUE.getId());
			}else{
				this.setIsCollect(CommonTypeEnum.FALSE.getId());
			}
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
	private String subTitle;
	
    /**
     * @单人起售价.
     */
    private Double price; 

    /**
     * @附图<路径,描述>.
     */
    private List<PictureVo> pictures; 

    /**
     * @集合方式.
     */
    private Integer supGatherWay; 
    
    
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
     * @儿童政策.
     */
    private String childRule; 


    /**
     * @推荐产品.
     */
    private List<ProductSnapVo> recommend; 

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
	 * 套餐
	 */
	private List<PackageVo> packages;

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

	public List<PictureVo> getPictures() {
		return pictures;
	}

	public void setPictures(List<PictureVo> pictures) {
		this.pictures = pictures;
	}

	public Integer getSupGatherWay() {
		return supGatherWay;
	}

	public void setSupGatherWay(Integer supGatherWay) {
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

	public String getChildRule() {
		return childRule;
	}

	public void setChildRule(String childRule) {
		this.childRule = childRule;
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

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public List<ProductSnapVo> getRecommend() {
		return recommend;
	}
	public void setRecommend(List<ProductSnapVo> recommend) {
		this.recommend = recommend;
	}
	
	
}
