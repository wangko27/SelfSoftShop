package net.shopxx.service;

import javax.servlet.http.HttpServletRequest;

/**
 * Service接口 - 缓存
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX768EA68E875E50CB2965E6DFD607D2CA
 * ============================================================================
 */

public interface CacheService {
	
	/**
	 * 刷新文章列表页面缓存
	 * 
	 * @param request
	 *            HttpServletRequest
	 *            
	 */
	public void flushArticleListPageCache(HttpServletRequest request);
	
	/**
	 * 刷新商品列表页面缓存
	 * 
	 * @param request
	 *            HttpServletRequest
	 *            
	 */
	public void flushGoodsListPageCache(HttpServletRequest request);
	
	/**
	 * 刷新商品评论列表页面缓存
	 * 
	 * @param request
	 *            HttpServletRequest
	 *            
	 */
	public void flushCommentListPageCache(HttpServletRequest request);
	
	/**
	 * 刷新在线留言页面缓存
	 * 
	 * @param request
	 *            HttpServletRequest
	 *            
	 */
	public void flushLeaveMessagePageCache(HttpServletRequest request);
	
	/**
	 * 刷新所有页面缓存
	 * 
	 * @param request
	 *            HttpServletRequest
	 *            
	 */
	public void flushAllPageCache(HttpServletRequest request);
	
}