package com.amx.bizservice.enums;

/**
 * 订单状态
 * @author DangerousHai
 *
 */
public enum OrderStateEnum {
	
	WAIT_COMFIRMATION("待确定",1),WAIT_PAY("待支付",2),WAIT_USE("待出行",3),WAIT_COMMENT("待评价",4),OK("已完成",5),CANCELED("已取消",6);
	
	private String name;
	private Integer id;
	
	private OrderStateEnum(String name, Integer id){
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
	
	/**
	 * 获取订单状态描述
	 * @param stateId 状态ID
	 * @return
	 */
	public static String getOrderStatusName(int stateId){
		if(WAIT_COMFIRMATION.getId().equals(stateId)){
			return WAIT_COMFIRMATION.getName(); 
		}
		if(WAIT_PAY.getId().equals(stateId)){
			return WAIT_PAY.getName(); 
		}
		if(WAIT_USE.getId().equals(stateId)){
			return WAIT_USE.getName(); 
		}
		if(WAIT_COMMENT.getId().equals(stateId)){
			return WAIT_COMMENT.getName(); 
		}
		if(OK.getId().equals(stateId)){
			return OK.getName(); 
		}
		if(CANCELED.getId().equals(stateId)){
			return CANCELED.getName(); 
		}
		
		return "无效订单";
	}
}
