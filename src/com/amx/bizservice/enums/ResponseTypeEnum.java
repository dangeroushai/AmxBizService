package com.amx.bizservice.enums;

/**
 * 服务器响应状态
 * @author DangerousHai
 *
 */
public enum ResponseTypeEnum {
	OK(true,200,"ok"),
	NOT_FOUND(false,404,"not found"),ACCESS_FORBIDDEN(false,403,"access forbidden"),UNAUTHORIZED(false,401,"unauthorized"),BAD_REQUEST(false,400,"bad request"),
	PARAMETER_ERROR(false,501,"parameter error"),SERVER_ERROR(false,500,"server error");
	
	private Response response;
	
	private ResponseTypeEnum(Boolean status, Integer code, String msg){
		this.response = new Response(status,code,msg);
	}	
	
	/**
	 * 返回数据时必须使用该方法新建响应对象，不能直接使用枚举值OK（枚举对象是静态的）
	 * @return
	 */
	public static Response getNewOkResponse(){
		ResponseTypeEnum okResponse = ResponseTypeEnum.OK;
		return okResponse.new Response(okResponse.getStatus(),okResponse.getCode(),okResponse.getMsg());
	}
	
	public Boolean getStatus() {
		return this.response.getStatus();
	}
	public String getMsg() {
		return this.response.getMsg();
	}
	public Integer getCode() {
		return this.response.getCode();
	}
	public Object getData() {
		return this.response.getData();
	}
	
	public class Response{
		/**
		 * 响应状态
		 */
		private Boolean status;
		/**
		 * 响应消息
		 */
		private String msg;
		/**
		 * 响应状态码
		 */
		private Integer code;
		/**
		 * 响应数据(默认为状态码)，当响应状态为200时需修改为实际返回数据
		 */
		private Object data;
		
		private Response(Boolean status, Integer code, String msg) {
			this.status = status;
			this.data = this.code = code;
			this.msg = msg;
		}

		public Boolean getStatus() {
			return status;
		}
		public void setStatus(Boolean status) {
			this.status = status;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		public Integer getCode() {
			return code;
		}
		public void setCode(Integer code) {
			this.code = code;
		}
		public Object getData() {
			return data;
		}
		public void setData(Object data) {
			this.data = data;
		}
	}
}

