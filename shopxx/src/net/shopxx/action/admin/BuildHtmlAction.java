package net.shopxx.action.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.shopxx.entity.Article;
import net.shopxx.entity.ArticleCategory;
import net.shopxx.entity.Goods;
import net.shopxx.entity.GoodsCategory;
import net.shopxx.service.ArticleCategoryService;
import net.shopxx.service.ArticleService;
import net.shopxx.service.CacheService;
import net.shopxx.service.GoodsCategoryService;
import net.shopxx.service.GoodsService;
import net.shopxx.service.HtmlService;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

/**
 * 后台Action类 - 生成静态
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX5130C538F3343650399C2A81DC78CB1F
 * ============================================================================
 */

@ParentPackage("admin")
public class BuildHtmlAction extends BaseAdminAction {

	private static final long serialVersionUID = -2197609380142883572L;

	@Resource(name = "articleServiceImpl")
	private ArticleService articleService;
	@Resource(name = "articleCategoryServiceImpl")
	private ArticleCategoryService articleCategoryService;
	@Resource(name = "goodsServiceImpl")
	private GoodsService goodsService;
	@Resource(name = "goodsCategoryServiceImpl")
	private GoodsCategoryService goodsCategoryService;
	@Resource(name = "htmlServiceImpl")
	private HtmlService htmlService;
	@Resource(name = "cacheServiceImpl")
	private CacheService cacheService;
	
	private String buildType;// 更新类型
	private String buildContent;// 更新内容
	private Integer maxResults;// 每次更新数
	private Integer firstResult;// 起始结果数
	private Date beginDate;// 开始日期
	private Date endDate;// 结束日期
	
	public String allInput() {
		return "all_input";
	}
	
