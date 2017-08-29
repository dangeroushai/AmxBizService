package com.amx.bizservice.model.vo.wap;

import java.util.ArrayList;
import java.util.List;

import com.amx.bizservice.model.bo.AdvertisementBo;
import com.amx.bizservice.model.bo.ArticleBo;
import com.amx.bizservice.model.bo.ProductBo;
import com.amx.bizservice.model.vo.web.BannerVo;


public class HomePageVo {
    private List<BannerVo> bannerList;
    private List<DestVo> destList;
    private List<DestRecVo> destRecList;
    private List<LineRecVo> lineRecList;
    private List<HourRecVo> hourRecList;
    private List<ArticleRecVo> articlesRecList;
    
	public HomePageVo(List<AdvertisementBo> bannerList,
			List<ProductBo> bannerRealList,
			List<AdvertisementBo> hotDestinations, List<AdvertisementBo> destRecList,
			List<ProductBo> hourRecList, List<ProductBo> lineRecList,List<ArticleBo> articleRecList) {
		if(bannerList != null){
			this.bannerList = new ArrayList<BannerVo>();
			
			for (int i = 0; i < bannerList.size(); i++) {
				ProductBo productSnap = (bannerRealList != null && bannerRealList.size() > i && bannerRealList.get(i) != null) ? bannerRealList.get(i) : null; 
				this.bannerList.add(new BannerVo(bannerList.get(i),productSnap));
			}
		}
		if(hotDestinations != null){
			this.destList = new ArrayList<HomePageVo.DestVo>();
			for(AdvertisementBo destBo : hotDestinations){
				this.destList.add(new DestVo(destBo));
			}
		}
		if(destRecList != null){
			this.destRecList = new ArrayList<HomePageVo.DestRecVo>();
			for(AdvertisementBo destRecBo : destRecList){
				this.destRecList.add(new DestRecVo(destRecBo));
			}
		}
		if(hourRecList != null){
			this.hourRecList = new ArrayList<HomePageVo.HourRecVo>();
			for(ProductBo prodBo : hourRecList){
				this.hourRecList.add(new HourRecVo(prodBo));
			}
		}
		if(lineRecList != null){
			this.lineRecList = new ArrayList<HomePageVo.LineRecVo>();
			for(ProductBo prodBo : lineRecList){
				this.lineRecList.add(new LineRecVo(prodBo));
			}
		}
		if(articleRecList != null){
			this.articlesRecList = new ArrayList<HomePageVo.ArticleRecVo>();
			for(ArticleBo artBo : articleRecList){
				this.articlesRecList.add(new ArticleRecVo(artBo));
			}
		}
	}
    
    class DestVo{
    	private Integer id;
    	private String name;
    	private String iconUrl;
		public DestVo(AdvertisementBo destBo) {
			if(destBo != null){
				this.id = destBo.getLinkRegionId();
				this.name = destBo.getTitle();
				this.iconUrl = destBo.getCoverPic();
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
		public String getIconUrl() {
			return iconUrl;
		}
		public void setIconUrl(String iconUrl) {
			this.iconUrl = iconUrl;
		}
    }
    class DestRecVo{
    	private Integer id;
    	private String name;
    	private String coverPicUrl;
    	
		public DestRecVo(AdvertisementBo destRecBo) {
			if(destRecBo != null){
				this.id = destRecBo.getLinkRegionId(); 
				this.name = destRecBo.getTitle(); 
				this.coverPicUrl = destRecBo.getCoverPic(); 
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
    }
    class LineRecVo{
    	private Long id;
    	private Integer typeId;
    	private String name;
    	private String subTitle;
    	private Double price;
    	private String coverPicUrl;
		public LineRecVo(ProductBo prodBo) {
			if(prodBo != null){
				this.id = prodBo.getId();
				this.typeId = prodBo.getTypeId();
				this.name = prodBo.getName();
				this.subTitle = prodBo.getSubTitle();
				this.price = prodBo.getPrice();
				this.coverPicUrl = prodBo.getCoverPic();
			}
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
		public String getSubTitle() {
			return subTitle;
		}
		public void setSubTitle(String subTitle) {
			this.subTitle = subTitle;
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
    }
    class HourRecVo{
    	private Long id;
    	private Integer typeId;
    	private String name;
    	private String destination;
    	private Double price;
    	private String coverPicUrl;
		public HourRecVo(ProductBo prodBo) {
			if(prodBo != null){
				this.id = prodBo.getId();
				this.typeId = prodBo.getTypeId();
				this.name = prodBo.getName();
				//FIXME - 目的地
				this.destination = prodBo.getStartCity();
				this.price = prodBo.getPrice();
				this.coverPicUrl = prodBo.getCoverPic();
			}
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
		public String getDestination() {
			return destination;
		}
		public void setDestination(String destination) {
			this.destination = destination;
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
    }
    class ArticleRecVo{
    	private Integer id;
    	private String title;
    	private String subTitle;
    	private String intro;
    	private String coverPicUrl;
		public ArticleRecVo(ArticleBo artBo) {
			if(artBo != null){
				this.id = artBo.getId();	
				this.title = artBo.getName();
				this.subTitle = artBo.getSubTitle();
				this.intro = artBo.getIntroduce();
				this.coverPicUrl = artBo.getCoverPic();
			}
		}
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getSubTitle() {
			return subTitle;
		}
		public void setSubTitle(String subTitle) {
			this.subTitle = subTitle;
		}
		public String getIntro() {
			return intro;
		}
		public void setIntro(String intro) {
			this.intro = intro;
		}
		public String getCoverPicUrl() {
			return coverPicUrl;
		}
		public void setCoverPicUrl(String coverPicUrl) {
			this.coverPicUrl = coverPicUrl;
		}
    }
    
	public List<BannerVo> getBannerList() {
		return bannerList;
	}
	public void setBannerList(List<BannerVo> bannerList) {
		this.bannerList = bannerList;
	}
	public List<DestVo> getDestList() {
		return destList;
	}
	public void setDestList(List<DestVo> destList) {
		this.destList = destList;
	}
	public List<DestRecVo> getDestRecList() {
		return destRecList;
	}
	public void setDestRecList(List<DestRecVo> destRecList) {
		this.destRecList = destRecList;
	}
	public List<LineRecVo> getLineRecList() {
		return lineRecList;
	}
	public void setLineRecList(List<LineRecVo> lineRecList) {
		this.lineRecList = lineRecList;
	}
	public List<HourRecVo> getHourRecList() {
		return hourRecList;
	}
	public void setHourRecList(List<HourRecVo> hourRecList) {
		this.hourRecList = hourRecList;
	}
	public List<ArticleRecVo> getArticlesRecList() {
		return articlesRecList;
	}
	public void setArticlesRecList(List<ArticleRecVo> articlesRecList) {
		this.articlesRecList = articlesRecList;
	}
    
}
