package com.amx.bizservice.model.vo.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.amx.bizservice.model.bo.FavoriteBo;
import com.amx.bizservice.model.bo.ProductBo;
import com.amx.bizservice.model.dto.PageResponseDto;

/**
 * 收藏列表
 * @author DangerousHai
 *
 */
public class FavoriteListVo {
    private Integer wishAmount;
    private Integer pageIndex;
    private Integer pageAmount;
    private List<FavoriteVo> wishList;



	public FavoriteListVo(PageResponseDto<FavoriteBo> dto,List<ProductBo> content) {
		if(dto != null){
			this.wishAmount = dto.getAmount().intValue();
			this.pageAmount = dto.getPageAmount();
			this.pageIndex = dto.getPageIndex();
			if(content != null && dto.getContent() != null){
				this.wishList = new ArrayList<FavoriteVo>();
				int index = 0;
				for (ProductBo pbo : content) {
					FavoriteBo fbo = dto.getContent().get(index++);
					this.wishList.add(new FavoriteVo(fbo,pbo));
				}
			}
		}
	}
	
	/**
	 * 收藏商品
	 * @author DangerousHai
	 *
	 */
	protected class FavoriteVo{

		public FavoriteVo(FavoriteBo fbo,ProductBo pbo ) {
			if(pbo != null){
				BeanUtils.copyProperties(pbo, this);
				this.id = fbo.getId();
				this.productTypeId = pbo.getTypeId();
				this.productId = pbo.getId();
				
				this.coverPicUrl = pbo.getCoverPic();
				this.introduce = pbo.getIntroduceHtml();
			}
		}	
		
		/**
	     * @主键.
	     */
		private Long id;
		
		/**
		 * @产品ID.
		 */
		private Long productId;
		
		/**
		 * @产品类型ID.
		 */
		private Integer productTypeId;
		
	    /**
	     * @名称.
	     */
		private String name;
		/**
		 * @名称.
		 */
		private String enName;
		
	    /**
	     * @编号.
	     */
	    private String no;
	    
	    /**
	     * @单人起售价.
	     */
	    private Double price; 

	    /**
	     * @产品简介.
	     */
	    private String introduce; 


	    
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
	     * 是否收藏
	     */
	    private Integer isCollect;
	    

	    /**
	     * @封面图.
	     */
	    private String coverPicUrl; 

	    /**
	     * @属性.
	     */
	    private List<AttributeVo> themes;



		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Long getProductId() {
			return productId;
		}

		public void setProductId(Long productId) {
			this.productId = productId;
		}

		public Integer getProductTypeId() {
			return productTypeId;
		}

		public void setProductTypeId(Integer productTypeId) {
			this.productTypeId = productTypeId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getEnName() {
			return enName;
		}

		public void setEnName(String enName) {
			this.enName = enName;
		}

		public String getNo() {
			return no;
		}

		public void setNo(String no) {
			this.no = no;
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

		public Integer getIsCollect() {
			return isCollect;
		}

		public void setIsCollect(Integer isCollect) {
			this.isCollect = isCollect;
		}

		public String getCoverPicUrl() {
			return coverPicUrl;
		}

		public void setCoverPicUrl(String coverPicUrl) {
			this.coverPicUrl = coverPicUrl;
		}

		public List<AttributeVo> getThemes() {
			return themes;
		}

		public void setThemes(List<AttributeVo> themes) {
			this.themes = themes;
		} 
	    
	}

	public Integer getWishAmount() {
		return wishAmount;
	}

	public void setWishAmount(Integer wishAmount) {
		this.wishAmount = wishAmount;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageAmount() {
		return pageAmount;
	}

	public void setPageAmount(Integer pageAmount) {
		this.pageAmount = pageAmount;
	}

	public List<FavoriteVo> getWishList() {
		return wishList;
	}

	public void setWishList(List<FavoriteVo> wishList) {
		this.wishList = wishList;
	}
	
}
