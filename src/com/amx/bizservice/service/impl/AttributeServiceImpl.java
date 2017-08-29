package com.amx.bizservice.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amx.bizservice.enums.AttributeTypeEnum;
import com.amx.bizservice.model.bo.AttributeBo;
import com.amx.bizservice.service.AttributeService;
import com.amx.bizservice.thrift.Response;
import com.amx.bizservice.util.JsonUtil;
import com.amx.bizservice.util.LogUtil;
import com.amx.bizservice.util.PictureUtil;
import com.fasterxml.jackson.databind.JavaType;

@Service("attributeService")
public class AttributeServiceImpl extends BaseService implements AttributeService{

	public AttributeServiceImpl(){
		this.ServiceName = "Attribute";
	}
	
	@Override
	public List<AttributeBo> findAllByIdList(List<Integer> idList) {
		Response response = thriftClient.serviceInvoke(this.ServiceName, "findAllByIdList", idList);
		List<AttributeBo> boList = null; 
		try {
			if(response != null){
				if(response.isState()){
					JavaType javaType = JsonUtil.getCollectionType(ArrayList.class, AttributeBo.class);		
					boList = JsonUtil.mapper.readValue(response.getData(),javaType);
					for (AttributeBo attributeBo : boList) {
						this.inflation(attributeBo);
					}
				}else{
					LogUtil.recordWarnLog(response.getMsg());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return boList;
	}

	@Override
	public List<AttributeBo> findAllThemeByIdList(List<Integer> idList) {
		List<AttributeBo> attrList = findAllByIdList(idList);
		/*移除非主题属性*/
		Iterator<AttributeBo> iterator = attrList.iterator();
		while(iterator.hasNext()){
			AttributeBo bo = iterator.next();
			if(bo.getParentId() == AttributeTypeEnum.TOP_LEVEL.getId() || bo.getTypeId() != AttributeTypeEnum.THEME.getId()){
				iterator.remove();
			}
		}
		
		return attrList;
	}

	@Override
	public AttributeBo findOne(Integer id) {
		Response response = thriftClient.serviceInvoke(this.ServiceName, "findOne", id);
		AttributeBo bo = null;
		try {
			if (response != null) {
				if (response.isState()) {
					bo = this.inflation(JsonUtil.mapper.readValue(response.getData(),AttributeBo.class));
				} else {
					LogUtil.recordWarnLog(response.getMsg());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return bo;
	}

	@Override
	public List<AttributeBo> findAllByTypeId(Integer typeId) {
		Response response = thriftClient.serviceInvoke(this.ServiceName, "findAllByTypeId", typeId);
		List<AttributeBo> boList = null; 
		try {
			if(response != null){
				if(response.isState()){
					JavaType javaType = JsonUtil.getCollectionType(ArrayList.class, AttributeBo.class);		
					boList = JsonUtil.mapper.readValue(response.getData(),javaType);
					
					Iterator<AttributeBo> iterator = boList.iterator();
					while (iterator.hasNext()) {
						//移除顶级属性
						AttributeBo next = iterator.next();
						if(next.getParentId().equals(AttributeTypeEnum.TOP_LEVEL.getId())){
							iterator.remove();
						}
					}
				}else{
					LogUtil.recordWarnLog(response.getMsg());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return boList;
	}

	private AttributeBo inflation(AttributeBo attributeBo) {
		attributeBo.setCoverPic(PictureUtil.getPicUrl(attributeBo.getCoverPic()));
		
		return attributeBo;
	}
}
