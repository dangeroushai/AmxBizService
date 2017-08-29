package com.amx.bizservice.enums;

/**
 * 执行数据库更新的状态码
 * @author DangerousHai
 *
 */
public enum UpdateCodeEnum {
	
	SUCCESS(200),EXIST(400),FAIL(500);
	
	private Integer code;
	
	private UpdateCodeEnum(Integer id){
		this.code = id;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer id) {
		this.code = id;
	}
}
