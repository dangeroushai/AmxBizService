package com.amx.bizservice.model.bo;

import java.util.List;



public class RegionBo {


    /**
     * @主键.
     */
    private Integer id; 


    /**
     * @所属地区.
     */
    private Integer parentId; 

    /**
     * @地区原始名称.
     */
    private String enName; 


    /**
     * @封面图.
     */
    private String coverPic; 

    /**
     * @地区名称（中文）.
     */
    private String name;
    
    
    private List<RegionBo> subRegionList;
    
    
    
    @Override
    public boolean equals(Object anotherObj) {
    	if(this == anotherObj ){
    		return true;
    	}
    	
    	if(anotherObj instanceof RegionBo){
    		RegionBo  anotherRegion = (RegionBo)anotherObj;
    		return this.id.equals(anotherRegion.getId());
    	}
    	
    	return false;
    }
    
    @Override
    public int hashCode() {
    	
    	return this.id;
//    	return super.hashCode();
    }
    
	public List<RegionBo> getSubRegionList() {
		return subRegionList;
	}

	public void setSubRegionList(List<RegionBo> subRegionList) {
		this.subRegionList = subRegionList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getCoverPic() {
		return coverPic;
	}

	public void setCoverPic(String coverPic) {
		this.coverPic = coverPic;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	} 
    
}
