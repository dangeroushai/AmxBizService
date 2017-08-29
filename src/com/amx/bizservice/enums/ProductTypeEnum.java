package com.amx.bizservice.enums;

/**
 * 产品类型
 * @author DangerousHai
 *
 */
public enum ProductTypeEnum {
	
	FREE("自由行",2),HOUR("小时游",3),TRAVEL("线路游",4);
	
	private String name;
	private Integer id;
	
	private ProductTypeEnum(String name, Integer id){
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
