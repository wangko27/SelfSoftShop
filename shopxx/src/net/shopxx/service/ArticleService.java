package net.shopxx.service;

import java.util.Date;
import java.util.List;

import net.shopxx.bean.Pager;
import net.shopxx.entity.Article;
import net.shopxx.entity.ArticleCategory;

/**
 * Service接口 - 文章
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXFCA2469C0D8EC758618D14A3D8EA6A05
 * ============================================================================
 */

public interface ArticleService extends BaseService<Article, String> {
	
	/**
	 * 根据文章分类、文章类型、是否包含子分类文章、最大结果数获取文章集合（只包含isPublication=true的对象）
	 * 
	 * @param articleCategory
	 *            文章分类,null表示无限制
	 *            
	 * @param type
	 *            文章类型,null表示无限制
	 *            
	 * @param isContainChildren
	 *            是否包含子分类文章
	 * 
	 * @param maxResults
	 *            最大结果数,null表示无限制
	 * 
	 * @return 此分类下的文章集合
	 */
	public List<Article> getArticleList(ArticleCategory articleCategory, String type, boolean isContainChildren, Integer maxResults);
	
	/**
	 * 根据文章分类、起始日期、结束日期、起始结果数、最大结果数获取文章集合（只包含isPublication=true的对象,包含子分类文章）
	 * 
	 * @param articleCategory
	 *            文章分类,null表示无限制
	 *            
	 * @param beginDate
	 *            起始日期,null表示无限制
	 *            
	 * @param endDate
	 *            结束日期,null表示无限制
	 *            
	 * @param firstResult
	 *            起始结果数,null表示无限制
	 *            
	 * @param maxResults
	 *            最大结果数,null表示无限制
	 * 
	 * @return 此分类下的所有文章集合
	 */
	public List<Article> getArticleList(ArticleCategory articleCategory, Date beginDate, Date endDate, Integer firstResult, Integer maxResults);
	
	/**
	 * 根据ArticleCategory和Pager对象,获取此分类下的文章分页对象（只包含isPublication=true的对象,包含子分类文章）
	 * 
	 * @param articleCategory
	 *            文章分类
	 *            
	 * @param pager
	 *            分页对象
	 * 
	 * @return Pager
	 */
	public Pager getArticlePager(ArticleCategory articleCategory, Pager pager);

	/**
	 * 根据分页对象搜索文章
	 * 
	 * @param pager
	 *            分页对象
	 * 
	 * @return 分页对象
	 */
	public Pager search(Pager pager);
	
	/**
	 * 根据文章ID获取并更新点击数
	 * 
	 * @param id
	 *            文章ID
	 * 
	 * @return 点击数
	 */
	public Integer viewHits(String id);
	
}