package net.shopxx.service.impl;

import javax.annotation.Resource;

import net.shopxx.dao.MemberRankDao;
import net.shopxx.entity.MemberRank;
import net.shopxx.service.MemberRankService;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

/**
 * Service实现类 - 会员分类
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX28C07EAA1399BE22D668354ED06ACC37
 * ============================================================================
 */

@Service("memberRankServiceImpl")
public class MemberRankServiceImpl extends BaseServiceImpl<MemberRank, String> implements MemberRankService {
	
	@Resource(name = "memberRankDaoImpl")
	MemberRankDao memberRankDao;

	@Resource(name = "memberRankDaoImpl")
	public void setBaseDao(MemberRankDao memberRankDao) {
		super.setBaseDao(memberRankDao);
	}
	
	@Transactional(readOnly = true)
	public boolean isExistByName(String name) {
		return memberRankDao.isExistByName(name);
	}
	
	@Transactional(readOnly = true)
	public boolean isUniqueByName(String oldName, String newName) {
		if (StringUtils.equalsIgnoreCase(oldName, newName)) {
			return true;
		} else {
			if (memberRankDao.isExistByName(newName)) {
				return false;
			} else {
				return true;
			}
		}
	}
	
	@Cacheable(modelId = "memberRankCaching")
	@Transactional(readOnly = true)
	public MemberRank getDefaultMemberRank() {
		MemberRank defaultMemberRank = memberRankDao.getDefaultMemberRank();
		if (defaultMemberRank != null && !Hibernate.isInitialized(defaultMemberRank)) {
			Hibernate.initialize(defaultMemberRank);
		}
		return defaultMemberRank;
	}
	
	@Transactional(readOnly = true)
	public MemberRank getMemberRankByScore(Integer score) {
		return memberRankDao.getMemberRankByScore(score);
	}
	
	@Transactional(readOnly = true)
	public MemberRank getUpMemberRankByScore(Integer score) {
		return memberRankDao.getUpMemberRankByScore(score);
	}

	@Override
	@CacheFlush(modelId = "memberRankFlushing")
	public void delete(MemberRank memberRank) {
		memberRankDao.delete(memberRank);
	}

	@Override
	@CacheFlush(modelId = "memberRankFlushing")
	public void delete(String id) {
		memberRankDao.delete(id);
	}

	@Override
	@CacheFlush(modelId = "memberRankFlushing")
	public void delete(String[] ids) {
		memberRankDao.delete(ids);
	}

	@Override
	@CacheFlush(modelId = "memberRankFlushing")
	public String save(MemberRank memberRank) {
		return memberRankDao.save(memberRank);
	}

	@Override
	@CacheFlush(modelId = "memberRankFlushing")
	public void update(MemberRank memberRank) {
		memberRankDao.update(memberRank);
	}

}