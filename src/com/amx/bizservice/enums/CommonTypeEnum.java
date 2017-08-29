package com.amx.bizservice.enums;

/**
 * 
 * @author DangerousHai
 *
 */
public enum CommonTypeEnum {
	
	TRUE("是",1),FALSE("否",0);
	
	private String name;
	private Integer id;
	
	private CommonTypeEnum(String name, Integer id){
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
