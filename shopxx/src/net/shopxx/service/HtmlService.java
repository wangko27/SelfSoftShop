package net.shopxx.service;

import java.util.Map;

import net.shopxx.entity.Article;
import net.shopxx.entity.Goods;

/**
 * Service接口 - 生成静态
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

public interface HtmlService {
	
	/**
	 * 根据Freemarker模板文件路径、Map数据生成HTML
	 * 
	 * @param templatePath
	 *            Freemarker模板文件路径
	 *            
	 * @param htmlPath
	 *            生成HTML路径
	 * 
	 * @param data
	 *            Map数据
	 * 
	 */
	public void buildHtml(String templatePath, String htmlPath, Map<String, Object> data);
	
	/**
	 * 生成首页HTML
	 * 
	 */
	public void buildIndexHtml();
	
	/**
	 * 生成登录HTML
	 * 
	 */
	public void buildLoginHtml();
	
	/**
	 * 生成注册协议HTML
	 * 
	 */
	public void buildRegisterAgreementHtml();
	
	/**
	 * 生成ADMIN.JS
	 * 
	 */
	public void buildAdminJs();
	
	/**
	 * 生成SHOP.JS
	 * 
	 */
	public void buildShopJs();
	
	/**
	 * 根据ID生成文章内容HTML
	 * 
	 * @param id
	 *            文章ID
	 */
	public void buildArticleContentHtml(String id);
	
	/**
	 * 根据文章生成文章内容HTML
	 * 
	 * @param article
	 *            文章
	 */
	public void buildArticleContentHtml(Article article);
	
	/**
	 * 生成所有文章内容HTML
	 * 
	 */
	public void buildArticleContentHtml();
	
	/**
	 * 根据ID生成商品内容HTML
	 * 
	 * @param id
	 *            商品ID
	 */
	public void buildGoodsContentHtml(String id);
	
	/**
	 * 根据商品生成商品内容HTML
	 * 
	 * @param goods
	 *            商品
	 */
	public void buildGoodsContentHtml(Goods goods);
	
	/**
	 * 生成所有商品内容HTML
	 * 
	 */
	public void buildGoodsContentHtml();
	
	/**
	 * 根据商品评论ID生成商品内容HTML
	 * 
	 * @param id
	 *            商品评论ID
	 */
	public void buildCommentGoodsContentHtml(String id);
	
	/**
	 * 生成错误页HTML
	 */
	public void buildErrorPageHtml();
	
	/**
	 * 生成权限错误页HTML
	 */
	public void buildErrorPageAccessDeniedHtml();
	
	/**
	 * 生成错误页500 HTML
	 */
	public void buildErrorPage500Html();
	
	/**
	 * 生成错误页404 HTML
	 */
	public void buildErrorPage404Html();
	
	/**
	 * 生成错误页403 HTML
	 */
	public void buildErrorPage403Html();
	
	/**
	 * 根据HTML路径、分页数删除文章内容HTML
	 * 
	 * @param htmlPath
	 *            HTML路径
	 * 
	 * @param pageCount
	 *            分页数
	 */
	public void deleteArticleContentHtml(String htmlPath, Integer pageCount);
	
	/**
	 * 根据HTML路径删除商品内容HTML
	 * 
	 * @param htmlPath
	 *            HTML路径
	 */
	public void deleteGoodsContentHtml(String htmlPath);
	
}