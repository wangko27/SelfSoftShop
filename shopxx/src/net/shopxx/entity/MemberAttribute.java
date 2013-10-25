package net.shopxx.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Transient;

import net.shopxx.util.JsonUtil;

import org.apache.commons.lang.StringUtils;

/**
 * 实体类 - 会员注册项
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX9F8BFD982E658EA8305195D192FB5E35
 * ============================================================================
 */

@Entity
public class MemberAttribute extends BaseEntity {

	private static final long serialVersionUID = 4513705276569738136L;
	
	// 系统默认注册项类型（姓名、性别、出生日期、地区、地址、邮编、电话、手机）
	public enum SystemAttributeType {
		name, gender, birth, area, address, zipCode, phone, mobile
	}
	
	// 自定义注册项类型（文本、数字、字母、单选项、多选项）
	public enum AttributeType {
		text, number, alphaint, select, checkbox
	}
	
	private String name;// 注册项名称
	private SystemAttributeType systemAttributeType;// 系统默认注册项类型
	private AttributeType attributeType;// 自定义注册项类型
	private String optionStore;// 可选项储存
	private Boolean isRequired;// 是否必填
	private Boolean isEnabled;// 是否启用
	private Integer orderList;// 排序
	private Integer propertyIndex;// 对象属性序号

	@Column(nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Enumerated
	@Column(updatable = false)
	public SystemAttributeType getSystemAttributeType() {
		return systemAttributeType;
	}

	public void setSystemAttributeType(SystemAttributeType systemAttributeType) {
		this.systemAttributeType = systemAttributeType;
	}

	@Enumerated
	@Column(updatable = false)
	public AttributeType getAttributeType() {
		return attributeType;
	}

	public void setAttributeType(AttributeType attributeType) {
		this.attributeType = attributeType;
	}
	
	public String getOptionStore() {
		return optionStore;
	}

	public void setOptionStore(String optionStore) {
		this.optionStore = optionStore;
	}

	@Column(nullable = false)
	public Boolean getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(Boolean isRequired) {
		this.isRequired = isRequired;
	}

	@Column(nullable = false)
	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public Integer getOrderList() {
		return orderList;
	}

	public void setOrderList(Integer orderList) {
		this.orderList = orderList;
	}
	
	@Column(nullable = false)
	public Integer getPropertyIndex() {
		return propertyIndex;
	}

	public void setPropertyIndex(Integer propertyIndex) {
		this.propertyIndex = propertyIndex;
	}
	
	// 获取可选项
	@SuppressWarnings("unchecked")
	@Transient
	public List<String> getOptionList() {
		if (StringUtils.isEmpty(optionStore)) {
			return null;
		}
		return JsonUtil.toObject(optionStore, ArrayList.class);
	}
	
	// 设置可选项
	@Transient
	public void setOptionList(List<String> optionList) {
		if (optionList == null || optionList.size() == 0) {
			optionStore = null;
			return;
		}
		optionStore = JsonUtil.toJson(optionList);
	}
	
	// 保存处理
	@Override
	@Transient
	public void onSave() {
		if (isRequired == null) {
			isRequired = false;
		}
		if (isEnabled == null) {
			isEnabled = false;
		}
		if (orderList == null || orderList < 0) {
			orderList = 0;
		}
	}
	
	// 更新处理
	@Override
	@Transient
	public void onUpdate() {
		if (isRequired == null) {
			isRequired = false;
		}
		if (isEnabled == null) {
			isEnabled = false;
		}
		if (orderList == null || orderList < 0) {
			orderList = 0;
		}
	}

}