package com.amx.bizservice.util;

import java.sql.Time;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtil {
	
	public final static SimpleDateFormat sdf_yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");
	public final static SimpleDateFormat sdf_yyMMddhhmmss = new SimpleDateFormat("yyyyMMddHHmmss");
	public final static SimpleDateFormat sdf_yy_MM_ddhhmmssSSS = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss.SSS");
	public final static SimpleDateFormat sdf_HHmm = new SimpleDateFormat("HH:mm");
	
	public final static NumberFormat numberFormat = NumberFormat.getNumberInstance();  
	  
	  
	public static String getFormatNumber(Object value){
		//禁用分组
		numberFormat.setGroupingUsed(false);
		//最大小数精度
		numberFormat.setMaximumFractionDigits(2);  
		numberFormat.setMinimumIntegerDigits(8);
		numberFormat.setMinimumIntegerDigits(1);
		  
		return numberFormat.format(value);
	}
	
	public static String getSdfYMDHMSSDate(Date date){
		if(date != null){
			return sdf_yy_MM_ddhhmmssSSS.format(date);
		}
		return null;
	}
	
	/**
	 * 将日期转为字符串
	 * @param Str
	 * @return
	 */
	public static String getSdfDate(Date date){
		if(date != null){
			return sdf_yyyy_MM_dd.format(date);
		}
		return null;
	}
	
	public static Date getSdfDate(String date){
		
		try {
			return sdf_yyyy_MM_dd.parse(date);
		} catch (ParseException e) {
//			throw new RuntimeException(e);
		}
		return null;
	}
	
	public static String getHMTime(Time time){
		if(time != null){
			return sdf_HHmm.format(time);
		}
		return null;
	}
	
	public static Time getHMTime(String time){
		
		try {
			return new Time(sdf_HHmm.parse(time).getTime());
		} catch (ParseException e) {
//			throw new RuntimeException(e);
		}
		return null;
	}

	public static String getYMDHMSDate(Date date) {
		return sdf_yyMMddhhmmss.format(date);
	}

	public static boolean isEmpty(String str) {
		if(str != null && !"".equals(str.trim())){
			return false;
		}
		
		return true;
	}

	/**
	 * 对字符串进行部分模糊化处理
	 * @param src
	 * @return
	 */
	public static String fuzzy(String src) {
		char[] charArray = src.toCharArray();
		int fuzzyStartIndex = charArray.length / 3 <= 0 ? 1 : charArray.length / 3; 
		int fuzzyEndIndex = charArray.length - fuzzyStartIndex; 
		if(charArray.length < 3){
			fuzzyStartIndex = 1;
			fuzzyEndIndex = charArray.length;
		}
		for (int i = 0; i < charArray.length; i++) {
			if(i >= fuzzyStartIndex && i< fuzzyEndIndex){
				charArray[i] = '*';
			}
		}
		return new String(charArray);
	}
}
