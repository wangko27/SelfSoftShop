package net.shopxx.service.impl;

import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import net.shopxx.service.CacheService;

import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

import com.opensymphony.oscache.base.Cache;
import com.opensymphony.oscache.web.ServletCacheAdministrator;

/**
 * Service实现类 - 缓存
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXE7656ABEA5A60D076604DDCE20B403C9
 * ============================================================================
 */

@Service("cacheServiceImpl")
public class CacheServiceImpl implements CacheService, ServletContextAware {
	
	public static final String ARTICLE_LIST_PAGE_CACHE_GROUP = "articleList";// 文章列表页面缓存Group
	public static final String GOODS_LIST_PAGE_CACHE_GROUP = "goodsList";// 文章列表页面缓存Group
	public static final String COMMENT_LIST_PAGE_CACHE_GROUP = "commentList";// 商品评论列表页面缓存Group
	public static final String LEAVE_MESSAGE_LIST_PAGE_CACHE_GROUP = "leaveMessageList";// 在线留言列表页面缓存Group
	
	private ServletContext servletContext;
	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	public void flushArticleListPageCache(HttpServletRequest request) {
		Cache cache = ServletCacheAdministrator.getInstance(servletContext).getCache(request, PageContext.APPLICATION_SCOPE); 
		cache.flushGroup(ARTICLE_LIST_PAGE_CACHE_GROUP);
	}
	
	public void flushGoodsListPageCache(HttpServletRequest request) {
		Cache cache = ServletCacheAdministrator.getInstance(servletContext).getCache(request, PageContext.APPLICATION_SCOPE); 
		cache.flushGroup(GOODS_LIST_PAGE_CACHE_GROUP);
	}
	
	public void flushCommentListPageCache(HttpServletRequest request) {
		Cache cache = ServletCacheAdministrator.getInstance(servletContext).getCache(request, PageContext.APPLICATION_SCOPE); 
		cache.flushGroup(COMMENT_LIST_PAGE_CACHE_GROUP);
	}
	
	public void flushLeaveMessagePageCache(HttpServletRequest request) {
		Cache cache = ServletCacheAdministrator.getInstance(servletContext).getCache(request, PageContext.APPLICATION_SCOPE); 
		cache.flushGroup(LEAVE_MESSAGE_LIST_PAGE_CACHE_GROUP);
	}
	
	public void flushAllPageCache(HttpServletRequest request) {
		Cache cache = ServletCacheAdministrator.getInstance(servletContext).getCache(request, PageContext.APPLICATION_SCOPE); 
		cache.flushAll(new Date());
	}

}