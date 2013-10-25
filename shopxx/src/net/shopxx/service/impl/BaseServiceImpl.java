package net.shopxx.service.impl;

import java.io.Serializable;
import java.util.List;

import net.shopxx.bean.Pager;
import net.shopxx.dao.BaseDao;
import net.shopxx.service.BaseService;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service实现类 - Service实现类基类
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX2C6B29872C5AE19E73A129BBF01DFF82
 * ============================================================================
 */

@Transactional
public class BaseServiceImpl<T, PK extends Serializable> implements BaseService<T, PK> {

	private BaseDao<T, PK> baseDao;

	public BaseDao<T, PK> getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao<T, PK> baseDao) {
		this.baseDao = baseDao;
	}
	
	@Transactional(readOnly = true)
	public T get(PK id) {
		return baseDao.get(id);
	}

	@Transactional(readOnly = true)
	public T load(PK id) {
		return baseDao.load(id);
	}

	@Transactional(readOnly = true)
	public List<T> getAllList() {
		return baseDao.getAllList();
	}
	
	@Transactional(readOnly = true)
	public Long getTotalCount() {
		return baseDao.getTotalCount();
	}

	@Transactional
	public PK save(T entity) {
		return baseDao.save(entity);
	}

	@Transactional
	public void update(T entity) {
		baseDao.update(entity);
	}
	
	@Transactional
	public void delete(T entity) {
		baseDao.delete(entity);
	}

	@Transactional
	public void delete(PK id) {
		baseDao.delete(id);
	}

	@Transactional
	public void delete(PK[] ids) {
		baseDao.delete(ids);
	}

	@Transactional(readOnly = true)
	public void flush() {
		baseDao.flush();
	}

	@Transactional(readOnly = true)
	public void evict(Object object) {
		baseDao.evict(object);
	}
	
	@Transactional(readOnly = true)
	public void clear() {
		baseDao.clear();
	}
	
	@Transactional(readOnly = true)
	public Pager findPager(Pager pager) {
		return baseDao.findPager(pager);
	}
	
	@Transactional(readOnly = true)
	public Pager findPager(Pager pager, Criterion... criterions) {
		return baseDao.findPager(pager, criterions);
	}
	
	@Transactional(readOnly = true)
	public Pager findPager(Pager pager, Order... orders) {
		return baseDao.findPager(pager, orders);
	}
	
	@Transactional(readOnly = true)
	public Pager findPager(Pager pager, Criteria criteria) {
		return baseDao.findPager(pager, criteria);
	}

}