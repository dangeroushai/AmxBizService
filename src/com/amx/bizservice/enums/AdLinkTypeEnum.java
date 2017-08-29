package com.amx.bizservice.enums;

/**
 * 广告链接类型
 * @author DangerousHai
 *
 */
public enum AdLinkTypeEnum {
	
	NULL("无",0),URL("url",1),PRODUCT("产品",2),ARTICLE("文章",3),REGION("地区",4),THEME("主题",5),SCENE("场景",6);
	
	private String name;
	private Integer id;
	
	private AdLinkTypeEnum(String name, Integer id){
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
