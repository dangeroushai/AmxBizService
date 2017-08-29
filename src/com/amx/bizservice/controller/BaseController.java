/*
 * 文件名：BaseController.java
 * 版权：Copyright 2007-2016 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： BaseController.java
 * 修改人：hanrui
 * 修改时间：2016年4月7日
 * 修改内容：新增
 */
package com.amx.bizservice.controller;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.amx.bizservice.mapper.editor.CustomDateEditor;
import com.amx.bizservice.mapper.editor.CustomTimeEditor;
import com.amx.bizservice.util.StringUtil;

public class BaseController {
	protected Logger logger;
	
	public BaseController() {
		this.logger = LoggerFactory.getLogger(this.getClass());
	}
	
	@ModelAttribute
	public void preHandler(){
		
	}
	
	/** 
     * form表单提交 Time类型数据绑定 (作用于当前控制器)
     * 
     * @param binder 
     * @see [类、类#方法、类#成员] 
     */  
	@InitBinder    
	public void initBinder(WebDataBinder binder) {    
        SimpleDateFormat timeFormat = StringUtil.sdf_HHmm;   
        SimpleDateFormat dateFormat = StringUtil.sdf_yyyy_MM_dd;   
        //注册
        binder.registerCustomEditor(Time.class, new CustomTimeEditor(timeFormat, true));    
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));    
	}  
}
