package net.shopxx.dao.impl;

import java.util.Date;
import java.util.List;

import net.shopxx.bean.Pager;
import net.shopxx.dao.ArticleDao;
import net.shopxx.entity.Article;
import net.shopxx.entity.ArticleCategory;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 文章
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX818712BD2CE092B961D2E741A622D073
 * ============================================================================
 */

@Repository("articleDaoImpl")
public class ArticleDaoImpl extends BaseDaoImpl<Article, String> implements ArticleDao {

	@SuppressWarnings("unchecked")
	public List<Article> getArticleList(ArticleCategory articleCategory, String type, boolean isContainChildren, Integer maxResults) {
		Query query = null;
		if (articleCategory != null) {
			if (StringUtils.equalsIgnoreCase(type, "top")) {
				if (isContainChildren) {
					String hql = "from Article as article where article.isPublication = :isPublication and article.isTop = :isTop and article.articleCategory.path like :path order by article.isTop desc, article.createDate desc";
					query = getSession().createQuery(hql);
					query.setParameter("isPublication", true).setParameter("isTop", true).setParameter("path", articleCategory.getPath() + "%");
				} else {
					String hql = "from Article as article where article.isPublication = :isPublication and article.articleCategory = :articleCategory and article.isTop = :isTop order by article.isTop desc, article.createDate desc";
					query = getSession().createQuery(hql);
					query.setParameter("isPublication", true).setParameter("articleCategory", articleCategory).setParameter("isTop", true);
				}
			} else if (StringUtils.equalsIgnoreCase(type, "recommend")) {
				if (isContainChildren) {
					String hql = "from Article as article where article.isPublication = :isPublication and article.isRecommend = :isRecommend and article.articleCategory.path like :path order by article.isTop desc, article.createDate desc";
					query = getSession().createQuery(hql);
					query.setParameter("isPublication", true).setParameter("isRecommend", true).setParameter("path", articleCategory.getPath() + "%");
				} else {
					String hql = "from Article as article where article.isPublication = :isPublication and article.articleCategory = :articleCategory and article.isRecommend = :isRecommend order by article.isTop desc, article.createDate desc";
					query = getSession().createQuery(hql);
					query.setParameter("isPublication", true).setParameter("articleCategory", articleCategory).setParameter("isRecommend", true);
				}
			} else if (StringUtils.equalsIgnoreCase(type, "hot")) {
				if (isContainChildren) {
					String hql = "from Article as article where article.isPublication = :isPublication and article.articleCategory.path like :path order by article.hits desc, article.createDate desc";
					query = getSession().createQuery(hql);
					query.setParameter("isPublication", true).setParameter("path", articleCategory.getPath() + "%");
				} else {
					String hql = "from Article as article where article.isPublication = :isPublication and article.articleCategory = :articleCategory order by article.hits desc, article.createDate desc";
					query = getSession().createQuery(hql);
					query.setParameter("isPublication", true).setParameter("articleCategory", articleCategory);
				}
			} else {
				if (isContainChildren) {
					String hql = "from Article as article where article.isPublication = :isPublication and article.articleCategory.path like :path order by article.isTop desc, article.createDate desc";
					query = getSession().createQuery(hql);
					query.setParameter("isPublication", true).setParameter("path", articleCategory.getPath() + "%");
				} else {
					String hql = "from Article as article where article.isPublication = :isPublication and article.articleCategory = :articleCategory order by article.isTop desc, article.createDate desc";
					query = getSession().createQuery(hql);
					query.setParameter("isPublication", true).setParameter("articleCategory", articleCategory);
				}
			}
		} else {
			if (StringUtils.equalsIgnoreCase(type, "top")) {
				String hql = "from Article as article where article.isPublication = :isPublication and article.isTop = :isTop order by article.isTop desc, article.createDate desc";
				query = getSession().createQuery(hql);
				query.setParameter("isPublication", true).setParameter("isTop", true);
			} else if (StringUtils.equalsIgnoreCase(type, "recommend")) {
				String hql = "from Article as article where article.isPublication = :isPublication and article.isRecommend = :isRecommend order by article.isTop desc, article.createDate desc";
				query = getSession().createQuery(hql);
				query.setParameter("isPublication", true).setParameter("isRecommend", true);
			} else if (StringUtils.equalsIgnoreCase(type, "hot")) {
				String hql = "from Article as article where article.isPublication = :isPublication order by article.hits desc, article.createDate desc";
				query = getSession().createQuery(hql);
				query.setParameter("isPublication", true);
			} else {
				String hql = "from Article as article where article.isPublication = :isPublication order by article.isTop desc, article.createDate desc";
				query = getSession().createQuery(hql);
				query.setParameter("isPublication", true);
			}
		}
		if (maxResults != null) {
			query.setMaxResults(maxResults);
		}
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Article> getArticleList(ArticleCategory articleCategory, Date beginDate, Date endDate, Integer firstResult, Integer maxResults) {
		Query query = null;
		if (articleCategory != null) {
			if (beginDate != null && endDate == null) {
				String hql = "from Article as article where article.isPublication = :isPublication and article.articleCategory.path like :path and article.createDate > :beginDate order by article.createDate desc";
				query = getSession().createQuery(hql);
				query.setParameter("isPublication", true).setParameter("path", articleCategory.getPath() + "%").setParameter("beginDate", beginDate);
			} else if (endDate != null && beginDate == null) {
				String hql = "from Article as article where article.isPublication = :isPublication and article.articleCategory.path like :path and article.createDate < :endDate order by article.createDate desc";
				query = getSession().createQuery(hql);
				query.setParameter("isPublication", true).setParameter("path", articleCategory.getPath() + "%").setParameter("endDate", endDate);
			} else if (endDate != null && beginDate != null) {
				String hql = "from Article as article where article.isPublication = :isPublication and article.articleCategory.path like :path and article.createDate > :beginDate and article.createDate < :endDate order by article.createDate desc";
				query = getSession().createQuery(hql);
				query.setParameter("isPublication", true).setParameter("path", articleCategory.getPath() + "%").setParameter("beginDate", beginDate).setParameter("endDate", endDate);
			} else {
				String hql = "from Article as article where article.isPublication = :isPublication and article.articleCategory.path like :path order by article.createDate desc";
				query = getSession().createQuery(hql);
				query.setParameter("isPublication", true).setParameter("path", articleCategory.getPath() + "%");
			}
		} else {
			if (beginDate != null && endDate == null) {
				String hql = "from Article as article where article.isPublication = :isPublication and article.createDate > :beginDate order by article.createDate desc";
				query = getSession().createQuery(hql);
				query.setParameter("isPublication", true).setParameter("beginDate", beginDate);
			} else if (endDate != null && beginDate == null) {
				String hql = "from Article as article where article.isPublication = :isPublication and article.createDate < :endDate order by article.createDate desc";
				query = getSession().createQuery(hql);
				query.setParameter("isPublication", true).setParameter("endDate", endDate);
			} else if (endDate != null && beginDate != null) {
				String hql = "from Article as article where article.isPublication = :isPublication and article.createDate > :beginDate and article.createDate < :endDate order by article.createDate desc";
				query = getSession().createQuery(hql);
				query.setParameter("isPublication", true).setParameter("beginDate", beginDate).setParameter("endDate", endDate);
			} else {
				String hql = "from Article as article where article.isPublication = :isPublication order by article.createDate desc";
				query = getSession().createQuery(hql);
				query.setParameter("isPublication", true);
			}
		}
		if (firstResult != null) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != null) {
			query.setMaxResults(maxResults);
		}
		return query.list();
	}
	
	public Pager getArticlePager(ArticleCategory articleCategory, Pager pager) {
		Criteria criteria = getSession().createCriteria(Article.class);
		criteria.createAlias("articleCategory", "articleCategory");
		criteria.add(Restrictions.eq("isPublication", true));
		criteria.add(Restrictions.or(Restrictions.eq("articleCategory", articleCategory), Restrictions.like("articleCategory.path", articleCategory.getPath() + "%")));
		return super.findPager(pager, criteria);
	}

}