package net.shopxx.service.impl;

import javax.annotation.Resource;

import net.shopxx.dao.ReshipDao;
import net.shopxx.entity.Reship;
import net.shopxx.service.ReshipService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service实现类 - 退货
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX14093C23520A786815DFA8C679BCDD5F
 * ============================================================================
 */

@Service("reshipServiceImpl")
public class ReshipServiceImpl extends BaseServiceImpl<Reship, String> implements ReshipService {
	
	@Resource(name = "reshipDaoImpl")
	private ReshipDao reshipDao;

	@Resource(name = "reshipDaoImpl")
	public void setBaseDao(ReshipDao reshipDao) {
		super.setBaseDao(reshipDao);
	}
	
	@Transactional(readOnly = true)
	public String getLastReshipSn() {
		return reshipDao.getLastReshipSn();
	}

}