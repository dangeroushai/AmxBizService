package com.amx.bizservice.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.amx.bizservice.model.bo.RegionBo;
import com.amx.bizservice.service.RegionService;
import com.amx.bizservice.thrift.Response;
import com.amx.bizservice.util.JsonUtil;
import com.amx.bizservice.util.LogUtil;
import com.amx.bizservice.util.PictureUtil;

@Service("regionService")
public class RegionServiceImpl extends BaseService implements RegionService{

	public RegionServiceImpl(){
		this.ServiceName = "Region";		
	}
	@Override
	public RegionBo getRegion(Integer id) {
		Response response = thriftClient.serviceInvoke(this.ServiceName, "findOne", id);
		RegionBo bo = null;
		try {
			if (response != null) {
				if (response.isState()) {
					bo = this.inflation(JsonUtil.mapper.readValue(response.getData(),RegionBo.class));
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
	public String getCityName(List<Integer> startCityIdList) {
		//FIXME - 可能找不到（容错处理）
		
		String thirdLevelCityName = null;
		
		List<RegionBo> boList = this.findAll(startCityIdList);
		
		
		if(boList != null && boList.size() > 0){
			
			int listSize = boList.size();
			
			if(listSize == 1){
				thirdLevelCityName = boList.get(0).getName();
			}else if(listSize == 2){
				thirdLevelCityName = boList.get(0).getParentId().equals(boList.get(1).getId()) ? boList.get(0).getName() : boList.get(1).getName(); 
			}else if(listSize == 3){
				int topLevelId = 0;
				int secondLevelId = 0;
				
				//获取第一级地域（洲）的ID
				for (RegionBo regionBo : boList) {
					if(regionBo.getParentId() == 0){
						topLevelId = regionBo.getId();
						break;
					}
				}
				
				//获取第二级地域（国家）的ID
				for (RegionBo regionBo : boList) {
					if(regionBo.getParentId() == topLevelId){
						secondLevelId = regionBo.getId();
						break;
					}
				}
				
				//获取第三级地域（城市）的名称
				for (RegionBo regionBo : boList) {
					if(regionBo.getParentId() == secondLevelId){
						thirdLevelCityName = regionBo.getName();
						break;
					}
				}
			}
		}
		
		return thirdLevelCityName;
	}
	
	
	@Override
	public List<RegionBo> findAll(Integer hierarchy) {
		List<RegionBo> regionList = null;
		List<RegionBo> allRegionList = findAll((List<Integer>)null);
		/*将扁平的地域 组织成层级关系*/
		//<id,regionBo>
		//第一级地域
		Map<Integer,RegionBo> firstRegionMap = new HashMap<Integer,RegionBo>();
		//第二级地域
		Map<Integer,RegionBo> secondRegionMap = new HashMap<Integer,RegionBo>();
		if(allRegionList != null){
			Iterator<RegionBo> iterator = allRegionList.iterator();
			if(hierarchy > 0){
				regionList = new ArrayList<RegionBo>();
				/* 处理第一级 */
				while(iterator.hasNext()){
					RegionBo regionBo = iterator.next();
					if(regionBo.getParentId().equals(0)){
						//将第一级地域加入返回列表
						regionList.add(regionBo);
						//将第一级地域加入第一级MAP
						firstRegionMap.put(regionBo.getId(), regionBo);
						//移除已经处理的地区
						iterator.remove();
					}
				}
			}
			if(hierarchy > 1){
				/* 处理第二级 */
				iterator = allRegionList.iterator();
				while(iterator.hasNext()){
					RegionBo regionBo = iterator.next();
					if(firstRegionMap.containsKey(regionBo.getParentId())){
						/*将第二级地域加入到第一级下属列表中*/
						RegionBo parentRegionBo = firstRegionMap.get(regionBo.getParentId());
						if(parentRegionBo.getSubRegionList() == null){
							parentRegionBo.setSubRegionList(new ArrayList<RegionBo>());
						}
						parentRegionBo.getSubRegionList().add(regionBo);
						//将第二级地域加入第二级MAP
						secondRegionMap.put(regionBo.getId(), regionBo);
						//移除已经处理的地区
						iterator.remove();
					}
				}
			}
			if(hierarchy > 2){
				/* 处理第三级 */
				iterator = allRegionList.iterator();
				while(iterator.hasNext()){
					RegionBo regionBo = iterator.next();
					if(secondRegionMap.containsKey(regionBo.getParentId())){
						/*将第三级地域加入到第二级下属列表中*/
						RegionBo parentRegionBo = secondRegionMap.get(regionBo.getParentId());
						if(parentRegionBo.getSubRegionList() == null){
							parentRegionBo.setSubRegionList(new ArrayList<RegionBo>());
						}
						parentRegionBo.getSubRegionList().add(regionBo);
						//移除已经处理的地区
						iterator.remove();
					}
				}
			}
		}
		
		return regionList;
	}
	
	
	private RegionBo inflation(RegionBo bo) {
		
		bo.setCoverPic(PictureUtil.getPicUrl(bo.getCoverPic()));
		
		return bo;
	}
	private List<RegionBo> findAll(List<Integer> idList) {
		Response response = thriftClient.serviceInvoke(this.ServiceName, "findAll", idList);
		List<RegionBo> boList = null;
		try {
			if (response != null) {
				if (response.isState()) {
					boList = JsonUtil.mapper.readValue(response.getData(),JsonUtil.getCollectionType(ArrayList.class, RegionBo.class));
					if(boList != null){
						for (RegionBo regionBo : boList) {
							this.inflation(regionBo);
						}
					}
				} else {
					LogUtil.recordWarnLog(response.getMsg());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return boList;
	}
}
