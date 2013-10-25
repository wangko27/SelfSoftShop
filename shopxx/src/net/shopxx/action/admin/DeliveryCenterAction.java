package net.shopxx.action.admin;

import javax.annotation.Resource;

import net.shopxx.entity.Area;
import net.shopxx.entity.DeliveryCenter;
import net.shopxx.service.AreaService;
import net.shopxx.service.DeliveryCenterService;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 发货点
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXE5A2C60595A4C64534D70AC9A5B94FD
 * ============================================================================
 */

@ParentPackage("admin")
public class DeliveryCenterAction extends BaseAdminAction {

	private static final long serialVersionUID = 7833922325834476473L;

	private String areaId;
	private DeliveryCenter deliveryCenter;

	@Resource(name = "deliveryCenterServiceImpl")
	private DeliveryCenterService deliveryCenterService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;

	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		deliveryCenter = deliveryCenterService.load(id);
		return INPUT;
	}

	// 列表
	public String list() {
		pager = deliveryCenterService.findPager(pager);
		return LIST;
	}

	// 删除
	public String delete() {
		deliveryCenterService.delete(ids);
		return ajax(Status.success, "删除成功!");
	}

	// 保存
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "deliveryCenter.name", message = "发货点名称不允许为空!"),
			@RequiredStringValidator(fieldName = "deliveryCenter.consignor", message = "发货人不允许为空!"),
			@RequiredStringValidator(fieldName = "areaId", message = "地区不允许为空!"),
			@RequiredStringValidator(fieldName = "deliveryCenter.address", message = "地址不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String save() {
		Area area = areaService.get(areaId);
		if (area == null) {
			addActionError("请选择地区!");
			return ERROR;
		}
		deliveryCenter.setArea(area);
		deliveryCenterService.save(deliveryCenter);
		redirectUrl = "delivery_center!list.action";
		return SUCCESS;
	}

	// 更新
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "deliveryCenter.name", message = "发货点名称不允许为空!"),
			@RequiredStringValidator(fieldName = "deliveryCenter.consignor", message = "发货人不允许为空!"),
			@RequiredStringValidator(fieldName = "areaId", message = "地区不允许为空!"),
			@RequiredStringValidator(fieldName = "deliveryCenter.address", message = "地址不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() {
		Area area = areaService.get(areaId);
		if (area == null) {
			addActionError("请选择地区!");
			return ERROR;
		}
		deliveryCenter.setArea(area);
		DeliveryCenter persistent = deliveryCenterService.load(id);
		BeanUtils.copyProperties(deliveryCenter, persistent, new String[] {"id", "createDate", "modifyDate"});
		deliveryCenterService.update(persistent);
		redirectUrl = "delivery_center!list.action";
		return SUCCESS;
	}

	public DeliveryCenter getDeliveryCenter() {
		return deliveryCenter;
	}

	public void setDeliveryCenter(DeliveryCenter deliveryCenter) {
		this.deliveryCenter = deliveryCenter;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

}