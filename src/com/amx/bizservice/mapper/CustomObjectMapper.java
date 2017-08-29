package com.amx.bizservice.mapper;

import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;

import com.amx.bizservice.util.StringUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
 
/**
 * 自定义类型转换器(处理json)
 * @author DangerousHai
 * 注意：要使用视图解析器，需去掉Controller方法上面的@ResponseBody
 */
public class CustomObjectMapper extends ObjectMapper {
     
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("deprecation")
	private static final Time anyTime = new Time(0, 0, 0);

	public CustomObjectMapper() {
        super();
        
		 //configure to ignore unknown property
//		configure(Feature.IGNORE_UNKNOWN,false);
        
//        this.setDateFormat(StringUtil.sdf_yyyy_MM_dd);
        
        final DateFormat sdf = StringUtil.sdf_HHmm; 
        SimpleModule module = new SimpleModule();
        
        //格式化时间
        module.addSerializer(Time.class, new JsonSerializer<Time>(){
			@Override
            public void serialize(Time value, JsonGenerator jsonGenerator,  SerializerProvider provider)  
                    throws IOException, JsonProcessingException {
				if(value.equals(anyTime)){
					jsonGenerator.writeString("全天");  
				}else{
					jsonGenerator.writeString(sdf.format(value));  
				}
            }
        });
        module.addDeserializer(Time.class, new JsonDeserializer<Time>(){
            @Override
            public Time deserialize(JsonParser jp, DeserializationContext ctxt)
                    throws IOException, JsonProcessingException {
                try {
                    return new Time(sdf.parse(jp.getText()).getTime());
                } catch (Exception e) {
                    return null;
                }
            }
        });
        
        //格式化数字
        module.addSerializer(Double.class, new JsonSerializer<Double>(){
        	@Override
        	public void serialize(Double value, JsonGenerator jsonGenerator,  SerializerProvider provider)  
        			throws IOException, JsonProcessingException {                
        		jsonGenerator.writeString(StringUtil.getFormatNumber(value));  
        	}
        });
        
        this.registerModule(module);
        
        //设置null转换""  
        this.getSerializerProvider().setNullValueSerializer(new NullSerializer());
    }
	
    //null的JSON序列  
    private class NullSerializer extends JsonSerializer<Object> {  
        public void serialize(Object value, JsonGenerator jgen,  
                SerializerProvider provider) throws IOException,  
                JsonProcessingException {  
            jgen.writeString("");  
        }  
    }  
}