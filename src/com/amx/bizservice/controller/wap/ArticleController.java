package com.amx.bizservice.controller.wap;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
import com.amx.bizservice.model.vo.wap.ArticleListVo;
import com.amx.bizservice.model.vo.wap.ArticleVo;
import com.amx.bizservice.service.ArticleService;

@Controller("mArticleController")
@RequestMapping("/articles")
public class ArticleController extends BaseController{
	
	@Autowired
	private ArticleService articleService;

	/**
	 * 文章详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/{id}")
	public Map<String, Object> getArticleDetail(@PathVariable("id") int id ){
		Object  response = ResponseTypeEnum.PARAMETER_ERROR;
		ArticleVo vo =  null;
		ArticleBo bo = articleService.getArticleDetail(id);
		if(bo != null ){
			response = ResponseTypeEnum.getNewOkResponse();
			//将Bo转为VO
			vo =  new ArticleVo(bo);
			//设置响应数据
			((Response)response).setData(vo);
		}
		
		return JsonResponseDto.getResult(response);
	}
	
	/**
	 * 文章列表
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)//注意：不能省略
	public Map<String, Object> getArticleList(@RequestParam(name = "pageindex", defaultValue = "0") int pageIndex,@RequestParam(name = "pagesize", defaultValue = "" + CommonConfig.WAP_PAGE_SIZE) int pageSize, @RequestParam(name = "typeid", defaultValue = "0") int typeId){
		Object  response = ResponseTypeEnum.PARAMETER_ERROR;
		if(pageSize <= 0){
			pageSize = CommonConfig.WAP_PAGE_SIZE;
		}
		//封装查询条件
		ArticleQuery query = new ArticleQuery();
		query.setPageIndex(pageIndex);
		query.setPageSize(pageSize);
		if(typeId != 0){//typeId = 0 -> 全部文章
			query.setTypeId(ArticleTypeEnum.getAttrIdByTypeId(typeId));
		}
		
		ArticleListVo vo =  null;
		PageResponseDto<ArticleBo> page = articleService.getArticleList(query);
		if(page != null){
			response = ResponseTypeEnum.getNewOkResponse();
			vo = new ArticleListVo(page );
			
			//设置响应数据
			((Response)response).setData(vo);
		}
		
		return JsonResponseDto.getResult(response);
	}
}
