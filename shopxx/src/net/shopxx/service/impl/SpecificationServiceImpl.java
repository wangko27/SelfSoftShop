package net.shopxx.service.impl;

import javax.annotation.Resource;

import net.shopxx.bean.SpecificationValue;
import net.shopxx.dao.SpecificationDao;
import net.shopxx.entity.Specification;
import net.shopxx.service.SpecificationService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service实现类 - 商品规格
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX2B3A4FEF05A42E033E79040EB368B28E
 * ============================================================================
 */

@Service("specificationServiceImpl")
public class SpecificationServiceImpl extends BaseServiceImpl<Specification, String> implements SpecificationService {
	
	@Resource(name = "specificationDaoImpl")
	private SpecificationDao specificationDao;

	@Resource(name = "specificationDaoImpl")
	public void setBaseDao(SpecificationDao specificationDao) {
		super.setBaseDao(specificationDao);
	}
	
	@Transactional(readOnly = true)
	public SpecificationValue getSpecificationValue(String specificationId, String specificationValueId) {
		Specification specification = specificationDao.get(specificationId);
		for (SpecificationValue specificationValue : specification.getSpecificationValueList()) {
			if (specificationValue.getId().equals(specificationValueId)) {
				return specificationValue;
			}
		}
		return null;
	}

}