package com.amx.bizservice.enums;

/**
 * 
 * @author DangerousHai
 *
 */
public enum RoleTypeEnum {
	
	PERSON("个人",4),	FIRM("企业",3),ADMIN("普管",2),SUPER_ADMIN("超管",1);
	
	private String name;
	private Integer id;
	
	private RoleTypeEnum(String name, Integer id){
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
