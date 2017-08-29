package com.amx.bizservice.model.vo.web;

import java.util.List;

import com.amx.bizservice.model.bo.CartBo;
import com.amx.bizservice.model.bo.LanguageBo;
import com.amx.bizservice.model.bo.PackageBo;
import com.amx.bizservice.model.bo.PriceBo;
import com.amx.bizservice.model.bo.ProductBo;
import com.amx.bizservice.model.dto.PageResponseDto;
import com.amx.bizservice.util.StringUtil;

/**
 * 收藏列表
 * @author DangerousHai
 *
 */
public class CartListVo {
    private Integer cartAmount;
    private Integer pageIndex;
    private Integer pageAmount;
    private List<CartVo> cartList;

	public CartListVo(PageResponseDto<CartBo> dto) {
		if(dto != null){
			this.cartAmount = dto.getAmount().intValue();
			this.pageAmount = dto.getPageAmount();
			this.pageIndex = dto.getPageIndex();
			//外部处理
			//this.cartList = 
			
			/*if(content != null && dto.getContent() != null){
				this.cartList = new ArrayList<CartVo>();
				int index = 0;
				for (ProductBo pbo : content) {
					CartBo fbo = dto.getContent().get(index++);
					this.cartList.add(new CartVo(fbo,pbo));
				}
			}*/
		}
	}
	
	public CartListVo() {
	}

	/**
	 * 购物车商品
	 * @author DangerousHai
	 *
	 */
	public class CartVo{

		public CartVo(CartBo cartbo,ProductBo prodbo ,PackageBo packbo ,LanguageBo lbo,PriceBo pricebo) {
			if(prodbo != null){
				this.productId = prodbo.getId();
				this.productTypeId = prodbo.getTypeId();
				this.productName = prodbo.getName();
				this.price = prodbo.getPrice();
				this.coverPicUrl = prodbo.getCoverPic();
				this.childRule = prodbo.getChildRule();
				if(packbo != null){
					this.packageName = packbo.getName();
					this.buyCondition = new BuyConditionVo(prodbo,packbo);
				}
				if(cartbo != null){
					this.id = cartbo.getId();
					this.languageId = cartbo.getLanguageId();
					this.packageId = cartbo.getPackageId();
					
					this.goOffDate = StringUtil.getSdfDate(cartbo.getGoOffDate());
					this.goOffTime = StringUtil.getHMTime(cartbo.getGoOffTime());
					this.adultNum = cartbo.getAdultNum();
					this.childNum = cartbo.getChildNum();
				}
				if(lbo != null){
					this.languageName = lbo.getName();
				}
				if(pricebo != null){
					if(pricebo.getCanBook()){
						//展示成人单价
						this.price = pricebo.getAdultPrice().doubleValue();
						this.totalPrice = pricebo.getPrice().doubleValue();
					}
				}
			}
		}	
		
		public CartVo() {
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
		private String productName;

		private Integer languageId;
		
		private String languageName;
		
		private Long packageId;
		
		private String packageName;
		
	    /**
	     * @单人起售价.
	     */
	    private Double price; 
	    /**
	     * @总价.
	     */
	    private Double totalPrice; 


	    /**
	     * @封面图.
	     */
	    private String coverPicUrl; 
	    private String goOffDate; 
	    private String goOffTime; 
	    private Integer adultNum; 
	    private Integer childNum; 
	    private String childRule;
	    
	    private BuyConditionVo buyCondition; 


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

		public String getProductName() {
			return productName;
		}

		public void setProductName(String productName) {
			this.productName = productName;
		}

		public Integer getLanguageId() {
			return languageId;
		}

		public void setLanguageId(Integer languageId) {
			this.languageId = languageId;
		}

		public String getLanguageName() {
			return languageName;
		}

		public void setLanguageName(String languageName) {
			this.languageName = languageName;
		}

		public Long getPackageId() {
			return packageId;
		}

		public void setPackageId(Long packageId) {
			this.packageId = packageId;
		}

		public String getPackageName() {
			return packageName;
		}

		public void setPackageName(String packageName) {
			this.packageName = packageName;
		}

		public Double getPrice() {
			return price;
		}

		public void setPrice(Double price) {
			this.price = price;
		}

		public Double getTotalPrice() {
			return totalPrice;
		}

		public void setTotalPrice(Double totalPrice) {
			this.totalPrice = totalPrice;
		}

		public String getCoverPicUrl() {
			return coverPicUrl;
		}

		public void setCoverPicUrl(String coverPicUrl) {
			this.coverPicUrl = coverPicUrl;
		}

		public String getGoOffDate() {
			return goOffDate;
		}

		public void setGoOffDate(String goOffDate) {
			this.goOffDate = goOffDate;
		}

		public String getGoOffTime() {
			return goOffTime;
		}

		public void setGoOffTime(String goOffTime) {
			this.goOffTime = goOffTime;
		}

		public Integer getAdultNum() {
			return adultNum;
		}

		public void setAdultNum(Integer adultNum) {
			this.adultNum = adultNum;
		}

		public Integer getChildNum() {
			return childNum;
		}

		public void setChildNum(Integer childNum) {
			this.childNum = childNum;
		}

		public String getChildRule() {
			return childRule;
		}

		public void setChildRule(String childRule) {
			this.childRule = childRule;
		}

		public BuyConditionVo getBuyCondition() {
			return buyCondition;
		}

		public void setBuyCondition(BuyConditionVo buyCondition) {
			this.buyCondition = buyCondition;
		}
		
	}

	public Integer getCartAmount() {
		return cartAmount;
	}

	public void setCartAmount(Integer cartAmount) {
		this.cartAmount = cartAmount;
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

	public List<CartVo> getCartList() {
		return cartList;
	}

	public void setCartList(List<CartVo> cartList) {
		this.cartList = cartList;
	}
	
}
