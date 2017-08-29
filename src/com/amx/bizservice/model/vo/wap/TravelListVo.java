package com.amx.bizservice.model.vo.wap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.amx.bizservice.model.bo.CustomHodometerBo;
import com.amx.bizservice.model.bo.ProductBo;
import com.amx.bizservice.model.bo.TravelBo;
import com.amx.bizservice.model.dto.PageResponseDto;

/**
 * 收藏列表
 * @author DangerousHai
 *
 */
public class TravelListVo {
    private Integer amount;
    private Integer pageIndex;
    private Integer pageAmount;
    private List<TravelVo> travelList;

	public TravelListVo(PageResponseDto<TravelBo> dto , List<ProductBo> content) {
		if(dto != null){
			this.amount = dto.getAmount().intValue();
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

		public TravelVo(TravelBo travelbo,ProductBo prodbo ){
			//XXX - 调用当前类的够造方法
			this(travelbo,prodbo,null);
		}
		
		public TravelVo(TravelBo travelbo,ProductBo prodbo ,List<CustomHodometerBo> customHodometerList) {
			if(prodbo != null){
				this.id = travelbo.getId();
				this.name = travelbo.getName();
				this.coverPicUrl = prodbo.getCoverPic();
				this.dayNum = travelbo.getDayAmount();
				if(customHodometerList != null && customHodometerList.size() > 0){
					this.startDate = customHodometerList.get(0).getGoOffDate();
					this.endDate = customHodometerList.get(customHodometerList.size()-1).getGoOffDate();
				}
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
		
		private Date startDate;
		private Date endDate;
		
		private String coverPicUrl;

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

		public Date getStartDate() {
			return startDate;
		}

		public void setStartDate(Date startDate) {
			this.startDate = startDate;
		}

		public Date getEndDate() {
			return endDate;
		}

		public void setEndDate(Date endDate) {
			this.endDate = endDate;
		}
	}


	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
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
