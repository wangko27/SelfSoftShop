package net.shopxx.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import net.shopxx.util.JsonUtil;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.ForeignKey;

/**
 * 实体类 - 商品属性
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX683FD9A1F4936F9B8563BDB221B35259
 * ============================================================================
 */

@Entity
@JsonIgnoreProperties(value = {"JavassistLazyInitializer", "handler", "createDate", "modifyDate", "optionStore", "propertyIndex", "goodsType"})
public class GoodsAttribute extends BaseEntity {

	private static final long serialVersionUID = 2989102568413246570L;
	
	public static final String OPTION_TEXT_SEPARATOR = ",";// 可选项文本分隔符
	
	// 商品属性类型（筛选项、输入项）
	public enum AttributeType {
		filter, input
	}

	private String name;// 名称
	private AttributeType attributeType;// 属性类型
	private String optionStore;// 可选项储存
	private Integer orderList;// 排序
	private Integer propertyIndex;// 对象属性序号
	
	private GoodsType goodsType;// 商品类型

	@Column(nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(nullable = false, updatable = false)
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
	public Integer getPropertyIndex() {
		return propertyIndex;
	}

	public void setPropertyIndex(Integer propertyIndex) {
		this.propertyIndex = propertyIndex;
	}

	public Integer getOrderList() {
		return orderList;
	}

	public void setOrderList(Integer orderList) {
		this.orderList = orderList;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@ForeignKey(name = "fk_goods_attribute_goods_type")
	public GoodsType getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(GoodsType goodsType) {
		this.goodsType = goodsType;
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
	
	// 获取可选项字符串
	@SuppressWarnings("unchecked")
	@Transient
	public String getOptionText() {
		if (StringUtils.isEmpty(optionStore)) {
			return null;
		}
		List<String> optionList = JsonUtil.toObject(optionStore, ArrayList.class);
		return StringUtils.join(optionList, OPTION_TEXT_SEPARATOR);
	}
	
	// 设置可选项字符串
	@Transient
	public void setOptionText(String optionText) {
		if (StringUtils.isEmpty(optionText)) {
			optionStore = null;
			return;
		}
		optionStore = JsonUtil.toJson(optionText.split(OPTION_TEXT_SEPARATOR));
	}
	
	// 保存处理
	@Override
	@Transient
	public void onSave() {
		if (orderList == null || orderList < 0) {
			orderList = 0;
		}
	}
	
	// 更新处理
	@Override
	@Transient
	public void onUpdate() {
		if (orderList == null || orderList < 0) {
			orderList = 0;
		}
	}

}