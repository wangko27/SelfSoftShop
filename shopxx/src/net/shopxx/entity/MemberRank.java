package net.shopxx.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 * 实体类 - 会员等级
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXFD5096449E0A05EE91B6802622B8C184
 * ============================================================================
 */

@Entity
public class MemberRank extends BaseEntity {

	private static final long serialVersionUID = 3599029355500655209L;

	private String name;// 等級名称
	private Double preferentialScale;// 优惠百分比
	private Integer score;// 所需积分
	private Boolean isDefault;// 是否为默认等级
	
	private Set<Member> memberSet = new HashSet<Member>();// 会员

	@Column(nullable = false, unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(nullable = false)
	public Double getPreferentialScale() {
		return preferentialScale;
	}

	public void setPreferentialScale(Double preferentialScale) {
		this.preferentialScale = preferentialScale;
	}
	
	@Column(nullable = false, unique = true)
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	@Column(nullable = false)
	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	@OneToMany(mappedBy = "memberRank", fetch = FetchType.LAZY)
	public Set<Member> getMemberSet() {
		return memberSet;
	}

	public void setMemberSet(Set<Member> memberSet) {
		this.memberSet = memberSet;
	}
	
	// 保存处理
	@Override
	@Transient
	public void onSave() {
		if (preferentialScale == null || preferentialScale < 0D) {
			preferentialScale = 0D;
		}
		if (score == null || score < 0) {
			score = 0;
		}
		if (isDefault == null) {
			isDefault = false;
		}
	}
	
	// 更新处理
	@Override
	@Transient
	public void onUpdate() {
		if (preferentialScale == null || preferentialScale < 0D) {
			preferentialScale = 0D;
		}
		if (score == null || score < 0) {
			score = 0;
		}
		if (isDefault == null) {
			isDefault = false;
		}
	}

}