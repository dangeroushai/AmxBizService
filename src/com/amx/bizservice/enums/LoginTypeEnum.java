package com.amx.bizservice.enums;

/**
 * 
 * @author DangerousHai
 *
 */
public enum LoginTypeEnum {
	
	OFFICIAL("官方",0),QQ("QQ",1),WEIBO("微博",2);
	
	private String name;
	private Integer id;
	
	private LoginTypeEnum(String name, Integer id){
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
