package com.amx.bizservice.model.vo.web;

import java.util.ArrayList;
import java.util.List;

import com.amx.bizservice.model.bo.AdvertisementBo;
import com.amx.bizservice.model.bo.AttributeBo;
import com.amx.bizservice.model.bo.RegionBo;

/**
 * 收藏列表
 * @author DangerousHai
 *
 */
public class CategoryHeaderVo {
	
    private String bgPicUrl;
    
    private String bgPicLinkUrl;
    
    private List<AttributeVo> sceneList;
    
    private List<AttributeVo> themeList;
    
    private List<DestinationVo> destList;
    
    private List<PriceRangeVo> priceRangeList;

	public CategoryHeaderVo() {
	}

	public CategoryHeaderVo(AdvertisementBo adBo,List<AttributeBo> themeList,List<AttributeBo> sceneList, List<RegionBo> regionList) {
		if(adBo != null){
			this.bgPicUrl = adBo.getCoverPic();
			this.bgPicLinkUrl = adBo.getLinkUrl();
		}
		if(themeList != null){
			this.themeList = new ArrayList<AttributeVo>();
			for (AttributeBo tbo : themeList) {
				tbo.setCoverPic(null);
				this.themeList.add(new AttributeVo(tbo));
			}
		}
		if(sceneList != null){
			this.sceneList = new ArrayList<AttributeVo>();
			for (AttributeBo sbo : sceneList) {
				sbo.setCoverPic(null);
				this.sceneList.add(new AttributeVo(sbo));
			}
		}
		if(regionList != null){
			this.destList = new ArrayList<DestinationVo>();
			for (RegionBo rbo : regionList) {
				rbo.setCoverPic(null);
				this.destList.add(new DestinationVo(rbo));
			}
		}
		//外部处理
		//this.priceRangeList
	}

	/**
	 * 目的地
	 * @author DangerousHai
	 *
	 */
	public class DestinationVo{

		public DestinationVo(){
		}	

		public DestinationVo(RegionBo rbo) {
			this.id = rbo.getId();
			this.name = rbo.getName();
			if(rbo.getSubRegionList() != null){
				this.subArea = new ArrayList<CategoryHeaderVo.DestinationVo>();
				for(RegionBo subBo : rbo.getSubRegionList()){
					this.subArea.add(new DestinationVo(subBo));
				}
			}
		}

		/**
	     * @主键.
	     */
		private Integer id;
		
	    /**
	     * @名称.
	     */
		private String name;
		
	    
	    private List<DestinationVo> subArea; 


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

		public List<DestinationVo> getSubArea() {
			return subArea;
		}

		public void setSubArea(List<DestinationVo> subArea) {
			this.subArea = subArea;
		}
	}

	/**
	 * 价格
	 * @author DangerousHai
	 *
	 */
	public class PriceRangeVo{

		public PriceRangeVo(){}	

		public PriceRangeVo(String lowPrice, String highPrice){ 
			this.lowPrice = lowPrice;
			this.highPrice = highPrice;
		}

		private String lowPrice;
		
		private String highPrice;
		
		public String getLowPrice() {
			return lowPrice;
		}
		public void setLowPrice(String lowPrice) {
			this.lowPrice = lowPrice;
		}
		public String getHighPrice() {
			return highPrice;
		}
		public void setHighPrice(String highPrice) {
			this.highPrice = highPrice;
		}
	}

	public String getBgPicUrl() {
		return bgPicUrl;
	}

	public void setBgPicUrl(String bgPicUrl) {
		this.bgPicUrl = bgPicUrl;
	}

	public String getBgPicLinkUrl() {
		return bgPicLinkUrl;
	}

	public void setBgPicLinkUrl(String bgPicLinkUrl) {
		this.bgPicLinkUrl = bgPicLinkUrl;
	}

	public List<AttributeVo> getSceneList() {
		return sceneList;
	}

	public void setSceneList(List<AttributeVo> sceneList) {
		this.sceneList = sceneList;
	}

	public List<AttributeVo> getThemeList() {
		return themeList;
	}

	public void setThemeList(List<AttributeVo> themeList) {
		this.themeList = themeList;
	}

	public List<DestinationVo> getDestList() {
		return destList;
	}

	public void setDestList(List<DestinationVo> destList) {
		this.destList = destList;
	}

	public List<PriceRangeVo> getPriceRangeList() {
		return priceRangeList;
	}

	public void setPriceRangeList(List<PriceRangeVo> priceRangeList) {
		this.priceRangeList = priceRangeList;
	}
	
}
