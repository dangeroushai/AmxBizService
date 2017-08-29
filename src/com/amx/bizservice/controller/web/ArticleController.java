package com.amx.bizservice.controller.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amx.bizservice.config.CommonConfig;
import com.amx.bizservice.controller.BaseController;
import com.amx.bizservice.enums.ArticleTypeEnum;
import com.amx.bizservice.enums.ResponseTypeEnum;
import com.amx.bizservice.enums.ResponseTypeEnum.Response;
import com.amx.bizservice.model.bo.ArticleBo;
import com.amx.bizservice.model.dto.JsonResponseDto;
import com.amx.bizservice.model.dto.PageResponseDto;
import com.amx.bizservice.model.qo.ArticleQuery;
import com.amx.bizservice.model.vo.web.ArticleListVo;
import com.amx.bizservice.model.vo.web.ArticleVo;
import com.amx.bizservice.service.ArticleService;

@Controller
public class ArticleController extends BaseController{
	
	@Autowired
	private ArticleService articleService;

	/**
	 * 文章详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/article/{id}")
	public Map<String, Object> getArticleDetail(@PathVariable("id") int id ){
		Object response = ResponseTypeEnum.PARAMETER_ERROR;
		ArticleVo vo =  null;
		ArticleBo bo = articleService.getArticleDetail(id);
		if(bo != null ){
			response = ResponseTypeEnum.getNewOkResponse();
			//将Bo转为VO
			vo =  new ArticleVo(bo, false);
			//设置响应数据
			((Response)response).setData(vo);
		}
		
		return JsonResponseDto.getResult(response);
	}
	
	/**
	 * 文章列表
	 */
	@ResponseBody
	@RequestMapping("/articlesList/{pageIndex}/{typeId}/{categroyId}/{keyword}")
	public Map<String, Object> getArticleList(@PathVariable("pageIndex") int pageIndex,   @PathVariable("typeId") int typeId, @PathVariable("categroyId") int categroyId, @PathVariable("keyword") String keyword){
		Object response = ResponseTypeEnum.PARAMETER_ERROR;
		//封装查询条件
		ArticleQuery query = new ArticleQuery();
		query.setPageIndex(pageIndex);
		query.setPageSize(CommonConfig.WEB_PAGE_SIZE);
		//无实际意义
		query.setCategroyId(categroyId);
		query.setTypeId(ArticleTypeEnum.getAttrIdByTypeId(typeId));
		query.setKeyword(keyword);
		
		ArticleListVo vo =  null;
		PageResponseDto<ArticleBo> page = articleService.getArticleList(query);
		if(page != null){
			response = ResponseTypeEnum.getNewOkResponse();
			//主题才显示简洁
			boolean isShowIntroduce = typeId == ArticleTypeEnum.CUSTOM_TEHME.getTypeId();
			vo = new ArticleListVo(page,isShowIntroduce );
			
			//设置响应数据
			((Response)response).setData(vo);
		}
		
		return JsonResponseDto.getResult(response);
	}
}
