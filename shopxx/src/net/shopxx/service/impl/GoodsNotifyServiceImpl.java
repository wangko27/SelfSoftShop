package net.shopxx.service.impl;

import javax.annotation.Resource;

import net.shopxx.bean.Pager;
import net.shopxx.dao.GoodsNotifyDao;
import net.shopxx.entity.GoodsNotify;
import net.shopxx.entity.Member;
import net.shopxx.service.GoodsNotifyService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service实现类 - 到货通知
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX9A3ACBDD2D3A5E96E54D057769342EAF
 * ============================================================================
 */

@Service("goodsNotifyServiceImpl")
public class GoodsNotifyServiceImpl extends BaseServiceImpl<GoodsNotify, String> implements GoodsNotifyService {

	@Resource(name = "goodsNotifyDaoImpl")
	private GoodsNotifyDao goodsNotifyDao;

	@Resource(name = "goodsNotifyDaoImpl")
	public void setBaseDao(GoodsNotifyDao goodsNotifyDao) {
		super.setBaseDao(goodsNotifyDao);
	}
	
	@Transactional(readOnly = true)
	public Pager findPager(Member member, Pager pager) {
		return goodsNotifyDao.findPager(member, pager);
	}
	
	@Transactional(readOnly = true)
	public Long getUnprocessedGoodsNotifyCount() {
		return goodsNotifyDao.getUnprocessedGoodsNotifyCount();
	}

}