	// 一键更新
	@SuppressWarnings("deprecation")
	@InputConfig(resultName = "ajaxError")
	public String all() throws Exception {
		long beginTimeMillis = System.currentTimeMillis();
		if (StringUtils.isEmpty(buildType)) {
			buildType = "all";
		}
		if (StringUtils.isEmpty(buildContent)) {
			buildContent = "index";
		}
		if (maxResults == null || maxResults <= 0) {
			maxResults = 50;
		}
		if (firstResult == null || firstResult < 0) {
			firstResult = 0;
		}
		
		if (buildContent.equalsIgnoreCase("index")) {
			htmlService.buildIndexHtml();
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put(STATUS_PARAMETER_NAME, "indexFinish");
			jsonMap.put("buildCount", 1);
			jsonMap.put("buildTime", System.currentTimeMillis() - beginTimeMillis);
			return ajax(jsonMap);
		} else if (buildContent.equalsIgnoreCase("login")) {
			htmlService.buildLoginHtml();
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put(STATUS_PARAMETER_NAME, "loginFinish");
			jsonMap.put("buildCount", 1);
			jsonMap.put("buildTime", System.currentTimeMillis() - beginTimeMillis);
			return ajax(jsonMap);
		} else if (buildContent.equalsIgnoreCase("registerAgreement")) {
			htmlService.buildRegisterAgreementHtml();
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put(STATUS_PARAMETER_NAME, "registerAgreementFinish");
			jsonMap.put("buildCount", 1);
			jsonMap.put("buildTime", System.currentTimeMillis() - beginTimeMillis);
			return ajax(jsonMap);
		} else if (buildContent.equalsIgnoreCase("adminJs")) {
			htmlService.buildAdminJs();
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put(STATUS_PARAMETER_NAME, "adminJsFinish");
			jsonMap.put("buildCount", 1);
			jsonMap.put("buildTime", System.currentTimeMillis() - beginTimeMillis);
			return ajax(jsonMap);
		} else if (buildContent.equalsIgnoreCase("shopJs")) {
			htmlService.buildShopJs();
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put(STATUS_PARAMETER_NAME, "shopJsFinish");
			jsonMap.put("buildCount", 1);
			jsonMap.put("buildTime", System.currentTimeMillis() - beginTimeMillis);
			return ajax(jsonMap);
		} else if (buildContent.equalsIgnoreCase("errorPage")) {
			htmlService.buildErrorPageHtml();
			htmlService.buildErrorPageAccessDeniedHtml();
			htmlService.buildErrorPage500Html();
			htmlService.buildErrorPage404Html();
			htmlService.buildErrorPage403Html();
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put(STATUS_PARAMETER_NAME, "errorPageFinish");
			jsonMap.put("buildCount", 5);
			jsonMap.put("buildTime", System.currentTimeMillis() - beginTimeMillis);
			return ajax(jsonMap);
		} else if (buildContent.equalsIgnoreCase("article")) {
			List<Article> articleList = null;
			if (buildType.equalsIgnoreCase("all")) {
				articleList = articleService.getArticleList(null, null, null, firstResult, maxResults);
			} else if(buildType.equalsIgnoreCase("date")) {
				if (beginDate != null) {
					beginDate.setHours(0);
					beginDate.setMinutes(0);
					beginDate.setSeconds(0);
				}
				if (endDate != null) {
					endDate.setHours(23);
					endDate.setMinutes(59);
					endDate.setSeconds(59);
				}
				articleList = articleService.getArticleList(null, beginDate, endDate, firstResult, maxResults);
			}
			if (articleList != null && articleList.size() > 0) {
				for (Article article : articleList) {
					htmlService.buildArticleContentHtml(article);
				}
			}
			if (articleList != null && articleList.size() == maxResults) {
				Map<String, Object> jsonMap = new HashMap<String, Object>();
				jsonMap.put(STATUS_PARAMETER_NAME, "articleBuilding");
				jsonMap.put("firstResult", firstResult + articleList.size());
				jsonMap.put("buildCount", maxResults);
				jsonMap.put("buildTime", System.currentTimeMillis() - beginTimeMillis);
				return ajax(jsonMap);
			} else {
				Map<String, Object> jsonMap = new HashMap<String, Object>();
				jsonMap.put(STATUS_PARAMETER_NAME, "articleFinish");
				jsonMap.put("buildCount", articleList.size());
				jsonMap.put("buildTime", System.currentTimeMillis() - beginTimeMillis);
				return ajax(jsonMap);
			}
		} else if (buildContent.equalsIgnoreCase("goods")) {
			List<Goods> goodsList = null;
			if (buildType.equalsIgnoreCase("all")) {
				goodsList = goodsService.getGoodsList(null, null, null, firstResult, maxResults);
			} else if(buildType.equalsIgnoreCase("date")) {
				if (beginDate != null) {
					beginDate.setHours(0);
					beginDate.setMinutes(0);
					beginDate.setSeconds(0);
				}
				if (endDate != null) {
					endDate.setHours(23);
					endDate.setMinutes(59);
					endDate.setSeconds(59);
				}
				goodsList = goodsService.getGoodsList(null, beginDate, endDate, firstResult, maxResults);
			}
			if (goodsList != null && goodsList.size() > 0) {
				for (Goods goods : goodsList) {
					htmlService.buildGoodsContentHtml(goods);
				}
			}
			if (goodsList != null && goodsList.size() == maxResults) {
				Map<String, Object> jsonMap = new HashMap<String, Object>();
				jsonMap.put(STATUS_PARAMETER_NAME, "goodsBuilding");
				jsonMap.put("firstResult", firstResult + goodsList.size());
				jsonMap.put("buildCount", maxResults);
				jsonMap.put("buildTime", System.currentTimeMillis() - beginTimeMillis);
				return ajax(jsonMap);
			} else {
				Map<String, Object> jsonMap = new HashMap<String, Object>();
				jsonMap.put(STATUS_PARAMETER_NAME, "goodsFinish");
				jsonMap.put("buildCount", goodsList.size());
				jsonMap.put("buildTime", System.currentTimeMillis() - beginTimeMillis);
				cacheService.flushAllPageCache(getRequest());
				return ajax(jsonMap);
			}
		}
		return NONE;
	}
	
	public String articleInput() {
		return "article_input";
	}
	
