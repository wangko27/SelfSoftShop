package net.shopxx.action.admin;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.Transient;

import net.shopxx.bean.GoodsParameter;
import net.shopxx.entity.Brand;
import net.shopxx.entity.GoodsAttribute;
import net.shopxx.entity.GoodsType;
import net.shopxx.entity.GoodsAttribute.AttributeType;
import net.shopxx.service.BrandService;
import net.shopxx.service.CacheService;
import net.shopxx.service.GoodsAttributeService;
import net.shopxx.service.GoodsTypeService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 商品类型
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXB9E483EA86B3007D9DF1F8E314D400D2
 * ============================================================================
 */

@ParentPackage("admin")
public class GoodsTypeAction extends BaseAdminAction {

	private static final long serialVersionUID = 8895838200173152426L;

	private GoodsType goodsType;
	private List<Brand> brandList;
	private List<GoodsAttribute> goodsAttributeList;
	private List<GoodsParameter> goodsParameterList;

	@Resource(name = "goodsTypeServiceImpl")
	private GoodsTypeService goodsTypeService;
	@Resource(name = "brandServiceImpl")
	private BrandService brandService;
	@Resource(name = "goodsAttributeServiceImpl")
	private GoodsAttributeService goodsAttributeService;
	@Resource(name = "cacheServiceImpl")
	private CacheService cacheService;

	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		goodsType = goodsTypeService.load(id);
		return INPUT;
	}

	// 列表
	public String list() {
		pager = goodsTypeService.findPager(pager);
		return LIST;
	}

	// 删除
	public String delete() {
		goodsTypeService.delete(ids);
		return ajax(Status.success, "删除成功!");
	}

	// 保存
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "goodsType.name", message = "类型名称不允许为空!") 
		}
	)
	@InputConfig(resultName = "error")
	public String save() {
		if (brandList != null) {
			goodsType.setBrandSet(new HashSet<Brand>(brandList));
		} else {
			goodsType.setBrandSet(null);
		}
		
		if (goodsParameterList != null) {
			for (Iterator<GoodsParameter> iterator = goodsParameterList.iterator(); iterator.hasNext();) {
				GoodsParameter goodsParameter = iterator.next();
				if (goodsParameter == null || StringUtils.isEmpty(goodsParameter.getName())) {
					iterator.remove();
				}
			}
		}
		goodsType.setGoodsParameterList(goodsParameterList);
		goodsTypeService.save(goodsType);
		if (goodsAttributeList != null) {
			for (GoodsAttribute goodsAttribute : goodsAttributeList) {
				if (StringUtils.isEmpty(goodsAttribute.getName()) || goodsAttribute.getAttributeType() == null || (goodsAttribute.getAttributeType() == AttributeType.filter && StringUtils.isEmpty(goodsAttribute.getOptionText()))) {
					continue;
				}
				Integer unusedPropertyIndex = goodsAttributeService.getUnusedPropertyIndex(goodsType);
				if (unusedPropertyIndex != null) {
					goodsAttribute.setGoodsType(goodsType);
					goodsAttribute.setPropertyIndex(unusedPropertyIndex);
					goodsAttributeService.save(goodsAttribute);
				}
			}
		}
		cacheService.flushGoodsListPageCache(getRequest());
		redirectUrl = "goods_type!list.action";
		return SUCCESS;
	}

	// 更新
	@InputConfig(resultName = "error")
	public String update() {
		if (brandList != null) {
			goodsType.setBrandSet(new HashSet<Brand>(brandList));
		} else {
			goodsType.setBrandSet(null);
		}
		
		if (goodsParameterList != null) {
			for (Iterator<GoodsParameter> iterator = goodsParameterList.iterator(); iterator.hasNext();) {
				GoodsParameter goodsParameter = iterator.next();
				if (goodsParameter == null || StringUtils.isEmpty(goodsParameter.getName())) {
					iterator.remove();
				}
			}
		}
		goodsType.setGoodsParameterList(goodsParameterList);
		
		GoodsType persistent = goodsTypeService.load(id);
		if (goodsAttributeList != null) {
			Set<GoodsAttribute> persistentGoodsAttributeSet = persistent.getGoodsAttributeSet();
			for (GoodsAttribute persistentGoodsAttribute : persistentGoodsAttributeSet) {
				if (!goodsAttributeList.contains(persistentGoodsAttribute)) {
					goodsAttributeService.delete(persistentGoodsAttribute);
				}
			}
			for (GoodsAttribute goodsAttribute : goodsAttributeList) {
				if (goodsAttribute == null || StringUtils.isEmpty(goodsAttribute.getName()) || goodsAttribute.getAttributeType() == null || (goodsAttribute.getAttributeType() == AttributeType.filter && StringUtils.isEmpty(goodsAttribute.getOptionText()))) {
					continue;
				}
				if (StringUtils.isNotEmpty(goodsAttribute.getId())) {
					GoodsAttribute persistentGoodsAttribute = goodsAttributeService.load(goodsAttribute.getId());
					BeanUtils.copyProperties(goodsAttribute, persistentGoodsAttribute, new String[] {"id", "createDate", "modifyDate", "goodsType", "propertyIndex"});
					goodsAttributeService.update(persistentGoodsAttribute);
				} else {
					Integer unusedPropertyIndex = goodsAttributeService.getUnusedPropertyIndex(persistent);
					if (unusedPropertyIndex != null) {
						goodsAttribute.setGoodsType(persistent);
						goodsAttribute.setPropertyIndex(unusedPropertyIndex);
						goodsAttributeService.save(goodsAttribute);
					}
				}
			}
		} else {
			Set<GoodsAttribute> persistentGoodsAttributeSet = persistent.getGoodsAttributeSet();
			for (GoodsAttribute persistentGoodsAttribute : persistentGoodsAttributeSet) {
				goodsAttributeService.delete(persistentGoodsAttribute);
			}
		}
		BeanUtils.copyProperties(goodsType, persistent, new String[] {"id", "createDate", "modifyDate", "goodsAttributeSet", "goodsSet"});
		goodsTypeService.update(persistent);
		cacheService.flushGoodsListPageCache(getRequest());
		redirectUrl = "goods_type!list.action";
		return SUCCESS;
	}
	
	// 获取商品属性类型集合
	@Transient
	public List<AttributeType> getAttributeTypeList() {
		return Arrays.asList(AttributeType.values());
	}
	
	// 获取所有商品品牌集合
	@Transient
	public List<Brand> getAllBrandList() {
		return brandService.getAllList();
	}

	public GoodsType getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(GoodsType goodsType) {
		this.goodsType = goodsType;
	}

	public List<Brand> getBrandList() {
		return brandList;
	}

	public void setBrandList(List<Brand> brandList) {
		this.brandList = brandList;
	}

	public List<GoodsAttribute> getGoodsAttributeList() {
		return goodsAttributeList;
	}

	public void setGoodsAttributeList(List<GoodsAttribute> goodsAttributeList) {
		this.goodsAttributeList = goodsAttributeList;
	}

	public List<GoodsParameter> getGoodsParameterList() {
		return goodsParameterList;
	}

	public void setGoodsParameterList(List<GoodsParameter> goodsParameterList) {
		this.goodsParameterList = goodsParameterList;
	}

}