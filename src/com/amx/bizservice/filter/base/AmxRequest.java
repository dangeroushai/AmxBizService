package com.amx.bizservice.filter.base;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.util.StringUtils;

import com.amx.bizservice.config.CommonConfig;


/**
 * 重写包装Request对象，旨在重写前端发送过来的请求
 * 之所以定义该类，原因如下：
 * 1.前端.NET MVC网站的请求头格式有错，会导致SPringMVC 无法处理视图
 * 2.将GET提交的数据的参数名转换为小写
 * 3.防止XXS攻击/sql注入：编码js/html
 * @author DangerousHai
 *
 */
public class AmxRequest extends HttpServletRequestWrapper {
	
	public static final String HEADER_ACCEPT_NAME = "ACCEPT";  
	public static final String HEADER_ACCEPT_VALUE = "application/json;charset=utf-8";  
	
	/**
	 * 参数名为小写的查询参数串
	 */
	private String lowerNameQueryString;
	
	/**
	 * GET参数名为小写（并对敏感字符进行转义）的参数Map
	 */
	private Map<String, String[]> lowerNameParameterMap;
	
	public AmxRequest(HttpServletRequest request) {  
    	super(request);  
    	
    	this.lowerNameParameterMap = this.escapeParameter(request.getParameterMap());

    	this.lowerNameQueryString = this.convertQueryStringToLower();
    }  

	@Override  
    public String getQueryString(){
    	return this.lowerNameQueryString;
    }
    
    @Override  
    public Map<String, String[]> getParameterMap(){
    	return this.lowerNameParameterMap;
    }
    
	@Override  
    public Enumeration<String> getParameterNames() {  
        Vector<String> vector = new Vector<String>(lowerNameParameterMap.keySet());  
        return vector.elements();  
    }  
  
    @Override  
    public String[] getParameterValues(String name) {  
        Object v = lowerNameParameterMap.get(name);  
        if (v == null) {  
            return null;  
        } else if (v instanceof String[]) {  
            return (String[]) v;  
        } else if (v instanceof String) {  
            return new String[] { (String) v };  
        } else {  
            return new String[] { v.toString() };  
        }  
    }  
  
    @Override
    public String getParameter(String name) {  
        Object v = lowerNameParameterMap.get(name);  
        if (v == null) {  
            return null;  
        } else if (v instanceof String[]) {  
            String[] strArr = (String[]) v;  
            if (strArr.length > 0) {  
                return strArr[0];  
            } else {  
                return null;  
            }  
        } else if (v instanceof String) {  
            return (String) v;  
        } else {  
            return v.toString();  
        }  
    }  
    
	@Override  
    public Enumeration<String> getHeaders(String name) {  
    	//重写request header ： ACCEPT
        if (null != name && name.toUpperCase().equals(HEADER_ACCEPT_NAME)) {  
            return new Enumeration<String>() {  
                private boolean hasGetted = false;  
  
                @Override  
                public String nextElement() {  
                    if (hasGetted) {  
                        throw new NoSuchElementException();  
                    } else {  
                        hasGetted = true;  
                        return HEADER_ACCEPT_VALUE;
                    }  
                }
  
                @Override  
                public boolean hasMoreElements() {  
                    return !hasGetted;  
                }  
            };  
        }  
        return super.getHeaders(name);  
    }  
    
    @Override
    public String getHeader(String name){
    	//重写request header ： ACCEPT
    	if (null != name && name.toUpperCase().equals(HEADER_ACCEPT_NAME)) {  
    		return HEADER_ACCEPT_VALUE;
    	}
    	return super.getHeader(name);  
    }
    
    /**
     * 将查询串参数名处理为小写
     * @return
     */
    private String convertQueryStringToLower(){
    	StringBuilder sb = new StringBuilder();
    	
    	String queryString = super.getQueryString();
    	if(!StringUtils.isEmpty(queryString)){
    		String[] kvPairs = queryString.split("&");
    		if(kvPairs != null && kvPairs.length != 0){
    			for (String string : kvPairs) {
    				//将所有查询参数的查询名称转换位小写
    				String[] kvPair = string.split("=");
    				String paramName = kvPair[0];
    				String paramValue = kvPair.length > 1 ? kvPair[1] : null;
    				sb.append(paramName.toLowerCase()).append("=").append(paramValue).append("&");
    				//将parameterMap中的参数名同时转为小写
    				this.convertParamNameToLower(paramName);
				}
    			//删除最有一个&
    			if(sb.length() != 0){
    				sb.deleteCharAt(sb.length()-1);
    			}
    		}
    	}
    	
		return sb.toString();
    }
    
    /**
     * 将GET提交的参数处理为小写
     * @param paramName
     */
    private void convertParamNameToLower(String paramName) {
    	String lowerParamName = paramName.toLowerCase();
    	//参数名本身即为全小写
    	if(lowerParamName.equals(paramName)){
    		return ;
    	}
    	//获取参数值
    	String[] parameValues = this.lowerNameParameterMap.get(paramName);
    	if(parameValues != null && parameValues.length != 0){
    		//移除原来的参数
    		this.lowerNameParameterMap.remove(paramName);
    		//放入新的小写的参数名
    		this.lowerNameParameterMap.put(lowerParamName, parameValues);
    	}
	}
    
    /**
	 * 对参数转义编码
	 */
	private Map<String, String[]> escapeParameter(Map<String, String[]> oldMap) {
		Map<String, String[]> newMap  = new HashMap<String, String[]>(oldMap.size());
    	
		for (Map.Entry<String, String[]> parameter : oldMap.entrySet()) {
			String key = parameter.getKey();
			String[] values = parameter.getValue();
			
			for (int i = 0; values != null && i < values.length; i++) {
				if(CommonConfig.ESCAPE_HTML){
					values[i] = StringEscapeUtils.escapeHtml(values[i]);
				}
				if(CommonConfig.ESCAPE_JAVASCRIPT){
					values[i] = StringEscapeUtils.escapeJavaScript(values[i]);
				}
				if(CommonConfig.ESCAPE_SQL){
					values[i] = StringEscapeUtils.escapeSql(values[i]);
				}
				// 将转码后的&quot;转换为双引号（"）
				values[i] = values[i].replace("&quot;", "\"");
			}
			newMap.put(key, values);
		}
		
		return newMap;
	}
} 
