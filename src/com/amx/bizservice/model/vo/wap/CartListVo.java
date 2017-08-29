package com.amx.bizservice.model.vo.wap;

import java.util.List;

import com.amx.bizservice.model.bo.CartBo;
import com.amx.bizservice.model.bo.LanguageBo;
import com.amx.bizservice.model.bo.PackageBo;
import com.amx.bizservice.model.bo.PriceBo;
import com.amx.bizservice.model.bo.ProductBo;
import com.amx.bizservice.model.dto.PageResponseDto;
import com.amx.bizservice.util.StringUtil;

/**
 * @author DangerousHai
 *
 */
public class CartListVo {
    private Integer amount;
    private Integer pageIndex;
    private Integer pageAmount;
    private List<CartVo> cartList;

	public CartListVo(PageResponseDto<CartBo> dto) {
		if(dto != null){
			this.amount = dto.getAmount().intValue();
			this.pageAmount = dto.getPageAmount();
			this.pageIndex = dto.getPageIndex();
		}
	}
	
	public class CartVo{
		/**
	     * @主键.
	     */
		private Long id;

		private Integer languageId;
		private String languageName;
		
		private Long packageId;
		private String packageName;
		
		private String goOffDate; 
		private String goOffTime; 
		private Integer adultNum; 
		private Integer childNum; 
		/**
		 * 总价（优惠前价格） 
		 */
	    private Double price; 
	    private Double childPrice;
	    private Double adultPrice;
	    private Integer payType;
	    private Double prePayPrice;
	    private Double obligation;
	    private String currencyType;

	    private ProductVo product;
	    

		public CartVo(CartBo cbo, ProductBo prodbo, PackageBo packbo,
				LanguageBo lbo, PriceBo pricebo) {
			if(cbo != null){
				this.id = cbo.getId();
				this.languageId = cbo.getLanguageId();
				this.packageId = cbo.getPackageId();
				this.goOffDate = StringUtil.getSdfDate(cbo.getGoOffDate());
				this.goOffTime = StringUtil.getHMTime(cbo.getGoOffTime());
				if("00:00".equals(this.goOffTime)){
					this.goOffTime = "全天";
				}
				this.adultNum = cbo.getAdultNum();
				this.childNum = cbo.getChildNum();
			}
			
			if(packbo != null){
				this.packageName = packbo.getName();
			}
			
			if(lbo != null){
				this.languageName = lbo.getName();
			}
			
			if(pricebo != null){
				this.price = pricebo.getPrice().doubleValue();
				this.childPrice = pricebo.getChildPrice().doubleValue();
				this.adultPrice = pricebo.getAdultPrice().doubleValue();
				this.payType = pricebo.getPayType();
				if(pricebo.getPrePayPrice() != null){
					this.prePayPrice = pricebo.getPrePayPrice().doubleValue();
				}
				if(pricebo.getObligation() != null){
					this.obligation = pricebo.getObligation().doubleValue();
				}
				this.currencyType = pricebo.getCurrencyType();
			}
			 
			if(prodbo != null){
				this.product = new ProductVo(prodbo);
			}
		}
	    
	    class ProductVo{
	    	private Long id; 
	    	private Integer typeId; 
	    	private String name; 
		    private String coverPicUrl;
			public ProductVo(ProductBo prodbo) {
				this.id = prodbo.getId();
				this.name = prodbo.getName();
				this.typeId = prodbo.getTypeId();
				this.coverPicUrl = prodbo.getCoverPic();
			}
			public Long getId() {
				return id;
			}
			public void setId(Long id) {
				this.id = id;
			}
			public Integer getTypeId() {
				return typeId;
			}
			public void setTypeId(Integer typeId) {
				this.typeId = typeId;
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
	    }

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
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

		public Double getPrice() {
			return price;
		}

		public void setPrice(Double price) {
			this.price = price;
		}

		public Double getChildPrice() {
			return childPrice;
		}

		public void setChildPrice(Double childPrice) {
			this.childPrice = childPrice;
		}

		public Double getAdultPrice() {
			return adultPrice;
		}

		public void setAdultPrice(Double adultPrice) {
			this.adultPrice = adultPrice;
		}

		public Integer getPayType() {
			return payType;
		}

		public void setPayType(Integer payType) {
			this.payType = payType;
		}

		public Double getPrePayPrice() {
			return prePayPrice;
		}

		public void setPrePayPrice(Double prePayPrice) {
			this.prePayPrice = prePayPrice;
		}

		public Double getObligation() {
			return obligation;
		}

		public void setObligation(Double obligation) {
			this.obligation = obligation;
		}

		public String getCurrencyType() {
			return currencyType;
		}

		public void setCurrencyType(String currencyType) {
			this.currencyType = currencyType;
		}

		public ProductVo getProduct() {
			return product;
		}

		public void setProduct(ProductVo product) {
			this.product = product;
		}
	}
	
	public Integer getArticleAmount() {
		return amount;
	}

	public void setArticleAmount(Integer articleAmount) {
		this.amount = articleAmount;
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

	public List<CartVo> getCartList() {
		return cartList;
	}

	public void setCartList(List<CartVo> cartList) {
		this.cartList = cartList;
	}
}
