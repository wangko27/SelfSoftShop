package net.shopxx.dao.impl;

import java.util.List;

import net.shopxx.dao.DeliveryTemplateDao;
import net.shopxx.entity.DeliveryTemplate;

import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 快递单模板
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

@Repository("deliveryTemplateDaoImpl")
public class DeliveryTemplateDaoImpl extends BaseDaoImpl<DeliveryTemplate, String> implements DeliveryTemplateDao {
	
	public DeliveryTemplate getDefaultDeliveryTemplate() {
		String hql = "from DeliveryTemplate as deliveryTemplate where deliveryTemplate.isDefault = :isDefault";
		DeliveryTemplate defaultDeliveryTemplate = (DeliveryTemplate) getSession().createQuery(hql).setParameter("isDefault", true).uniqueResult();
		if(defaultDeliveryTemplate == null) {
			hql = "from DeliveryTemplate as deliveryTemplate order by deliveryTemplate.createDate asc";
			defaultDeliveryTemplate = (DeliveryTemplate) getSession().createQuery(hql).setMaxResults(1).uniqueResult();
		}
		return defaultDeliveryTemplate;
	}
	
	// 保存时若对象isDefault=true,则设置其它对象isDefault值为false
	@Override
	@SuppressWarnings("unchecked")
	public String save(DeliveryTemplate deliveryTemplate) {
		if (deliveryTemplate.getIsDefault()) {
			String hql = "from DeliveryTemplate as deliveryTemplate where deliveryTemplate.isDefault = :isDefault";
			List<DeliveryTemplate> deliveryTemplateList = getSession().createQuery(hql).setParameter("isDefault", true).list();
			if (deliveryTemplateList != null) {
				for (DeliveryTemplate d : deliveryTemplateList) {
					d.setIsDefault(false);
				}
			}
		}
		return super.save(deliveryTemplate);
	}

	// 更新时若对象isDefault=true,则设置其它对象isDefault值为false
	@Override
	@SuppressWarnings("unchecked")
	public void update(DeliveryTemplate deliveryTemplate) {
		if (deliveryTemplate.getIsDefault()) {
			String hql = "from DeliveryTemplate as deliveryTemplate where deliveryTemplate.isDefault = :isDefault and deliveryTemplate != :deliveryTemplate";
			List<DeliveryTemplate> deliveryTemplateList = getSession().createQuery(hql).setParameter("isDefault", true).setParameter("deliveryTemplate", deliveryTemplate).list();
			if (deliveryTemplateList != null) {
				for (DeliveryTemplate d : deliveryTemplateList) {
					d.setIsDefault(false);
				}
			}
		}
		super.update(deliveryTemplate);
	}
	
}