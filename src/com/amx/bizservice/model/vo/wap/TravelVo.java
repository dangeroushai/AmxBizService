package com.amx.bizservice.model.vo.wap;

import java.util.List;


/**
 * 
 * @author DangerousHai
 *
 */
public class TravelVo {

	/**
     * @主键.
     */
	private Long id;
	
	/**
	 * @名称.
	 */
	private String name;
	
	private Integer dayNum;
	
	private String updateTime;
	
    private List<HodometerVo> hodometer;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDayNum() {
		return dayNum;
	}

	public void setDayNum(Integer dayNum) {
		this.dayNum = dayNum;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public List<HodometerVo> getHodometer() {
		return hodometer;
	}

	public void setHodometer(List<HodometerVo> hodometer) {
		this.hodometer = hodometer;
	}
}
