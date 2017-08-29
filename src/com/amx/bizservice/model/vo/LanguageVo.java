package com.amx.bizservice.model.vo;

import com.amx.bizservice.model.bo.LanguageBo;


public class LanguageVo {
	
	/**
     * @主键.
     */
    private int id; 


    /**
     * @语言名称（中文）.
     */
    private String name;
    
    public LanguageVo(LanguageBo bo){
    	if(bo != null){
	    	this.id = bo.getId();
	    	this.name = bo.getName();
    	}
    }


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	} 

}
