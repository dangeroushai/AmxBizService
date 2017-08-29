package com.amx.bizservice.enums;

/**
 * 产品付款类型
 * @author DangerousHai
 *
 */
public enum PayTypeEnum {
	
	PAY_FULL("付全款",1),PAY_PART("付部分",2);
	
	private String name;
	private Integer id;
	
	private PayTypeEnum(String name, Integer id){
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
