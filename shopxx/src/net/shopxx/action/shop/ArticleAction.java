package net.shopxx.action.shop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.shopxx.entity.ArticleCategory;
import net.shopxx.service.ArticleCategoryService;
import net.shopxx.service.ArticleService;

import org.apache.struts2.convention.annotation.ParentPackage;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 前台Action类 - 文章
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX392AA3637D2315A30E492D893AD58FA1
 * ============================================================================
 */

@ParentPackage("shop")
public class ArticleAction extends BaseShopAction {

	private static final long serialVersionUID = -25541236985328967L;
	
	private String sign;
	private ArticleCategory articleCategory;
	private List<ArticleCategory> pathList;

	@Resource(name = "articleServiceImpl")
	private ArticleService articleService;
	@Resource(name = "articleCategoryServiceImpl")
	private ArticleCategoryService articleCategoryService;

	// 文章分类列表
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "sign", message = "参数错误!")
		}
	)
	@InputConfig(resultName = "error")
	public String list() {
		articleCategory = articleCategoryService.getArticleCategoryBySign(sign);
		if (articleCategory == null) {
			addActionError("参数错误!");
			return ERROR;
		}
		
		pager.setSearchBy(null);
		pager.setKeyword(null);
		pathList = articleCategoryService.getArticleCategoryPathList(articleCategory);
		pager = articleService.getArticlePager(articleCategory, pager);
		return LIST;
	}
	
	// 文章搜索
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "pager.keyword", message = "搜索关键词不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String search() throws Exception {
		pager = articleService.search(pager);
		return "search";
	}
	
	// 获取文章点击数
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "id", message = "文章ID不允许为空!")
		}
	)
	@InputConfig(resultName = "ajaxError")
	public String ajaxHits() {
		Integer hits = articleService.viewHits(id);
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put(STATUS_PARAMETER_NAME, Status.success);
		jsonMap.put("hits", hits.toString());
		return ajax(jsonMap);
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public ArticleCategory getArticleCategory() {
		return articleCategory;
	}

	public void setArticleCategory(ArticleCategory articleCategory) {
		this.articleCategory = articleCategory;
	}

	public List<ArticleCategory> getPathList() {
		return pathList;
	}

	public void setPathList(List<ArticleCategory> pathList) {
		this.pathList = pathList;
	}

}