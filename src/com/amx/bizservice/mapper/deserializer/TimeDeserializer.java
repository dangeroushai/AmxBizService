package com.amx.bizservice.mapper.deserializer;

import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;

import com.amx.bizservice.util.StringUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * 提交json数据时使用该类反序列化Time
 * @author DangerousHai
 *
 */
public class TimeDeserializer extends JsonDeserializer<Time> {

	@Override
	public Time deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		try {
			if("全天".equals(p.getText().trim())){
				return new Time(StringUtil.sdf_HHmm.parse("0:00").getTime());
			}
			return new Time(StringUtil.sdf_HHmm.parse(p.getText()).getTime());
		} catch (ParseException e) {
//			throw new RuntimeException(e);
		}
		return null;
	}
}
