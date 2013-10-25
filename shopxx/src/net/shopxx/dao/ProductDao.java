package net.shopxx.dao;

import java.util.List;

import net.shopxx.entity.Product;

/**
 * Dao接口 - 货品
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX345CC4FC5532785F1241F140FCE5A740
 * ============================================================================
 */

public interface ProductDao extends BaseDao<Product, String> {
	
	/**
	 * 根据货品编号判断此货品是否存在（不区分大小写）
	 * 
	 */
	public boolean isExistByProductSn(String productSn);
	
	/**
	 * 根据货品ID获取同属商品的货品
	 * 
	 */
	public List<Product> getSiblingsProductList(String productId);
	
	/**
	 * 获取货品库存报警数
	 *            
	 * @return 货品库存报警数
	 */
	public Long getStoreAlertCount();
	
}