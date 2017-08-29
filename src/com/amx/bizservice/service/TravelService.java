package com.amx.bizservice.service;

import com.amx.bizservice.model.bo.TravelBo;
import com.amx.bizservice.model.bo.UpdateResponseBo;
import com.amx.bizservice.model.dto.PageResponseDto;
import com.amx.bizservice.model.qo.PageQuery;



public interface TravelService{

	PageResponseDto<TravelBo> getTravelList(PageQuery query);

	UpdateResponseBo deleteTravel(long userId, long travelId);

	TravelBo getTravel(long userId, long trlongavelId);

	Long insertTravel(TravelBo travelBo);

	UpdateResponseBo updateTravel(TravelBo travelBo);

}
 