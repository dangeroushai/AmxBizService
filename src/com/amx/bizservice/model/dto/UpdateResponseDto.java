package com.amx.bizservice.model.dto;


/**
 * 封装数据更新的执行结果
 * @author DangerousHai
 *
 */
public class UpdateResponseDto {
	/**
	 * 状态码
	 */
	private Integer code;
	
	/**
	 * 消息
	 */
	private String msg;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
