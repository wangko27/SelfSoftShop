package net.shopxx.service;

import net.shopxx.bean.SpecificationValue;
import net.shopxx.entity.Specification;

/**
 * Service接口 - 商品规格
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX0093A02DC48CD73BDC71C06BA52A9AD2
 * ============================================================================
 */

public interface SpecificationService extends BaseService<Specification, String> {
	
	/**
	 * 根据商品规格ID、商品规格值ID获取规格值
	 * 
	 * @param specificationId
	 *            商品规格ID
	 *            
	 * @param specificationValueId
	 *            商品规格值ID
	 *            
	 * @return 商品规格值
	 */
	public SpecificationValue getSpecificationValue(String specificationId, String specificationValueId);

}