package com.amx.bizservice.enums;


/**
 * 产品类型
 * @author DangerousHai
 *
 */
public enum GenderTypeEnum {
	
	MAN("男",1),WOMAN("女",2),UNKONW("保密",3);
	
	private String name;
	private Integer id;
	
	private GenderTypeEnum(String name, Integer id){
		this.name = name;
		this.id = id;
	}
	
	public static GenderTypeEnum getGenderByWeiBo(String gender){
		if("m".equalsIgnoreCase(gender)){
			return MAN;
		}
		if("f".equalsIgnoreCase(gender)){
			return WOMAN;
		}
		return UNKONW;
	}
	

	public static GenderTypeEnum getGenderByQQ(String gender) {
		if("男".equalsIgnoreCase(gender)){
			return MAN;
		}
		if("女".equalsIgnoreCase(gender)){
			return WOMAN;
		}
		return UNKONW;
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
