package net.shopxx.action.admin;

import java.io.File;

import javax.annotation.Resource;

import net.shopxx.entity.FriendLink;
import net.shopxx.service.FriendLinkService;
import net.shopxx.util.ImageUtil;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 友情链接
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX2C31672600793CF1B08F7481F47E77D6
 * ============================================================================
 */

@ParentPackage("admin")
public class FriendLinkAction extends BaseAdminAction {
	
	private static final long serialVersionUID = -1618646588525569834L;
	
	private FriendLink friendLink;
	private File logo;

	@Resource(name = "friendLinkServiceImpl")
	private FriendLinkService friendLinkService;
	
	// 添加
	public String add() {
		return INPUT;
	}
	
	// 编辑
	public String edit() {
		friendLink = friendLinkService.load(id);
		return INPUT;
	}

	// 列表
	public String list() {
		pager = friendLinkService.findPager(pager);
		return LIST;
	}

	// 删除
	public String delete() {
		friendLinkService.delete(ids);
		return ajax(Status.success, "删除成功!");
	}

	// 保存
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "friendLink.name", message = "友情链接名称不允许为空!"),
			@RequiredStringValidator(fieldName = "friendLink.url", message = "链接地址不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "friendLink.orderList", min = "0", message = "排序必须为零或正整数!")
		}
	)
	@InputConfig(resultName = "error")
	public String save() throws Exception {
		if (logo != null) {
			String logoPath = ImageUtil.copyImageFile(getServletContext(), logo);
			friendLink.setLogoPath(logoPath);
		} else {
			friendLink.setLogoPath(null);
		}
		friendLinkService.save(friendLink);
		redirectUrl = "friend_link!list.action";
		return SUCCESS;
	}
	
	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "friendLink.name", message = "友情链接名称不允许为空!"),
			@RequiredStringValidator(fieldName = "friendLink.url", message = "链接地址不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "friendLink.orderList", min = "0", message = "排序必须为零或正整数!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() throws Exception {
		FriendLink persistent = friendLinkService.load(id);
		if (logo != null) {
			String logoPath = ImageUtil.copyImageFile(getServletContext(), logo);
			persistent.setLogoPath(logoPath);
		}
		BeanUtils.copyProperties(friendLink, persistent, new String[]{"id", "createDate", "modifyDate", "logoPath"});
		friendLinkService.update(persistent);
		redirectUrl = "friend_link!list.action";
		return SUCCESS;
	}

	public FriendLink getFriendLink() {
		return friendLink;
	}

	public void setFriendLink(FriendLink friendLink) {
		this.friendLink = friendLink;
	}

	public File getLogo() {
		return logo;
	}

	public void setLogo(File logo) {
		this.logo = logo;
	}

}