	// 更新文章
	@InputConfig(resultName = "ajaxError")
	public String article() {
		long beginTimeMillis = System.currentTimeMillis();
		if (maxResults == null || maxResults <= 0) {
			maxResults = 50;
		}
		if (firstResult == null || firstResult < 0) {
			firstResult = 0;
		}

		ArticleCategory articleCategory = null;
		if (StringUtils.isNotEmpty(id)) {
			articleCategory = articleCategoryService.load(id);
		}
		List<Article> articleList = articleService.getArticleList(articleCategory, null, null, firstResult, maxResults);
		
		if (articleList != null && articleList.size() > 0) {
			for (Article article : articleList) {
				htmlService.buildArticleContentHtml(article);
			}
		}
		if (articleList != null && articleList.size() == maxResults) {
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put(STATUS_PARAMETER_NAME, "ARTICLE_BUILDING");
			jsonMap.put("firstResult", firstResult + articleList.size());
			jsonMap.put("buildCount", maxResults);
			jsonMap.put("buildTime", System.currentTimeMillis() - beginTimeMillis);
			return ajax(jsonMap);
		} else {
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put(STATUS_PARAMETER_NAME, "ARTICLE_FINISH");
			jsonMap.put("buildCount", articleList.size());
			jsonMap.put("buildTime", System.currentTimeMillis() - beginTimeMillis);
			cacheService.flushArticleListPageCache(getRequest());
			return ajax(jsonMap);
		}
	}
	
	public String goodsInput() {
		return "goods_input";
	}
	
	// 更新商品
	@InputConfig(resultName = "ajaxError")
	public String goods() {
		long beginTimeMillis = System.currentTimeMillis();
		if (maxResults == null || maxResults <= 0) {
			maxResults = 50;
		}
		if (firstResult == null || firstResult < 0) {
			firstResult = 0;
		}
		
		GoodsCategory goodsCategory = null;
		if (StringUtils.isNotEmpty(id)) {
			goodsCategory = goodsCategoryService.load(id);
		}
		List<Goods> goodsList = goodsService.getGoodsList(goodsCategory, null, null, firstResult, maxResults);
		
		if (goodsList != null && goodsList.size() > 0) {
			for (Goods goods : goodsList) {
				htmlService.buildGoodsContentHtml(goods);
			}
		}
		if (goodsList != null && goodsList.size() == maxResults) {
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put(STATUS_PARAMETER_NAME, "PRODUCT_BUILDING");
			jsonMap.put("firstResult", firstResult + goodsList.size());
			jsonMap.put("buildCount", maxResults);
			jsonMap.put("buildTime", System.currentTimeMillis() - beginTimeMillis);
			return ajax(jsonMap);
		} else {
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put(STATUS_PARAMETER_NAME, "PRODUCT_FINISH");
			jsonMap.put("buildCount", goodsList.size());
			jsonMap.put("buildTime", System.currentTimeMillis() - beginTimeMillis);
			cacheService.flushGoodsListPageCache(getRequest());
			return ajax(jsonMap);
		}
	}
	
	// 获取文章分类树
	public List<ArticleCategory> getArticleCategoryTreeList() {
		return articleCategoryService.getArticleCategoryTreeList();
	}
	
	// 获取商品分类树
	public List<GoodsCategory> getGoodsCategoryTreeList() {
		return goodsCategoryService.getGoodsCategoryTreeList();
	}
	
	// 获取默认开始日期
	public Date getDefaultBeginDate() {
		return DateUtils.addDays(new Date(), -7);
	}

	// 获取默认结束日期
	public Date getDefaultEndDate() {
		return new Date();
	}

	public String getBuildType() {
		return buildType;
	}

	public void setBuildType(String buildType) {
		this.buildType = buildType;
	}

	public String getBuildContent() {
		return buildContent;
	}

	public void setBuildContent(String buildContent) {
		this.buildContent = buildContent;
	}

	public Integer getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
	}
	
	public Integer getFirstResult() {
		return firstResult;
	}

	public void setFirstResult(Integer firstResult) {
		this.firstResult = firstResult;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}