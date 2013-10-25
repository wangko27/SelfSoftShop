package net.shopxx.service.impl;

import javax.annotation.Resource;

import net.shopxx.dao.DeliveryCenterDao;
import net.shopxx.entity.DeliveryCenter;
import net.shopxx.service.DeliveryCenterService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service实现类 - 发货点
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXEEB47174264A6259B9A1BA5D141B72C0
 * ============================================================================
 */

@Service("deliveryCenterServiceImpl")
public class DeliveryCenterServiceImpl extends BaseServiceImpl<DeliveryCenter, String> implements DeliveryCenterService {
	
	@Resource(name = "deliveryCenterDaoImpl")
	private DeliveryCenterDao deliveryCenterDao;
	
	@Resource(name = "deliveryCenterDaoImpl")
	public void setBaseDao(DeliveryCenterDao deliveryCenterDao) {
		super.setBaseDao(deliveryCenterDao);
	}
	
	@Transactional(readOnly = true)
	public DeliveryCenter getDefaultDeliveryCenter() {
		return deliveryCenterDao.getDefaultDeliveryCenter();
	}

}