package net.shopxx.service.impl;

import javax.annotation.Resource;

import net.shopxx.dao.RefundDao;
import net.shopxx.entity.Refund;
import net.shopxx.service.RefundService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service实现类 - 退款
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX33368CD07557B60629179EF8EDB49808
 * ============================================================================
 */

@Service("refundServiceImpl")
public class RefundServiceImpl extends BaseServiceImpl<Refund, String> implements RefundService {
	
	@Resource(name = "refundDaoImpl")
	private RefundDao refundDao;

	@Resource(name = "refundDaoImpl")
	public void setBaseDao(RefundDao refundDao) {
		super.setBaseDao(refundDao);
	}
	
	@Transactional(readOnly = true)
	public String getLastRefundSn() {
		return refundDao.getLastRefundSn();
	}
	
	@Transactional(readOnly = true)
	public Refund getRefundByRefundSn(String refundSn) {
		return refundDao.getRefundByRefundSn(refundSn);
	}

}