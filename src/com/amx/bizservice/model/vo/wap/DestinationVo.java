package com.amx.bizservice.model.vo.wap;

import com.amx.bizservice.model.bo.RegionBo;


public class DestinationVo {
	
	private Integer id;
    private String name;
    private String coverPicUrl;

	public DestinationVo(RegionBo countryRegionBo) {
		if(countryRegionBo != null){
			this.id = countryRegionBo.getId();
			this.name = countryRegionBo.getName();
			this.coverPicUrl = countryRegionBo.getCoverPic();
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
