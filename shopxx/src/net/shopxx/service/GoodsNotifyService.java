package net.shopxx.service;

import net.shopxx.bean.Pager;
import net.shopxx.entity.GoodsNotify;
import net.shopxx.entity.Member;

/**
 * Service接口 - 到货通知
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX868582BB07E8457F3171FCCADB94B449
 * ============================================================================
 */

public interface GoodsNotifyService extends BaseService<GoodsNotify, String> {

	/**
	 * 根据Member、Pager获取到货通知分页对象
	 * 
	 * @param member
	 *            Member对象
	 *            
	 * @return 到货通知分页对象
	 */
	public Pager findPager(Member member, Pager pager);
	
	/**
	 * 获取未处理缺货登记数
	 *            
	 * @return 未处理缺货登记数
	 */
	public Long getUnprocessedGoodsNotifyCount();
	
}