package net.shopxx.service.impl;

import java.util.List;

import javax.annotation.Resource;

import net.shopxx.dao.PaymentConfigDao;
import net.shopxx.entity.PaymentConfig;
import net.shopxx.service.PaymentConfigService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

/**
 * Service实现类 - 支付方式配置
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXC5ECD51B27A3F8CBD9F726E2B2CA7738
 * ============================================================================
 */

@Service("paymentConfigServiceImpl")
public class PaymentConfigServiceImpl extends BaseServiceImpl<PaymentConfig, String> implements PaymentConfigService {
	
	@Resource(name = "paymentConfigDaoImpl")
	private PaymentConfigDao paymentConfigDao;
	
	@Resource(name = "paymentConfigDaoImpl")
	public void setBaseDao(PaymentConfigDao PaymentConfigDao) {
		super.setBaseDao(PaymentConfigDao);
	}
	
	@Cacheable(modelId = "paymentConfigCaching")
	@Transactional(readOnly = true)
	public List<PaymentConfig> getNonDepositPaymentConfigList() {
		return paymentConfigDao.getNonDepositPaymentConfigList();
	}
	
	@Cacheable(modelId = "paymentConfigCaching")
	@Transactional(readOnly = true)
	public List<PaymentConfig> getNonDepositOfflinePaymentConfigList() {
		return paymentConfigDao.getNonDepositOfflinePaymentConfigList();
	}
	
	@Override
	@Cacheable(modelId = "paymentConfigCaching")
	@Transactional(readOnly = true)
	public List<PaymentConfig> getAllList() {
		return paymentConfigDao.getAllList();
	}

	@Override
	@CacheFlush(modelId = "paymentConfigFlushing")
	public void delete(PaymentConfig paymentConfig) {
		paymentConfigDao.delete(paymentConfig);
	}

	@Override
	@CacheFlush(modelId = "paymentConfigFlushing")
	public void delete(String id) {
		paymentConfigDao.delete(id);
	}

	@Override
	@CacheFlush(modelId = "paymentConfigFlushing")
	public void delete(String[] ids) {
		paymentConfigDao.delete(ids);
	}

	@Override
	@CacheFlush(modelId = "paymentConfigFlushing")
	public String save(PaymentConfig paymentConfig) {
		return paymentConfigDao.save(paymentConfig);
	}

	@Override
	@CacheFlush(modelId = "paymentConfigFlushing")
	public void update(PaymentConfig paymentConfig) {
		paymentConfigDao.update(paymentConfig);
	}

}