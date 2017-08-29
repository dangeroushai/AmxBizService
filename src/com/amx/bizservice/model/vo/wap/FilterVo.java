package com.amx.bizservice.model.vo.wap;

import java.util.ArrayList;
import java.util.List;

import com.amx.bizservice.model.bo.AttributeBo;
import com.amx.bizservice.model.bo.RegionBo;


public class FilterVo {
	
	private DestinationVo destination;
	private List<AttributeVo> sceneList;
	private List<AttributeVo> themeList;

	
	public class AttributeVo{
		/**
	     * @主键.
	     */
	    private Integer id; 


	    /**
	     * @名称.
	     */
	    private String name;
	    
	    /**
	     * @封面图.
	     */
	    private String coverPicUrl;

		public AttributeVo(AttributeBo attributeBo) {
			this.id = attributeBo.getId();
			this.name = attributeBo.getName();
			this.coverPicUrl = attributeBo.getCoverPic();
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


	public FilterVo(RegionBo destBo) {
		this.destination = new DestinationVo(destBo);
		this.sceneList = new ArrayList<FilterVo.AttributeVo>();
		this.themeList = new ArrayList<FilterVo.AttributeVo>();
	}

	public DestinationVo getDestination() {
		return destination;
	}

	public void setDestination(DestinationVo destination) {
		this.destination = destination;
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
}
