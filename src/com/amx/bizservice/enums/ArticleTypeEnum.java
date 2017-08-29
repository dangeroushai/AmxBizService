package com.amx.bizservice.enums;

import java.util.List;

/**
 * 文章属性
 * @author DangerousHai
 *
 */
public enum ArticleTypeEnum {
	//NOTE : attrid依赖数据库
	CUSTOM_TEHME("主题定制",1,302),TRAVEL_NOTE("漫行纪",2,301),ABOUT_US("关于爱漫行",3,305),WEB_POLICY("网站条款",4,304),HELP_CENTER("帮助中心",5,303);
	
	private String name;
	//文章类型
	private Integer typeId;
	//文章类型-对应数据库中的attribute.id
	private Integer attrId;
	
	private ArticleTypeEnum(String name, Integer typeId, Integer attrId){
		this.name = name;
		this.typeId = typeId;
		this.attrId = attrId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Integer getAttrId() {
		return attrId;
	}

	public void setAttrId(Integer attrId) {
		this.attrId = attrId;
	}
	
	public static int getAttrIdByTypeId(int typeId){
		int attrId = 0;
		ArticleTypeEnum[] values = ArticleTypeEnum.values();
		for (int i = 0; i < values.length; i++) {
			if(values[i].getTypeId() == typeId){
				attrId = values[i].getAttrId();
				break;
			}
		}
		return attrId;
	}

	public static Integer getTypeIdByAttrIdList(List<Integer> attrIdList) {
		int typeId = 0;
		
		if(attrIdList != null && attrIdList.size() != 0){
			if(attrIdList.contains(CUSTOM_TEHME.getAttrId())){
				return CUSTOM_TEHME.getTypeId();
			}
			
			ArticleTypeEnum[] values = ArticleTypeEnum.values();
			for (int i = 0; i < values.length; i++) {
				if(attrIdList.contains(values[i].getAttrId())){
					typeId = values[i].getTypeId();
					break;
				}
			}
		}
		
		return typeId;
	}
	
}
