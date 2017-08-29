package com.amx.bizservice.mapper.editor;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import org.springframework.util.StringUtils;

/**
 * 自定义的日期（java.util.date）属性绑定类
 * @author DangerousHai
 *
 */
public class CustomDateEditor extends PropertyEditorSupport{

	/**
	 * 支持的时间格式：HH:mm/HH:mm:ss
	 */
	private final DateFormat dateFormat;

	/**
	 * 是否允许为空
	 */
	private final boolean allowEmpty;
	
	public CustomDateEditor(DateFormat dateFormat, boolean allowEmpty){
		this.dateFormat = dateFormat;
		this.allowEmpty = allowEmpty;
	}
	
	/**
	 * Parse the Time from the given text, using the specified DateFormat.
	 */
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (this.allowEmpty && !StringUtils.hasText(text)) {
			setValue(null);
		} else {
			try {
				setValue(new Date(this.dateFormat.parse(text).getTime()));
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
		Date value = (Date) getValue();
		return (value != null ? this.dateFormat.format(value) : "");
	}
}
