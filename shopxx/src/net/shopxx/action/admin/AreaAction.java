package net.shopxx.action.admin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.shopxx.entity.Area;
import net.shopxx.service.AreaService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 地区
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXAECC1B974FB9EE5128F3B8A118CCEC03
 * ============================================================================
 */

@ParentPackage("admin")
public class AreaAction extends BaseAdminAction {

	private static final long serialVersionUID = 6254431866456845575L;

	private Area area;
	private String parentId;
	private Area parent;
	private List<Area> areaList = new ArrayList<Area>();

	@Resource(name = "areaServiceImpl")
	private AreaService areaService;

	// 添加
	public String add() {
		if (StringUtils.isNotEmpty(parentId)) {
			parent = areaService.load(parentId);
		}
		return INPUT;
	}

	// 编辑
	public String edit() {
		area = areaService.load(id);
		parent = area.getParent();
		return INPUT;
	}

	// 列表
	public String list() {
		if (StringUtils.isNotEmpty(parentId)) {
			parent = areaService.load(parentId);
			areaList = new ArrayList<Area>(parent.getChildren());
		} else {
			areaList = areaService.getRootAreaList();
		}
		return LIST;
	}

	// 删除
	public String delete() {
		areaService.delete(id);
		return ajax(Status.success, "地区删除成功!");
	}

	// 保存
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "area.name", message = "名称不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "area.orderList", min = "0", message = "排序必须为零或正整数!")
		}
	)
	@InputConfig(resultName = "error")
	public String save() {
		if (StringUtils.isNotEmpty(parentId)) {
			area.setParent(areaService.load(parentId));
		} else {
			area.setParent(null);
		}
		areaService.save(area);
		redirectUrl = "area!list.action?parentId=" + parentId;
		return SUCCESS;
	}

	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "area.name", message = "名称不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "area.orderList", min = "0", message = "排序必须为零或正整数!")
		}
	)
	public String update() {
		Area persistent = areaService.load(id);
		Area parent = persistent.getParent();
		if (parent != null) {
			parentId = parent.getId();
		}
		persistent.setName(area.getName());
		persistent.setOrderList(area.getOrderList());
		areaService.update(persistent);
		redirectUrl = "area!list.action?parentId=" + parentId;
		return SUCCESS;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<Area> getAreaList() {
		return areaList;
	}

	public void setAreaList(List<Area> areaList) {
		this.areaList = areaList;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Area getParent() {
		return parent;
	}

	public void setParent(Area parent) {
		this.parent = parent;
	}

}