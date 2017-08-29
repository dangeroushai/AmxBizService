package com.amx.bizservice.enums;

/**
 * 属性类型
 * @author DangerousHai
 *
 */
public enum AttributeTypeEnum {
	
	//TOP_LEVEL - 非属性类别，表示没有父级属性的顶级元素
	THEME("主题属性",3),SCENE("场景属性",4),TOP_LEVEL("顶级属性",0);
	
	private String name;
	private Integer id;
	
	private AttributeTypeEnum(String name, Integer id){
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
