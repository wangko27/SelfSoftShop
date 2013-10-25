package net.shopxx.action.admin;

import java.util.Set;

import javax.annotation.Resource;

import net.shopxx.entity.Member;
import net.shopxx.entity.MemberRank;
import net.shopxx.service.MemberRankService;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 会员等级
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX01E1B0AF20100A00A008418505742C55
 * ============================================================================
 */

@ParentPackage("admin")
public class MemberRankAction extends BaseAdminAction {

	private static final long serialVersionUID = -5451875129461788865L;

	private MemberRank memberRank;

	@Resource(name = "memberRankServiceImpl")
	private MemberRankService memberRankService;

	// 是否已存在 ajax验证
	public String checkName() {
		String oldValue = getParameter("oldValue");
		String newValue = memberRank.getName();
		if (memberRankService.isUniqueByName(oldValue, newValue)) {
			return ajax("true");
		} else {
			return ajax("false");
		}
	}

	// 列表
	public String list() {
		pager = memberRankService.findPager(pager);
		return LIST;
	}

	// 删除
	public String delete() {
		for (String id : ids) {
			MemberRank memberRank = memberRankService.load(id);
			Set<Member> memberSet = memberRank.getMemberSet();
			if (memberSet != null && memberSet.size() > 0) {
				return ajax(Status.error, "会员等级[" + memberRank.getName() + "]下存在会员,删除失败!");
			}
		}
		long totalCount = memberRankService.getTotalCount();
		if (ids.length >= totalCount) {
			return ajax(Status.error, "删除失败!必须至少保留一个会员等级");
		}
		memberRankService.delete(ids);
		return ajax(Status.success, "删除成功!");
	}

	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		memberRank = memberRankService.load(id);
		return INPUT;
	}

	// 保存
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "memberRank.name", message = "等级名称不允许为空!")
		}, 
		requiredFields = { 
			@RequiredFieldValidator(fieldName = "memberRank.preferentialScale", message = "优惠百分比不允许为空!"),
			@RequiredFieldValidator(fieldName = "memberRank.score", message = "所需积分不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "memberRank.score", min = "0", message = "所需积分只允许为正整数或零!")
		}
	)
	@InputConfig(resultName = "error")
	public String save() {
		if (memberRankService.isExistByName(memberRank.getName())) {
			addActionError("名称已存在!");
			return ERROR;
		}
		if (memberRank.getPreferentialScale() < 0) {
			addActionError("优惠百分比必须大于或等于零!");
			return ERROR;
		}
		if (memberRankService.getMemberRankByScore(memberRank.getScore()) != null) {
			addActionError("已存在相同积分的会员等级!");
			return ERROR;
		}
		memberRankService.save(memberRank);
		redirectUrl = "member_rank!list.action";
		return SUCCESS;
	}

	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "memberRank.name", message = "等级名称不允许为空!")
		}, 
		requiredFields = { 
			@RequiredFieldValidator(fieldName = "memberRank.preferentialScale", message = "优惠百分比不允许为空!"),
			@RequiredFieldValidator(fieldName = "memberRank.score", message = "所需积分不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "memberRank.score", min = "0", message = "所需积分只允许为正整数或零!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() {
		MemberRank persistent = memberRankService.load(id);
		if (!memberRankService.isUniqueByName(persistent.getName(), memberRank.getName())) {
			addActionError("名称已存在!");
			return ERROR;
		}
		if (memberRank.getPreferentialScale() < 0) {
			addActionError("优惠百分比必须大于或等于零!");
			return ERROR;
		}
		MemberRank equalScoreMemberRank = memberRankService.getMemberRankByScore(memberRank.getScore());
		if (equalScoreMemberRank != null && equalScoreMemberRank != persistent) {
			addActionError("已存在相同积分的会员等级!");
			return ERROR;
		}
		BeanUtils.copyProperties(memberRank, persistent, new String[] {"id", "createDate", "modifyDate", "memberSet"});
		memberRankService.update(persistent);
		redirectUrl = "member_rank!list.action";
		return SUCCESS;
	}

	public MemberRank getMemberRank() {
		return memberRank;
	}

	public void setMemberRank(MemberRank memberRank) {
		this.memberRank = memberRank;
	}

}