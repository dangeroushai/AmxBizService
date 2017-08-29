package com.amx.bizservice.model.vo.wap;

import java.util.ArrayList;
import java.util.List;

import com.amx.bizservice.model.bo.ProductBo;

/**
 * 产品列表
 * @author DangerousHai
 *
 */
public class ProductListVo {
    private Integer amount = 0;
    private Integer pageIndex = 0;
    private Integer pageAmount = 0;
    private List<ProductSnapVo> productList;
	
	public ProductListVo(List<ProductBo> productList) {
		this.productList = new ArrayList<ProductSnapVo>();
		if(productList != null){
			for(ProductBo bo : productList){
				ProductSnapVo prodSnap = new ProductSnapVo(bo);
				this.productList.add(prodSnap);
			}
		}
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

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public List<ProductSnapVo> getProductList() {
		return productList;
	}

	public void setProductList(List<ProductSnapVo> productList) {
		this.productList = productList;
	}
}
