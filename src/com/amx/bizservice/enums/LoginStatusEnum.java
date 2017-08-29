package com.amx.bizservice.enums;

/**
 * 
 * @author DangerousHai
 *
 */
public enum LoginStatusEnum {
	
	OFFLINE("离线",0),ONLINE("在线",1);
	
	private String name;
	private Integer id;
	
	private LoginStatusEnum(String name, Integer id){
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
