package com.amx.bizservice.model.vo.input;

import java.sql.Time;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.amx.bizservice.mapper.deserializer.TimeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * 
 * @author DangerousHai
 *
 */

public class CartVo{
	
	private Long id; 

   /**
    * @套餐ID.
    */
   private Long packageId; 

   /**
    * @出发日期.
    */
   @DateTimeFormat(pattern = "yyyy-MM-dd")//页面提交时的日期格式
   private Date goOffDate; 

   /**
    * @成人数.
    */
   private Integer adultNum; 

   /**
    * @儿童数.
    */
   private Integer childNum; 

   /**
    * @语言ID.
    */
   private Integer languageId; 

   /**
    * @产品TYPE_ID.
    */
   private Integer productTypeId; 
   /**
    * @产品ID.
    */
   private Long productId; 
   /**
    * @产品ID.
    */
   private Long travelId; 


   /**
    * @出发时间.
    */
   //XXX - 反序列化Time类型
   @JsonDeserialize(using = TimeDeserializer.class)
   private Time goOffTime;


public Long getId() {
	return id;
}


public void setId(Long id) {
	this.id = id;
}


public Long getPackageId() {
	return packageId;
}


public void setPackageId(Long packageId) {
	this.packageId = packageId;
}


public Date getGoOffDate() {
	return goOffDate;
}


public void setGoOffDate(Date goOffDate) {
	this.goOffDate = goOffDate;
}


public Integer getAdultNum() {
	return adultNum;
}


public void setAdultNum(Integer adultNum) {
	this.adultNum = adultNum;
}


public Integer getChildNum() {
	return childNum;
}


public void setChildNum(Integer childNum) {
	this.childNum = childNum;
}


public Integer getLanguageId() {
	return languageId;
}


public void setLanguageId(Integer languageId) {
	this.languageId = languageId;
}


public Integer getProductTypeId() {
	return productTypeId;
}


public void setProductTypeId(Integer productTypeId) {
	this.productTypeId = productTypeId;
}


public Long getProductId() {
	return productId;
}


public void setProductId(Long productId) {
	this.productId = productId;
}


public Long getTravelId() {
	return travelId;
}


public void setTravelId(Long travelId) {
	this.travelId = travelId;
}


public Time getGoOffTime() {
	return goOffTime;
}


public void setGoOffTime(Time goOffTime) {
	this.goOffTime = goOffTime;
}


	
}
