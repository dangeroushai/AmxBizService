package com.amx.bizservice.mapper.editor;

import java.beans.PropertyEditorSupport;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;

import org.springframework.util.StringUtils;

/**
 * 自定义的时间（java.sql.Time）属性绑定类
 * @author DangerousHai
 *
 */
public class CustomTimeEditor extends PropertyEditorSupport{

	/**
	 * 支持的时间格式：HH:mm/HH:mm:ss
	 */
	private final DateFormat dateFormat;

	/**
	 * 是否允许为空
	 */
	private final boolean allowEmpty;
	
	public CustomTimeEditor(DateFormat dateFormat, boolean allowEmpty){
		this.dateFormat = dateFormat;
		this.allowEmpty = allowEmpty;
	}
	
	/**
	 * Parse the Time from the given text, using the specified DateFormat.
	 */
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (this.allowEmpty && !StringUtils.hasText(text)) {
			// Treat empty String as null value.
			setValue(null);
		} else {
			try {
				if("全天".equals(text.trim())){
					//使用0：00代表全天任意时刻
					setValue(new Time(this.dateFormat.parse("0:00").getTime()));
				}else{
					setValue(new Time(this.dateFormat.parse(text).getTime()));
				}
			}
			catch (ParseException ex) {
				setValue(null);
			}
		}
	}

	/**
	 * Format the Time as String, using the specified DateFormat.
	 */
	@Override
	public String getAsText() {
		Time value = (Time) getValue();
		return (value != null ? this.dateFormat.format(value) : "");
	}

}
