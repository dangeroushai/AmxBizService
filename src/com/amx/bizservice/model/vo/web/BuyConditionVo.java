package com.amx.bizservice.model.vo.web;

import java.util.ArrayList;
import java.util.List;

import com.amx.bizservice.model.bo.LanguageBo;
import com.amx.bizservice.model.bo.PackageBo;
import com.amx.bizservice.model.bo.ProductBo;
import com.amx.bizservice.model.vo.LanguageVo;


public class BuyConditionVo {
	
    private List<Integer> saleRule;
    
    private List<String> exceptionDate;
    
    private String baseDate;
    
    private List<String> timeRule;
 
    private Integer maxAdultNum;
    private Integer maxChildNum;
    private Integer maxPersonNum;
    private Integer minPersonNum;

    private List<LanguageVo> languages;

	public BuyConditionVo(ProductBo prodbo , PackageBo packbo) {
		if(prodbo != null){
			this.saleRule = prodbo.getSaleRuleList();
			this.exceptionDate = prodbo.getExceptionDateList();
			this.baseDate = prodbo.getBaseDate();
			this.timeRule = prodbo.getTimeRuleList();
			if(prodbo.getLanguageList() != null){
				this.languages = new ArrayList<LanguageVo>(); 
				
				for(LanguageBo lbo : prodbo.getLanguageList()){
					this.languages.add(new LanguageVo(lbo));
				}
			}
		}
		if(packbo != null){
			this.maxAdultNum = packbo.getMaxAdultNum();
			this.maxChildNum = packbo.getMaxChildNum();
			this.maxPersonNum = packbo.getMaxPersonNum();
			this.minPersonNum = packbo.getMinPersonNum();
		}
	}

	public List<Integer> getSaleRule() {
		return saleRule;
	}

	public void setSaleRule(List<Integer> saleRule) {
		this.saleRule = saleRule;
	}

	public List<String> getExceptionDate() {
		return exceptionDate;
	}

	public void setExceptionDate(List<String> exceptionDate) {
		this.exceptionDate = exceptionDate;
	}

	public String getBaseDate() {
		return baseDate;
	}

	public void setBaseDate(String baseDate) {
		this.baseDate = baseDate;
	}

	public List<String> getTimeRule() {
		return timeRule;
	}

	public void setTimeRule(List<String> timeRule) {
		this.timeRule = timeRule;
	}

	public Integer getMaxAdultNum() {
		return maxAdultNum;
	}

	public void setMaxAdultNum(Integer maxAdultNum) {
		this.maxAdultNum = maxAdultNum;
	}

	public Integer getMaxChildNum() {
		return maxChildNum;
	}

	public void setMaxChildNum(Integer maxChildNum) {
		this.maxChildNum = maxChildNum;
	}

	public Integer getMaxPersonNum() {
		return maxPersonNum;
	}

	public void setMaxPersonNum(Integer maxPersonNum) {
		this.maxPersonNum = maxPersonNum;
	}

	public Integer getMinPersonNum() {
		return minPersonNum;
	}

	public void setMinPersonNum(Integer minPersonNum) {
		this.minPersonNum = minPersonNum;
	}

	public List<LanguageVo> getLanguages() {
		return languages;
	}

	public void setLanguages(List<LanguageVo> languages) {
		this.languages = languages;
	}
    
    

}
