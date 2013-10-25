package net.shopxx.service;

import net.shopxx.entity.Product;

/**
 * Service接口 - 货品
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX62F330D4576F1CE472FE4A72A3C1070A
 * ============================================================================
 */

public interface ProductService extends BaseService<Product, String> {
	
	/**
	 * 根据货品编号判断此货品是否存在（不区分大小写）
	 * 
	 */
	public boolean isExistByProductSn(String productSn);
	
	/**
	 * 根据货品编号判断此货品是否唯一（不区分大小写）
	 * 
	 */
	public boolean isUniqueByProductSn(String oldProductSn, String newProductSn);
	
	/**
	 * 获取货品库存报警数
	 *            
	 * @return 货品库存报警数
	 */
	public Long getStoreAlertCount();

}