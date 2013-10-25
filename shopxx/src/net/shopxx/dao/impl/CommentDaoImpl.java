package net.shopxx.dao.impl;

import java.util.List;

import net.shopxx.bean.Pager;
import net.shopxx.dao.CommentDao;
import net.shopxx.entity.Comment;
import net.shopxx.entity.Goods;

import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 评论
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXF443BAE0CC7277A3DD71774267D31005
 * ============================================================================
 */

@Repository("commentDaoImpl")
public class CommentDaoImpl extends BaseDaoImpl<Comment, String> implements CommentDao {
	
	public Pager getCommentPager(Pager pager) {
		return super.findPager(pager, Restrictions.isNull("forComment"));
	}
	
	public Pager getCommentPager(Pager pager, Goods goods) {
		return super.findPager(pager, Restrictions.isNull("forComment"), Restrictions.eq("isShow", true), Restrictions.eq("goods", goods));
	}
	
	@SuppressWarnings("unchecked")
	public List<Comment> getCommentList(Goods goods, Integer maxResults) {
		Query query = null;
		if (goods != null) {
			String hql = "from Comment as comment where comment.isShow = :isShow and comment.forComment is null and comment.goods = :goods order by comment.createDate desc";
			query = getSession().createQuery(hql);
			query.setParameter("isShow", true).setParameter("goods", goods);
		} else {
			String hql = "from Comment as comment where comment.isShow = :isShow and comment.forComment is null order by comment.createDate desc";
			query = getSession().createQuery(hql);
			query.setParameter("isShow", true);
		}
		if (maxResults != null) {
			query.setMaxResults(maxResults);
		}
		return query.list();
	}
	
}