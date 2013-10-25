package net.shopxx.dao.impl;

import java.util.Set;

import net.shopxx.dao.GoodsTypeDao;
import net.shopxx.entity.Goods;
import net.shopxx.entity.GoodsType;

import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 商品类型
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX98FDAF6620D898936043F11DC7A029CC
 * ============================================================================
 */

@Repository("goodsTypeDaoImpl")
public class GoodsTypeDaoImpl extends BaseDaoImpl<GoodsType, String> implements GoodsTypeDao {
	
	// 关联处理
	@Override
	public void delete(GoodsType goodsType) {
		Set<Goods> goodsSet = goodsType.getGoodsSet();
		int i = 0;
		for (Goods goods : goodsSet) {
			goods.setGoodsType(null);
			goods.setGoodsAttributeValueToNull();
			if(i % 20 == 0) {
				flush();
				clear();
			}
			i ++;
		}
		super.delete(goodsType);
	}

	// 关联处理
	@Override
	public void delete(String id) {
		GoodsType goodsType = super.load(id);
		this.delete(goodsType);
	}

	// 关联处理
	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			GoodsType goodsType = super.load(id);
			this.delete(goodsType);
		}
	}

}