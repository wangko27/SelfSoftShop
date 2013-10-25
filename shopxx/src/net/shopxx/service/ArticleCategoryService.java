package net.shopxx.service;

import java.util.List;

import net.shopxx.entity.Article;
import net.shopxx.entity.ArticleCategory;

/**
 * Service接口 - 文章分类
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXF631FF5629197454FEED5CB4F84F0ED2
 * ============================================================================
 */

public interface ArticleCategoryService extends BaseService<ArticleCategory, String> {
	
	/**
	 * 判断标识是否存在（不区分大小写）
	 * 
	 * @param sign
	 *            标识
	 * 
	 */
	public boolean isExistBySign(String sign);
	
	/**
	 * 判断标识是否唯一（不区分大小写）
	 * 
	 * @param oldSign
	 *            旧标识
	 *            
	 * @param newSign
	 *            新标识
	 * 
	 */
	public boolean isUniqueBySign(String oldSign, String newSign);
	
	/**
	 * 根据标识获取ArticleCategory对象
	 * 
	 * @param sign
	 *            sign
	 * 
	 */
	public ArticleCategory getArticleCategoryBySign(String sign);
	
	/**
	 * 获取文章分类树集合
	 * 
	 * @return 文章分类树集合
	 * 
	 */
	public List<ArticleCategory> getArticleCategoryTree();
	
	/**
	 * 获取文章分类树集合;
	 * 
	 * @return 文章分类树集合
	 * 
	 */
	public List<ArticleCategory> getArticleCategoryTreeList();
	
	/**
	 * 获取顶级文章分类集合
	 * 
	 * @param maxResults
	 *            最大结果数,null表示无限制
	 * 
	 * @return 顶级文章分类集合
	 * 
	 */
	public List<ArticleCategory> getRootArticleCategoryList(Integer maxResults);
	
	/**
	 * 根据ArticleCategory对象获取所有父类集合,若无父类则返回null
	 * 
	 * @param articleCategory
	 *            articleCategory
	 *            
	 * @param isContainParent
	 *            是否包含所有父分类
	 * 
	 * @param maxResults
	 *            最大结果数,null表示无限制
	 * 
	 * @return 父类集合
	 * 
	 */
	public List<ArticleCategory> getParentArticleCategoryList(ArticleCategory articleCategory, boolean isContainParent, Integer maxResults);
	
	/**
	 * 根据Article对象获取所有父类集合,若无父类则返回null
	 * 
	 * @param article
	 *            article
	 *            
	 * @param isContainParent
	 *            是否包含所有父分类
	 * 
	 * @param maxResults
	 *            最大结果数,null表示无限制
	 * 
	 * @return 父类集合
	 * 
	 */
	public List<ArticleCategory> getParentArticleCategoryList(Article article, boolean isContainParent, Integer maxResults);
	
	/**
	 * 根据ArticleCategory对象获取所有子类集合,若无子类则返回null
	 * 
	 * @param articleCategory
	 *            articleCategory
	 *            
	 * @param isContainChildren
	 *            是否包含所有子分类
	 * 
	 * @param maxResults
	 *            最大结果数,null表示无限制
	 * 
	 * @return 子类集合
	 * 
	 */
	public List<ArticleCategory> getChildrenArticleCategoryList(ArticleCategory articleCategory, boolean isContainChildren, Integer maxResults);
	
	/**
	 * 根据ArticleCategory对象获取路径集合
	 * 
	 * @param articleCategory
	 *            articleCategory
	 * 
	 * @return ArticleCategory路径集合
	 * 
	 */
	public List<ArticleCategory> getArticleCategoryPathList(ArticleCategory articleCategory);

}