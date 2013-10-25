package net.shopxx.dao.impl;

import java.util.List;

import net.shopxx.dao.DeliveryCenterDao;
import net.shopxx.entity.DeliveryCenter;

import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 发货点
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX2A5505E6E90FBFF03FB4B33E05AE2E5C
 * ============================================================================
 */

@Repository("deliveryCenterDaoImpl")
public class DeliveryCenterDaoImpl extends BaseDaoImpl<DeliveryCenter, String> implements DeliveryCenterDao {
	
	public DeliveryCenter getDefaultDeliveryCenter() {
		String hql = "from DeliveryCenter as deliveryCenter where deliveryCenter.isDefault = :isDefault";
		DeliveryCenter defaultDeliveryCenter = (DeliveryCenter) getSession().createQuery(hql).setParameter("isDefault", true).uniqueResult();
		if(defaultDeliveryCenter == null) {
			hql = "from DeliveryCenter as deliveryCenter order by deliveryCenter.createDate asc";
			defaultDeliveryCenter = (DeliveryCenter) getSession().createQuery(hql).setMaxResults(1).uniqueResult();
		}
		return defaultDeliveryCenter;
	}
	
	// 保存时若对象isDefault=true,则设置其它对象isDefault值为false
	@Override
	@SuppressWarnings("unchecked")
	public String save(DeliveryCenter deliveryCenter) {
		if (deliveryCenter.getIsDefault()) {
			String hql = "from DeliveryCenter as deliveryCenter where deliveryCenter.isDefault = :isDefault";
			List<DeliveryCenter> deliveryCenterList = getSession().createQuery(hql).setParameter("isDefault", true).list();
			if (deliveryCenterList != null) {
				for (DeliveryCenter d : deliveryCenterList) {
					d.setIsDefault(false);
				}
			}
		}
		return super.save(deliveryCenter);
	}

	// 更新时若对象isDefault=true,则设置其它对象isDefault值为false
	@Override
	@SuppressWarnings("unchecked")
	public void update(DeliveryCenter deliveryCenter) {
		if (deliveryCenter.getIsDefault()) {
			String hql = "from DeliveryCenter as deliveryCenter where deliveryCenter.isDefault = :isDefault and deliveryCenter != :deliveryCenter";
			List<DeliveryCenter> deliveryCenterList = getSession().createQuery(hql).setParameter("isDefault", true).setParameter("deliveryCenter", deliveryCenter).list();
			if (deliveryCenterList != null) {
				for (DeliveryCenter d : deliveryCenterList) {
					d.setIsDefault(false);
				}
			}
		}
		super.update(deliveryCenter);
	}

}