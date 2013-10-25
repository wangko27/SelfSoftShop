package net.shopxx.service;


/**
 * Service接口 - 任务
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX3F6674D6C2E7DF7287EF69622E4F46B5
 * ============================================================================
 */

public interface JobService {
	
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
	 * 生成文章内容HTML
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
	 * 生成商品内容HTML
	 * 
	 */
	public void buildGoodsContentHtml();
	
	/**
	 * 生成错误页HTML
	 * 
	 */
	public void buildErrorHtml();
	
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