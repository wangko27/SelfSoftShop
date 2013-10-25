package net.shopxx.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.shopxx.bean.Pager;
import net.shopxx.bean.Pager.Order;
import net.shopxx.dao.GoodsDao;
import net.shopxx.entity.Brand;
import net.shopxx.entity.Goods;
import net.shopxx.entity.GoodsAttribute;
import net.shopxx.entity.GoodsCategory;
import net.shopxx.entity.Member;
import net.shopxx.service.GoodsService;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.queryParser.QueryParser;
import org.compass.core.Compass;
import org.compass.core.CompassHits;
import org.compass.core.CompassQuery;
import org.compass.core.CompassQueryBuilder;
import org.compass.core.CompassSession;
import org.compass.core.CompassTemplate;
import org.compass.core.CompassQuery.SortDirection;
import org.compass.core.CompassQuery.SortPropertyType;
import org.compass.core.CompassQueryBuilder.CompassBooleanQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

/**
 * Service实现类 - 商品
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX603F6052C7BD6E1C6113519369127127
 * ============================================================================
 */

@Service("goodsServiceImpl")
public class GoodsServiceImpl extends BaseServiceImpl<Goods, String> implements GoodsService {

	@Resource(name = "goodsDaoImpl")
	private GoodsDao goodsDao;
	@Resource(name = "compassTemplate")
	private CompassTemplate compassTemplate;

	@Resource(name = "goodsDaoImpl")
	public void setBaseDao(GoodsDao goodsDao) {
		super.setBaseDao(goodsDao);
	}
	
	@Transactional(readOnly = true)
	public boolean isExistByGoodsSn(String goodsSn) {
		return goodsDao.isExistByGoodsSn(goodsSn);
	}
	
	@Transactional(readOnly = true)
	public boolean isUniqueByGoodsSn(String oldGoodsSn, String newGoodsSn) {
		if (StringUtils.equalsIgnoreCase(oldGoodsSn, newGoodsSn)) {
			return true;
		} else {
			if (goodsDao.isExistByGoodsSn(newGoodsSn)) {
				return false;
			} else {
				return true;
			}
		}
	}
	
	@Cacheable(modelId = "goodsCaching")
	@Transactional(readOnly = true)
	public List<Goods> getGoodsList(GoodsCategory goodsCategory, String type, boolean isContainChildren, Integer maxResults) {
		return goodsDao.getGoodsList(goodsCategory, type, isContainChildren, maxResults);
	}
	
	@Transactional(readOnly = true)
	public List<Goods> getGoodsList(GoodsCategory goodsCategory, Date beginDate, Date endDate, Integer firstResult, Integer maxResults) {
		return goodsDao.getGoodsList(goodsCategory, beginDate, endDate, firstResult, maxResults);
	}
	
	@Transactional(readOnly = true)
	public Pager getGoodsPager(GoodsCategory goodsCategory, Pager pager) {
		return goodsDao.getGoodsPager(goodsCategory, pager);
	}
	
	@Transactional(readOnly = true)
	public Pager getGoodsPager(GoodsCategory goodsCategory, Brand brand, Map<GoodsAttribute, String> goodsAttributeMap, Pager pager) {
		return goodsDao.getGoodsPager(goodsCategory, brand, goodsAttributeMap, pager);
	}
	
	@Transactional(readOnly = true)
	public Pager getFavoriteGoodsPager(Member member, Pager pager) {
		return goodsDao.getFavoriteGoodsPager(member, pager);
	}
	
	@Transactional(readOnly = true)
	public Long getMarketableGoodsCount() {
		return goodsDao.getMarketableGoodsCount();
	}
	
	@Transactional(readOnly = true)
	public Long getUnMarketableGoodsCount() {
		return goodsDao.getUnMarketableGoodsCount();
	}
	
	public Pager search(Pager pager) {
		Compass compass = compassTemplate.getCompass();
		CompassSession compassSession = compass.openSession();
		CompassQueryBuilder compassQueryBuilder = compassSession.queryBuilder();
		CompassBooleanQueryBuilder compassBooleanQueryBuilder = compassQueryBuilder.bool();

		compassBooleanQueryBuilder.addMust(compassQueryBuilder.term("isMarketable", true));
		compassBooleanQueryBuilder.addMust(compassQueryBuilder.queryString(QueryParser.escape(pager.getKeyword())).toQuery());
		
		CompassQuery compassQuery = compassBooleanQueryBuilder.toQuery();
		
		String orderBy = pager.getOrderBy();
		Order order = pager.getOrder();
		if (StringUtils.isEmpty(orderBy) || order == null) {
			compassQuery.addSort("isBest", SortPropertyType.STRING, SortDirection.REVERSE);
			compassQuery.addSort("isNew", SortPropertyType.STRING, SortDirection.REVERSE);
			compassQuery.addSort("isHot", SortPropertyType.STRING, SortDirection.REVERSE);
		} else {
			if (StringUtils.equalsIgnoreCase(orderBy, "price")) {
				if (order == Order.asc) {
					compassQuery.addSort("price", SortPropertyType.FLOAT);
				} else {
					compassQuery.addSort("price", SortPropertyType.FLOAT, SortDirection.REVERSE);
				}
			}
		}
		
		CompassHits compassHits = compassQuery.hits();
		List<Goods> result = new ArrayList<Goods>();
		int firstResult = (pager.getPageNumber() - 1) * pager.getPageSize();
		int maxReasults = pager.getPageSize();
		int totalCount = compassHits.length();

		int end = Math.min(totalCount, firstResult + maxReasults);
		for (int i = firstResult; i < end; i++) {
			Goods goods = (Goods) compassHits.data(i);
			result.add(goods);
		}
		compassSession.close();
		pager.setResult(result);
		pager.setTotalCount(totalCount);
		return pager;
	}

	@Override
	@CacheFlush(modelId = "goodsFlushing")
	public void delete(Goods goods) {
		super.delete(goods);
	}

	@Override
	@CacheFlush(modelId = "goodsFlushing")
	public void delete(String id) {
		super.delete(id);
	}

	@Override
	@CacheFlush(modelId = "goodsFlushing")
	public void delete(String[] ids) {
		super.delete(ids);
	}

	@Override
	@CacheFlush(modelId = "goodsFlushing")
	public String save(Goods goods) {
		return super.save(goods);
	}

	@Override
	@CacheFlush(modelId = "goodsFlushing")
	public void update(Goods goods) {
		super.update(goods);
	}

}