
package com.amx.bizservice.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.amx.bizservice.model.bo.HodometerBo;
import com.amx.bizservice.service.HodometerService;
import com.amx.bizservice.thrift.Response;
import com.amx.bizservice.util.JsonUtil;
import com.amx.bizservice.util.LogUtil;
import com.fasterxml.jackson.databind.JavaType;

@Service("hodometerService")
public class HodometerServiceImpl extends BaseService implements HodometerService{
	
/*	private Comparator<HodometerBo> c = new Comparator<HodometerBo>() {
		@Override
		public int compare(HodometerBo o1, HodometerBo o2) {
			return o1.getItemOrder() - o2.getItemOrder();
		}
	};*/

	public HodometerServiceImpl(){
		this.ServiceName = "Hodometer";		
	}

	@Override
	public Map<Integer, List<HodometerBo>> getHodometerByProductId(long id){
		//<dayOrder,item>
		Map<Integer, List<HodometerBo>> map = null;
		List<HodometerBo> hodometerBoList = this.findAllByProductId(id);
		if(hodometerBoList != null){
			/* 将同一天的行程活动归到一起  */
			map = new HashMap<Integer, List<HodometerBo>>();  
			for(HodometerBo hbo : hodometerBoList){
				if(map.get(hbo.getDayOrder()) == null){
					map.put(hbo.getDayOrder(), new ArrayList<HodometerBo>());
				}
				map.get(hbo.getDayOrder()).add(hbo);
			}
		}
		/*if(map != null){
			//按项目序号排序
			//TODO - 若再查数据库时已排序，则无需再排序
			for (Map.Entry<Integer, List<HodometerBo>> entry : map.entrySet()) {
				Collections.sort(entry.getValue(), c);
			
		}}*/
		
		return map;
	}

	
	private List<HodometerBo> findAllByProductId(long id) {
		Response response = thriftClient.serviceInvoke(this.ServiceName, "findAllByProductId", id);
		List<HodometerBo> boList = null; 
		try {
			if(response != null){
				if(response.isState()){
					JavaType javaType = JsonUtil.getCollectionType(ArrayList.class, HodometerBo.class);		
					boList = JsonUtil.mapper.readValue(response.getData(),javaType);
				}else{
					LogUtil.recordWarnLog(response.getMsg());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return boList;
	}
	
}
