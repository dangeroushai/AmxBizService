package com.amx.bizservice.model.vo.web;

import java.util.ArrayList;
import java.util.List;

import com.amx.bizservice.model.bo.ProductBo;
import com.amx.bizservice.model.bo.TravelBo;
import com.amx.bizservice.model.dto.PageResponseDto;
import com.amx.bizservice.util.StringUtil;

/**
 * 收藏列表
 * @author DangerousHai
 *
 */
public class TravelListVo {
    private Integer travelAmount;
    private Integer pageIndex;
    private Integer pageAmount;
    private List<TravelVo> travelList;

	public TravelListVo(PageResponseDto<TravelBo> dto , List<ProductBo> content) {
		if(dto != null){
			this.travelAmount = dto.getAmount().intValue();
			this.pageAmount = dto.getPageAmount();
			this.pageIndex = dto.getPageIndex();
			
			if(content != null && dto.getContent() != null){
				this.travelList = new ArrayList<TravelVo>();
				int index = 0;
				for (ProductBo pbo : content) {
					TravelBo bo = dto.getContent().get(index++);
					this.travelList.add(new TravelVo(bo,pbo));
				}
			}
		}
	}
	
	public TravelListVo() {
	}

	/**
	 * 购物车商品
	 * @author DangerousHai
	 *
	 */
	public class TravelVo{

		public TravelVo(TravelBo travelbo,ProductBo prodbo ) {
			if(prodbo != null){
				this.id = travelbo.getId();
				this.name = travelbo.getName();
				this.coverPicUrl = prodbo.getCoverPic();
				this.dayNum = travelbo.getDayAmount();
				this.updateTime = StringUtil.getSdfDate(travelbo.getModifyTime());
			}
		}	
		
		public TravelVo() {
		}

		/**
	     * @主键.
	     */
		private Long id;
		
		/**
		 * @名称.
		 */
		private String name;
		
		private Integer dayNum;
		
		private String coverPicUrl;
		
		private String updateTime;

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

		public Integer getDayNum() {
			return dayNum;
		}

		public void setDayNum(Integer dayNum) {
			this.dayNum = dayNum;
		}

		public String getCoverPicUrl() {
			return coverPicUrl;
		}

		public void setCoverPicUrl(String coverPicUrl) {
			this.coverPicUrl = coverPicUrl;
		}

		public String getUpdateTime() {
			return updateTime;
		}

		public void setUpdateTime(String updateTime) {
			this.updateTime = updateTime;
		}
		
	}

	public Integer getTravelAmount() {
		return travelAmount;
	}

	public void setTravelAmount(Integer travelAmount) {
		this.travelAmount = travelAmount;
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

	public List<TravelVo> getTravelList() {
		return travelList;
	}

	public void setTravelList(List<TravelVo> travelList) {
		this.travelList = travelList;
	}
	
}
