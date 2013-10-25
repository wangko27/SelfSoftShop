package net.shopxx.dao.impl;

import java.util.Set;

import net.shopxx.dao.BrandDao;
import net.shopxx.entity.Brand;
import net.shopxx.entity.Goods;
import net.shopxx.entity.GoodsType;

import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 品牌
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX64B5A05594CB1C3B74C8A9B4F94F5991
 * ============================================================================
 */

@Repository("brandDaoImpl")
public class BrandDaoImpl extends BaseDaoImpl<Brand, String> implements BrandDao {
	
	// 关联处理
	@Override
	public void delete(Brand brand) {
		Set<Goods> goodsSet = brand.getGoodsSet();
		if (goodsSet != null) {
			for (Goods goods : goodsSet) {
				goods.setBrand(null);
			}
		}
		
		Set<GoodsType> goodsTypeSet = brand.getGoodsTypeSet();
		if (goodsTypeSet != null) {
			for (GoodsType goodsType : goodsTypeSet) {
				goodsType.getBrandSet().remove(brand);
			}
		}
		
		super.delete(brand);
	}

	// 关联处理
	@Override
	public void delete(String id) {
		Brand brand = load(id);
		this.delete(brand);
	}

	// 关联处理
	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			Brand brand = load(id);
			this.delete(brand);
		}
	}
	
}