package com.amx.bizservice.model.vo.web;

import java.util.List;

import com.amx.bizservice.model.bo.AdvertisementBo;
import com.amx.bizservice.model.bo.RegionBo;

/**
 * 根据地区推荐产品
 * @author DangerousHai
 *
 */
public class ProductRecByAreaVo {
	
    private String zhAreaName;
    private String enAreaName;
    private Integer areaId;
    private String bgPicUrl;
    private List<ProductVo> products;

	public ProductRecByAreaVo(AdvertisementBo abo,RegionBo rbo) {
		if(abo != null){
			//优先使用广告中设置的名称，为空则使用地名
			this.zhAreaName = "".equals(abo.getTitle().trim()) ? rbo.getName() : abo.getTitle(); 
			this.enAreaName = "".equals(abo.getSubTitle().trim()) ? rbo.getEnName() : abo.getSubTitle();
			this.areaId = abo.getLinkRegionId();
			this.bgPicUrl = abo.getCoverPic();
			//在外部设置
			//this.products 
		}
	}



	public String getZhAreaName() {
		return zhAreaName;
	}



	public void setZhAreaName(String zhAreaName) {
		this.zhAreaName = zhAreaName;
	}



	public String getEnAreaName() {
		return enAreaName;
	}



	public void setEnAreaName(String enAreaName) {
		this.enAreaName = enAreaName;
	}



	public Integer getAreaId() {
		return areaId;
	}



	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}



	public String getBgPicUrl() {
		return bgPicUrl;
	}



	public void setBgPicUrl(String bgPicUrl) {
		this.bgPicUrl = bgPicUrl;
	}



	public List<ProductVo> getProducts() {
		return products;
	}



	public void setProducts(List<ProductVo> products) {
		this.products = products;
	}

}
