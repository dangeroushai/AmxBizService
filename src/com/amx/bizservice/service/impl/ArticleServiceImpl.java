package com.amx.bizservice.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.amx.bizservice.config.CommonConfig;
import com.amx.bizservice.enums.ArticleTypeEnum;
import com.amx.bizservice.model.bo.ArticleBo;
import com.amx.bizservice.model.dto.PageResponseDto;
import com.amx.bizservice.model.qo.ArticleQuery;
import com.amx.bizservice.service.ArticleService;
import com.amx.bizservice.thrift.Response;
import com.amx.bizservice.util.JsonUtil;
import com.amx.bizservice.util.LogUtil;
import com.amx.bizservice.util.PictureUtil;
import com.fasterxml.jackson.core.type.TypeReference;

@Service("articleService")
public class ArticleServiceImpl extends BaseService implements ArticleService {

	public ArticleServiceImpl() {
		this.ServiceName = "Article";
	}

	@Override
	public ArticleBo getArticleDetail(int id) {
		ArticleBo bo = this.findOne(id);
		if(bo != null){
			this.inflation(bo);
		}
		return bo;
	}

	@Override
	public ArticleBo getArticleSnap(int id) {
		ArticleBo articleBo = this.getArticleDetail(id);
		if(articleBo != null){
			/* 剔除不需的信息 */
			articleBo.setAttrIdList(null);
			articleBo.setContent(null);
			articleBo.setDestIdList(null);
			articleBo.setPageView(null);
			articleBo.setSource(null);
			articleBo.setSourceUrl(null);
		}
		
		return articleBo;
	}
	
	@Override
	public PageResponseDto<ArticleBo> getArticleList(ArticleQuery query) {
		Response response = thriftClient.serviceInvoke(this.ServiceName, "findAllByQuery", query);
		PageResponseDto<ArticleBo> page = null;
		try {
			if (response != null) {
				if (response.isState()) {
					page = JsonUtil.mapper.readValue(response.getData(),new TypeReference<PageResponseDto<ArticleBo>>() {});
					List<ArticleBo> content = page.getContent();
					if(content != null){
						for (ArticleBo articleBo : content) {
							inflation(articleBo);
						}
					}
				} else {
					LogUtil.recordWarnLog(response.getMsg());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return page;
	}
	
	@Override
	public List<ArticleBo> getArticlesByAttrId(Integer attrId) {
		List<ArticleBo> list = null;
		ArticleQuery query = new ArticleQuery();
		query.setPageIndex(0);
		query.setPageSize(CommonConfig.WEB_PAGE_SIZE);
		query.setTypeId(attrId);
		
		PageResponseDto<ArticleBo> pageResponseDto = getArticleList(query);
		if(pageResponseDto != null){
			list = pageResponseDto.getContent();
		}
		
		return list;
	}
	
	private void inflation(ArticleBo articleBo) {
		//封面图
		articleBo.setCoverPic(PictureUtil.getPicUrl(articleBo.getCoverPic()));
		articleBo.setTypeId(ArticleTypeEnum.getTypeIdByAttrIdList(articleBo.getAttrIdList()));
	}

	/**
	 * 调用数据服务，获取基础文章对象
	 * @param pid
	 * @return
	 */
	private ArticleBo findOne(int pid) {
		Response response = thriftClient.serviceInvoke(this.ServiceName, "findOne", pid);
		ArticleBo ArticleBo = null;
		try {
			if (response != null) {
				if (response.isState()) {
					ArticleBo = JsonUtil.mapper.readValue(response.getData(),ArticleBo.class);
				} else {
					LogUtil.recordWarnLog(response.getMsg());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return ArticleBo;
	}
}
