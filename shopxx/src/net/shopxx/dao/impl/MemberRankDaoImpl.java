package net.shopxx.dao.impl;

import java.util.List;

import net.shopxx.dao.MemberRankDao;
import net.shopxx.entity.MemberRank;

import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 会员分类
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX1FC43548A8F1563DB9E55475E0AE0011
 * ============================================================================
 */

@Repository("memberRankDaoImpl")
public class MemberRankDaoImpl extends BaseDaoImpl<MemberRank, String> implements MemberRankDao {
	
	public boolean isExistByName(String name) {
		String hql = "from MemberRank as memberRank where lower(memberRank.name) = lower(:name)";
		MemberRank memberRank = (MemberRank) getSession().createQuery(hql).setParameter("name", name).uniqueResult();
		if (memberRank != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public MemberRank getDefaultMemberRank() {
		String hql = "from MemberRank as memberRank where memberRank.isDefault = :isDefault";
		MemberRank defaultMemberRank = (MemberRank) getSession().createQuery(hql).setParameter("isDefault", true).uniqueResult();
		if(defaultMemberRank == null) {
			hql = "from MemberRank as memberRank order by memberRank.createDate asc";
			defaultMemberRank = (MemberRank) getSession().createQuery(hql).setMaxResults(1).uniqueResult();
		}
		return defaultMemberRank;
	}
	
	public MemberRank getMemberRankByScore(Integer score) {
		String hql = "from MemberRank as memberRank where memberRank.score = :score";
		return (MemberRank) getSession().createQuery(hql).setParameter("score", score).uniqueResult();
	}
	
	public MemberRank getUpMemberRankByScore(Integer score) {
		String hql = "from MemberRank as memberRank where memberRank.score <= :score order by memberRank.score desc";
		return (MemberRank) getSession().createQuery(hql).setParameter("score", score).setMaxResults(1).uniqueResult();
	}
	
	// 保存时若对象isDefault=true,则设置其它对象isDefault值为false
	@Override
	@SuppressWarnings("unchecked")
	public String save(MemberRank memberRank) {
		if (memberRank.getIsDefault()) {
			String hql = "from MemberRank as memberRank where memberRank.isDefault = :isDefault";
			List<MemberRank> memberRankList = getSession().createQuery(hql).setParameter("isDefault", true).list();
			if (memberRankList != null) {
				for (MemberRank r : memberRankList) {
					r.setIsDefault(false);
				}
			}
		}
		return super.save(memberRank);
	}

	// 更新时若对象isDefault=true,则设置其它对象isDefault值为false
	@Override
	@SuppressWarnings("unchecked")
	public void update(MemberRank memberRank) {
		if (memberRank.getIsDefault()) {
			String hql = "from MemberRank as memberRank where memberRank.isDefault = :isDefault and memberRank != :memberRank";
			List<MemberRank> memberRankList = getSession().createQuery(hql).setParameter("isDefault", true).setParameter("memberRank", memberRank).list();
			if (memberRankList != null) {
				for (MemberRank r : memberRankList) {
					r.setIsDefault(false);
				}
			}
		}
		super.update(memberRank);
	}

}