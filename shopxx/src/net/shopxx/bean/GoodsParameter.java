package net.shopxx.bean;

import net.shopxx.util.CommonUtil;

/**
 * Bean类 - 商品参数
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXD7D506B0E8767709FDAC4269652F8E43
 * ============================================================================
 */

public class GoodsParameter implements Comparable<GoodsParameter> {
	
	private String id;// ID
	private String name;// 参数名称
	private Integer orderList;// 排序
	
	public GoodsParameter() {
		this.id = CommonUtil.getUUID();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOrderList() {
		return orderList;
	}

	public void setOrderList(Integer orderList) {
		this.orderList = orderList;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof GoodsParameter) {
			GoodsParameter goodsParameter = (GoodsParameter) obj;
			if (this.getId() == null || goodsParameter.getId() == null) {
				return false;
			} else {
				return (this.getId().equals(goodsParameter.getId()));
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return id == null ? System.identityHashCode(this) : (this.getClass().getName() + this.getId()).hashCode();
	}

	public int compareTo(GoodsParameter goodsParameter) {
		if (goodsParameter.getOrderList() == null) {
			return 1;
		}
		if (this.getOrderList() == null) {
			return -1;
		}
		return this.getOrderList().compareTo(goodsParameter.getOrderList());
	}

}