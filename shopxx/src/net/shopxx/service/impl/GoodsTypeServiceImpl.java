package net.shopxx.service.impl;

import javax.annotation.Resource;

import net.shopxx.dao.GoodsTypeDao;
import net.shopxx.entity.GoodsType;
import net.shopxx.service.GoodsTypeService;

import org.springframework.stereotype.Service;

/**
 * Service实现类 - 商品类型
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX11AE7AEEC7428ABAA892EED06C4E877F
 * ============================================================================
 */

@Service("goodsTypeServiceImpl")
public class GoodsTypeServiceImpl extends BaseServiceImpl<GoodsType, String> implements GoodsTypeService {

	@Resource(name = "goodsTypeDaoImpl")
	public void setBaseDao(GoodsTypeDao goodsTypeDao) {
		super.setBaseDao(goodsTypeDao);
	}

}