package com.amx.bizservice.model.bo;



public class ConfigBo {
	

	/**
	 * @主键.
	 */
	private Integer id;
	
	/**
	 * @名.
	 */
	private String name;
	

    /**
     * @key
     */
    private String key; 

    /**
     * value.
     */
    private String value;

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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	} 

}
