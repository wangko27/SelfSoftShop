package net.shopxx.action.admin;

import java.util.List;

import javax.annotation.Resource;

import net.shopxx.entity.Article;
import net.shopxx.entity.ArticleCategory;
import net.shopxx.service.ArticleCategoryService;
import net.shopxx.service.ArticleService;
import net.shopxx.service.CacheService;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 文章
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX8DDD613CBE6FE3988F4CE19EAC8724CB
 * ============================================================================
 */

@ParentPackage("admin")
public class ArticleAction extends BaseAdminAction {

	private static final long serialVersionUID = -6825456589196458406L;

	private Article article;

	@Resource(name = "articleServiceImpl")
	private ArticleService articleService;
	@Resource(name = "articleCategoryServiceImpl")
	private ArticleCategoryService articleCategoryService;
	@Resource(name = "cacheServiceImpl")
	private CacheService cacheService;

	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		article = articleService.load(id);
		return INPUT;
	}

	// 列表
	public String list() {
		pager = articleService.findPager(pager);
		return LIST;
	}

	// 删除
	public String delete() throws Exception {
		StringBuffer logInfoStringBuffer = new StringBuffer("删除文章: ");
		for (String id : ids) {
			Article article = articleService.load(id);
			articleService.delete(article);
			logInfoStringBuffer.append(article.getTitle() + " ");
		}
		logInfo = logInfoStringBuffer.toString();
		
		cacheService.flushArticleListPageCache(getRequest());
		
		return ajax(Status.success, "删除成功!");
	}

	// 保存
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "article.title", message = "标题不允许为空!"),
			@RequiredStringValidator(fieldName = "article.content", message = "内容不允许为空!"),
			@RequiredStringValidator(fieldName = "article.articleCategory.id", message = "文章分类不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String save() throws Exception {
		articleService.save(article);
		logInfo = "添加文章: " + article.getTitle();
		
		cacheService.flushArticleListPageCache(getRequest());
		
		redirectUrl = "article!list.action";
		return SUCCESS;
	}

	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "article.title", message = "标题不允许为空!"),
			@RequiredStringValidator(fieldName = "article.content", message = "内容不允许为空!"),
			@RequiredStringValidator(fieldName = "article.articleCategory.id", message = "文章分类不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() throws Exception {
		Article persistent = articleService.load(id);
		BeanUtils.copyProperties(article, persistent, new String[] {"id", "createDate", "modifyDate", "pageCount", "htmlPath", "hits"});
		articleService.update(persistent);
		logInfo = "编辑文章: " + article.getTitle();
		
		cacheService.flushArticleListPageCache(getRequest());
		
		redirectUrl = "article!list.action";
		return SUCCESS;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public List<ArticleCategory> getArticleCategoryTreeList() {
		return articleCategoryService.getArticleCategoryTreeList();
	}

}