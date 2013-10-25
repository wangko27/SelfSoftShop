package net.shopxx.service.impl;

import javax.annotation.Resource;

import net.shopxx.bean.Pager;
import net.shopxx.dao.OrderDao;
import net.shopxx.entity.Member;
import net.shopxx.entity.Order;
import net.shopxx.service.OrderService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service实现类 - 订单
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXDD7076EB801A97081A271D1D0D7AF8F7
 * ============================================================================
 */

@Service("orderServiceImpl")
public class OrderServiceImpl extends BaseServiceImpl<Order, String> implements OrderService {
	
	@Resource(name = "orderDaoImpl")
	private OrderDao orderDao;

	@Resource(name = "orderDaoImpl")
	public void setBaseDao(OrderDao orderDao) {
		super.setBaseDao(orderDao);
	}
	
	@Transactional(readOnly = true)
	public String getLastOrderSn() {
		return orderDao.getLastOrderSn();
	}
	
	@Transactional(readOnly = true)
	public Pager getOrderPager(Member member, Pager pager) {
		return orderDao.getOrderPager(member, pager);
	}
	
	@Transactional(readOnly = true)
	public Long getUnprocessedOrderCount() {
		return orderDao.getUnprocessedOrderCount();
	}
	
	@Transactional(readOnly = true)
	public Long getPaidUnshippedOrderCount() {
		return orderDao.getPaidUnshippedOrderCount();
	}

}