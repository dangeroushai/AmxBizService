package com.amx.bizservice.model.vo.web;

import java.util.List;

import com.amx.bizservice.model.bo.AdvertisementBo;
import com.amx.bizservice.model.bo.AttributeBo;
import com.amx.bizservice.util.PictureUtil;

/**
 * 根据地区推荐产品
 * @author DangerousHai
 *
 */
public class ProductRecBySceneVo {
	/**
	 * 场景Id
	 */
	private Integer id;
    private String name;
    private String coverPicUrl;
    private List<ProductVo> products;



	public ProductRecBySceneVo(AdvertisementBo abo,AttributeBo attrbo) {
		if(abo != null && attrbo != null){
			this.id = abo.getLinkAttributeId();
			//优先使用广告中设置的名称，为空则使用地名
			this.name = "".equals(abo.getTitle().trim()) ? attrbo.getName() : abo.getTitle(); 
			this.coverPicUrl = PictureUtil.getPicUrl(abo.getCoverPic());
			//在外部设置
			//this.products 
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



	public String getCoverPicUrl() {
		return coverPicUrl;
	}



	public void setCoverPicUrl(String coverPicUrl) {
		this.coverPicUrl = coverPicUrl;
	}



	public List<ProductVo> getProducts() {
		return products;
	}



	public void setProducts(List<ProductVo> products) {
		this.products = products;
	}

}
