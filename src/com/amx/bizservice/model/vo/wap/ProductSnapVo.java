package com.amx.bizservice.model.vo.wap;

import java.util.ArrayList;
import java.util.List;

import com.amx.bizservice.model.bo.AttributeBo;
import com.amx.bizservice.model.bo.ProductBo;


public class ProductSnapVo{
	/**
     * @主键.
     */
	private Long id;
	
    /**
     * @名称.
     */
	private String name;
	
	private String no;
	
	private String subTitle;

    /**
     * @出发地.
     */
    private String startCity; 
    /**
     * @目的地.
     */
    private String destination;
    
    /**
     * @产品简介.
     */
    private String introduce; 
    
    /**
     * @单人起售价.
     */
    private Double price; 

    /**
     * @封面图.
     */
    private String coverPicUrl; 

    /**
     * @产品类型.
     */
    private Integer typeId; 

    /**
     * @属性.
     */
    private List<ThemeVo> themes; 
    
    class ThemeVo{
		/**
	     * @主键.
	     */
		private Integer id;
		
	    /**
	     * @名称.
	     */
		private String name;

		public ThemeVo(AttributeBo attrBo) {
			if(attrBo != null){
				this.id = attrBo.getId();
				this.name = attrBo.getName();
			}
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
    }

	ProductSnapVo(ProductBo bo) {
		if(bo != null){
			this.id = bo.getId();
			this.typeId = bo.getTypeId();
			this.no = bo.getNo();
			this.name = bo.getName();
			this.subTitle = bo.getSubTitle();
			this.price = bo.getPrice();
			this.introduce = bo.getIntroduceHtml();
			this.startCity = bo.getStartCity();
			this.destination = bo.getDestination();
			this.coverPicUrl = bo.getCoverPic();
			
			if(bo.getAttrList() != null){
				this.themes = new ArrayList<ThemeVo>();
				for (AttributeBo attrBo  : bo.getAttrList() ) {
					this.themes.add(this.new ThemeVo(attrBo));
				}
			}
		}
	}

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

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getStartCity() {
		return startCity;
	}

	public void setStartCity(String startCity) {
		this.startCity = startCity;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCoverPicUrl() {
		return coverPicUrl;
	}

	public void setCoverPicUrl(String coverPicUrl) {
		this.coverPicUrl = coverPicUrl;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public List<ThemeVo> getThemes() {
		return themes;
	}

	public void setThemes(List<ThemeVo> themes) {
		this.themes = themes;
	}

}