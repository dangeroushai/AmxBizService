package com.amx.bizservice.model.bo;

import java.math.BigDecimal;

public class CurrencyBo {

    /**
     * @主键.
     */
    private Integer id; 


    /**
     * @货币单位（中文）.
     */
    private String name; 

    /**
     * @汇率.
     */
    private BigDecimal parities; 

    /**
     * @货币单位（简称）.
     */
    private String abbreviation;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getParities() {
		return parities;
	}

	public void setParities(BigDecimal parities) {
		this.parities = parities;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	} 


}
