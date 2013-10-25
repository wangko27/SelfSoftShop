package net.shopxx.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.shopxx.bean.Pager;
import net.shopxx.entity.Brand;
import net.shopxx.entity.Goods;
import net.shopxx.entity.GoodsAttribute;
import net.shopxx.entity.GoodsCategory;
import net.shopxx.entity.Member;

/**
 * Dao接口 - 商品
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

public interface GoodsDao extends BaseDao<Goods, String> {
	
	/**
	 * 判断商品编号是否存在（不区分大小写）
	 * 
	 * @param goodsSn
	 *            商品编号
	 */
	public boolean isExistByGoodsSn(String goodsSn);
	
	/**
	 * 根据商品分类、商品类型、是否包含子分类商品、最大结果数获取商品集合（只包含isMarketable=true的对象）
	 * 
	 * @param goodsCategory
	 *            商品分类,null表示无限制
	 *            
	 * @param type
	 *            商品类型,null表示无限制
	 *            
	 * @param isContainChildren
	 *            是否包含子分类商品
	 * 
	 * @param maxResults
	 *            最大结果数,null表示无限制
	 * 
	 * @return 此分类下的商品集合
	 */
	public List<Goods> getGoodsList(GoodsCategory goodsCategory, String type, boolean isContainChildren, Integer maxResults);
	
	/**
	 * 根据商品分类、起始日期、结束日期、起始结果数、最大结果数获取商品集合（只包含isMarketable=true的对象,包含子分类商品）
	 * 
	 * @param goodsCategory
	 *            商品分类,null表示无限制
	 *            
	 * @param beginDate
	 *            起始日期,null表示无限制
	 *            
	 * @param endDate
	 *            结束日期,null表示无限制
	 *            
	 * @param firstResult
	 *            起始结果数,null表示无限制
	 *            
	 * @param maxResults
	 *            最大结果数,null表示无限制
	 * 
	 * @return 此分类下的所有商品集合
	 */
	public List<Goods> getGoodsList(GoodsCategory goodsCategory, Date beginDate, Date endDate, Integer firstResult, Integer maxResults);
	
	/**
	 * 根据GoodsCategory和Pager对象,获取此分类下的商品分页对象（只包含isMarketable=true的对象,包含子分类商品）
	 * 
	 * @param goodsCategory
	 *            商品分类
	 *            
	 * @param pager
	 *            分页对象
	 * 
	 * @return Pager
	 */
	public Pager getGoodsPager(GoodsCategory goodsCategory, Pager pager);
	
	/**
	 * 根据商品分类、商品品牌、商品属性Map、Pager,获取此分类下的商品分页对象（只包含isMarketable=true的对象,包含子分类商品）
	 * 
	 * @param goodsCategory
	 *            商品分类,null表示无限制
	 *            
	 * @param brand
	 *            商品品牌,null表示无限制
	 *    
	 * @param goodsAttributeMap
	 *            商品属性Map,null表示无限制
	 *            
	 * @param pager
	 *            分页对象
	 * 
	 * @return Pager
	 */
	public Pager getGoodsPager(GoodsCategory goodsCategory, Brand brand, Map<GoodsAttribute, String> goodsAttributeMap, Pager pager);
	
	/**
	 * 根据Member、Pager获取收藏商品分页对象
	 * 
	 * @param member
	 *            Member对象
	 *            
	 * @return 收藏商品分页对象
	 */
	public Pager getFavoriteGoodsPager(Member member, Pager pager);
	
	/**
	 * 获取已上架商品数
	 *            
	 * @return 已上架商品数
	 */
	public Long getMarketableGoodsCount();
	
	/**
	 * 获取已下架商品数
	 *            
	 * @return 已下架商品数
	 */
	public Long getUnMarketableGoodsCount();

}